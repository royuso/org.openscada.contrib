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

import org.openscada.utils.beans.AbstractPropertyChange;

public class TankState extends AbstractPropertyChange
{
    public final static String PROP_VOLUME = "volume";

    private double volume;

    public void setVolume ( final double volume )
    {
        firePropertyChange ( PROP_VOLUME, this.volume, this.volume = volume );
    }

    public double getVolume ()
    {
        return this.volume;
    }
}
