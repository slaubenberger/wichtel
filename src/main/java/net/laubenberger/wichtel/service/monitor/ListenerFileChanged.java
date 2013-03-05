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

import net.laubenberger.wichtel.misc.Event;
import net.laubenberger.wichtel.misc.Listener;

/**
 * ListenerFileChanged
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public interface ListenerFileChanged extends Listener {
	/**
	 * Informs the listener that the monitor has started.
	 *
	 * @param event for the listener
	 * @since 0.0.1
	 */
	void monitorStarted(Event<MonitorFile> event);

	/**
	 * Informs the listener that the monitor has stopped.
	 *
	 * @param event for the listener
	 * @since 0.0.1
	 */
	void monitorStopped(Event<MonitorFile> event);
	
	/**
	 * Informs the listener that the file has changed.
	 *
	 * @param event for the listener
	 * @since 0.0.1
	 */
	void fileChanged(Event<MonitorFile> event);

	/**
	 * Informs the listener that the file has not been found.
	 *
	 * @param event for the listener
	 * @since 0.0.1
	 */
	void fileNotFound(Event<MonitorFile> event);
}
