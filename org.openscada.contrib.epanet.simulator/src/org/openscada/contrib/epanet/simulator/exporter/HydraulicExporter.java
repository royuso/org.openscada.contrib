package org.openscada.contrib.epanet.simulator.exporter;

import java.util.LinkedList;
import java.util.List;

import org.addition.epanet.hydraulic.HydraulicSim;
import org.addition.epanet.hydraulic.structures.SimulationNode;
import org.addition.epanet.hydraulic.structures.SimulationPump;
import org.addition.epanet.hydraulic.structures.SimulationTank;
import org.openscada.contrib.epanet.simulator.Hive;
import org.openscada.contrib.epanet.simulator.exporter.nodes.PumpExporter;
import org.openscada.contrib.epanet.simulator.exporter.nodes.TankExporter;
import org.openscada.da.server.common.item.factory.DefaultChainItemFactory;

public class HydraulicExporter
{

    private final HydraulicSim sim;

    private DefaultChainItemFactory itemFactory;

    private final Hive hive;

    private DefaultChainItemFactory tankItemFactory;

    private final List<ExporterObject> exporters = new LinkedList<ExporterObject> ();

    private DefaultChainItemFactory pumpsItemFactory;

    public HydraulicExporter ( final HydraulicSim hydSim, final Hive hive )
    {
        this.sim = hydSim;
        this.hive = hive;

        for ( final SimulationPump pump : hydSim.getnPumps () )
        {
            createPump ( pump );
        }

        for ( final SimulationNode node : hydSim.getnNodes () )
        {
            if ( node instanceof SimulationTank )
            {
                createTank ( (SimulationTank)node );
            }
        }
    }

    private void createPump ( final SimulationPump pump )
    {
        this.exporters.add ( new PumpExporter ( pump ) );
    }

    private void createTank ( final SimulationTank tank )
    {
        this.exporters.add ( new TankExporter ( tank ) );
    }

    public void start ()
    {
        this.itemFactory = new DefaultChainItemFactory ( this.hive, this.hive.getRootFolder (), "hydraulic", "hydraulic" );
        this.tankItemFactory = this.itemFactory.createSubFolderFactory ( "tanks" );
        this.pumpsItemFactory = this.itemFactory.createSubFolderFactory ( "pumps" );

        final ExporterContext context = new ExporterContext ( this.tankItemFactory, this.pumpsItemFactory );

        for ( final ExporterObject exporter : this.exporters )
        {
            exporter.start ( context );
        }
    }

    public void stop ()
    {
        for ( final ExporterObject exporter : this.exporters )
        {
            exporter.stop ();
        }
        this.itemFactory.dispose ();
    }

    public void update ()
    {
        for ( final ExporterObject exporter : this.exporters )
        {
            exporter.update ();
        }
    }

}
