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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.junit.Test;


/**
 * JUnit test for {@link HelperArray}
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 */
public class HelperArrayTest {

	@Test
	public void testIsValid() {
		assertFalse(HelperArray.isValid(HelperArray.EMPTY_ARRAY_STRING));
		assertFalse(HelperArray.isValid((String[])null));
		assertTrue(HelperArray.isValid(new String[1]));
		
//		assertTrue(HelperArray.isValid(1));
		
		assertFalse(HelperArray.isValid(HelperArray.EMPTY_ARRAY_BYTE));
		assertFalse(HelperArray.isValid((byte[])null));
		assertTrue(HelperArray.isValid(new byte[1]));
	}

	@Test
	public void testConcatenateObjects() {
		final String[] a = {"A", "B", "C"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final String[] b = {"D"}; //$NON-NLS-1$
		final String[] c = {"E", "F"}; //$NON-NLS-1$ //$NON-NLS-2$

		final String[] array = HelperArray.concatenate(a, b, c);

		assertArrayEquals(new String[]{"A", "B", "C", "D", "E", "F"}, array); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
	
		try {
			HelperArray.concatenate(a, b, null);
			fail("array is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testConcatenateBytes() {
		final byte[] a = {1, 2, 3};
		final byte[] b = {4};
		final byte[] c = {5, 6};

		final byte[] array = HelperArray.concatenate(a, b, c);

		assertArrayEquals(new byte[]{1, 2, 3, 4, 5, 6}, array);
		
		try {
			HelperArray.concatenate(a, b, null);
			fail("array is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testConcatenateChar() {
		final char[] a = {'A', 'B', 'C'};
		final char[] b = {'D'};
		final char[] c = {'E', 'F'};

		final char[] array = HelperArray.concatenate(a, b, c);

		assertArrayEquals(new char[]{'A', 'B', 'C', 'D', 'E', 'F'}, array);
		
		try {
			HelperArray.concatenate(a, b, null);
			fail("array is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testRemoveDuplicates() {
		final String[] array = {"A", "A", "A", "B", "B", "C"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

		assertEquals(3, HelperArray.removeDuplicates(array).length);

		try {
			HelperArray.removeDuplicates(null);
			fail("array is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testContains() {
		final String[] array = {"A", "B", "C", "D", "E", "F"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

		assertTrue(HelperArray.contains(array, "A")); //$NON-NLS-1$
		assertFalse(HelperArray.contains(array, "G")); //$NON-NLS-1$
		assertFalse(HelperArray.contains(HelperArray.EMPTY_ARRAY_STRING, "G")); //$NON-NLS-1$
		assertFalse(HelperArray.contains(array, null));

		try {
			HelperArray.contains(null, "A"); //$NON-NLS-1$
			fail("array is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testDumpObjects() {
		final String[] array = {"A", "B", "C"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

//		System.err.println(HelperArray.dump(array).length());
		assertEquals(5, HelperArray.dump(array).length());

		try {
			HelperArray.dump((String[])null);
			fail("array is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testDumpBytes() {
		final byte[] array = {1, 2, 3};

//		System.err.println(HelperArray.dump(array).length());
		assertEquals(5, HelperArray.dump(array).length());

		try {
			HelperArray.dump((byte[])null);
			fail("array is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testDumpChars() {
		final char[] array = {'A', 'B', 'C'};
		
//		System.err.println(HelperArray.dump(array).length());
		assertEquals(5, HelperArray.dump(array).length());

		try {
			HelperArray.dump((char[])null);
			fail("array is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testGetArray() {
		assertArrayEquals(new String[]{"A", "B", "C"}, HelperArray.getArray("A", "B", "C")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		assertArrayEquals(null, HelperArray.getArray((String[])null));
		
//		assertArrayEquals(new byte[]{1, 2, 3}, HelperArray.getArray((byte)1, (byte)2, (byte)3));
//		assertArrayEquals(null, HelperArray.getArray((byte[])null));
//
//		assertArrayEquals(new char[]{'A', 'B', 'C'}, HelperArray.getArray('A', 'B', 'C'));
//		assertArrayEquals(null, HelperArray.getArray((String[])null));
	}
}
