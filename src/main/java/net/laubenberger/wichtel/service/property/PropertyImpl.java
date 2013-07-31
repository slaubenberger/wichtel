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
package net.laubenberger.wichtel.service.property;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.helper.HelperNumber;
import net.laubenberger.wichtel.helper.HelperString;
import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.service.ServiceAbstract;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the properties class for file and stream access.
 * 
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public class PropertyImpl extends ServiceAbstract implements Property {
	private static final Logger log = LoggerFactory.getLogger(PropertyImpl.class);

	private Properties properties;

	public PropertyImpl(final InputStream inputStream, final String encoding) throws IOException {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(inputStream, encoding));

		load(inputStream, encoding);
	}

	public PropertyImpl(final File file, final String encoding) throws IOException {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(file, encoding));
	
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
			load(bis, encoding);
		}
	}

	public PropertyImpl(final InputStream inputStream) throws IOException {
		this(inputStream, Constants.ENCODING_DEFAULT);
	}

	public PropertyImpl(final File file) throws IOException {
		this(file, Constants.ENCODING_DEFAULT);
	}
	
	private void load(final InputStream inputStream, final String encoding) throws IOException {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart(inputStream, encoding));

		if (null == inputStream) {
			throw new RuntimeExceptionIsNull("inputStream"); //$NON-NLS-1$
		}
		if (null == encoding) {
			throw new RuntimeExceptionIsNull("encoding"); //$NON-NLS-1$
		}
		
		properties = new Properties();
		try (InputStreamReader isr = new InputStreamReader(inputStream, encoding)) {
			properties.load(isr);
		}
		
		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}
	
	/*
	 * Implemented methods
	 */

	@Override
	public Properties getProperties() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(properties));
		return properties;
	}

	@Override
	public String getValue(final String key) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(key));

		final String result = properties.getProperty(key);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public Boolean getBoolean(final String key) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(key));

		final String value = properties.getProperty(key);
		final Boolean result = null == value ? null : Boolean.parseBoolean(value);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public BigDecimal getNumber(final String key) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(key));

		final BigDecimal result = HelperNumber.getNumber(properties.getProperty(key));

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public File getFile(final String key) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(key));

		final String value = properties.getProperty(key);
		final File result = null == value ? null : new File(value);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public URL getURL(final String key) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(key));

		final String value = properties.getProperty(key);
		URL result = null;

		if (null != value) {
			try {
				result = new URL(value);
			} catch (MalformedURLException ex) {
				if (log.isInfoEnabled()) log.info("URL is invalid: " + HelperString.quote(value), ex); //$NON-NLS-1$
			}
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}
