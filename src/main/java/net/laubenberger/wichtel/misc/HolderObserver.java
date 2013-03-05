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

import java.util.Observable;
import java.util.Observer;


/**
 * Defines the methods for the observer holder (it also delegates the methods from {@link Observable}).
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public interface HolderObserver {
	void setNotifyEnabled(boolean enabled);

	boolean isNotifyEnabled();

	void addObserver(Observer o);

	void deleteObserver(Observer o);

	void notifyObservers();

	void notifyObservers(Object arg);

	void deleteObservers();

	boolean hasChanged();

	int countObservers();
}   