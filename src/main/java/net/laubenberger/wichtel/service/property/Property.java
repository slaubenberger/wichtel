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

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Properties;

import net.laubenberger.wichtel.service.Service;


/**
 * Defines the methods for the implementation of the properties.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public interface Property extends Service {
	/**
	 * Returns the properties {@link Properties}.
	 *
	 * @return {@link Properties}
	 * @see Properties
	 * @since 0.0.1
	 */
	Properties getProperties();

	/**
	 * Returns the value of a property as {@link String}.
	 *
	 * @param key of the property
	 * @return {@link String} associated to the given key
	 * @since 0.0.1
	 */
	String getValue(String key);

	/**
	 * Returns the value of a property as {@link Boolean}.
	 *
	 * @param key of the property
	 * @return {@link Boolean} associated to the given key
	 * @since 0.0.1
	 */
	Boolean getBoolean(String key);

	/**
	 * Returns the value of a property as {@link BigDecimal}.
	 *
	 * @param key of the property
	 * @return {@link BigDecimal} associated to the given key
	 * @since 0.0.1
	 */
	BigDecimal getNumber(String key);

	/**
	 * Returns the value of a property as {@link File}.
	 *
	 * @param key for the property
	 * @return {@link File} associated to the given key
	 * @since 0.0.1
	 */
	File getFile(String key);

	/**
	 * Returns the value of a property as {@link URL}.
	 *
	 * @param key for the property
	 * @return {@link URL} associated to the given key
	 * @since 0.0.1
	 */
	URL getURL(String key);
}   

