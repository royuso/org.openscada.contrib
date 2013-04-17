package org.openscada.contrib.epanet.simulator;

import org.openscada.da.core.server.Hive;
import org.openscada.da.server.common.HiveCreator;
import org.w3c.dom.Node;

public class HiveCreatorImpl implements HiveCreator
{

    @Override
    public Hive createHive ( final String reference, final Node node ) throws Exception
    {
        if ( org.openscada.contrib.epanet.simulator.Hive.class.getName ().equals ( reference ) )
        {
            return new org.openscada.contrib.epanet.simulator.Hive ( node );
        }
        return null;
    }

}
