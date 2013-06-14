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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import jfxtras.labs.scene.control.gauge.Gauge;
import jfxtras.labs.scene.control.gauge.Gauge.RadialRange;
import jfxtras.labs.scene.control.gauge.GaugeBuilder;
import jfxtras.labs.scene.control.gauge.GaugeBuilder.GaugeType;

import org.openscada.da.client.DataItemValue;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.skin.SkinBase;

public class GaugeControlSkin extends SkinBase<GaugeControl, BehaviorBase<GaugeControl>>
{

    private final Gauge gauge;

    public GaugeControlSkin ( final GaugeControl control )
    {
        super ( control, new BehaviorBase<> ( control ) );

        final BorderPane pane = new BorderPane ();

        final GaugeBuilder<?> builder = GaugeBuilder.create ();
        builder.gaugeType ( GaugeType.RADIAL );
        builder.radialRange ( RadialRange.RADIAL_300 );
        builder.glowVisible ( true );
        this.gauge = builder.build ();

        pane.setCenter ( this.gauge );

        getChildren ().add ( pane );

        control.inputProperty ().addListener ( new ChangeListener<DataItemValue> () {

            @Override
            public void changed ( final ObservableValue<? extends DataItemValue> observable, final DataItemValue oldValue, final DataItemValue newValue )
            {
                updateState ( newValue );
            }
        } );
    }

    protected void updateState ( final DataItemValue newValue )
    {
        try
        {
            this.gauge.setGlowOn ( false );
            if ( newValue.isError () )
            {
                this.gauge.setGlowColor ( Color.MAGENTA );
                this.gauge.setGlowOn ( true );
            }
            else if ( newValue.isAlarm () )
            {
                this.gauge.setGlowColor ( Color.RED );
                this.gauge.setGlowOn ( true );
            }
            else if ( newValue.isWarning () )
            {
                this.gauge.setGlowColor ( Color.ORANGE );
                this.gauge.setGlowOn ( true );
            }
            else if ( newValue.isManual () )
            {
                this.gauge.setGlowColor ( Color.TURQUOISE );
                this.gauge.setGlowOn ( true );
            }
            this.gauge.setValue ( newValue.getValue ().asDouble () );
        }
        catch ( final Exception e )
        {
        	e.printStackTrace ();
            this.gauge.setGlowColor ( Color.MAGENTA );
            this.gauge.setGlowOn ( true );
        }
    }

}
