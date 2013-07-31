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

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

import net.laubenberger.wichtel.helper.HelperLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is the skeleton for SSL secured socket servers.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public abstract class ServerSSLAbstract extends ServerAbstract {
	private static final Logger log = LoggerFactory.getLogger(ServerSSLAbstract.class);
	
	protected ServerSSLAbstract() {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor());
	}

	protected ServerSSLAbstract(final int port, final int timeout) {
		super(port, timeout);
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(port, timeout));
	}

	protected ServerSSLAbstract(final int port) {
		super(port);
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(port));
	}


	/*
	 * Overridden methods
	 */

	@Override
	public void start() throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		setRunning(true);

		final ServerSocketFactory sslFactory = SSLServerSocketFactory.getDefault();
		final ServerSocket serverSocket = sslFactory.createServerSocket(getPort());

		if (0 < getTimeout()) {
			serverSocket.setSoTimeout(getTimeout());
		}

		setServerSocket(serverSocket);

		setThread(new Thread(this));
		getThread().start();
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
}
