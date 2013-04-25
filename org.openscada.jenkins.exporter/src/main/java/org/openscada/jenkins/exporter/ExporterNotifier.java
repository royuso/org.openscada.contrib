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

package org.openscada.jenkins.exporter;

import hudson.Extension;
import hudson.model.TaskListener;
import hudson.model.Run;
import hudson.model.listeners.RunListener;
import hudson.tasks.BuildStepMonitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Extension
public class ExporterNotifier extends RunListener<Run<?, ?>>
{

    private final static Logger logger = LoggerFactory.getLogger ( ExporterNotifier.class );

    public BuildStepMonitor getRequiredMonitorService ()
    {
        return BuildStepMonitor.BUILD;
    }

    @Override
    public void onStarted ( final Run<?, ?> r, final TaskListener listener )
    {
        logger.debug ( "Started build: {}", r.getParent ().getName () );

        final ProjectState project = Exporter.get ().getManager ().getState ( r.getParent ().getFullName () );
        if ( project != null )
        {
            new ProjectStateUpdater ( project ).update ( r );
        }

        super.onStarted ( r, listener );
    }

    @Override
    public void onCompleted ( final Run<?, ?> r, final TaskListener listener )
    {
        logger.debug ( "Build completed: {}", r, r.getResult () );

        final ProjectState project = Exporter.get ().getManager ().getState ( r.getParent ().getFullName () );
        if ( project != null )
        {
            new ProjectStateUpdater ( project ).update ( r );
        }

        super.onCompleted ( r, listener );
    }

    @Override
    public void onDeleted ( final Run<?, ?> r )
    {
        logger.debug ( "Build deleted: {}", r );
        super.onDeleted ( r );
    }
}
