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

package net.laubenberger.wichtel.service.timer;


/**
 * Defines the methods for the implementation of the countdown timer.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public interface CountdownTimer extends TimeMachine {
	/**
	 * Starts immediately the countdown with a given runtime and standard interval of 1000ms.
	 *
	 * @param runtime of the countdown
	 * @since 0.0.1
	 */
	void start(long runtime);

	/**
	 * Start the countdown with a given delay, runtime and interval.
	 *
	 * @param delay	 until the timer starts
	 * @param runtime  of the countdown
	 * @param interval of the countdown
	 * @since 0.0.1
	 */
	void start(long delay, long runtime, long interval);
}

