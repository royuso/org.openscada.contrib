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
