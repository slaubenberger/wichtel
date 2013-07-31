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

package net.laubenberger.wichtel.helper.encoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.laubenberger.wichtel.helper.HelperEnvironment;
import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionExceedsVmMemory;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;


/**
 * Encodes and decodes data to Hex format.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public abstract class EncoderHex {
	private static final Logger log = LoggerFactory.getLogger(EncoderHex.class);

	/**
	 * Encodes a byte-array to a hex {@link String}.
	 *
	 * @param input bytes to be converted
	 * @return hex representation of a byte array
	 * @since 0.0.1
	 */
	public static String encode(final byte... input) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (input.length * 2 > HelperEnvironment.getMemoryFree()) {
			throw new RuntimeExceptionExceedsVmMemory("input", input.length * 2); //$NON-NLS-1$
		}

		final StringBuilder hexString = new StringBuilder(input.length * 2);

		for (final byte digest : input) {
			final String hex = Integer.toHexString(0xFF & digest);
			if (1 == hex.length()) {
				hexString.append('0');
			}
			hexString.append(hex);
		}

		final String result = hexString.toString();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Decodes a hex {@link String} to a byte-array.
	 *
	 * @param input hex string to be converted
	 * @return byte-array representation of a hex string
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

		final byte[] result = new byte[input.length() / 2];

		for (int ii = 0; ii < result.length; ii++) {
			result[ii] = (byte) Integer.parseInt(input.substring(2 * ii, 2 * ii + 2), 16);
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}
