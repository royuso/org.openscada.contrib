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

package org.openscada.contrib.javafx.sample1;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application
{

    public static ConnectionManager connectionManager = new ConnectionManager ();

    @Override
    public void start ( final Stage primaryStage ) throws Exception
    {
        final Parent root = FXMLLoader.load ( getClass ().getResource ( "Main.fxml" ) );

        primaryStage.setTitle ( "openSCADA JavaFX Sample 1" );
        primaryStage.setScene ( new Scene ( root, 640, 480 ) );

        primaryStage.show ();
    }

    @Override
    public void stop () throws Exception
    {
        connectionManager.dispose ();
        super.stop ();
    }

    public static void main ( final String[] args )
    {
        launch ( args );
    }

}
