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

import java.util.HashSet;
import java.util.Set;

import org.openscada.core.client.AutoReconnectController;
import org.openscada.da.client.Connection;

public class ConnectionManager
{
    private final Set<Connection> connections = new HashSet<> ();

    private final Set<AutoReconnectController> controllers = new HashSet<> ();

    public void addConnection ( final Connection connection, final AutoReconnectController controller )
    {
        this.connections.add ( connection );
        this.controllers.add ( controller );
    }

    public void removeConnection ( final Connection connection, final AutoReconnectController controller )
    {
        this.connections.remove ( connection );
        this.controllers.remove ( controller );
    }

    public void dispose ()
    {
        for ( final AutoReconnectController controller : this.controllers )
        {
            controller.dispose ( false );
        }
        this.controllers.clear ();
        for ( final Connection connection : this.connections )
        {
            connection.dispose ();
        }
        this.connections.clear ();
    }
}
