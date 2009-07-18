/*
 *   $Id$
 *   
 *   Copyright 2009 Glencoe Software, Inc. All rights reserved.
 *   Use is subject to license terms supplied in LICENSE.txt
 */

package ome.services.roi.test;

import junit.framework.TestCase;
import ome.services.roi.GeomTool;
import omero.model.SmartLineI;
import omero.model.SmartShape.Util;

import org.testng.annotations.Test;

/**
 *
 */
@Test(groups = { "rois" })
public class ShapeUnitTest extends TestCase {

    GeomTool geomTool = new GeomTool(null, null, null);

    @Test
    public void testGeometryOfLineGood() throws Exception {
        SmartLineI l = (SmartLineI) geomTool.ln(0, 0, 1, 1);
        assertTrue(Util.checkNonNull(l.asPoints()));
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testGeometryOfLineBad() throws Exception {
        SmartLineI l = (SmartLineI) geomTool.ln(0, 0, 1, 1);
        l.setY2(null);
        assertFalse(Util.checkNonNull(l.asPoints()));
    }
}
