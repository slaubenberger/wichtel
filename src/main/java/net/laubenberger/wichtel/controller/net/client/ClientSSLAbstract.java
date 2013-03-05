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

package net.laubenberger.wichtel.controller.net.client;

import javax.net.ssl.SSLSocketFactory;
import javax.net.SocketFactory;

import net.laubenberger.wichtel.helper.HelperLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Skeleton for SSL secured clients.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public abstract class ClientSSLAbstract extends ClientAbstract {
	private static final Logger log = LoggerFactory.getLogger(ClientSSLAbstract.class);
	
	protected ClientSSLAbstract(final String host, final int port) {
		super(host, port);
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(host, port));
	}


	/*
	 * Overridden methods
	 */

	@Override
	public void start() throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		final SocketFactory sslFactory = SSLSocketFactory.getDefault();

		setSocket(sslFactory.createSocket(getHost(), getPort()));

		setThread(new Thread(this));
		getThread().start();

		fireStarted();
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
}
