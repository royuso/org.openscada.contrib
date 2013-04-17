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
