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

package org.openscada.contrib.epanet.simulator.exporter;

import org.openscada.da.server.common.item.factory.DefaultChainItemFactory;
import org.openscada.da.server.common.item.factory.ItemFactory;

public class ExporterContext
{

    private final ItemFactory tankFactory;

    private final ItemFactory pumpFactory;

    private final ItemFactory nodeFactory;

    private final ItemFactory valveFactory;

    private final ItemFactory linkFactory;

    public ExporterContext ( final DefaultChainItemFactory itemFactory )
    {
        this.tankFactory = itemFactory.createSubFolderFactory ( "tanks" );
        this.pumpFactory = itemFactory.createSubFolderFactory ( "pumps" );
        this.nodeFactory = itemFactory.createSubFolderFactory ( "nodes" );
        this.valveFactory = itemFactory.createSubFolderFactory ( "valves" );
        this.linkFactory = itemFactory.createSubFolderFactory ( "links" );
    }

    public ItemFactory getTankFactory ()
    {
        return this.tankFactory;
    }

    public ItemFactory getPumpFactory ()
    {
        return this.pumpFactory;
    }

    public ItemFactory getNodeFactory ()
    {
        return this.nodeFactory;
    }

    public ItemFactory getLinkFactory ()
    {
        return this.linkFactory;
    }

    public ItemFactory getValveFactory ()
    {
        return this.valveFactory;
    }
}
