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


public class ConnectionManagerWrapper
{

    private static ConnectionManager connectionManager;

    private int counter = 0;

    public ConnectionManagerWrapper ()
    {
        synchronized ( ConnectionManagerWrapper.class )
        {
            if ( connectionManager == null )
            {
                connectionManager = createConnectionManager ();
                try
                {
                    connectionManager.start ();
                }
                catch ( final Exception e )
                {
                    throw new RuntimeException ( e );
                }
            }
            this.counter++;
        }
    }

    public ConnectionManager getConnectionManager ()
    {
        return connectionManager;
    }

    public void dispose ()
    {
        synchronized ( ConnectionManagerWrapper.class )
        {
            this.counter--;
            if ( this.counter <= 0 && connectionManager != null )
            {
                try
                {
                    connectionManager.stop ();
                }
                catch ( final Exception e )
                {
                    e.printStackTrace ();
                }
                connectionManager = null;
            }
        }
    }

    private static ConnectionManager createConnectionManager ()
    {
        return new ConnectionManager ();
    }
}
