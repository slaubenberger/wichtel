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

import java.util.ArrayList;
import java.util.Collection;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.junit.Test;


/**
 * JUnit test for {@link HelperCollection}
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 */
public class HelperCollectionTest {
	@Test
	public void testIsValid() {
		final Collection<String> list = new ArrayList<>();

		assertFalse(HelperCollection.isValid(list));
		list.add("Hi"); //$NON-NLS-1$
		assertTrue(HelperCollection.isValid(list));

		assertFalse(HelperCollection.isValid(null));
	}

	@Test
	public void testToArray() {
		final String[] array = HelperCollection.toArray(HelperCollection.getList("A", "B", "C")); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$

		assertArrayEquals(new String[]{"A", "B", "C"}, array); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		try {
			HelperCollection.toArray(null);
			fail("collection is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperCollection.toArray(new ArrayList<String>());
			fail("collection is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testGetList() {
		final Collection<String> list = HelperCollection.getList("A", "B", "C"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$

		assertEquals(3, list.size());
		
		assertEquals(0, HelperCollection.getList(HelperArray.EMPTY_ARRAY_STRING).size());
		
		try {
			HelperCollection.getList(null);
			fail("elements is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetSet() {
		final Collection<String> list = HelperCollection.getSet("A", "A", "A", "B", "B", "C"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

		assertEquals(3, list.size());
		
		assertEquals(0, HelperCollection.getSet(HelperArray.EMPTY_ARRAY_STRING).size());
		
		try {
			HelperCollection.getSet(null);
			fail("elements is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testRemoveDuplicates() {
		Collection<String> list = new ArrayList<>();

		assertEquals(0, HelperCollection.removeDuplicates(list).size());

		list = HelperCollection.getList("A", "A", "A", "B", "B", "C"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$

		assertEquals(3, HelperCollection.removeDuplicates(list).size());

		try {
			HelperCollection.removeDuplicates(null);
			fail("collection is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testDump() {
		final Collection<String> list = HelperCollection.getList("A", "B", "C"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$

		assertEquals(5, HelperCollection.dump(list).length());

		try {
			HelperCollection.dump(null);
			fail("iterable is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}
