/*
 * org.openmicroscopy.shoola.util.ui.JXTaskPaneContainerSingle
 *
 *------------------------------------------------------------------------------
 * Copyright (C) 2006-2009 University of Dundee. All rights reserved.
 *
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 *------------------------------------------------------------------------------
 */
package org.openmicroscopy.shoola.util.ui;


//Java imports
import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;

//Third-party libraries
import info.clearthought.layout.TableLayout;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.jdesktop.swingx.VerticalLayout;

//Application-internal dependencies

/**
 * A JXTaskPaneContainer allowing no more than a JXTaskPane expanded at the 
 * same time.
 *
 * @author Jean-Marie Burel &nbsp;&nbsp;&nbsp;&nbsp;
 *         <a href="mailto:j.burel@dundee.ac.uk">j.burel@dundee.ac.uk</a>
 * @author Donald MacDonald &nbsp;&nbsp;&nbsp;&nbsp;
 *         <a href="mailto:donald@lifesci.dundee.ac.uk">donald@lifesci.dundee.ac.uk</a>
 * @version 3.0
 * <small>
 * (<b>Internal version:</b> $Revision: $Date: $)
 * </small>
 * @since 3.0-Beta4
 */
public class JXTaskPaneContainerSingle 
	extends JXTaskPaneContainer
	implements PropertyChangeListener
{

	/** Bound property indicating the selection of a new task pane. */
	public static final String SELECTED_TASKPANE_PROPERTY = "selectedTaskPane";

	/** The map hosting the <code>JXTaskPane</code>s. */
	private Map<JXTaskPane, Integer> panes;
	
	/** Flag indicating that a tab pane can or cannot be expanded. */
	private boolean	expandable;
	
	/** The height of a <code>JXTaskPane</code> when collapsed.*/
	private int minimumHeight;
	
	private TableLayout layout;
	
	/** Initializes the component. */
	private void initialize()
	{
		expandable = true;
		panes = new HashMap<JXTaskPane, Integer>();
		if (getLayout() instanceof VerticalLayout) {
			VerticalLayout vl = (VerticalLayout) getLayout();
			vl.setGap(1);
		}
		layout = new TableLayout();
		double[] columns = {TableLayout.FILL};
		
		layout.setColumn(columns);
		setLayout(layout);
		setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		setBackground(UIUtilities.BACKGROUND);
	}
	
	/** Creates a new instance. */
	public JXTaskPaneContainerSingle()
	{
		initialize();
	}
	
	/**
	 * Passes <code>true</code> to allow a component to be expanded,
	 * <code>false</code> otherwise.
	 * 
	 * @param expandable The value to set.
	 */
	public void setExpandable(boolean expandable)
	{
		this.expandable = expandable;
	}
	
	/**
	 * Returns <code>true</code> if one of the <code>JXTaskPane</code>s 
	 * is expanded, <code>false</code> otherwise.
	 * 
	 * @return See above.
	 */
	public boolean hasTaskPaneExpanded()
	{
		Component[] comps = getComponents();
		if (comps == null) return false;
		JXTaskPane pane;
		for (int i = 0; i < comps.length; i++) {
			if (comps[i] instanceof JXTaskPane) {
				pane = (JXTaskPane) comps[i];
				if (!pane.isCollapsed()) return true;
			}
		}
		return false;
	}

	/**
	 * Return the list of JXTaskPanes in the component.
	 * @return See above.
	 */
	public List<JXTaskPane> getTaskPanes()
	{
		List<JXTaskPane> list = new ArrayList<JXTaskPane>();
		Iterator<JXTaskPane> iterator = panes.keySet().iterator();
		while(iterator.hasNext())
			list.add(iterator.next());
		return list;
	}
	
	/**
	 * Overridden to attach listener to the component if it is a 
	 * <code>JXTaskPane</code>.
	 * @see JXTaskPaneContainer#add(Component)
	 */
	public void add(JXTaskPane component)
	{
		int index = panes.size();
		//super.add(component);
		panes.put(component, index);
		if (component.isCollapsed())
			minimumHeight = component.getPreferredSize().height;
		layout.insertRow(index, TableLayout.PREFERRED);
		super.add(component, "0, "+index);
		component.addPropertyChangeListener(
				UIUtilities.COLLAPSED_PROPERTY_JXTASKPANE, this);
	}

	/**
	 * Reacts to the expansion of <code>JXTaskPane</code>s.
	 * @see PropertyChangeListener#propertyChange(PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt)
	{
		JXTaskPane src = (JXTaskPane) evt.getSource();
		if (!expandable) {
			src.setCollapsed(true);
			src.setSpecial(false);
			return;
		}
		Container parent = src.getParent();
		Component[] comp = parent.getComponents();
		Component c;
		JXTaskPane p;
		if (src.isCollapsed()) {
			if (hasTaskPaneExpanded()) return;
			for (int i = 0; i < comp.length; i++) {
				c = comp[i];
				if (c instanceof JXTaskPane) {
					p = (JXTaskPane) c;
					if (p == src) {
						layout.setRow(i, minimumHeight);
					}
				}
			}
			firePropertyChange(SELECTED_TASKPANE_PROPERTY, null, src);
			return;
		}
		
		for (int i = 0; i < comp.length; i++) {
			c = comp[i];
			if (c instanceof JXTaskPane) {
				p = (JXTaskPane) c;
				if (p != src) {
					p.setCollapsed(true);
					p.setSpecial(false);
					layout.setRow(i, minimumHeight);
				} else layout.setRow(i, TableLayout.FILL);
			}
		}
		src.setSpecial(true);
		firePropertyChange(SELECTED_TASKPANE_PROPERTY, null, src);
	}

}
