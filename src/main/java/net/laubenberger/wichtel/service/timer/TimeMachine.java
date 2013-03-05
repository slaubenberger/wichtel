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

package net.laubenberger.wichtel.service.timer;

import net.laubenberger.wichtel.misc.HolderListener;
import net.laubenberger.wichtel.service.Service;


/**
 * Defines the methods for the implementation of the timer.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public interface TimeMachine extends Service, HolderListener<ListenerTimer> {
	/**
	 * Returns the running state of the timer.
	 *
	 * @return true/false
	 * @since 0.0.1
	 */
	boolean isRunning();

	/**
	 * Returns the current interval in ms of the timer.
	 *
	 * @return current time of the timer
	 * @since 0.0.1
	 */
	long getInterval();

	/**
	 * Stops immediately the timer.
	 * 
	 * @since 0.0.1
	 */
	void stop();

	/**
	 * Returns the current time in ms of the timer.
	 *
	 * @return current time of the timer
	 * @since 0.0.1
	 */
	long getTime();

	/**
	 * Sets the time in ms of the timer.
	 *
	 * @param time
	 * @since 0.0.1
	 */
	void setTime(long time);
}   

