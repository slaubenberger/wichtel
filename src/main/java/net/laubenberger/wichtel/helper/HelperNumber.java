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

import java.math.BigDecimal;

import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Helper class for numbers.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public abstract class HelperNumber {
	private static final Logger log = LoggerFactory.getLogger(HelperNumber.class);

	public static final BigDecimal NUMBER_8 = new BigDecimal("8"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_10 = BigDecimal.TEN;
	public static final BigDecimal NUMBER_100 = new BigDecimal("100"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_1000 = new BigDecimal("1000"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_10000 = new BigDecimal("10000"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_100000 = new BigDecimal("100000"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_1000000 = new BigDecimal("1000000"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_16 = new BigDecimal("16"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_32 = new BigDecimal("32"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_64 = new BigDecimal("64"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_128 = new BigDecimal("128"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_256 = new BigDecimal("256"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_512 = new BigDecimal("512"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_1024 = new BigDecimal("1024"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_2048 = new BigDecimal("2048"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_4096 = new BigDecimal("4096"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_8192 = new BigDecimal("8192"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_16384 = new BigDecimal("16384"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_32768 = new BigDecimal("32768"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_65536 = new BigDecimal("65536"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_131072 = new BigDecimal("131072"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_262144 = new BigDecimal("262144"); //$NON-NLS-1$
	public static final BigDecimal NUMBER_16777216 = new BigDecimal("16777216"); //$NON-NLS-1$


	/**
	 * Multiply 1-n values together.
	 *
	 * @param values 1-n values
	 * @return calculated value
	 * @since 0.0.1
	 */
	public static BigDecimal multiply(final Number... values) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(values));
		if (null == values) {
			throw new RuntimeExceptionIsNull("values"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(values)) {
			throw new RuntimeExceptionIsEmpty("values"); //$NON-NLS-1$
		}
		
		BigDecimal result = null;

		for (int ii = 0; ii < values.length; ii++) {
			result = new BigDecimal((0 == ii ? values[0] : multiply(result, values[ii])).toString());
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Multiply two values together.
	 *
	 * @param a first value
	 * @param b second value
	 * @return calculated value
	 * @since 0.0.1
	 */
	public static BigDecimal multiply(final Number a, final Number b) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(a, b));
		if (null == a) {
			throw new RuntimeExceptionIsNull("a"); //$NON-NLS-1$
		}
		if (null == b) {
			throw new RuntimeExceptionIsNull("b"); //$NON-NLS-1$
		}

		final BigDecimal numberA = new BigDecimal(a.toString());
		final BigDecimal numberB = new BigDecimal(b.toString());

		final BigDecimal result = numberA.multiply(numberB, Constants.DEFAULT_MATHCONTEXT);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Add 1-n values together.
	 *
	 * @param values 1-n values
	 * @return calculated value
	 * @since 0.0.1
	 */
	public static BigDecimal add(final Number... values) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(values));
		if (null == values) {
			throw new RuntimeExceptionIsNull("values"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(values)) {
			throw new RuntimeExceptionIsEmpty("values"); //$NON-NLS-1$
		}

		BigDecimal result = null;

		for (int ii = 0; ii < values.length; ii++) {
			result = new BigDecimal((0 == ii ? values[0] : add(result, values[ii])).toString());
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Add two values together.
	 *
	 * @param a first value
	 * @param b second value
	 * @return calculated value
	 * @since 0.0.1
	 */
	public static BigDecimal add(final Number a, final Number b) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(a, b));
		if (null == a) {
			throw new RuntimeExceptionIsNull("a"); //$NON-NLS-1$
		}
		if (null == b) {
			throw new RuntimeExceptionIsNull("b"); //$NON-NLS-1$
		}

		final BigDecimal numberA = new BigDecimal(a.toString());
		final BigDecimal numberB = new BigDecimal(b.toString());

		final BigDecimal result = numberA.add(numberB);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns a {@link BigDecimal} value from a {@link String}.
	 *
	 * @param text value to convert
	 * @return {@link BigDecimal} from the {@link String}
	 */
	public static BigDecimal getNumber(final String text) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(text));

		final String value = HelperString.getNumericString(text);

		BigDecimal result = null;

		if (null != value) {
			result = new BigDecimal(value);
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns a {@link String} value from a {@link Number}.
	 *
	 * @param number value to convert
	 * @return {@link String} from the {@link Number}
	 */
	public static String getString(final Number number) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(number));

		final String result = null == number ? null : number.toString();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}
