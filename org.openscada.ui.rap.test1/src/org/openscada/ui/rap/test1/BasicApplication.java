package org.openscada.ui.rap.test1;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.rap.rwt.application.Application;
import org.eclipse.rap.rwt.application.ApplicationConfiguration;
import org.eclipse.rap.rwt.client.WebClient;

public class BasicApplication implements ApplicationConfiguration
{

    @Override
    public void configure ( final Application application )
    {
        final Map<String, String> properties = new HashMap<String, String> ();
        properties.put ( WebClient.PAGE_TITLE, "Hello openSCADA" );
        application.addEntryPoint ( "/rap1", BasicEntryPoint.class, properties );
    }

}
