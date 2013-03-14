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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.junit.Test;


/**
 * JUnit test for {@link HelperMap}
 *
 * @author Stefan Laubenberger
 * @version 0.0.2, 2013-03-14
 */
public class HelperMapTest {
	@Test
	public void testIsValid() {
		final Map<String, String> map = new HashMap<>();

		assertFalse(HelperMap.isValid(map));
		map.put("Hi", "Stefan"); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(HelperMap.isValid(map));
		
		assertFalse(HelperMap.isValid(null));
	}

	@Test
	public void testGetKeys() {
		final Map<String, String> map = new HashMap<>();

		assertEquals(0, HelperMap.getKeys(map).size());
		
		map.put("1", "Silvan"); //$NON-NLS-1$ //$NON-NLS-2$
		map.put("2", "Roman"); //$NON-NLS-1$ //$NON-NLS-2$
		map.put("3", "Stefan"); //$NON-NLS-1$ //$NON-NLS-2$

		assertEquals(5, HelperCollection.dump(HelperMap.getKeys(map)).length());
		
		try {
			HelperMap.getKeys(null);
			fail("map is null!"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetValues() {
		final Map<String, String> map = new HashMap<>();

		assertEquals(0, HelperMap.getValues(map).size());
		
		map.put("1", "Silvan"); //$NON-NLS-1$ //$NON-NLS-2$
		map.put("2", "Roman"); //$NON-NLS-1$ //$NON-NLS-2$
		map.put("3", "Stefan"); //$NON-NLS-1$ //$NON-NLS-2$

		assertEquals(19, HelperCollection.dump(HelperMap.getValues(map)).length());
		
		try {
			HelperMap.getValues(null);
			fail("map is null!"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testDump() {
		final Map<String, String> map = new HashMap<>();

		map.put("1", "Silvan"); //$NON-NLS-1$ //$NON-NLS-2$
		map.put("2", "Roman"); //$NON-NLS-1$ //$NON-NLS-2$
		map.put("3", "Stefan"); //$NON-NLS-1$ //$NON-NLS-2$

		assertEquals(25, HelperMap.dump(map).length());

		try {
			HelperMap.dump(null);
			fail("map is null!"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}
