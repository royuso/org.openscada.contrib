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
    }
}
