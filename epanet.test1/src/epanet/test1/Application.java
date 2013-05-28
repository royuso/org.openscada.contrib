package epanet.test1;

import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.addition.epanet.hydraulic.HydraulicSim;
import org.addition.epanet.hydraulic.structures.SimulationNode;
import org.addition.epanet.hydraulic.structures.SimulationTank;
import org.addition.epanet.network.Network;
import org.addition.epanet.network.io.input.InputParser;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

public class Application implements IApplication
{
    private volatile boolean running = true;

    @Override
    public Object start ( final IApplicationContext context ) throws Exception
    {
        final String[] args = (String[])context.getArguments ().get ( IApplicationContext.APPLICATION_ARGS );

        final Logger log = Logger.getLogger ( Application.class.toString () );
        log.setUseParentHandlers ( false );
        log.setLevel ( Level.ALL );
        log.addHandler ( new ConsoleHandler () );

        final Network net = new Network ();

        final InputParser parserINP = InputParser.create ( Network.FileType.INP_FILE, log );
        parserINP.parse ( net, new File ( args[0] ) );

        final HydraulicSim hydSim = new HydraulicSim ( net, log );

        System.out.println ( "INIT" );

        dump ( hydSim );

        System.out.println ( "START" );

        while ( this.running )
        {
            final long time = hydSim.simulateSingleStep ();

            dump ( hydSim );

            Thread.sleep ( time );
        }

        return null;
    }

    private void dump ( final HydraulicSim hydSim )
    {
        for ( final SimulationNode node : hydSim.getnNodes () )
        {
            // System.out.println ( "NodeType: " + node.getClass () );
            if ( node instanceof SimulationTank )
            {
                System.out.println ( node.getId () + " - Volume: " + ( (SimulationTank)node ).getSimVolume () );
            }
        }
    }

    @Override
    public void stop ()
    {
        this.running = false;
    }

}
