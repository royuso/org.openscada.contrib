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

import hudson.model.Result;

import org.openscada.utils.beans.AbstractPropertyChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectState extends AbstractPropertyChange
{

    private static final Logger logger = LoggerFactory.getLogger ( ProjectState.class );

    public static final String PROP_GOOD = "good";

    public static final String PROP_RUNNING = "running";

    private boolean good;

    private boolean running;

    public boolean isGood ()
    {
        return this.good;
    }

    public boolean isRunning ()
    {
        return this.running;
    }

    void setRunning ( final boolean running )
    {
        firePropertyChange ( PROP_RUNNING, this.running, this.running = running );
    }

    void setGood ( final boolean good )
    {
        firePropertyChange ( PROP_GOOD, this.good, this.good = good );
    }

    void setResult ( final Result result )
    {
        logger.info ( "Setting state: {}", result );
        setGood ( result == Result.SUCCESS );
    }
}
