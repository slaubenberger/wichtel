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
import java.net.URL;
import java.util.List;
import java.util.Locale;

import javax.swing.KeyStroke;

import net.laubenberger.wichtel.misc.HolderListener;
import net.laubenberger.wichtel.model.misc.Language;
import net.laubenberger.wichtel.service.Service;


/**
 * Defines the methods for the implementation of the localizer for different languages (i18n standard).
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public interface Localizer extends Service, HolderListener<ListenerLocale> {
	/**
	 * Returns the current locale {@link Locale} of the localizer.
	 *
	 * @return current {@link Locale} of the localizer
	 * @see Locale
	 * @since 0.0.1
	 */
	Locale getLocale();

	/**
	 * Sets the current locale {@link Locale} of the localizer.
	 *
	 * @param locale of the localizer
	 * @see Locale
	 * @since 0.0.1
	 */
	void setLocale(Locale locale);

	/**
	 * Returns the value for a key.
	 *
	 * @param key for the value
	 * @return value for the key
	 * @since 0.0.1
	 */
	String getValue(String key);

	/**
	 * Returns the value of a key as {@link BigDecimal}.
	 *
	 * @param key
	 * @return {@link BigDecimal} associated to the given key
	 * @since 0.0.1
	 */
	BigDecimal getNumber(String key);

	/**
	 * Returns the value of a key as {@link Boolean}.
	 *
	 * @param key
	 * @return {@link Boolean} associated to the given key
	 * @since 0.0.1
	 */
	Boolean getBoolean(String key);

	/**
	 * Returns the accelerator ({@link KeyStroke}) for a key.
	 *
	 * @param key for the accelerator
	 * @return accelerator ({@link KeyStroke}) for the key
	 * @see KeyStroke
	 * @since 0.0.1
	 */
	KeyStroke getAccelerator(String key);

	/**
	 * Returns the mnemonic for a key.
	 *
	 * @param key for the mnemonic
	 * @return mnemonic for the key
	 * @since 0.0.1
	 */
	int getMnemonic(String key);

	/**
	 * Returns the tooltip for a key.
	 *
	 * @param key for the tooltip
	 * @return tooltip for the key
	 * @since 0.0.1
	 */
	String getTooltip(String key);

	/**
	 * Returns the {@link File} for a key.
	 *
	 * @param key
	 * @return {@link File} associated to the given key
	 * @since 0.0.1
	 */
	File getFile(String key);

	/**
	 * Returns the {@link URL} for a key.
	 *
	 * @param key
	 * @return {@link URL} associated to the given key
	 * @since 0.0.1
	 */
	URL getURL(String key);
	
	/**
	 * Returns a {@link List} of all available {@link Language}.
	 *
	 * @return {@link List} containing all available {@link Language}
	 * @see Language
	 * @since 0.0.1
	 */
	List<Language> getAvailableLanguages();
}
