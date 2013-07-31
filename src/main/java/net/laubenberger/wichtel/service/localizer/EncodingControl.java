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

package net.laubenberger.wichtel.service.localizer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Encoding control for the {@link LocalizerFile}.
 * 
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public class EncodingControl extends Control {
	private static final Logger log = LoggerFactory.getLogger(EncodingControl.class);

	private final String encoding;
	
	
	public EncodingControl() {
		this(Constants.ENCODING_DEFAULT);
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor());

	}
	
	public EncodingControl(final String encoding) {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(encoding));

		if (null == encoding) {
			throw new RuntimeExceptionIsNull("encoding"); //$NON-NLS-1$
		}
		
		this.encoding = encoding;
	}

	@Override
	public ResourceBundle newBundle(final String baseName, final Locale locale, final String format, final ClassLoader loader, final boolean reload)
			throws IllegalAccessException, InstantiationException, IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(baseName, locale, format, loader, reload));

		final String bundleName = toBundleName(baseName, locale);
		final String resourceName = toResourceName(bundleName, "properties"); //$NON-NLS-1$
		ResourceBundle result = null;
		InputStream stream = null;
		if (reload) {
			final URL url = loader.getResource(resourceName);
			if (null != url) {
				final URLConnection connection = url.openConnection();
				if (null != connection) {
					connection.setUseCaches(false);
					stream = connection.getInputStream();
				}
			}
		} else {
			stream = loader.getResourceAsStream(resourceName);
		}

		if (null != stream) {
			try {
				result = new PropertyResourceBundle(new InputStreamReader(stream, encoding));
			} finally {
				stream.close();
			}
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}
