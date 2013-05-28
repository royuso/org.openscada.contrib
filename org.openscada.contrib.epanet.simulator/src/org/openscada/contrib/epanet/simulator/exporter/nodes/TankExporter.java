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

import org.addition.epanet.hydraulic.structures.SimulationTank;
import org.openscada.contrib.epanet.simulator.exporter.ExporterContext;
import org.openscada.contrib.epanet.simulator.exporter.ExporterObject;
import org.openscada.da.server.common.exporter.ObjectExporter;

public class TankExporter implements ExporterObject
{
    private final SimulationTank tank;

    private final TankState tankState;

    private ObjectExporter exporter;

    public TankExporter ( final SimulationTank tank )
    {
        this.tank = tank;
        this.tankState = new TankState ();
    }

    @Override
    public void start ( final ExporterContext context )
    {
        this.exporter = new ObjectExporter ( context.getTankFactory (), true, true, this.tank.getId () + "." );
        this.exporter.attachTarget ( this.tankState );
    }

    @Override
    public void stop ()
    {
        this.exporter.dispose ();
    }

    @Override
    public void update ()
    {
        this.tankState.setVolume ( this.tank.getSimVolume () );
        this.tankState.setHead ( this.tank.getSimHead () );
    }
}
