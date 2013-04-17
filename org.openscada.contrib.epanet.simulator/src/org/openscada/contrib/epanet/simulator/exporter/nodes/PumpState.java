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

package org.openscada.contrib.epanet.simulator.exporter.nodes;

import org.addition.epanet.network.structures.Link.StatType;
import org.openscada.utils.beans.AbstractPropertyChange;

public class PumpState extends AbstractPropertyChange
{
    public final static String PROP_STATE = "state";

    public final static String PROP_RUNNING = "running";

    private StatType state;

    private boolean running;

    public boolean isRunning ()
    {
        return this.running;
    }

    protected void setRunning ( final boolean running )
    {
        firePropertyChange ( PROP_RUNNING, this.running, this.running = running );
    }

    protected void setState ( final StatType state )
    {
        firePropertyChange ( PROP_STATE, this.state, this.state = state );
    }

    public StatType getState ()
    {
        return this.state;
    }
}
