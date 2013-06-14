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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;

import org.openscada.da.client.DataItemValue;

public class GaugeControl extends Control
{
    private static final String DEFAULT_STYLE_CLASS = "openscada-gauge-control";

    private final ObjectProperty<DataItemValue> input = new SimpleObjectProperty<> ();

    public GaugeControl ()
    {
        getStyleClass ().setAll ( DEFAULT_STYLE_CLASS );
    }

    @Override
    protected String getUserAgentStylesheet ()
    {
        return "org/openscada/contrib/javafx/sample1/gauge.css";
    }

    public DataItemValue getInput ()
    {
        return this.input.get ();
    }

    public void setInput ( final DataItemValue input )
    {
        this.input.set ( input );
    }

    public ObjectProperty<DataItemValue> inputProperty ()
    {
        return this.input;
    }

}
