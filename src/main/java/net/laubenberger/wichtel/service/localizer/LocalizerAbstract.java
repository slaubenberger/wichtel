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

package net.laubenberger.wichtel.service.localizer;

import java.io.File;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.helper.HelperNumber;
import net.laubenberger.wichtel.helper.HelperString;
import net.laubenberger.wichtel.misc.Event;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.service.ServiceAbstract;


/**
 * Abstract localizer implementation.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public abstract class LocalizerAbstract extends ServiceAbstract implements Localizer {
	private static final Logger log = LoggerFactory.getLogger(LocalizerAbstract.class);

	private final Collection<ListenerLocale> listeners = new HashSet<>();

	private final Event<Localizer> event = new Event<Localizer>(this);

	private Locale locale = Locale.getDefault();


	/*
	 * Private methods
	 */

	protected void fireLocaleChanged() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());

		for (final ListenerLocale listener : listeners) {
			listener.localeChanged(event);
		}

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}


	/*
	 * Implemented methods
	 */

	@Override
	public Boolean getBoolean(final String key) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(key));

		final String value = getValue(key);
		final Boolean result = null == value ? null : Boolean.parseBoolean(value);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public BigDecimal getNumber(final String key) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(key));

		final BigDecimal result = HelperNumber.getNumber(getValue(key));

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public File getFile(final String key) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(key));

		final String value = getValue(key);
		final File result = null == value ? null : new File(value);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public URL getURL(final String key) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(key));


		final String value = getValue(key);
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

	@Override
	public Locale getLocale() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(locale));
		return locale;
	}

	@Override
	public void setLocale(final Locale locale) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(locale));
		if (null == locale) {
			throw new RuntimeExceptionIsNull("locale"); //$NON-NLS-1$
		}

		this.locale = locale;
		fireLocaleChanged();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void addListener(final ListenerLocale listener) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(listener));
		if (null == listener) {
			throw new RuntimeExceptionIsNull("listener"); //$NON-NLS-1$
		}

		listeners.add(listener);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void deleteListener(final ListenerLocale listener) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(listener));
		if (null == listener) {
			throw new RuntimeExceptionIsNull("listener"); //$NON-NLS-1$
		}

		listeners.remove(listener);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
}
