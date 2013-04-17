package org.openscada.contrib.epanet.simulator.exporter;


public interface ExporterObject
{

    public void start ( ExporterContext context );

    public void stop ();

    public void update ();

}
