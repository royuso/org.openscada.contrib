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

import org.addition.epanet.hydraulic.structures.SimulationPump;
import org.addition.epanet.network.structures.Link.StatType;

public class PumpState extends LinkState
{
    public final static String PROP_RUNNING = "running";

    public final static String PROP_SETTING = "setting";

    private boolean running;

    private double setting;

    public boolean isRunning ()
    {
        return this.running;
    }

    protected void setRunning ( final boolean running )
    {
        firePropertyChange ( PROP_RUNNING, this.running, this.running = running );
    }

    public double getSetting ()
    {
        return this.setting;
    }

    protected void setSetting ( final double setting )
    {
        firePropertyChange ( PROP_SETTING, this.setting, this.setting = setting );
    }

    public void update ( final SimulationPump pump )
    {
        super.update ( pump );
        setRunning ( pump.getSimStatus () == StatType.OPEN );
        setSetting ( pump.getSimSetting () );
    }
}
