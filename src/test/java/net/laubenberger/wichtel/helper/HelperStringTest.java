/*
 * Copyright (c) 2007-2013 by Stefan Laubenberger.
 *
 * "wichtel" is free software: you can redistribute it and/or modify
 * it under the terms of the General Public License v2.0.
 *
 * "wichtel" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU General Public License for more details:
 * <http://www.gnu.org/licenses>
 *
 * This distribution is available at:
 * <https://github.com/slaubenberger/wichtel/>
 *
 * Contact information:
 * Stefan Laubenberger
 * Bullingerstrasse 53
 * CH-8004 Zuerich
 *
 * <http://www.laubenberger.net>
 *
 * <laubenberger@gmail.com>
 */

package net.laubenberger.wichtel.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;

import org.junit.Test;


/**
 *JUnit test for {@link HelperString}
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 */
public class HelperStringTest {
	@Test
	public void testIsValid() {
		assertFalse(HelperString.isValid(new StringBuilder()));
		assertFalse(HelperString.isValid(HelperString.EMPTY_STRING));
		assertTrue(HelperString.isValid("123")); //$NON-NLS-1$
	}

	@Test
	public void testIsNumeric() {
		assertFalse(HelperString.isNumeric(HelperString.EMPTY_STRING));
		assertTrue(HelperString.isNumeric("123.0")); //$NON-NLS-1$
		assertTrue(HelperString.isNumeric("123")); //$NON-NLS-1$
		assertTrue(HelperString.isNumeric("123.23")); //$NON-NLS-1$
		assertTrue(HelperString.isNumeric("000123.23")); //$NON-NLS-1$
		assertTrue(HelperString.isNumeric("-123.23")); //$NON-NLS-1$
		assertFalse(HelperString.isNumeric("123.23abc")); //$NON-NLS-1$
		assertFalse(HelperString.isNumeric("123..23")); //$NON-NLS-1$

		try {
			HelperString.isNumeric(null);
			fail("input is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testFill() {
		assertEquals(10, HelperString.fill('1', 10).length());

		try {
			HelperString.fill('1', 0);
			fail("fillLength must be greater than 0"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testReverse() {
		assertEquals("6791 regrebnebuaL nafetS", HelperString.reverse("Stefan Laubenberger 1976")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(HelperString.EMPTY_STRING, HelperString.reverse(HelperString.EMPTY_STRING));

		try {
			HelperString.reverse(null);
         fail("input is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetNumericString() {
		assertEquals("1123.0", HelperString.getNumericString("1abc!@123.0")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("123.23", HelperString.getNumericString("abc!@123.23abc!@")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("-123.23", HelperString.getNumericString("-abc!@-123.23abc!@")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("123.23", HelperString.getNumericString("abc!@123.2-3abc!@")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("123.23", HelperString.getNumericString("abc!@123..23abc!@")); //$NON-NLS-1$ //$NON-NLS-2$

		assertNull(HelperString.getNumericString(HelperString.EMPTY_STRING));
		assertNull(HelperString.getNumericString(HelperString.PERIOD));
		assertNull(HelperString.getNumericString(HelperString.NEGATIVE_SIGN));
		assertNull(HelperString.getNumericString("abc!@")); //$NON-NLS-1$

		try {
			HelperString.reverse(null);
         fail("input is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testConcatenate() {
		assertEquals("StefanLaubenberger", HelperString.concatenate("Stefan", "Laubenberger")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		assertEquals("Stefan Laubenberger", HelperString.concatenate(new String[]{"Stefan", "Laubenberger\n"}, HelperString.SPACE, true)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		assertEquals("Stefan Laubenberger\n", HelperString.concatenate(new String[]{"Stefan", "Laubenberger\n"}, HelperString.SPACE, false)); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		try {
			HelperString.concatenate(null);
         fail("strings is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperString.concatenate(HelperArray.EMPTY_ARRAY_STRING);
         fail("strings is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperString.concatenate(null, HelperString.SPACE, true);
         fail("strings is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperString.concatenate(HelperArray.EMPTY_ARRAY_STRING, HelperString.SPACE, true);
         fail("strings is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testStartsWith() {
		assertTrue(HelperString.startsWith("StEfan", "sTe")); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(HelperString.startsWith("StEfan", "sTa")); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(HelperString.startsWith(HelperString.EMPTY_STRING, "sTe")); //$NON-NLS-1$

		try {
			HelperString.startsWith(null, "sTe"); //$NON-NLS-1$
			fail("string is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperString.startsWith("StEfan", null); //$NON-NLS-1$
			fail("prefix is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperString.startsWith("StEfan", HelperString.EMPTY_STRING); //$NON-NLS-1$
			fail("prefix is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testEndsWith() {
		assertTrue(HelperString.endsWith("StefAn", "FaN")); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(HelperString.endsWith("StEfAn", "faM")); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(HelperString.endsWith(HelperString.EMPTY_STRING, "FaN")); //$NON-NLS-1$

		try {
			HelperString.endsWith(null, "FaN"); //$NON-NLS-1$
			fail("string is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperString.endsWith("StEfan", null); //$NON-NLS-1$
			fail("suffix is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperString.endsWith("StEfan", HelperString.EMPTY_STRING); //$NON-NLS-1$
			fail("suffix is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testContains() {
		assertTrue(HelperString.contains("Stefan", "eFA")); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(HelperString.contains("StEfAn", "aFA")); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(HelperString.contains(HelperString.EMPTY_STRING, "eFA")); //$NON-NLS-1$

		try {
			HelperString.contains(null, "FaN"); //$NON-NLS-1$
			fail("string is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperString.contains("StEfan", null); //$NON-NLS-1$
			fail("part is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperString.contains("StEfan", HelperString.EMPTY_STRING); //$NON-NLS-1$
			fail("part is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}
