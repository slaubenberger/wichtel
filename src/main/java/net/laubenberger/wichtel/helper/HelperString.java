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

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Helper class for strings.
 *
 * @author Stefan Laubenberger
 * @version 0.0.2, 2013-03-14
 * @since 0.0.1
 */
public abstract class HelperString {
	private static final Logger log = LoggerFactory.getLogger(HelperString.class);

	public static final String NEW_LINE = System.lineSeparator();

	public static final String EMPTY_STRING = ""; //$NON-NLS-1$
	public static final String TAB = "\t"; //$NON-NLS-1$
	public static final String SINGLE_QUOTE = "'"; //$NON-NLS-1$
	public static final String DOUBLE_QUOTE = "\""; //$NON-NLS-1$
	public static final String SPACE = " "; //$NON-NLS-1$
	public static final String PERIOD = "."; //$NON-NLS-1$
	public static final String COMMA = ","; //$NON-NLS-1$
	public static final String SEMICOLON = ";"; //$NON-NLS-1$
	public static final String COLON = ":"; //$NON-NLS-1$
	public static final String AMPERSAND = "&"; //$NON-NLS-1$
	public static final String COPYRIGHT = "©"; //$NON-NLS-1$
	public static final String REGISTERED = "®"; //$NON-NLS-1$
	public static final String TRADEMARK = "™"; //$NON-NLS-1$
	public static final String NUMBER = "№"; //$NON-NLS-1$
	public static final String PLUS_SIGN = "+"; //$NON-NLS-1$
	public static final String NEGATIVE_SIGN = "-"; //$NON-NLS-1$
	public static final String PERCENT = "%"; //$NON-NLS-1$
	public static final String PERMILLE = "‰"; //$NON-NLS-1$
	public static final String PERMYRIAD = "‱"; //$NON-NLS-1$
	public static final String AT = "@"; //$NON-NLS-1$
	public static final String TILDE = "~"; //$NON-NLS-1$
	public static final String PI = "µ"; //$NON-NLS-1$
	public static final String POWER_OF_2 = "²"; //$NON-NLS-1$
	public static final String POWER_OF_3 = "³"; //$NON-NLS-1$

	private static final Pattern PATTERN = Pattern.compile("[^0-9.]+"); //$NON-NLS-1$
	
	/**
	 * Checks if a {@link CharSequence} is valid.
	 *
	 * @param input to check
	 * @return true/false
	 * @see CharSequence
	 * @since 0.0.1
	 */
	public static boolean isValid(final CharSequence input) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));

		final boolean result = !(null == input || 0 == input.length());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Checks if a {@link String} is full numeric.
	 *
	 * @param input to check
	 * @return true/false
	 * @since 0.0.1
	 */
	public static boolean isNumeric(final String input) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}

		boolean result =  input.matches("-?\\d+(\\.\\d+)?"); //$NON-NLS-1$
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Fill a {@link String} with a char.
	 *
	 * @param fillChar	char to fill the string
	 * @param fillLength length of the filled string
	 * @return filled {@link String}
	 * @since 0.0.1
	 */
	public static String fill(final char fillChar, final int fillLength) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(fillChar, fillLength));
		if (0 >= fillLength) {
			throw new RuntimeExceptionMustBeGreater("fillLength", fillLength, 0); //$NON-NLS-1$
		}

		int length = fillLength;
		final char[] chars = new char[length];

		while (0 < length) {
			--length;
			chars[length] = fillChar;
		}
		final String result = new String(chars);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Reverses a {@link String}.
	 *
	 * @param input {@link String}
	 * @return reversed {@link String}
	 * @since 0.0.1
	 */
	public static String reverse(final String input) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		
		final String result = new StringBuilder(input).reverse().toString();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Clean a {@link String} to numeric chars.
	 *
	 * @param input {@link String}
	 * @return numeric {@link String}
	 * @since 0.0.1
	 */
	public static String getNumericString(final String input) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		
		String result = null;
		if (isValid(input)) {
			boolean isNegative = false;
			if (input.startsWith(NEGATIVE_SIGN)) {
				isNegative = true;
			}
	
			final Matcher matcher = PATTERN.matcher(input);
			final String temp = matcher.replaceAll(EMPTY_STRING);
			
			boolean isPeriod = false;
			final StringBuilder sb = new StringBuilder(temp.length());
	
			// remove multiple periods
			for (int ii = 0; ii < temp.length(); ii++) {
				final char character = temp.charAt(ii);
	
				if ('.' == character) {
					if (!isPeriod) {
						sb.append(PERIOD);
						isPeriod = true;
					}
				} else {
					sb.append(character);
				}
			}
	
			if (!(temp.isEmpty() || isPeriod && 1 == sb.length())) {
				result = isNegative ? NEGATIVE_SIGN + sb.toString() : sb.toString();
			}
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Concatenates strings with a separator (e.g. for CSV export).
	 *
	 * @param separator between the strings
	 * @param isTrimmed true/false
	 * @param strings	to concatenate
	 * @return concatenated {@link String}
	 * @since 0.0.1
	 */
	public static String concatenate(final String[] strings, final String separator, final boolean isTrimmed) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(strings, separator, isTrimmed));
		if (null == strings) {
			throw new RuntimeExceptionIsNull("strings"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(strings)) {
			throw new RuntimeExceptionIsEmpty("strings"); //$NON-NLS-1$
		}

		final StringBuilder sb = new StringBuilder();

		for (final String string : strings) {
			if (null == string) {
				throw new RuntimeExceptionIsNull("string"); //$NON-NLS-1$
			}

			if (null != separator && 0 < sb.length()) {
				sb.append(separator);
			}

			if (isTrimmed) {
				sb.append(string.trim());
			} else {
				sb.append(string);
			}
		}
		final String result = sb.toString();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Concatenates strings.
	 *
	 * @param strings to concatenate
	 * @return concatenated {@link String}
	 * @since 0.0.1
	 */
	public static String concatenate(final String... strings) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(strings));

		final String result = concatenate(strings, null, true);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

//	/**
//	 * Returns a {@link String} from a given byte-array and encoding.
//	 *
//	 * @param data	  for the {@link String}
//	 *                 //     * @param length of the {@link String}
//	 * @param encoding of the given data
//	 * @return new {@link String}
//	 * @throws UnsupportedEncodingException
//	 * @since 0.0.1
//	 */
//	public static String toString(final byte[] data, final String encoding) throws UnsupportedEncodingException {
//		log.debug(HelperLog.methodStart(data, encoding));
//		if (null == data) {
//			throw new RuntimeExceptionIsNull("data"); //$NON-NLS-1$
//		}
//		if (null == encoding) {
//			throw new RuntimeExceptionIsNull("encoding"); //$NON-NLS-1$
//		}
//		if (!isValid(encoding)) {
//			throw new RuntimeExceptionIsEmpty("encoding"); //$NON-NLS-1$
//		}
//
//		final String result = new String(data, encoding);
//
//		log.debug(HelperLog.methodExit(result));
//		return result;
//	}
//
//	/**
//	 * Returns a byte-array from a given {@link String} and encoding.
//	 *
//	 * @param input	 {@link String} for the byte-array
//	 * @param encoding of the given {@link String}
//	 * @return {@link String} as byte-array
//	 * @throws UnsupportedEncodingException
//	 * @since 0.0.1
//	 */
//	public static byte[] toBytes(final String input, final String encoding) throws UnsupportedEncodingException {
//		log.debug(HelperLog.methodStart(input, encoding));
//		if (null == input) {
//			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
//		}
//		if (null == encoding) {
//			throw new RuntimeExceptionIsNull("encoding"); //$NON-NLS-1$
//		}
//		if (!isValid(encoding)) {
//			throw new RuntimeExceptionIsEmpty("encoding"); //$NON-NLS-1$
//		}
//
//
//		final byte[] result = input.getBytes(encoding);
//
//		log.debug(HelperLog.methodExit(result));
//		return result;
//	}

	/**
	 * The same as startsWith() from {@link String}, but ignores the case.
	 *
	 * @param string to inspect
	 * @param prefix to find at the start of the string
	 * @return true/false
	 * @since 0.0.1
	 */
	public static boolean startsWith(final String string, final String prefix) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(string, prefix));
		if (null == string) {
			throw new RuntimeExceptionIsNull("string"); //$NON-NLS-1$
		}
		if (null == prefix) {
			throw new RuntimeExceptionIsNull("prefix"); //$NON-NLS-1$
		}
		if (!isValid(prefix)) {
			throw new RuntimeExceptionIsEmpty("prefix"); //$NON-NLS-1$
		}

//		final boolean result = string.matches("(?i)" + prefix + ".*");  //$NON-NLS-1$//$NON-NLS-2$
		final boolean result = string.toUpperCase(Locale.getDefault()).startsWith(prefix.toUpperCase(Locale.getDefault()));
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * The same as endsWith() from {@link String}, but ignores the case.
	 *
	 * @param string to inspect
	 * @param suffix to find at the end of the string
	 * @return true/false
	 * @since 0.0.1
	 */
	public static boolean endsWith(final String string, final String suffix) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(string, suffix));
		if (null == string) {
			throw new RuntimeExceptionIsNull("string"); //$NON-NLS-1$
		}
		if (null == suffix) {
			throw new RuntimeExceptionIsNull("suffix"); //$NON-NLS-1$
		}
		if (!isValid(suffix)) {
			throw new RuntimeExceptionIsEmpty("suffix"); //$NON-NLS-1$
		}

//		final boolean result = string.matches("(?i).*" + suffix); //$NON-NLS-1$
		final boolean result = string.toUpperCase(Locale.getDefault()).endsWith(suffix.toUpperCase(Locale.getDefault()));

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * The same as contains() from {@link String}, but ignores the case.
	 *
	 * @param string to inspect
	 * @param part	to find in the string
	 * @return true/false
	 * @since 0.0.1
	 */
	public static boolean contains(final String string, final String part) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(string, part));
		if (null == string) {
			throw new RuntimeExceptionIsNull("string"); //$NON-NLS-1$
		}
		if (null == part) {
			throw new RuntimeExceptionIsNull("part"); //$NON-NLS-1$
		}
		if (!isValid(part)) {
			throw new RuntimeExceptionIsEmpty("part"); //$NON-NLS-1$
		}

//		final boolean result = string.matches("(?i).*" + part + ".*");  //$NON-NLS-1$//$NON-NLS-2$
		final boolean result = string.toUpperCase(Locale.getDefault()).contains(part.toUpperCase(Locale.getDefault()));

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
	

	/**
	 * Quote a {@link String} with a given quote sign.
	 *
	 * @param input {@link String}
	 * @return quoted {@link String}
	 * @since 0.0.1
	 */
	public static String quote(final String input, final String quoteSign) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		
		if (null == quoteSign) {
			throw new RuntimeExceptionIsNull("quoteSign"); //$NON-NLS-1$
		}		
		
		final String result = quoteSign + input + quoteSign;

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Quote a {@link String} with single quotes.
	 *
	 * @param input {@link String}
	 * @return quoted {@link String}
	 * @since 0.0.1
	 */
	public static String quote(final String input) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));
		
		final String result = quote(input, SINGLE_QUOTE);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}