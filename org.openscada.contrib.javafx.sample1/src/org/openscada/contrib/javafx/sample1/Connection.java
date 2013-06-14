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

package org.openscada.contrib.javafx.sample1;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import org.openscada.core.ConnectionInformation;
import org.openscada.core.client.AutoReconnectController;
import org.openscada.core.client.ConnectionState;
import org.openscada.core.client.ConnectionStateListener;
import org.openscada.da.client.ItemManagerImpl;
import org.openscada.da.client.ngp.ConnectionImpl;

public class Connection
{
    private String connectionString;

    private ConnectionImpl connection;

    private final ObservableList<Item> items;

    private ItemManagerImpl itemManager;

    private AutoReconnectController controller;

    public Connection ()
    {
        this.items = FXCollections.observableArrayList ();
        this.items.addListener ( new ListChangeListener<Item> () {

            @Override
            public void onChanged ( final javafx.collections.ListChangeListener.Change<? extends Item> change )
            {
                while ( change.next () )
                {
                    if ( change.wasAdded () )
                    {
                        for ( final Item item : change.getAddedSubList () )
                        {
                            itemAdded ( item );
                        }
                    }
                    if ( change.wasRemoved () )
                    {
                        for ( final Item item : change.getRemoved () )
                        {
                            itemRemoved ( item );
                        }
                    }
                }
            }
        } );
    }

    protected void itemAdded ( final Item item )
    {
        if ( this.connection != null )
        {
            item.register ( this.connection, this.itemManager );
        }
    }

    protected void itemRemoved ( final Item item )
    {
        if ( this.connection != null )
        {
            item.unregister ();
        }
    }

    public void setConnectionString ( final String connectionString ) throws Exception
    {
        disposeConnection ();

        this.connectionString = connectionString;

        createConnection ( connectionString );
    }

    private void createConnection ( final String connectionString ) throws Exception
    {

        this.connection = new ConnectionImpl ( ConnectionInformation.fromURI ( this.connectionString ) );
        connection.addConnectionStateListener(new ConnectionStateListener() {
			
			@Override
			public void stateChange(org.openscada.core.client.Connection connection, ConnectionState state,
					Throwable e) {
				System.out.println(state);
				if ( e != null )
					e.printStackTrace();
			}
		});
        this.controller = new AutoReconnectController ( this.connection, 10 * 1000 );
        this.controller.connect ();
        this.itemManager = new ItemManagerImpl ( this.connection );

        Application.connectionManager.addConnection ( this.connection, this.controller );

        // realize items
        for ( final Item item : this.items )
        {
            item.register ( this.connection, this.itemManager );
        }
    }

    private void disposeConnection ()
    {
        if ( this.connection == null )
        {
            return;
        }

        for ( final Item item : this.items )
        {
            item.unregister ();
        }

        Application.connectionManager.removeConnection ( this.connection, this.controller );

        this.itemManager = null;
        this.controller.dispose ( true );
        this.controller = null;
        this.connection.dispose ();
        this.connection = null;
    }

    public String getConnectionString ()
    {
        return this.connectionString;
    }

    public List<Item> getItems ()
    {
        return this.items;
    }

    @Override
    protected void finalize () throws Throwable
    {
        disposeConnection ();
        super.finalize ();
    }
}
