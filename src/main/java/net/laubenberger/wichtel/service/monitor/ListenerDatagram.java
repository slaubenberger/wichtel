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

import java.net.DatagramPacket;

import net.laubenberger.wichtel.misc.Event;
import net.laubenberger.wichtel.misc.Listener;

/**
 * ListenerDatagram
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public interface ListenerDatagram extends Listener {
	/**
	 * Informs the listener that the monitor has started.
	 *
	 * @param event for the listener
	 * @since 0.0.1
	 */
	void monitorStarted(Event<MonitorDatagram> event);

	/**
	 * Informs the listener that the monitor has stopped.
	 *
	 * @param event for the listener
	 * @since 0.0.1
	 */
	void monitorStopped(Event<MonitorDatagram> event);
	
	/**
	 * Informs the listener that a {@link DatagramPacket} was received.
	 *
	 * @param event for the listener
	 * @see DatagramPacket
	 * @since 0.0.1
	 */
	void packetReceived(Event<MonitorDatagram> event);
}
