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

package net.laubenberger.wichtel.misc.xml.adapter;

import java.util.HashMap;
import java.util.Map;import java.util.Map.Entry;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.laubenberger.wichtel.misc.xml.XmlEntry;
import net.laubenberger.wichtel.misc.xml.XmlMap;
import net.laubenberger.wichtel.model.crypto.HashCodeAlgo;

/**
 * Map adapter for the key {@link HashCodeAlgo} and value {@link String}.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public class MapAdapterHashCode extends XmlAdapter<XmlMap, Map<HashCodeAlgo, String>> {

	/*
	 * Overridden methods
	 */

	@Override
	public XmlMap marshal(final Map<HashCodeAlgo, String> map) throws Exception {
		if (null != map) {
			final XmlMap xmlMap = new XmlMap();

			for (final Entry<HashCodeAlgo, String> entry : map.entrySet()) {
				xmlMap.getEntries().add(new XmlEntry(entry.getKey().name(), entry.getValue()));
			}
			return xmlMap;
		}
		return null;
	}

	@Override
	public Map<HashCodeAlgo, String> unmarshal(final XmlMap xmlMap) throws Exception {
		if (null != xmlMap) {
			final Map<HashCodeAlgo, String> map = new HashMap<>(xmlMap.getEntries().size());
			for (final XmlEntry entry : xmlMap.getEntries()) {
				map.put(HashCodeAlgo.valueOf(entry.getKey()), entry.getValue());
			}
			return map;
		}
		return null;
	}
}
