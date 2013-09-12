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

import org.addition.epanet.hydraulic.structures.SimulationLink;
import org.addition.epanet.network.structures.Link.StatType;
import org.eclipse.scada.utils.beans.AbstractPropertyChange;

public class LinkState extends AbstractPropertyChange
{

    public static final String PROP_STATE = "state";

    public static final String PROP_FLOW = "flow";

    private StatType state;

    private double flow;

    protected void setState ( final StatType state )
    {
        firePropertyChange ( PROP_STATE, this.state, this.state = state );
    }

    public StatType getState ()
    {
        return this.state;
    }

    public double getFlow ()
    {
        return this.flow;
    }

    protected void setFlow ( final double flow )
    {
        firePropertyChange ( PROP_FLOW, this.flow, this.flow = flow );
    }

    public void update ( final SimulationLink link )
    {
        setState ( link.getSimStatus () );
        setFlow ( link.getSimFlow () );
    }

}