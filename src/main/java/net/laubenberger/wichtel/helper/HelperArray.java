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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Helper class for arrays.
 *
 * @author Stefan Laubenberger
 * @version 0.2.0, 2014-05-12
 * @since 0.0.1
 */
public final class HelperArray { //TODO implement all methods for all primitive types
	private static final Logger log = LoggerFactory.getLogger(HelperArray.class);

	public static final Class<?>[] EMPTY_ARRAY_CLASS = new Class[0];
	public static final Object[] EMPTY_ARRAY_OBJECT = new Object[0];
	public static final String[] EMPTY_ARRAY_STRING = new String[0];
	public static final Boolean[] EMPTY_ARRAY_BOOLEAN = new Boolean[0];
	public static final Double[] EMPTY_ARRAY_DOUBLE = new Double[0];
	public static final Integer[] EMPTY_ARRAY_INTEGER = new Integer[0];
	public static final Float[] EMPTY_ARRAY_FLOAT = new Float[0];
	public static final byte[] EMPTY_ARRAY_BYTE = new byte[0];
	public static final char[] EMPTY_ARRAY_CHAR = new char[0];
	public static final Long[] EMPTY_ARRAY_LONG = new Long[0];
	public static final Short[] EMPTY_ARRAY_SHORT = new Short[0];
	public static final BigInteger[] EMPTY_ARRAY_BIG_INTEGER = new BigInteger[0];
	public static final BigDecimal[] EMPTY_ARRAY_BIG_DECIMAL = new BigDecimal[0];

    private HelperArray() {
        //do nothing
    }

	/**
	 * Checks if an array is valid.
	 *
	 * @param arg to check
	 * @return true/false
	 * @since 0.0.1
	 */
	public static boolean isValid(final Object... arg) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(arg));

		final boolean result = !(null == arg || 0 == arg.length);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Checks if a byte-array is valid.
	 *
	 * @param arg to check
	 * @return true/false
	 * @since 0.0.1
	 */
	public static boolean isValid(final byte... arg) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(arg));

		final boolean result = !(null == arg || 0 == arg.length);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Checks if a char-array is valid.
	 *
	 * @param arg to check
	 * @return true/false
	 * @since 0.0.1
	 */
	public static boolean isValid(final char... arg) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(arg));

		final boolean result = !(null == arg || 0 == arg.length);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return !(null == arg || 0 == arg.length);
	}

	/**
	 * Concatenate 1-n arrays to one array.
	 *
	 * @param arrays to concatenate
	 * @return concatenated result array
	 * @since 0.0.1
	 */
	public static <T> T[] concatenate(final T[]... arrays) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(new Object[]{arrays}));
		if (null == arrays) {
			throw new RuntimeExceptionIsNull("arrays"); //$NON-NLS-1$
		}
		if (!isValid(new Object[]{arrays})) {
			throw new RuntimeExceptionIsEmpty("arrays"); //$NON-NLS-1$
		}

		final List<T> resultList = new ArrayList<>();

		for (final T[] array : arrays) {
			if (null == array) {
				throw new RuntimeExceptionIsNull("array"); //$NON-NLS-1$
			}
			resultList.addAll(Arrays.asList(array));
		}

		final T[] result = resultList.toArray(arrays[0]);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Concatenate 1-n byte-arrays to one byte-array.
	 *
	 * @param arrays to concatenate
	 * @return concatenated result array
	 * @since 0.0.1
	 */
	public static byte[] concatenate(final byte[]... arrays) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(new Object[]{arrays}));
		if (null == arrays) {
			throw new RuntimeExceptionIsNull("arrays"); //$NON-NLS-1$
		}
		if (!isValid(new Object[]{arrays})) {
			throw new RuntimeExceptionIsEmpty("arrays"); //$NON-NLS-1$
		}

		int totalSize = 0;

		for (final byte[] array : arrays) {
			if (null == array) {
				throw new RuntimeExceptionIsNull("array"); //$NON-NLS-1$
			}
			totalSize += array.length;
		}

		final byte[] result = new byte[totalSize];
		int offset = 0;

		for (final byte[] array : arrays) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Concatenate 1-n char-arrays to one char-array.
	 *
	 * @param arrays to concatenate
	 * @return concatenated result array
	 * @since 0.0.1
	 */
	public static char[] concatenate(final char[]... arrays) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(new Object[]{arrays}));
		if (null == arrays) {
			throw new RuntimeExceptionIsNull("arrays"); //$NON-NLS-1$
		}
		if (!isValid(new Object[]{arrays})) {
			throw new RuntimeExceptionIsEmpty("arrays"); //$NON-NLS-1$
		}

		int totalSize = 0;

		for (final char[] array : arrays) {
			if (null == array) {
				throw new RuntimeExceptionIsNull("array"); //$NON-NLS-1$
			}
			totalSize += array.length;
		}

		final char[] result = new char[totalSize];
		int offset = 0;

		for (final char[] array : arrays) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Removes duplicate objects from an array.
	 *
	 * @param array containing duplicate objects
	 * @return array without duplicates
	 * @since 0.0.1
	 */
	public static <T> T[] removeDuplicates(final T... array) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(array));
		if (null == array) {
			throw new RuntimeExceptionIsNull("array"); //$NON-NLS-1$
		}

		final T[] result = HelperCollection.toArray(HelperCollection.removeDuplicates(Arrays.asList(array)));

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Checks if an array contains an given object.
	 *
	 * @param array  to check
	 * @param object to search in the array
	 * @return true/false
	 * @since 0.0.1
	 */
	public static <T> boolean contains(final T[] array, final T object) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(array, object));
		if (null == array) {
			throw new RuntimeExceptionIsNull("array"); //$NON-NLS-1$
		}
		
		final boolean result = Arrays.asList(array).contains(object);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Dump an array.
	 *
	 * @param array to dump
	 * @return dump string
	 * @since 0.0.1
	 */
	public static String dump(final Object... array) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(array));
		if (null == array) {
			throw new RuntimeExceptionIsNull("array"); //$NON-NLS-1$
		}

		final StringBuilder sb = new StringBuilder();

		for (final Object element : array) {
			if (0 < sb.length()) {
				sb.append(HelperString.NEW_LINE);
			}
			sb.append(element);
		}

		final String result = sb.toString();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Dump a byte-array.
	 *
	 * @param array to dump
	 * @return dump string
	 * @since 0.0.1
	 */
	public static String dump(final byte... array) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(array));
		if (null == array) {
			throw new RuntimeExceptionIsNull("array"); //$NON-NLS-1$
		}

		final StringBuilder sb = new StringBuilder();

		for (final byte element : array) {
			if (0 < sb.length()) {
				sb.append(HelperString.NEW_LINE);
			}
			sb.append(element);
		}

		final String result = sb.toString();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Dump a char-array.
	 *
	 * @param array to dump
	 * @return dump string
	 * @since 0.0.1
	 */
	public static String dump(final char... array) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(array));
		if (null == array) {
			throw new RuntimeExceptionIsNull("array"); //$NON-NLS-1$
		}
		
		final StringBuilder sb = new StringBuilder();

		for (final char element : array) {
			if (0 < sb.length()) {
				sb.append(HelperString.NEW_LINE);
			}
			sb.append(element);
		}

		final String result = sb.toString();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
	
	/**
	 * Returns an array with the given elements.
	 *
	 * @param elements for the array
	 * @return array with the given elements
	 * @since 0.0.1
	 */
	public static <E> E[] getArray(final E... elements) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(elements));
//		if (!isValid(elements)) {
//			throw new RuntimeExceptionIsNullOrEmpty("elements"); //$NON-NLS-1$
//		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(elements));
		return elements;
	}
}