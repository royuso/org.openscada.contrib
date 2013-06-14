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

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import org.openscada.da.client.DataItemValue;

public class NumericConverter extends AbstractValueConverter
{
    private final DoubleProperty value = new SimpleDoubleProperty ( this, "value" );

    public void setValue ( final double value )
    {
        this.value.set ( value );
    }

    public double getValue ()
    {
        return this.value.get ();
    }

    public DoubleProperty valueProperty ()
    {
        return this.value;
    }

    @Override
    protected void update ( final DataItemValue value )
    {
        super.update ( value );

        this.value.setValue ( value.getValue ().asDouble ( null ) );
    }
}
