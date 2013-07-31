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

package net.laubenberger.wichtel.helper.launcher;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.helper.HelperString;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This launcher starts the system browser and displays an URI.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public abstract class LauncherBrowser {
	private static final Logger log = LoggerFactory.getLogger(LauncherBrowser.class);

	/**
	 * Displays an {@link URI} in the default browser application.
	 *
	 * @param uri for the browser (e.g. "http://www.laubenberger.net/")
	 * @throws IOException
	 * @see URI
	 * @since 0.0.1
	 */
	public static void browse(final URI uri) throws IOException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(uri));
		if (null == uri) {
			throw new RuntimeExceptionIsNull("uri"); //$NON-NLS-1$
		}

		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().browse(uri);
		} else {
			throw new RuntimeException("Browser not supported by your machine"); //$NON-NLS-1$
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Displays an {@link String} in the default browser application.
	 *
	 * @param url for the browser (e.g. "www.laubenberger.net")
	 * @throws IOException
	 * @throws URISyntaxException
	 * @since 0.0.1
	 */
	public static void browse(final String url) throws IOException, URISyntaxException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(url));
		if (null == url) {
			throw new RuntimeExceptionIsNull("url"); //$NON-NLS-1$
		}
		if (!HelperString.isValid(url)) {
			throw new RuntimeExceptionIsEmpty("url"); //$NON-NLS-1$
		}

		final String prefix = "://"; //$NON-NLS-1$

		if (HelperString.contains(url, prefix)) {
			browse(new URI(url));
		} else {
			//best guess as protocol is http
			browse(new URI("http://" + url)); //$NON-NLS-1$
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
	
	/**
	 * Displays an {@link URL} in the default browser application.
	 *
	 * @param url for the browser (e.g. "http://www.laubenberger.net/")
	 * @throws IOException
	 * @throws URISyntaxException 
	 * @see URL
	 * @since 0.0.1
	 */
	public static void browse(final URL url) throws IOException, URISyntaxException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(url));

		browse(url.toURI());

		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
}
