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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;

import net.laubenberger.wichtel.AllTests;
import net.laubenberger.wichtel.helper.HelperArray;
import net.laubenberger.wichtel.helper.HelperString;
import net.laubenberger.wichtel.misc.Constants;

import org.junit.Test;


/**
 *  JUnit test for {@link EncoderBase64}
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 */
public class EncoderBase64Test {
	@Test
	public void testPassEncodeAndDecodeString() {

		// Test with empty string data
		String encoded = EncoderBase64.encode(HelperString.EMPTY_STRING);
		byte[] decoded = EncoderBase64.decode(encoded);
		
		try {
			assertEquals(HelperString.EMPTY_STRING, new String(decoded, Constants.ENCODING_UTF8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		// Test with real string data
		encoded = EncoderBase64.encode(AllTests.DATA);
		decoded = EncoderBase64.decode(encoded);

		try {
			assertEquals(AllTests.DATA, new String(decoded, Constants.ENCODING_UTF8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPassEncodeAndDecodeByteArray() {

		// Test with empty byte-array data
		char[] encoded = EncoderBase64.encode(HelperArray.EMPTY_ARRAY_BYTE);
		byte[] decoded = EncoderBase64.decode(encoded);
		
		assertArrayEquals(HelperArray.EMPTY_ARRAY_BYTE, decoded);
		
		// Test with real byte-array data
		encoded = EncoderBase64.encode(AllTests.DATA.getBytes());
		decoded = EncoderBase64.decode(encoded);

		assertArrayEquals(AllTests.DATA.getBytes(), decoded);
	}
	
	@Test
	public void testFailEncode() {
		try {
			EncoderBase64.encode((byte[])null);
			fail("input is null"); //$NON-NLS-1$
		} catch (IllegalArgumentException ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			EncoderBase64.encode((String)null);
			fail("input is null"); //$NON-NLS-1$
		} catch (IllegalArgumentException ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			EncoderBase64.encode(null, Constants.ENCODING_UTF8);
			fail("input is null"); //$NON-NLS-1$
		} catch (IllegalArgumentException ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			EncoderBase64.encode(AllTests.DATA, "blabla"); //$NON-NLS-1$
			fail("unsupported encoding"); //$NON-NLS-1$
		} catch (UnsupportedEncodingException e) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			EncoderBase64.encode(AllTests.DATA, HelperString.EMPTY_STRING);
			fail("encoding is empty"); //$NON-NLS-1$
		} catch (IllegalArgumentException ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			EncoderBase64.encode(AllTests.DATA, null);
			fail("encoding is null"); //$NON-NLS-1$
		} catch (IllegalArgumentException ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testFailDecode() {
		try {
			EncoderBase64.decode((char[])null);
			fail("input is null"); //$NON-NLS-1$
		} catch (IllegalArgumentException ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			EncoderBase64.decode((String)null);
			fail("input is null"); //$NON-NLS-1$
		} catch (IllegalArgumentException ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}
