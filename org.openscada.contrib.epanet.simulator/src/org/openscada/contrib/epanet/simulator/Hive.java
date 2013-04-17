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

package org.openscada.contrib.epanet.simulator;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.addition.epanet.hydraulic.HydraulicSim;
import org.addition.epanet.network.Network;
import org.addition.epanet.network.Network.FileType;
import org.addition.epanet.network.io.input.InputParser;
import org.openscada.contrib.epanet.simulator.exporter.HydraulicExporter;
import org.openscada.da.server.browser.common.FolderCommon;
import org.openscada.da.server.common.ValidationStrategy;
import org.openscada.da.server.common.impl.HiveCommon;
import org.openscada.da.server.epanet.simulator.configuration.ConfigurationType;
import org.openscada.da.server.epanet.simulator.configuration.TypeType;
import org.openscada.da.server.epanet.simulator.configuration.util.ConfigurationResourceImpl;
import org.openscada.utils.concurrent.NamedThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.w3c.dom.Node;

public class Hive extends HiveCommon
{
    private final static Logger logger = LoggerFactory.getLogger ( Hive.class );

    private final java.util.logging.Logger julLogger;

    private Network network;

    private HydraulicSim hydSim;

    private FolderCommon rootFolder;;

    private HydraulicExporter hydExporter;

    private ScheduledExecutorService executor;

    private final Runnable runOnce;

    public Hive ( final Node node ) throws Exception
    {
        this.julLogger = java.util.logging.Logger.getLogger ( Hive.class.toString () );
        this.julLogger.setUseParentHandlers ( false );
        this.julLogger.addHandler ( new SLF4JBridgeHandler () );

        this.runOnce = new Runnable () {

            @Override
            public void run ()
            {
                runOnce ();
            }
        };

        setRootFolder ( this.rootFolder = new FolderCommon () );
        setValidatonStrategy ( ValidationStrategy.GRANT_ALL );

        configure ( node );
    }

    @Override
    public FolderCommon getRootFolder ()
    {
        return this.rootFolder;
    }

    private void configure ( final Node node ) throws Exception
    {
        final ConfigurationResourceImpl res = new ConfigurationResourceImpl ( null );
        res.load ( node, null );
        final ConfigurationType cfg = (ConfigurationType)res.getContents ().get ( 0 );

        final String fileName = cfg.getFile ();
        final TypeType type = cfg.getType ();
        FileType fileType;
        if ( type == null )
        {
            fileType = FileType.INP_FILE;
        }
        else
        {
            fileType = FileType.valueOf ( type.getLiteral () + "_FILE" );
        }

        final InputParser parser = InputParser.create ( fileType, this.julLogger );
        this.network = new Network ();
        parser.parse ( this.network, new File ( fileName ) );

        // run infinite
        this.network.getPropertiesMap ().setDuration ( Long.MAX_VALUE );

        this.hydSim = new HydraulicSim ( this.network, this.julLogger );

        this.hydExporter = new HydraulicExporter ( this.hydSim, this );
    }

    @Override
    public String getHiveId ()
    {
        return "org.openscada.contrib.epanet.simulator";
    }

    @Override
    public void start () throws Exception
    {
        this.executor = Executors.newSingleThreadScheduledExecutor ( new NamedThreadFactory ( "org.openscada.contrib.epanet.simulator/HydraulicSimulator" ) );
        this.executor.execute ( this.runOnce );

        super.start ();

        this.hydExporter.start ();
    }

    protected void runOnce ()
    {
        try
        {

            final long delay = (long) ( this.hydSim.simulateSingleStep () * 1000 * 0.25 );
            logger.trace ( "Delay: {}", delay );

            long target = System.currentTimeMillis () + delay;

            this.hydExporter.update ();
            target -= System.currentTimeMillis ();

            if ( target <= 0 )
            {
                this.executor.execute ( this.runOnce );
            }
            else
            {
                this.executor.schedule ( this.runOnce, target, TimeUnit.MILLISECONDS );
            }
        }
        catch ( final Exception e )
        {
            logger.warn ( "Failed to run simulation step", e );
        }
    }

    @Override
    public void stop () throws Exception
    {
        this.hydExporter.stop ();

        super.stop ();

        if ( this.executor != null )
        {
            this.executor.shutdownNow ();
            this.executor = null;
        }
    }

}
