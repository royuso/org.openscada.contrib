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

import org.addition.epanet.hydraulic.structures.SimulationValve;
import org.addition.epanet.network.structures.Link.StatType;
import org.openscada.contrib.epanet.simulator.exporter.ExporterContext;
import org.openscada.contrib.epanet.simulator.exporter.ExporterObject;
import org.openscada.core.Variant;
import org.openscada.da.server.common.DataItemCommand;
import org.openscada.da.server.common.DataItemCommand.Listener;
import org.openscada.da.server.common.exporter.ObjectExporter;

public class ValveExporter implements ExporterObject
{
    private final SimulationValve valve;

    private final ValveState valveState;

    private ObjectExporter exporter;

    public ValveExporter ( final SimulationValve valve )
    {
        this.valve = valve;
        this.valveState = new ValveState ();
    }

    @Override
    public void start ( final ExporterContext context )
    {
        this.exporter = new ObjectExporter ( context.getValveFactory (), false, true, this.valve.getLink ().getId () + "." );
        this.exporter.attachTarget ( this.valveState );

        final DataItemCommand startCommand = context.getValveFactory ().createCommand ( this.valve.getLink ().getId () + ".open", null );
        startCommand.addListener ( new Listener () {

            @Override
            public void command ( final Variant value ) throws Exception
            {
                openValve ();
            }
        } );
        final DataItemCommand stopCommand = context.getValveFactory ().createCommand ( this.valve.getLink ().getId () + ".close", null );
        stopCommand.addListener ( new Listener () {

            @Override
            public void command ( final Variant value ) throws Exception
            {
                closeValve ();
            }
        } );
    }

    protected void openValve ()
    {
        this.valve.setSimStatus ( StatType.OPEN );
    }

    protected void closeValve ()
    {
        this.valve.setSimStatus ( StatType.CLOSED );
    }

    @Override
    public void stop ()
    {
        this.exporter.dispose ();
    }

    @Override
    public void update ()
    {
        this.valveState.update ( this.valve );
    }
}
