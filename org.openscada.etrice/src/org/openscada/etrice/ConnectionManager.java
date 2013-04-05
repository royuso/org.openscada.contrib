/*
 * This file is part of the openSCADA project
 * 
 * Copyright (C) 2013 Jens Reimann (ctron@dentrassi.de)
 *
 * openSCADA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * only, as published by the Free Software Foundation.
 *
 * openSCADA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License version 3 for more details
 * (a copy is included in the LICENSE file that accompanied this code).
 *
 * You should have received a copy of the GNU Lesser General Public License
 * version 3 along with openSCADA. If not, see
 * <http://opensource.org/licenses/lgpl-3.0.html> for a copy of the LGPLv3 License.
 */

package org.openscada.etrice;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.openscada.core.ConnectionInformation;
import org.openscada.core.Variant;
import org.openscada.core.client.AutoReconnectController;
import org.openscada.da.client.Connection;
import org.openscada.da.client.DataItem;
import org.openscada.da.client.DataItemValue;
import org.openscada.da.client.ItemManager;
import org.openscada.da.client.ItemManagerImpl;
import org.openscada.da.client.ngp.ConnectionImpl;
import org.openscada.etrice.ClientItemProtocol.ClientItemProtocolPort;

public class ConnectionManager
{

    private static class ConnectionHolder
    {
        private final Connection connection;

        private final AutoReconnectController autoReconnectController;

        private final ItemManager itemManager;

        public ConnectionHolder ( final Connection connection )
        {
            this.connection = connection;
            this.autoReconnectController = new AutoReconnectController ( connection );
            this.itemManager = new ItemManagerImpl ( connection );
        }

        public void connect ()
        {
            this.autoReconnectController.connect ();
        }

        public Connection getConnection ()
        {
            return this.connection;
        }

        public ItemManager getItemManager ()
        {
            return this.itemManager;
        }

        public void dispose ()
        {
            this.autoReconnectController.dispose ();
            this.connection.dispose ();
        }
    }

    private final Map<String, ConnectionHolder> connections = new HashMap<String, ConnectionHolder> ();

    public void start ()
    {
    }

    public synchronized void stop ()
    {
        for ( final ConnectionHolder c : this.connections.values () )
        {
            c.dispose ();
        }
        this.connections.clear ();
    }

    protected synchronized ConnectionHolder getConnection ( final String connectionUri ) throws Exception
    {
        ConnectionHolder c = this.connections.get ( connectionUri );
        if ( c != null )
        {
            return c;
        }

        final ConnectionImpl con = new ConnectionImpl ( ConnectionInformation.fromURI ( connectionUri ) );

        c = new ConnectionHolder ( con );

        if ( c != null )
        {
            this.connections.put ( connectionUri, c );
            c.connect ();
        }

        return c;
    }

    private static class ItemWrapper implements Observer
    {
        private final ClientItemProtocolPort item;

        public ItemWrapper ( final ClientItemProtocolPort item )
        {
            this.item = item;
        }

        @Override
        public void update ( final Observable o, final Object arg )
        {
            if ( arg instanceof DataItemValue )
            {
                final Variant v = ( (DataItemValue)arg ).getValue ();
                if ( v == null || v.isNull () )
                {
                    this.item.updateValue ( null );
                }
                else
                {
                    this.item.updateValue ( v.asString ( null ) );
                }
            }
        }
    }

    public DataItem getItem ( final String connectionUri, final String itemId, final ClientItemProtocolPort item )
    {
        ConnectionHolder connection;
        try
        {
            connection = getConnection ( connectionUri );
        }
        catch ( final Exception e )
        {
            return null;
        }

        if ( connection == null )
        {
            return null;
        }

        final DataItem di = new DataItem ( itemId );

        di.addObserver ( new ItemWrapper ( item ) );

        di.register ( connection.getItemManager () );
        return di;
    }

    public void write ( final String connectionUri, final String itemId, final Variant value )
    {
        ConnectionHolder c;
        try
        {
            c = getConnection ( connectionUri );
        }
        catch ( final Exception e )
        {
            return;
        }

        if ( c != null )
        {
            c.getConnection ().write ( itemId, value, null, null );
        }
    }
}
