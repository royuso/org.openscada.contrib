package org.openscada.etrice;

import org.openscada.core.ConnectionInformation;
import org.openscada.da.server.ngp.Exporter;

public class HiveWrapper
{

    private static HiveImpl hive;

    private static Exporter exporter;

    private int counter = 0;

    public HiveWrapper ()
    {
        synchronized ( HiveWrapper.class )
        {
            if ( hive == null )
            {
                hive = createHive ();
                try
                {
                    hive.start ();

                    exporter = new Exporter ( hive, ConnectionInformation.fromURI ( "da:ngp://0.0.0.0:2199" ) );
                    exporter.start ();

                }
                catch ( final Exception e )
                {
                    throw new RuntimeException ( e );
                }
            }
            this.counter++;
        }
    }

    public HiveImpl getHive ()
    {
        return hive;
    }

    public void dispose ()
    {
        synchronized ( HiveWrapper.class )
        {
            this.counter--;
            if ( this.counter <= 0 && hive != null )
            {
                try
                {
                    exporter.stop ();
                    hive.stop ();
                }
                catch ( final Exception e )
                {
                    e.printStackTrace ();
                }
                hive = null;
                exporter = null;
            }
        }
    }

    private static HiveImpl createHive ()
    {
        return new HiveImpl ();
    }
}
