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

package net.laubenberger.wichtel.controller.net.client;

import java.io.IOException;
import java.net.Socket;

import net.laubenberger.wichtel.misc.HolderListener;
import net.laubenberger.wichtel.misc.extendedObject.ExtendedObject;


/**
 * Defines the methods for the implementation of a socket client.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public interface Client extends ExtendedObject, Runnable, HolderListener<ListenerClient> {
	/**
	 * Returns the current data from readStream().
	 *
	 * @return current read data
	 * @since 0.0.1
	 */
	byte[] getData();

	/**
	 * Returns the host of the {@link Socket}.
	 *
	 * @return host, not null
	 * @since 0.0.1
	 */
	String getHost();

	/**
	 * Sets the host for the {@code Socket}.
	 *
	 * @param host  hostname or ip of the server, not null
	 * @since 0.0.1
	 */
	void setHost(String host);

	/**
	 * Returns the port of the {@code Socket}.
	 *
	 * @return port, from 0 to 65535
	 * @since 0.0.1
	 */
	int getPort();

	/**
	 * Sets the port for the {@code Socket}.
	 *
	 * @param port  port of the server, from 0 to 65535
	 * @since 0.0.1
	 */
	void setPort(int port);

	/**
	 * Returns the current {@code Socket} of the client.
	 *
	 * @return {@code Socket} of the client, not null
	 * @since 0.0.1
	 */
	Socket getSocket();

	/**
	 * Sets the {@code Socket} for the client.
	 *
	 * @param socket  socket for the client, not null
	 * @see Socket
	 * @since 0.0.1
	 */
	void setSocket(Socket socket);

	/**
	 * Start the client and open the {@code Socket}.
	 *
	 * @throws IOException
	 * @since 0.0.1
	 */
	void start() throws IOException;

	/**
	 * Stop the client and close the {@code Socket}.
	 *
	 * @throws IOException
	 * @since 0.0.1
	 */
	void stop() throws IOException;

	/**
	 * Returns the state of the connection from the client to the server.
	 *
	 * @return true/false
	 * @since 0.0.1
	 */
	boolean isRunning();

	/**
	 * Reads a socket-stream into a byte-array.
	 *
	 * @return byte-array, null if something went wrong processed 
	 * @throws IOException
	 * @since 0.0.1
	 */
	byte[] readStream() throws IOException;

	/**
	 * Writes on a socket-stream from a byte-array.
	 *
	 * @param data  write bytes on a socket
	 * @throws IOException
	 * @since 0.0.1
	 */
	void writeStream(byte... data) throws IOException;
}   
