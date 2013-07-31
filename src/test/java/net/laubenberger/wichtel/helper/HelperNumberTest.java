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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.junit.Test;


/**
 * JUnit test for {@link HelperNumber}
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 */
public class HelperNumberTest {
	@Test
	public void testMultiply() {
		assertEquals(new BigDecimal(120), HelperNumber.multiply(1, 2, 3, 4, 5));

		try {
			HelperNumber.multiply(1, 2, null, 4, 5);
			fail("value is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperNumber.multiply(null);
			fail("values is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	
		try {
			HelperNumber.multiply(HelperArray.EMPTY_ARRAY_BIG_DECIMAL);
			fail("values is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testAdd() {
		assertEquals(new BigDecimal(15), HelperNumber.add(1, 2, 3, 4, 5));

		try {
			HelperNumber.add(1, 2, null, 4, 5);
			fail("value is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperNumber.add(null);
			fail("values is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	
		try {
			HelperNumber.add(HelperArray.EMPTY_ARRAY_BIG_DECIMAL);
			fail("values is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testGetNumber() {
		assertEquals(new BigDecimal("12.35"), HelperNumber.getNumber("12.35")); //$NON-NLS-1$ //$NON-NLS-2$
		assertNull(HelperNumber.getNumber("blabla")); //$NON-NLS-1$
		
		try {
			HelperNumber.getNumber(null);
			fail("text is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	
	@Test
	public void testGetString() {
		assertEquals("12.35", HelperNumber.getString(new BigDecimal("12.35"))); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("NaN", HelperNumber.getString(Double.NaN)); //$NON-NLS-1$
		assertNull(HelperNumber.getString(null));
	}
}


