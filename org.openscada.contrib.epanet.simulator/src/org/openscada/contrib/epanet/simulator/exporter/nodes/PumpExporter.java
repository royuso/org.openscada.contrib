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
import org.openscada.contrib.epanet.simulator.exporter.ExporterContext;
import org.openscada.contrib.epanet.simulator.exporter.ExporterObject;
import org.openscada.core.Variant;
import org.openscada.da.server.common.DataItemCommand;
import org.openscada.da.server.common.DataItemCommand.Listener;
import org.openscada.da.server.common.exporter.ObjectExporter;

public class PumpExporter implements ExporterObject
{
    private final SimulationPump pump;

    private final PumpState pumpState;

    private ObjectExporter exporter;

    public PumpExporter ( final SimulationPump pump )
    {
        this.pump = pump;
        this.pumpState = new PumpState ();
    }

    @Override
    public void start ( final ExporterContext context )
    {
        this.exporter = new ObjectExporter ( context.getPumpFactory (), false, true, this.pump.getLink ().getId () + "." );
        this.exporter.attachTarget ( this.pumpState );

        final DataItemCommand startCommand = context.getPumpFactory ().createCommand ( this.pump.getLink ().getId () + ".start", null );
        startCommand.addListener ( new Listener () {

            @Override
            public void command ( final Variant value ) throws Exception
            {
                startPump ();
            }
        } );
        final DataItemCommand stopCommand = context.getPumpFactory ().createCommand ( this.pump.getLink ().getId () + ".stop", null );
        stopCommand.addListener ( new Listener () {

            @Override
            public void command ( final Variant value ) throws Exception
            {
                stopPump ();
            }
        } );
    }

    protected void startPump ()
    {
        this.pump.setSimStatus ( StatType.OPEN );
    }

    protected void stopPump ()
    {
        this.pump.setSimStatus ( StatType.CLOSED );
    }

    @Override
    public void stop ()
    {
        this.exporter.dispose ();
    }

    @Override
    public void update ()
    {
        this.pumpState.setState ( this.pump.getSimStatus () );
        this.pumpState.setRunning ( this.pump.getSimStatus () == StatType.OPEN );
    }
}
