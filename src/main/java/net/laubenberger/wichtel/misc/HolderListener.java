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

package net.laubenberger.wichtel.misc;


/**
 * Defines the methods for the listener holder (observable).
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public interface HolderListener<L extends Listener> {
	/**
	 * Add a listener.
	 *
	 * @param listener to add
	 * @since 0.0.1
	 */
	void addListener(L listener);

	/**
	 * Remove and delete a listener.
	 * <strong>Note:</strong> This method could be type-safe via covariance in the implementation
	 *
	 * @param listener to remove
	 * @since 0.0.1
	 */
	void deleteListener(L listener);
//	<T extends Listener> void deleteListener(T listener);

//	/**
//	 * Removes and deletes all registered listeners.
//	 *
//	 * @since 0.0.1
//	 */
//	void deleteListeners();
//
//	/**
//	 * Counts all registered listeners.
//	 *
//	 * @since 0.0.1
//	 */
//	int countListeners();
}   