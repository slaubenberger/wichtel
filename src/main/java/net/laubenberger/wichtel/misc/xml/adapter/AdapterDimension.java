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

import java.awt.Dimension;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import net.laubenberger.wichtel.misc.xml.XmlEntry;
import net.laubenberger.wichtel.misc.xml.XmlMap;

/**
 * Map adapter for {@link Dimension}.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public class AdapterDimension extends XmlAdapter<XmlMap, Dimension> {

	/*
	 * Overridden methods
	 */

	@Override
	public XmlMap marshal(final Dimension dim) throws Exception {
		if (null != dim) {
			final XmlMap xmlMap = new XmlMap();

			xmlMap.getEntries().add(new XmlEntry("x", String.valueOf(dim.width))); //$NON-NLS-1$
			xmlMap.getEntries().add(new XmlEntry("y", String.valueOf(dim.height))); //$NON-NLS-1$

			return xmlMap;
		}
		return null;
	}

	@Override
	public Dimension unmarshal(final XmlMap xmlMap) throws Exception {
		if (null != xmlMap && 2 == xmlMap.getEntries().size()) {
			
			return new Dimension(Integer.valueOf(xmlMap.getEntries().get(0).getValue()), Integer.valueOf(xmlMap.getEntries().get(1).getValue()));
		}
		return null;
	}
}
