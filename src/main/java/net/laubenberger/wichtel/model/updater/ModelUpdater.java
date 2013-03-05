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
package net.laubenberger.wichtel.model.updater;

import java.net.URL;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.laubenberger.wichtel.model.crypto.HashCodeAlgo;
import net.laubenberger.wichtel.model.misc.Document;
import net.laubenberger.wichtel.model.misc.Platform;
import net.laubenberger.wichtel.model.updater.ModelUpdaterImpl.XmlAdapterModelUpdater;

/**
 * The interface for the updater model.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
@XmlJavaTypeAdapter(XmlAdapterModelUpdater.class)
public interface ModelUpdater extends Document {
	String MEMBER_LOCATIONS = "locations"; //$NON-NLS-1$
	String MEMBER_HASHS = "hashs"; //$NON-NLS-1$


	/**
	 * Returns all locations.
	 *
	 * @return {@link Map} containing all update locations
	 * @see URL
	 * @see Platform
	 * @since 0.0.1
	 */
	Map<Platform, URL> getLocations();

	/**
	 * Sets all update {@link URL} locations.
	 *
	 * @param locations {@link Map} containing all update {@link URL} locations
	 * @see URL
	 * @since 0.0.1
	 */
	void setLocations(Map<Platform, URL> locations);

	/**
	 * Returns the {@link URL} location for a given {@link Platform}.
	 *
	 * @param platform for the location
	 * @return location
	 * @see URL
	 * @see Platform
	 * @since 0.0.1
	 */
	URL getLocation(Platform platform);

	/**
	 * Returns the default update {@link URL} location (ANY platform).
	 *
	 * @return default update location
	 * @see URL
	 * @since 0.0.1
	 */
	URL getLocation();

	/**
	 * Returns all hashs.
	 *
	 * @return {@link Map} containing all hashs
	 * @see HashCodeAlgo
	 * @since 0.0.1
	 */
	Map<HashCodeAlgo, String> getHashs();

	/**
	 * Sets all hashs.
	 *
	 * @param hashs {@link Map} containing all hashs
	 * @see HashCodeAlgo
	 * @since 0.0.1
	 */
	void setHashs(Map<HashCodeAlgo, String> hashs);

	/**
	 * Returns the hash for a given {@link HashCodeAlgo}.
	 *
	 * @param hashCodeAlgo for the hash
	 * @return hash
	 * @see HashCodeAlgo
	 * @since 0.0.1
	 */
	String getHash(HashCodeAlgo hashCodeAlgo);

	/**
	 * Returns the default hash (generated with SHA256).
	 *
	 * @return default hash
	 * @since 0.0.1
	 */
	String getHash();
}
