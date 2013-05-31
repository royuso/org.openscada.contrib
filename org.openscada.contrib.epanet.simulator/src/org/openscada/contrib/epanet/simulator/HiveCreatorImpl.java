/**
 * This module is an EPANET simulation driver for use with openSCADA DA
 * 
 * Copyright (C) 2013 Jens Reimann (ctron@dentrassi.de)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.openscada.contrib.epanet.simulator;

import org.openscada.da.core.server.Hive;
import org.openscada.da.core.server.HiveCreator;
import org.openscada.da.server.epanet.simulator.configuration.ConfigurationType;

public class HiveCreatorImpl implements HiveCreator
{

    @Override
    public Hive createHive ( final String reference, final Object configuration ) throws Exception
    {
        if ( org.openscada.contrib.epanet.simulator.Hive.class.getName ().equals ( reference ) )
        {
            if ( configuration instanceof ConfigurationType )
            {
                return new org.openscada.contrib.epanet.simulator.Hive ( (ConfigurationType)configuration );
            }
        }
        return null;
    }

}
