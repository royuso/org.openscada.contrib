package org.openscada.contrib.epanet.simulator.exporter.nodes;

import org.addition.epanet.network.structures.Link.StatType;
import org.openscada.utils.beans.AbstractPropertyChange;

public class PumpState extends AbstractPropertyChange
{
    public final static String PROP_STATE = "state";

    public final static String PROP_RUNNING = "running";

    private StatType state;

    private boolean running;

    public boolean isRunning ()
    {
        return this.running;
    }

    protected void setRunning ( final boolean running )
    {
        firePropertyChange ( PROP_RUNNING, this.running, this.running = running );
    }

    protected void setState ( final StatType state )
    {
        firePropertyChange ( PROP_STATE, this.state, this.state = state );
    }

    public StatType getState ()
    {
        return this.state;
    }
}
