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

package net.laubenberger.wichtel.service.updater;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;import java.util.Map.Entry;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.laubenberger.wichtel.misc.xml.XmlEntry;
import net.laubenberger.wichtel.misc.xml.XmlMap;
import net.laubenberger.wichtel.model.misc.Platform;

/**
 * Map adapter for the key {@link Platform} and value {@link URL}.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public class MapAdapterPlatformURL extends XmlAdapter<XmlMap, Map<Platform, URL>> {

	/*
	 * Overridden methods
	 */

	@Override
	public XmlMap marshal(final Map<Platform, URL> map) throws Exception {
		if (null != map) {
			final XmlMap xmlMap = new XmlMap();

			for (final Entry<Platform, URL> entry : map.entrySet()) {
				xmlMap.getEntries().add(new XmlEntry(entry.getKey().name(), entry.getValue().toExternalForm()));
			}
			return xmlMap;
		}
		return null;
	}

	@Override
	public Map<Platform, URL> unmarshal(final XmlMap xmlMap) throws Exception {
		if (null != xmlMap) {
			final Map<Platform, URL> map = new HashMap<>(xmlMap.getEntries().size());
			for (final XmlEntry entry : xmlMap.getEntries()) {
				map.put(Platform.valueOf(entry.getKey()), new URL(entry.getValue()));
			}
			return map;
		}
		return null;
	}
}
