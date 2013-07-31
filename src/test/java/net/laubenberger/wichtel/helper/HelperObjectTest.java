/*
 * Copyright (c) 2007-2013 by Stefan Laubenberger.
 *
 * "wichtel" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License v3.0.
 *
 * "wichtel" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details:
 * -----------------------------------------------------------
 * http://www.gnu.org/licenses
 *
 *
 * This distribution is available at:
 * ----------------------------------
 * https://github.com/slaubenberger/wichtel/
 *
 *
 * Contact information:
 * --------------------
 * Stefan Laubenberger
 * Bullingerstrasse 53
 * CH-8004 Zuerich
 *
 * http://www.laubenberger.net
 * laubenberger@gmail.com
 */

package net.laubenberger.wichtel.helper;

import net.laubenberger.wichtel.AllTests;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test for {@link HelperObject}
 * 
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 */
public class HelperObjectTest
{
    @Test
    public void testCreateInstance() {
        try {
            assertEquals(HelperString.EMPTY_STRING, HelperObject.createInstance(String.class));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            assertEquals(
                    AllTests.DATA,
                    HelperObject.createInstance(String.class, HelperArray.getArray(String.class),
                            HelperArray.getArray(AllTests.DATA)));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            HelperObject.createInstance(null);
            fail("class is null"); //$NON-NLS-1$
        } catch (RuntimeExceptionIsNull ex) {
            // nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            HelperObject.createInstance(null, new Class[]{String.class}, new Object[]{AllTests.DATA});
            fail("class is null"); //$NON-NLS-1$
        } catch (RuntimeExceptionIsNull ex) {
            // nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            HelperObject.createInstance(String.class, HelperArray.EMPTY_ARRAY_CLASS, new Object[]{AllTests.DATA});
            fail("paramClazzes is empty"); //$NON-NLS-1$
        } catch (RuntimeExceptionIsEmpty ex) {
            // nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            HelperObject.createInstance(String.class, new Class[]{String.class}, HelperArray.EMPTY_ARRAY_OBJECT);
            fail("params is empty"); //$NON-NLS-1$
        } catch (RuntimeExceptionIsEmpty ex) {
            // nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testSerialize() {
        try {
            // System.err.println(HelperObject.serialize(AllTests.DATA).length);
            assertEquals(1183, HelperObject.serialize(AllTests.DATA).length);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            HelperObject.serialize(null);
            fail("object is null"); //$NON-NLS-1$
        } catch (RuntimeExceptionIsNull ex) {
            // nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testDeserialize() {
        try {
            assertEquals(AllTests.DATA, HelperObject.deserialize(HelperObject.serialize(AllTests.DATA)));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            assertEquals(AllTests.DATA,
                    HelperObject.deserialize(String.class, HelperObject.serialize(AllTests.DATA)));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            HelperObject.deserialize(null, AllTests.DATA.getBytes());
            fail("clazz is null"); //$NON-NLS-1$
        } catch (RuntimeExceptionIsNull ex) {
            // nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            HelperObject.deserialize(null);
            fail("data is null"); //$NON-NLS-1$
        } catch (RuntimeExceptionIsNull ex) {
            // nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            HelperObject.deserialize(HelperArray.EMPTY_ARRAY_BYTE);
            fail("data is empty"); //$NON-NLS-1$
        } catch (RuntimeExceptionIsEmpty ex) {
            // nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testClone() {
        try {
            assertEquals(AllTests.DATA, HelperObject.clone(AllTests.DATA));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
			  assertNotSame(AllTests.DATA, HelperObject.clone(AllTests.DATA));
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            HelperObject.deserialize(null);
            fail("original is null"); //$NON-NLS-1$
        } catch (RuntimeExceptionIsNull ex) {
            // nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsMethodAvailable() {
        assertTrue(HelperObject.isMethodAvailable(String.class, "indexOf")); //$NON-NLS-1$
        assertFalse(HelperObject.isMethodAvailable(String.class, "blabla")); //$NON-NLS-1$

        try {
            HelperObject.isMethodAvailable(null, "indexOf"); //$NON-NLS-1$
            fail("clazz is null"); //$NON-NLS-1$
        } catch (RuntimeExceptionIsNull ex) {
            // nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }

        try {
            HelperObject.isMethodAvailable(String.class, null);
            fail("methodName is null"); //$NON-NLS-1$
        } catch (RuntimeExceptionIsNull ex) {
            // nothing to do
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    @Test
    public void testIsEquals() {
        assertTrue(HelperObject.isEquals("A", "A")); //$NON-NLS-1$//$NON-NLS-2$
        assertTrue(HelperObject.isEquals(null, null));
        assertFalse(HelperObject.isEquals("A", "B")); //$NON-NLS-1$ //$NON-NLS-2$
        assertFalse(HelperObject.isEquals("A", null)); //$NON-NLS-1$
        assertFalse(HelperObject.isEquals(null, "B")); //$NON-NLS-1$
    }

//    @Test
//    public void testQuote() {
//        assertEquals("'A'", HelperObject.quote("A")); //$NON-NLS-1$//$NON-NLS-2$
//
//        try {
//            HelperObject.quote(null);
//            fail("object is null"); //$NON-NLS-1$
//        } catch (RuntimeExceptionIsNull ex) {
//            // nothing to do
//        } catch (Exception ex) {
//            fail(ex.getMessage());
//        }
//    }
}
