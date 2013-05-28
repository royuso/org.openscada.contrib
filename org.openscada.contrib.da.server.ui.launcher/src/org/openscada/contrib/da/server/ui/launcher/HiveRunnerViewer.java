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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.statushandlers.StatusManager;
import org.openscada.da.core.server.Hive;
import org.openscada.da.server.exporter.Export;
import org.openscada.ui.utils.status.StatusHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HiveRunnerViewer
{

    private final static Logger logger = LoggerFactory.getLogger ( HiveRunnerViewer.class );

    private Button start;

    private Button stop;

    private Hive hive;

    private Shell shell;

    private final List<Exporter> exporters = new LinkedList<Exporter> ();

    private final WritableList exporterList = new WritableList ( this.exporters, Exporter.class );

    private ListViewer viewer;

    private static class Exporter
    {
        private final Export exporter;

        public Exporter ( final Export exporter )
        {
            this.exporter = exporter;
        }

        public void start () throws Exception
        {
            this.exporter.start ();
        }

        public void stop () throws Exception
        {
            this.exporter.stop ();
        }

        @Override
        public String toString ()
        {
            return this.exporter.getConnectionInformation ().toString ();
        }
    }

    public HiveRunnerViewer ()
    {
    }

    public void create ( final Composite parent )
    {
        this.shell = parent.getShell ();

        final Composite wrapper = new Composite ( parent, SWT.NONE );

        parent.setLayout ( new GridLayout ( 1, false ) );
        wrapper.setLayout ( new FillLayout ( SWT.HORIZONTAL ) );
        wrapper.setLayoutData ( new GridData ( SWT.BEGINNING, SWT.CENTER, false, false ) );

        this.start = new Button ( wrapper, SWT.PUSH );
        this.start.setText ( "Start" );
        this.start.addSelectionListener ( new SelectionAdapter () {
            @Override
            public void widgetSelected ( final org.eclipse.swt.events.SelectionEvent e )
            {
                handleStart ();
            };
        } );
        this.stop = new Button ( wrapper, SWT.PUSH );
        this.stop.setText ( "Stop" );
        this.stop.addSelectionListener ( new SelectionAdapter () {
            @Override
            public void widgetSelected ( final org.eclipse.swt.events.SelectionEvent e )
            {
                handleStop ();
            };
        } );

        this.viewer = new ListViewer ( parent );
        this.viewer.getControl ().setLayoutData ( new GridData ( SWT.FILL, SWT.FILL, true, true ) );
        this.viewer.setContentProvider ( new ObservableListContentProvider () );
        this.viewer.setInput ( this.exporterList );

        parent.addDisposeListener ( new DisposeListener () {

            @Override
            public void widgetDisposed ( final DisposeEvent e )
            {
                handleDispose ();
            }
        } );
        update ();
    }

    protected void handleStop ()
    {
        final MultiStatus ms = new MultiStatus ( Activator.PLUGIN_ID, 0, "Stopping hive", null );
        try
        {
            for ( final Exporter exporter : this.exporters )
            {
                try
                {
                    exporter.stop ();
                }
                catch ( final Exception e )
                {
                    logger.warn ( "Failed to stop exporter", e );
                    ms.add ( StatusHelper.convertStatus ( Activator.PLUGIN_ID, e ) );
                }
            }
            if ( this.hive != null )
            {
                this.hive.stop ();
            }
        }
        catch ( final Exception e )
        {
            logger.warn ( "Failed to stop hive", e );
            ms.add ( StatusHelper.convertStatus ( Activator.PLUGIN_ID, e ) );
        }
        ErrorDialog.openError ( this.shell, "Failed to stop", null, ms );
    }

    protected void handleStart ()
    {
        try
        {
            if ( this.hive == null )
            {
                return;
            }

            this.hive.start ();
            for ( final Exporter exporter : this.exporters )
            {
                exporter.start ();
            }
        }
        catch ( final Exception e )
        {
            logger.warn ( "Failed to start hive", e );

            StatusManager.getManager ().handle ( StatusHelper.convertStatus ( Activator.PLUGIN_ID, e ), StatusManager.BLOCK );
        }
    }

    protected void handleDispose ()
    {
        handleStop ();
    }

    public void setFocus ()
    {
        this.start.setFocus ();
    }

    public void setHive ( final Hive hive )
    {
        handleStop ();

        this.hive = hive;
        update ();
    }

    private void update ()
    {
        this.start.setEnabled ( this.hive != null );
        this.stop.setEnabled ( this.hive != null );
    }

    public void addExporter ( final Export exporter )
    {
        this.exporterList.add ( new Exporter ( exporter ) );
        update ();
    }
}
