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

package net.laubenberger.wichtel.service.monitor;

import java.io.File;

import net.laubenberger.wichtel.misc.HolderListener;


/**
 * Defines the methods for the implementation of a file monitor.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public interface MonitorFile extends Monitor, HolderListener<ListenerFileChanged> {
	/**
	 * Starts immediately the monitor with a given interval.
	 *
	 * @param interval of the monitor
	 * @since 0.0.1
	 */
	void start(long interval);

	/**
	 * Starts the monitor with a given delay and interval.
	 *
	 * @param delay	 until the monitor starts
	 * @param interval of the monitor
	 * @since 0.0.1
	 */
	void start(long delay, long interval);
	
	/**
	 * Returns the current interval in ms of the monitor.
	 *
	 * @return current time of the timer
	 * @since 0.0.1
	 */
	long getInterval();
	
	/**
	 * Returns the file from the monitor.
	 *
	 * @return {@link File}
	 * @since 0.0.1
	 */
	File getFile();
}
