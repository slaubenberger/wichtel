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
import java.net.Socket;

import net.laubenberger.wichtel.misc.HolderListener;


/**
 * Defines the methods for the implementation of the datagram monitor.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public interface MonitorDatagram extends Monitor, Runnable, HolderListener<ListenerDatagram> {
	/**
	 * Returns the port of the {@link Socket}.
	 *
	 * @return port
	 * @since 0.0.1
	 */
	int getPort();

	/**
	 * Sets the port (0 - 65535) for the {@link Socket}.
	 *
	 * @param port of the server
	 * @since 0.0.1
	 */
	void setPort(int port);

	/**
	 * Returns the current {@link DatagramPacket}.
	 *
	 * @return current {@link DatagramPacket}
	 * @see DatagramPacket
	 * @since 0.0.1
	 */
	DatagramPacket getPacket();
}
