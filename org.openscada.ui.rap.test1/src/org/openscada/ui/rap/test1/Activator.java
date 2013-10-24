package org.openscada.ui.rap.test1;

import java.util.Dictionary;
import java.util.Hashtable;

import org.eclipse.scada.core.ConnectionInformation;
import org.eclipse.scada.core.client.DriverFactory;
import org.eclipse.scada.core.connection.provider.ConnectionRequest;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator
{

    public static Activator INSTANCE;

    private BundleContext context;

    private ServiceRegistration<ConnectionRequest> handle;

    @Override
    public void start ( final BundleContext context ) throws Exception
    {
        INSTANCE = this;
        this.context = context;

        final ConnectionRequest request = new ConnectionRequest ( "connection.da", ConnectionInformation.fromURI ( "da:ngp://admin:admin12@demo.openscada.org:2101" ), 10000, true );

        final Dictionary<String, String> properties = new Hashtable<String, String> ();
        properties.put ( DriverFactory.DRIVER_NAME, request.getConnectionInformation ().getDriver () );
        properties.put ( DriverFactory.INTERFACE_NAME, request.getConnectionInformation ().getInterface () );

        this.handle = context.registerService ( ConnectionRequest.class, request, properties );
    }

    @Override
    public void stop ( final BundleContext context ) throws Exception
    {
        this.handle.unregister ();
        INSTANCE = null;
    }

    public static Activator getDefault ()
    {
        return INSTANCE;
    }

    public BundleContext getContext ()
    {
        return this.context;
    }

}
