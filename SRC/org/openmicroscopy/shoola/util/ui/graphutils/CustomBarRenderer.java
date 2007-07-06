/*
 * org.openmicroscopy.shoola.util.ui.graphutils.CustomBarRenderer 
 *
  *------------------------------------------------------------------------------
 *  Copyright (C) 2006-2007 University of Dundee. All rights reserved.
 *
 *
 * 	This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *------------------------------------------------------------------------------
 */
package org.openmicroscopy.shoola.util.ui.graphutils;


//Java imports

//Third-party libraries
import java.awt.Color;
import java.awt.Paint;
import java.util.List;

import org.jfree.chart.renderer.category.BarRenderer;

//Application-internal dependencies

/** 
 * 
 *
 * @author  Jean-Marie Burel &nbsp;&nbsp;&nbsp;&nbsp;
 * 	<a href="mailto:j.burel@dundee.ac.uk">j.burel@dundee.ac.uk</a>
 * @author	Donald MacDonald &nbsp;&nbsp;&nbsp;&nbsp;
 * 	<a href="mailto:donald@lifesci.dundee.ac.uk">donald@lifesci.dundee.ac.uk</a>
 * @version 3.0
 * <small>
 * (<b>Internal version:</b> $Revision: $Date: $)
 * </small>
 * @since OME3.0
 */
public class CustomBarRenderer
	extends BarRenderer
{	
	/** The colors. */
	private List<Color> colours;
	
	/**
	 * Creates a new renderer.
	 *
	 * @param colours  the colors.
	 */
	public CustomBarRenderer(List<Color> colours) 
	{
		this.colours = colours;
	}
	
	/**
	 * Returns the paint for an item.  Overrides the default behaviour inherited
	 * from AbstractSeriesRenderer.
	 *
	 * @param series  the series.
	 * @param column  the category.
	 *
	 * @return The item color.
	 */
	public Paint getItemPaint(int series, int column) 
	{
		return colours.get(series);
	}
}


