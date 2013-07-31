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

package net.laubenberger.wichtel.service.monitor;

import net.laubenberger.wichtel.service.Service;


/**
 * Defines the methods for the implementation of a monitor.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public interface Monitor extends Service {
	/**
	 * Starts immediately the monitor.
	 *
	 * @since 0.0.1
	 */
	void start();
	
	/**
	 * Stops immediately the monitor.
	 *
	 * @since 0.0.1
	 */
	void stop();
	
	/**
	 * Returns the state of the monitor.
	 *
	 * @return true/false
	 * @since 0.0.1
	 */
	boolean isRunning();
}
