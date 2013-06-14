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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.openscada.da.client.DataItemValue;

public abstract class AbstractValueConverter
{

    private final ObjectProperty<DataItemValue> inputState = new SimpleObjectProperty<> ( this, "inputState" );

    public void setInputState ( final DataItemValue inputState )
    {
        this.inputState.set ( inputState );
        update ( inputState );
    }

    public DataItemValue getInputState ()
    {
        return this.inputState.get ();
    }

    public ObjectProperty<DataItemValue> inputStateProperty ()
    {
        return this.inputState;
    }

    private final BooleanProperty error = new SimpleBooleanProperty ( this, "error" );

    public boolean isError ()
    {
        return this.error.get ();
    }

    public void setError ( final boolean error )
    {
        this.error.set ( error );
    }

    public BooleanProperty errorProperty ()
    {
        return this.error;
    }

    private final BooleanProperty valid = new SimpleBooleanProperty ( this, "valid" );

    public boolean isValid ()
    {
        return this.valid.get ();
    }

    public void setValid ( final boolean valid )
    {
        this.valid.set ( valid );
    }

    public BooleanProperty validProperty ()
    {
        return this.valid;
    }

    private final ObservableList<String> styles = new SimpleListProperty<String> ( FXCollections.<String> observableArrayList () );

    public ObservableList<String> stylesProperty ()
    {
        return this.styles;
    }

    public ObservableList<String> getStyles ()
    {
        return this.styles;
    }

    protected void update ( final DataItemValue value )
    {
        final boolean valid = !value.isError () && value.isConnected () && value.getValue () != null && !value.getValue ().isNull ();
        System.out.println ( "Value: " + value + " -> " + valid );
        this.valid.set ( valid );
    }
}
