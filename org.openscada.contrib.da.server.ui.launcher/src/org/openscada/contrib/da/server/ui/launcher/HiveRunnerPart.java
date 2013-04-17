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

package org.openscada.contrib.da.server.ui.launcher;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;
import org.openscada.da.core.server.Hive;
import org.openscada.da.server.exporter.Export;

public class HiveRunnerPart extends ViewPart
{
    public static final String VIEW_ID = "org.openscada.contrib.da.server.ui.launcher.HiveRunner";

    private final HiveRunnerViewer viewer;

    public HiveRunnerPart ()
    {
        this.viewer = new HiveRunnerViewer ();
    }

    @Override
    public void createPartControl ( final Composite parent )
    {
        this.viewer.create ( parent );
    }

    @Override
    public void setFocus ()
    {
        this.viewer.setFocus ();
    }

    public void setHive ( final Hive hive )
    {
        this.viewer.setHive ( hive );
        if ( hive != null )
        {
            setContentDescription ( hive.toString () );
        }
    }

    public void addExporter ( final Export exporter )
    {
        this.viewer.addExporter ( exporter );
    }

}
