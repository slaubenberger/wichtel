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
import java.net.ServerSocket;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.helper.HelperNumber;
import net.laubenberger.wichtel.misc.Event;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeSmaller;
import net.laubenberger.wichtel.misc.extendedObject.ExtendedObjectAbstract;


/**
 * Skeleton for socket servers.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public abstract class ServerAbstract extends ExtendedObjectAbstract implements Server, ListenerServerThread {
	private static final Logger log = LoggerFactory.getLogger(ServerAbstract.class);
	
	private Thread thread;

	private final Collection<ServerThread> threads = new HashSet<>();

//    private final Map<UUID, ServerThread> mapThread = new ConcurrentHashMap<UUID, ServerThread>();

	private ServerSocket serverSocket;
	private int port;
	private int timeout; //ServerSocketSocket timeout in milliseconds

	private boolean isRunning;

	protected ServerAbstract() {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor());
	}

	protected ServerAbstract(final int port, final int timeout) {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(port, timeout));

		setPort(port);
		setTimeout(timeout);
	}

	protected ServerAbstract(final int port) {
		this(port, 0);
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(port));
	}

	/**
	 * Returns the current {@link Thread} of the server.
	 *
	 * @return thread of the server
	 * @see Thread
	 * @since 0.0.1
	 */
	public Thread getThread() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(thread));
		return thread;
	}

	/**
	 * Sets the current {@link Thread} for the server.
	 *
	 * @param thread for the server
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

	protected void setRunning(final boolean isRunning) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(isRunning));
		
		this.isRunning = isRunning;
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}


	/*
	 * Implemented methods
	 */

	@Override
	public ServerSocket getServerSocket() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(serverSocket));
		return serverSocket;
	}

	@Override
	public int getPort() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(port));
		return port;
	}

	@Override
	public int getTimeout() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(timeout));
		return timeout;
	}

	@Override
	public void setPort(final int port) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(port));
		
		if (0 >= port) {
			throw new RuntimeExceptionMustBeGreater("port", port, 0); //$NON-NLS-1$
		}
		if (HelperNumber.NUMBER_65536.intValue() <= port) {
			throw new RuntimeExceptionMustBeSmaller("port", port, 65535); //$NON-NLS-1$
		}

		this.port = port;
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void setServerSocket(final ServerSocket serverSocket) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(serverSocket));
		
		if (null == serverSocket) {
			throw new RuntimeExceptionIsNull("serverSocket"); //$NON-NLS-1$
		}

		this.serverSocket = serverSocket;
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void setTimeout(final int timeout) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(timeout));
		
		if (0 > timeout) {
			throw new RuntimeExceptionMustBeGreater("timeout", timeout, 0); //$NON-NLS-1$
		}

		this.timeout = timeout;
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

//    public void addServerThread(final UUID uuid, final ServerThread serverThread) {
//        mapThread.put(uuid, serverThread);
//    }
//
//    public void removeServerThread(final UUID uuid) {
//        mapThread.remove(uuid);
//    }
//

	@Override
	public Collection<ServerThread> getServerThreads() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(threads));
		return Collections.unmodifiableCollection(threads);
	}

	@Override
	public void start() throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		serverSocket = new ServerSocket(port);

		if (0 < timeout) {
			serverSocket.setSoTimeout(timeout);
		}

		thread = new Thread(this);
		thread.start();

		isRunning = true;
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void stop() throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		isRunning = false;

		if (null != serverSocket && !serverSocket.isClosed()) {
			serverSocket.close();
		}

		for (final ServerThread thread : threads) {
			thread.stop();
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
	public void serverThreadStarted(final Event<ServerThread> event) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(event));
		
		if (null == event) {
			throw new RuntimeExceptionIsNull("event"); //$NON-NLS-1$
		}

		threads.add(event.getSource());
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void serverThreadStopped(final Event<ServerThread> event) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(event));
		
		if (null == event) {
			throw new RuntimeExceptionIsNull("event"); //$NON-NLS-1$
		}

		threads.remove(event.getSource());
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
}
