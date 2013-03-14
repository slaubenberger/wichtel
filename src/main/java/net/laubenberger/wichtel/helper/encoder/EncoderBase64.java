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

import java.io.UnsupportedEncodingException;

import net.laubenberger.wichtel.helper.HelperEnvironment;
import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.helper.HelperString;
import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionExceedsVmMemory;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Encodes and decodes data to Base64 format.
 *
 * @author Stefan Laubenberger
 * @version 0.0.2, 2013-03-14
 * @since 0.0.1
 */
public abstract class EncoderBase64 {
	private static final Logger log = LoggerFactory.getLogger(EncoderBase64.class);

	private static final String ERROR_ILLEGAL_CHARACTER = "Illegal character in Base64 encoded data"; //$NON-NLS-1$

	private static final char[] MAP1 = new char[64];
	private static final byte[] MAP2 = new byte[128];

	// Mapping table from 6-bit nibbles to Base64 characters.

	static {
		int ii = 0;
		for (char c = 'A'; 'Z' >= c; c++) {
			MAP1[ii] = c;
			ii++;
		}
		for (char c = 'a'; 'z' >= c; c++) {
			MAP1[ii] = c;
			ii++;
		}
		for (char c = '0'; '9' >= c; c++) {
			MAP1[ii] = c;
			ii++;
		}
		MAP1[ii] = '+';
		ii++;
		MAP1[ii] = '/';
	}

	// Mapping table from Base64 characters to 6-bit nibbles.

	static {
		for (int ii = 0; ii < MAP2.length; ii++) {
			MAP2[ii] = (byte) -1;
		}
		for (int ii = 0; 64 > ii; ii++) {
			MAP2[MAP1[ii]] = (byte) ii;
		}
	}

	/**
	 * Encodes a {@link String} with the default encoding (UTF-8) into Base64 format.
	 * No blanks or line breaks are inserted.
	 *
	 * @param input {@link String} to be encoded
	 * @return {@link String} with the Base64 encoded data
	 * @since 0.0.1
	 */
	public static String encode(final String input) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		
		String result = null;
		try {
			result = encode(input, Constants.ENCODING_DEFAULT);
		} catch (UnsupportedEncodingException ex) {
			// should never happen!
			log.error("Encoding invalid", ex); //$NON-NLS-1$
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
	
	/**
	 * Encodes a {@link String} with the chosen encoding into Base64 format.
	 * No blanks or line breaks are inserted.
	 *
	 * @param input {@link String} to be encoded
	 * @param encoding of the {@link String}
	 * @return {@link String} with the Base64 encoded data
	 * @throws UnsupportedEncodingException 
	 * @since 0.0.1
	 */
	public static String encode(final String input, final String encoding) throws UnsupportedEncodingException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, encoding));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (input.length() * 2 > HelperEnvironment.getMemoryFree()) {
			throw new RuntimeExceptionExceedsVmMemory("input", input.length() * 2); //$NON-NLS-1$
		}
		if (null == encoding) {
			throw new RuntimeExceptionIsNull("encoding"); //$NON-NLS-1$
		}
		if (!HelperString.isValid(encoding)) {
			throw new RuntimeExceptionIsEmpty("encoding"); //$NON-NLS-1$
		}

		final String result = new String(encode(input.getBytes(encoding)));

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Encodes a byte-array into Base64 format.
	 * No blanks or line breaks are inserted.
	 *
	 * @param input byte-array to be encoded
	 * @return character array with the Base64 encoded data
	 * @since 0.0.1
	 */
	public static char[] encode(final byte... input) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (input.length * 2 > HelperEnvironment.getMemoryFree()) {
			throw new RuntimeExceptionExceedsVmMemory("input", input.length * 2); //$NON-NLS-1$
		}

		final char[] result = encode(input, input.length);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Decodes a {@link String} from Base64 format.
	 *
	 * @param input Base64 string to be decoded
	 * @return Array containing the decoded data bytes
	 * @since 0.0.1
	 */
	public static byte[] decode(final String input) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (input.length() * 2 > HelperEnvironment.getMemoryFree()) {
			throw new RuntimeExceptionExceedsVmMemory("input", input.length() * 2); //$NON-NLS-1$
		}

		final byte[] result = decode(input.toCharArray());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Decodes a byte array from Base64 format.
	 * No blanks or line breaks are allowed within the Base64 encoded data.
	 *
	 * @param input character array containing the Base64 encoded data
	 * @return Array containing the decoded data bytes
	 * @since 0.0.1
	 */
	public static byte[] decode(final char... input) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (input.length * 2 > HelperEnvironment.getMemoryFree()) {
			throw new RuntimeExceptionExceedsVmMemory("input", input.length * 2); //$NON-NLS-1$
		}

		int iLen = input.length;

		if (0 != iLen % 4) {
			throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4"); //$NON-NLS-1$
		}

		while (0 < iLen && '=' == input[iLen - 1]) {
			iLen--;
		}

		final int oLen = iLen * 3 / 4;
		final byte[] result = new byte[oLen];

		int ip = 0;
		int op = 0;
		while (ip < iLen) {
			final int i0 = input[ip];
			ip++;
			final int i1 = input[ip];
			ip++;
			final int i2 = ip < iLen ? input[ip] : 'A';
			ip++;
			final int i3 = ip < iLen ? input[ip] : 'A';
			ip++;

			if (Byte.MAX_VALUE < i0 || Byte.MAX_VALUE < i1 || Byte.MAX_VALUE < i2 || Byte.MAX_VALUE < i3) {
				throw new IllegalArgumentException(ERROR_ILLEGAL_CHARACTER);
			}
			final int b0 = MAP2[i0];
			final int b1 = MAP2[i1];
			final int b2 = MAP2[i2];
			final int b3 = MAP2[i3];

			if (0 > b0 || 0 > b1 || 0 > b2 || 0 > b3) {
				throw new IllegalArgumentException(ERROR_ILLEGAL_CHARACTER);
			}

			final int o0 = b0 << 2 | b1 >>> 4;
			final int o1 = (b1 & 0xf) << 4 | b2 >>> 2;
			final int o2 = (b2 & 3) << 6 | b3;
			result[op] = (byte) o0;
			op++;
			if (op < oLen) {
				result[op] = (byte) o1;
				op++;
			}
			if (op < oLen) {
				result[op] = (byte) o2;
				op++;
			}
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}


	/*
	 * Private methods
	 */

	/**
	 * Encodes a byte-array into Base64 format.
	 * No blanks or line breaks are inserted.
	 *
	 * @param in	Array containing the data bytes to be encoded
	 * @param iLen Number of bytes to process in
	 * @return Character array with the Base64 encoded data
	 * @since 0.0.1
	 */
	private static char[] encode(final byte[] in, final int iLen) {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart(in, iLen));

		final int oDataLen = (iLen * 4 + 2) / 3; // output length without padding
		final int oLen = (iLen + 2) / 3 * 4; // output length including padding
		final char[] result = new char[oLen];
		int ip = 0;
		int op = 0;

		while (ip < iLen) {
			final int i0 = in[ip] & 0xff;
			ip++;
			final int i1 = ip < iLen ? in[ip] & 0xff : 0;
			ip++;
			final int i2 = ip < iLen ? in[ip] & 0xff : 0;
			ip++;
			final int o0 = i0 >>> 2;
			final int o1 = (i0 & 3) << 4 | i1 >>> 4;
			final int o2 = (i1 & 0xf) << 2 | i2 >>> 6;
			final int o3 = i2 & 0x3F;
			result[op] = MAP1[o0];
			op++;
			result[op] = MAP1[o1];
			op++;
			result[op] = op < oDataLen ? MAP1[o2] : '=';
			op++;
			result[op] = op < oDataLen ? MAP1[o3] : '=';
			op++;
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}
