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

package net.laubenberger.wichtel.model.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;


/**
 * JUnit test for {@link Bit}
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 */
public class UnitBitTest {

	@Test
	public void testConvert() {

		for (final Bit unit : Bit.values()) {
			assertEquals(Bit.BIT.convertTo(unit, BigDecimal.ONE), unit.factor);
		}

		try {
			Bit.BIT.convertTo(null, BigDecimal.ONE);
			fail("toUnit is null"); //$NON-NLS-1$
		} catch (IllegalArgumentException ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			Bit.BIT.convertTo(Bit.BYTE, null);
			fail("value is null"); //$NON-NLS-1$
		} catch (IllegalArgumentException ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}
