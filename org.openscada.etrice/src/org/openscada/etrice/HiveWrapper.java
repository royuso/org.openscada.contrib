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

import org.openscada.core.ConnectionInformation;
import org.openscada.da.server.ngp.Exporter;

public class HiveWrapper
{

    private static HiveImpl hive;

    private static Exporter exporter;

    private int counter = 0;

    public HiveWrapper ()
    {
        synchronized ( HiveWrapper.class )
        {
            if ( hive == null )
            {
                hive = createHive ();
                try
                {
                    hive.start ();

                    exporter = new Exporter ( hive, ConnectionInformation.fromURI ( System.getProperty ( "org.openscada.etrice.ngpExporter", "da:ngp://0.0.0.0:2199" ) ) );
                    exporter.start ();

                }
                catch ( final Exception e )
                {
                    throw new RuntimeException ( e );
                }
            }
            this.counter++;
        }
    }

    public HiveImpl getHive ()
    {
        return hive;
    }

    public void dispose ()
    {
        synchronized ( HiveWrapper.class )
        {
            this.counter--;
            if ( this.counter <= 0 && hive != null )
            {
                try
                {
                    exporter.stop ();
                    hive.stop ();
                }
                catch ( final Exception e )
                {
                    e.printStackTrace ();
                }
                hive = null;
                exporter = null;
            }
        }
    }

    private static HiveImpl createHive ()
    {
        return new HiveImpl ();
    }
}
