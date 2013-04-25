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

package org.openscada.jenkins.exporter;

import hudson.Extension;
import hudson.init.InitMilestone;
import hudson.init.Initializer;
import hudson.model.Job;

import java.io.IOException;

import jenkins.model.GlobalConfiguration;
import jenkins.model.Jenkins;
import jenkins.util.ServerTcpPort;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

@Extension
public class Exporter extends GlobalConfiguration
{

    private volatile int port = 2101;

    private ProjectManager manager;

    private String password;

    public Exporter ()
    {
        load ();
    }

    public void setPort ( final int port )
    {
        this.port = port;
        this.manager.setPort ( port );
    }

    public int getPort ()
    {
        return this.port;
    }

    private void setPassword ( final String password )
    {
        this.password = password;
        this.manager.setPassword ( password );
    }

    @Override
    public boolean configure ( final StaplerRequest req, final JSONObject json ) throws FormException
    {
        setPort ( new ServerTcpPort ( json.getJSONObject ( "port" ) ).getPort () );
        setPassword ( json.getString ( "password" ) );
        return true;
    }

    public static Exporter get ()
    {
        return Jenkins.getInstance ().getExtensionList ( GlobalConfiguration.class ).get ( Exporter.class );
    }

    @Initializer ( after = InitMilestone.JOB_LOADED, fatal = false )
    public static void init () throws IOException, InterruptedException
    {
        get ().start ();
    }

    public void start ()
    {
        this.manager = new ProjectManager ( this );
        this.manager.setPort ( this.port );

        for ( final Job<?, ?> project : Jenkins.getInstance ().getAllItems ( Job.class ) )
        {
            this.manager.addProject ( project.getFullName (), project.getLastBuild () );
        }
    }

    public ProjectManager getManager ()
    {
        return this.manager;
    }

    public void stop ()
    {
        this.manager.dispose ();
        this.manager = null;
    }

}
