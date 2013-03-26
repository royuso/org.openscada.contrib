package org.openscada.etrice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.openscada.core.Variant;
import org.openscada.core.server.OperationParameters;
import org.openscada.da.core.WriteResult;
import org.openscada.da.core.server.Hive;
import org.openscada.da.server.browser.common.FolderCommon;
import org.openscada.da.server.browser.common.query.GroupFolder;
import org.openscada.da.server.browser.common.query.IDNameProvider;
import org.openscada.da.server.browser.common.query.InvisibleStorage;
import org.openscada.da.server.browser.common.query.ItemDescriptor;
import org.openscada.da.server.browser.common.query.SplitGroupProvider;
import org.openscada.da.server.browser.common.query.SplitNameProvider;
import org.openscada.da.server.common.HiveServiceRegistry;
import org.openscada.da.server.common.chain.DataItemInputOutputChained;
import org.openscada.da.server.common.configuration.ConfigurableHive;
import org.openscada.da.server.common.impl.HiveCommon;
import org.openscada.etrice.ServerItemProtocol.ServerItemProtocolReplPort;
import org.openscada.utils.concurrent.FutureTask;
import org.openscada.utils.concurrent.NotifyFuture;

public class HiveImpl extends HiveCommon implements Hive, ConfigurableHive, HiveServiceRegistry
{

    private final InvisibleStorage storage;

    private FolderCommon rootFolder;

    public HiveImpl ()
    {

        setRootFolder ( this.rootFolder = new FolderCommon () );

        this.storage = new InvisibleStorage ();

        final Map<String, Variant> attributes = new HashMap<String, Variant> ( 1 );
        final GroupFolder allItemsFolder = new GroupFolder ( new SplitGroupProvider ( new IDNameProvider (), "\\.", 0, 2 ), new SplitNameProvider ( new IDNameProvider (), "\\.", 0, 2, "." ) );
        this.storage.addChild ( allItemsFolder );
        this.rootFolder.add ( "all", allItemsFolder, attributes );
    }

    private final Map<String, DataItemInputOutputChained> items = new HashMap<String, DataItemInputOutputChained> ();

    public DataItemInputOutputChained registerItem ( final String itemId, final ServerItemProtocolReplPort port )
    {
        DataItemInputOutputChained item = this.items.get ( itemId );
        if ( item != null )
        {
            return item;
        }

        item = new DataItemInputOutputChained ( itemId, getOperationService () ) {

            @Override
            protected NotifyFuture<WriteResult> startWriteCalculatedValue ( final Variant value, final OperationParameters operationParameters )
            {

                return processWrite ( value, operationParameters, port );

            }
        };

        System.out.println ( "Create new item: " + itemId );

        registerItem ( item );

        final Map<String, Variant> attributes = new HashMap<String, Variant> ( 1 );
        this.storage.added ( new ItemDescriptor ( item, attributes ) );

        this.items.put ( itemId, item );
        return item;
    }

    protected NotifyFuture<WriteResult> processWrite ( final Variant value, final OperationParameters operationParameters, final ServerItemProtocolReplPort port )
    {

        final FutureTask<WriteResult> future = new FutureTask<WriteResult> ( new Callable<WriteResult> () {
            @Override
            public WriteResult call () throws Exception
            {
                port.writeCall ( value.asString ( null ) );
                return WriteResult.OK;
            }
        } );

        getOperationService ().execute ( future );
        return future;
    }

    @Override
    public String getHiveId ()
    {
        return "org.openscada.etrice";
    }

}
