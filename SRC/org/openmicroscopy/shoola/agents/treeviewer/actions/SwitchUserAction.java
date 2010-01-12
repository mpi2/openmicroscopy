/*
 * org.openmicroscopy.shoola.agents.treeviewer.actions.SwitchUserAction 
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
package org.openmicroscopy.shoola.agents.treeviewer.actions;


//Java imports
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Action;

//Third-party libraries

//Application-internal dependencies
import org.openmicroscopy.shoola.agents.treeviewer.IconManager;
import org.openmicroscopy.shoola.agents.treeviewer.TreeViewerAgent;
import org.openmicroscopy.shoola.agents.treeviewer.browser.Browser;
import org.openmicroscopy.shoola.agents.treeviewer.view.TreeViewer;
import org.openmicroscopy.shoola.util.ui.UIUtilities;

import pojos.ExperimenterData;
import pojos.GroupData;

/** 
 * Action to bring up the Switch user dialog.
 *
 * @author  Jean-Marie Burel &nbsp;&nbsp;&nbsp;&nbsp;
 * <a href="mailto:j.burel@dundee.ac.uk">j.burel@dundee.ac.uk</a>
 * @author Donald MacDonald &nbsp;&nbsp;&nbsp;&nbsp;
 * <a href="mailto:donald@lifesci.dundee.ac.uk">donald@lifesci.dundee.ac.uk</a>
 * @version 3.0
 * <small>
 * (<b>Internal version:</b> $Revision: $Date: $)
 * </small>
 * @since OME3.0
 */
public class SwitchUserAction 
	extends TreeViewerAction
{

	/** The name of the action. */
	private static final String NAME = "Switch User...";
	
	/** The description of the action. */
	private static final String DESCRIPTION = "Select another " +
			"user and view his/her data.";
	
    /** 
     * Enables the action if the browser is not ready.
     * @see TreeViewerAction#onBrowserStateChange(Browser)
     */
    protected void onBrowserStateChange(Browser browser)
    {
    	if (browser == null) return;
    	if (browser == null) return;
    	if (browser.getState() == Browser.READY) {
    		Map m = TreeViewerAgent.getAvailableUserGroups();
    		ExperimenterData exp =  TreeViewerAgent.getUserDetails();
    		Iterator i = m.entrySet().iterator();
    		GroupData group;
    		boolean enabled = false;
    		Entry entry;
    		List list;
    		while (i.hasNext()) {
    			entry = (Entry) i.next();
    			group = (GroupData) entry.getKey();
				if (group.getId() == exp.getDefaultGroup().getId()) {
					list  = (List) entry.getValue();
					enabled = list.size() > 1;
					break;
				}
			}
    		setEnabled(enabled);
    	} else setEnabled(false);
    	// setEnabled(browser.getState() == Browser.READY);
    }
    
    /**
     * Creates a new instance.
     * 
     * @param model Reference to the Model. Mustn't be <code>null</code>.
     */
	public SwitchUserAction(TreeViewer model)
	{
		super(model);
		name = NAME;
		putValue(Action.SHORT_DESCRIPTION, 
                UIUtilities.formatToolTipText(DESCRIPTION));
        IconManager im = IconManager.getInstance();
        putValue(Action.SMALL_ICON, im.getIcon(IconManager.OWNER));
	}
	
    /**
     * Brings up the switch user dialog.
     * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
     */
    public void actionPerformed(ActionEvent e) { model.retrieveUserGroups(); }
    
}
