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

package net.laubenberger.wichtel.helper.encoder;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import net.laubenberger.wichtel.AllTests;
import net.laubenberger.wichtel.helper.HelperArray;

import org.junit.Test;


/**
 * JUnit test for {@link EncoderHex}
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 */
public class EncoderHexTest {
	@Test
	public void testPassEncodeAndDecode() {
		// Test with empty byte-array data
		String encoded = EncoderHex.encode(HelperArray.EMPTY_ARRAY_BYTE);
		byte[] decoded = EncoderHex.decode(encoded);
		
		assertArrayEquals(HelperArray.EMPTY_ARRAY_BYTE, decoded);

		// Test with real byte-array data
		encoded = EncoderHex.encode(AllTests.DATA.getBytes());
		decoded = EncoderHex.decode(encoded);

		assertArrayEquals(AllTests.DATA.getBytes(), decoded);
	}
	
	@Test
	public void testFailEncode() {
		try {
			EncoderHex.encode(null);
			fail("input is null"); //$NON-NLS-1$
		} catch (IllegalArgumentException ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testFailDecode() {
		try {
			EncoderHex.decode(null);
			fail("input is null"); //$NON-NLS-1$
		} catch (IllegalArgumentException ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}


