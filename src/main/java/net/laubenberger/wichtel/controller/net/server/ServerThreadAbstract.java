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

package net.laubenberger.wichtel.controller.net.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.laubenberger.wichtel.helper.HelperArray;
import net.laubenberger.wichtel.helper.HelperIO;
import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.misc.Event;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.extendedObject.ExtendedObjectAbstract;


/**
 * Skeleton for socket server threads.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public abstract class ServerThreadAbstract extends ExtendedObjectAbstract implements ServerThread {
	private static final Logger log = LoggerFactory.getLogger(ServerThreadAbstract.class);
	
	private final Event<ServerThread> event = new Event<ServerThread>(this);

	private Thread thread;

	private final Collection<ListenerServerThread> listeners = new HashSet<>();

	private Socket socket;

	private boolean isRunning;


	protected ServerThreadAbstract(final Socket socket) {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(socket));
		
		setSocket(socket);
	}

	/**
	 * Returns the current {@link Thread}.
	 *
	 * @return thread
	 * @see Thread
	 * @since 0.0.1
	 */
	public Thread getThread() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(thread));
		return thread;
	}

	/**
	 * Sets the current {@link Thread}.
	 *
	 * @param thread for the client
	 * @see Thread
	 * @since 0.0.1
	 */
	protected void setThread(final Thread thread) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(thread));
		
		if (null == thread) {
			throw new RuntimeExceptionIsNull("thread"); //$NON-NLS-1$
		}

		this.thread = thread;
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}


	/*
	 * Private methods
	 */

	protected void fireStreamRead() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());
		
		for (final ListenerServerThread listener : listeners) {
			listener.serverThreadStreamRead(event);
		}
		
		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}

	protected void fireStarted() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());
		
		isRunning = true;

		for (final ListenerServerThread listener : listeners) {
			listener.serverThreadStarted(event);
		}
		
		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}

	protected void fireStopped() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());
		
		isRunning = false;

		for (final ListenerServerThread listener : listeners) {
			listener.serverThreadStopped(event);
		}
		
		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}


	/*
	 * Implemented methods
	 */

	@Override
	public Socket getSocket() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(socket));
		return socket;
	}

	@Override
	public void setSocket(final Socket socket) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(socket));
		
		if (null == socket) {
			throw new RuntimeExceptionIsNull("socket"); //$NON-NLS-1$
		}

		this.socket = socket;
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public byte[] readStream() throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		final InputStream is = socket.getInputStream();
		byte[] result = HelperArray.EMPTY_ARRAY_BYTE;
		byte input;

		do {
			input = (byte) is.read();

			if (-1 != input) {
				result = HelperArray.concatenate(result, new byte[]{input});
			}
		} while (-1 != input);

		if (null == result) { //client lost
			stop();
		}
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public void writeStream(final byte... data) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(data));
		
		HelperIO.writeStream(socket.getOutputStream(), HelperArray.concatenate(data, new byte[]{(byte) -1}));
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}


	@Override
	public void start() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
//		if (null == thread) {
		thread = new Thread(this);
		thread.start();
//		}

		fireStarted();
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void stop() throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		fireStopped();

		if (null != socket && !socket.isClosed()) {
			socket.close();
		}

		if (null != thread) {
			if (thread.isAlive()) {
				thread.interrupt();
//			} else {
//				thread = null;
			}
		}
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public boolean isRunning() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(isRunning));
		return isRunning;
	}

	@Override
	public void addListener(final ListenerServerThread listener) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(listener));
		
		if (null == listener) {
			throw new RuntimeExceptionIsNull("listener"); //$NON-NLS-1$
		}

		listeners.add(listener);
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void deleteListener(final ListenerServerThread listener) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(listener));
		
		if (null == listener) {
			throw new RuntimeExceptionIsNull("listener"); //$NON-NLS-1$
		}

		listeners.remove(listener);
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
}
