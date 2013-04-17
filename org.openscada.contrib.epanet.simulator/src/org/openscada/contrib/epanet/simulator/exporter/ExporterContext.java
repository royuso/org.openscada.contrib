package org.openscada.contrib.epanet.simulator.exporter;

import org.openscada.da.server.common.item.factory.ItemFactory;

public class ExporterContext
{

    private final ItemFactory tankFactory;

    private final ItemFactory pumpFactory;

    public ExporterContext ( final ItemFactory tankFactory, final ItemFactory pumpFactory )
    {
        this.tankFactory = tankFactory;
        this.pumpFactory = pumpFactory;
    }

    public ItemFactory getTankFactory ()
    {
        return this.tankFactory;
    }

    public ItemFactory getPumpFactory ()
    {
        return this.pumpFactory;
    }
}
