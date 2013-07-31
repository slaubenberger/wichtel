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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for maps.
 * 
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public abstract class HelperMap {
	private static final Logger log = LoggerFactory.getLogger(HelperMap.class);

	/**
	 * Checks if a {@link Map} is valid.
	 * 
	 * @param arg
	 *           to check
	 * @return true/false
	 * @see Map
	 * @since 0.0.1
	 */
	public static boolean isValid(final Map<?, ?> arg) { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(arg));

		final boolean result = !(null == arg || arg.isEmpty());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Get a {@link List} of all keys from a {@link Map}.
	 * 
	 * @param map
	 *           to dump
	 * @return {@link List} containing all keys from a {@link Map}
	 * @see Map
	 * @since 0.0.1
	 */
	public static <K, V> List<K> getKeys(final Map<K, V> map) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(map));
		if (null == map) {
			throw new RuntimeExceptionIsNull("map"); //$NON-NLS-1$
		}

		final List<K> result = new ArrayList<>(map.size());

		for (final Entry<K, V> pair : map.entrySet()) {
			result.add(pair.getKey());
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Get a {@link List} of all values from a {@link Map}.
	 * 
	 * @param map
	 *           to dump
	 * @return {@link List} containing all values from a {@link Map}
	 * @see Map
	 * @since 0.0.1
	 */
	public static <K, V> List<V> getValues(final Map<K, V> map) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(map));
		if (null == map) {
			throw new RuntimeExceptionIsNull("map"); //$NON-NLS-1$
		}

		final List<V> result = new ArrayList<>(map.size());

		for (final Entry<K, V> pair : map.entrySet()) {
			result.add(pair.getValue());
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Dump a {@link Map}.
	 * 
	 * @param map
	 *           to dump
	 * @return dump string
	 * @see Map
	 * @since 0.0.1
	 */
	public static String dump(final Map<?, ?> map) { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(map));
		if (null == map) {
			throw new RuntimeExceptionIsNull("map"); //$NON-NLS-1$
		}

		final StringBuilder sb = new StringBuilder();

		for (final Entry<?, ?> pair : map.entrySet()) {
			if (0 < sb.length()) {
				sb.append(HelperString.NEW_LINE);
			}
			sb.append(pair.getKey());
			sb.append('=');
			sb.append(pair.getValue());
		}

		final String result = sb.toString();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
	
	/**
	 * Returns a 2D array of the given {@link Map}.
	 * 
	 * @param map for the array
	 * @return array with the map content
	 * @see Map
	 * @since 0.0.1
	 */
	public static Object[][] toArray(final Map<?, ?> map) { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(map));
		if (null == map) {
			throw new RuntimeExceptionIsNull("map"); //$NON-NLS-1$
		}	
		
		final Object[][] result = new Object[map.size()][2];

		
		final Iterator<?> iter = map.entrySet().iterator();

		int ii = 0;
		while(iter.hasNext()){
		    final Entry<?, ?> mapping = (Entry<?, ?>) iter.next();

		    result[ii][0] = mapping.getKey();
		    result[ii][1] = mapping.getValue();

		    ii++;
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}