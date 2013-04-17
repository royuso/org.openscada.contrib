/*
 * This file is part of the openSCADA project
 * 
 * Copyright (C) 2013 Jens Reimann (ctron@dentrassi.de)
 *
 * openSCADA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * only, as published by the Free Software Foundation.
 *
 * openSCADA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License version 3 for more details
 * (a copy is included in the LICENSE file that accompanied this code).
 *
 * You should have received a copy of the GNU Lesser General Public License
 * version 3 along with openSCADA. If not, see
 * <http://opensource.org/licenses/lgpl-3.0.html> for a copy of the LGPLv3 License.
 */

package org.openscada.contrib.da.server.ui.launcher;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.ui.IWorkbenchPage;
import org.openscada.contrib.da.server.exporter.DocumentRoot;
import org.openscada.contrib.da.server.exporter.ExportType;
import org.openscada.contrib.da.server.exporter.ExporterPackage;
import org.openscada.contrib.da.server.exporter.HiveType;
import org.openscada.contrib.da.server.exporter.util.ExporterResourceFactoryImpl;
import org.openscada.core.ConnectionInformation;
import org.openscada.da.core.server.Hive;
import org.openscada.da.server.exporter.Export;
import org.openscada.da.server.exporter.NetExport;
import org.openscada.da.server.exporter.NgpExport;
import org.openscada.ui.databinding.AbstractSelectionHandler;
import org.openscada.ui.databinding.SelectionHelper;
import org.w3c.dom.Document;

public class StartExporter extends AbstractSelectionHandler
{

    @Override
    public Object execute ( final ExecutionEvent event ) throws ExecutionException
    {
        for ( final IFile file : SelectionHelper.iterable ( getSelection (), IFile.class ) )
        {
            try
            {
                processFile ( file );
            }
            catch ( final Exception e )
            {
                throw new ExecutionException ( "Failed to process", e );
            }
        }
        return null;
    }

    private void processFile ( final IFile file ) throws Exception
    {
        final ResourceSetImpl resourceSet = new ResourceSetImpl ();

        resourceSet.getResourceFactoryRegistry ().getExtensionToFactoryMap ().put ( "*", new ExporterResourceFactoryImpl () );

        final Resource resource = resourceSet.createResource ( URI.createURI ( file.getLocationURI ().toString () ) );
        resource.load ( null );

        final DocumentRoot root = (DocumentRoot)EcoreUtil.getObjectByType ( resource.getContents (), ExporterPackage.Literals.DOCUMENT_ROOT );

        for ( final HiveType hive : root.getConfiguration ().getHive () )
        {
            final Hive hiveImpl = createHive ( hive.getRef (), hive.getConfiguration ().getAny () );
            final HiveRunnerPart part = (HiveRunnerPart)getActivePage ().showView ( HiveRunnerPart.VIEW_ID, hive.getRef (), IWorkbenchPage.VIEW_ACTIVATE );
            part.setHive ( hiveImpl );
            for ( final ExportType export : hive.getExport () )
            {
                final Export exporter = createExporter ( hiveImpl, export.getUri () );
                part.addExporter ( exporter );
            }
        }
    }

    private Export createExporter ( final Hive hive, final String uri ) throws Exception
    {
        final ConnectionInformation ci = ConnectionInformation.fromURI ( uri );
        if ( !ci.getInterface ().equals ( "da" ) )
        {
            throw new IllegalArgumentException ( String.format ( "Only the interface 'da' may be used (%s)", ci.getInterface () ) );
        }

        final String driver = ci.getDriver ();
        if ( "ngp".equals ( driver ) )
        {
            return new NgpExport ( hive, ci );
        }
        if ( "net".equals ( driver ) )
        {
            return new NetExport ( hive, ci );
        }

        throw new IllegalArgumentException ( String.format ( "Illegal driver used '%s' (only 'net' and 'ngp' are allowed)", driver ) );
    }

    private Hive createHive ( final String ref, final FeatureMap featureMap ) throws Exception
    {
        final Entry first = featureMap.get ( 0 );

        final XMLResourceImpl x = new XMLResourceImpl ();
        final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ();
        final DocumentBuilder db = dbf.newDocumentBuilder ();
        final Document doc = db.newDocument ();

        // convert ecore to dom
        x.getContents ().add ( (EObject)first.getValue () );
        x.save ( doc, null, null );

        final BundleContextHiveFactory factory = new BundleContextHiveFactory ( Activator.getDefault ().getBundle ().getBundleContext () );
        return factory.createHive ( ref, doc.getDocumentElement () );
    }
}
