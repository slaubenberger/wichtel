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

package net.laubenberger.wichtel.misc.xml.adapter;

import java.util.HashMap;
import java.util.Map;import java.util.Map.Entry;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.laubenberger.wichtel.misc.xml.XmlEntry;
import net.laubenberger.wichtel.misc.xml.XmlMap;

/**
 * Map adapter for the key {@link String} and value {@link String}.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public class MapAdapterString extends XmlAdapter<XmlMap, Map<String, String>> {

	/*
	 * Overridden methods
	 */

	@Override
	public XmlMap marshal(final Map<String, String> map) throws Exception {
		if (null != map) {
			final XmlMap xmlMap = new XmlMap();

			for (final Entry<String, String> entry : map.entrySet()) {
				xmlMap.getEntries().add(new XmlEntry(entry.getKey(), entry.getValue()));
			}
			return xmlMap;
		}
		return null;
	}

	@Override
	public Map<String, String> unmarshal(final XmlMap xmlMap) throws Exception {
		if (null != xmlMap) {
			final Map<String, String> map = new HashMap<String, String>(xmlMap.getEntries().size());
			for (final XmlEntry entry : xmlMap.getEntries()) {
				map.put(String.valueOf(entry.getKey()), entry.getValue());
			}
			return map;
		}
		return null;
	}
}
