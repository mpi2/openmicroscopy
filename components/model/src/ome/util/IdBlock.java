/*
 * ome.util.IdBlock
 *
 *   Copyright 2006 University of Dundee. All rights reserved.
 *   Use is subject to license terms supplied in LICENSE.txt
 */

package ome.util;

// Java imports

// Third-party libraries

// Application-internal dependencies
import ome.model.IObject;

/**
 * {@link ome.util.CBlock CBlock} implementation which collects ids from
 * {@link ome.model.IObject IObjects}
 * 
 * @author Josh Moore &nbsp;&nbsp;&nbsp;&nbsp; <a
 *         href="mailto:josh.moore@gmx.de">josh.moore@gmx.de</a>
 * @version 3.0 <small> (<b>Internal version:</b> $Rev$ $Date$) </small>
 * @since 3.0
 */
public class IdBlock implements CBlock {

    public Object call(IObject arg0) {
        return arg0.getId();
    }

}