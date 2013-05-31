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

import org.openscada.da.server.epanet.simulator.configuration.ConfigurationPackage;
import org.openscada.utils.init.Initializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelIntializerImpl implements Initializer
{

    private final static Logger logger = LoggerFactory.getLogger ( ModelIntializerImpl.class );

    @Override
    public void initialize ( final Object type )
    {
        if ( type.equals ( "emf" ) )
        {
            logger.info ( "Initializing model" );
            ConfigurationPackage.eINSTANCE.eClass ();
            logger.info ( "Initialized model: {}", ConfigurationPackage.eNS_URI );
        }
    }

}
