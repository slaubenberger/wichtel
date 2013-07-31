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

package net.laubenberger.wichtel.misc.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * XML entry representation for a map entry.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "key", "value"})
@XmlRootElement(name = "entry")
public class XmlEntry {
	@XmlElement(name = "id")
	private String id;
	@XmlElement(name = "key", required = true)
	private final String key;
	@XmlElement(name = "value", required = true)
	private final String value;

	public XmlEntry() {
		super();
		key = null;
		value = null;
	}

	public XmlEntry(final String key, final String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public XmlEntry(final String id, final String key, final String value) {
		super();
		this.id = id;
		this.key = key;
		this.value = value;
	}
	
	public String getId() {
		return id;
	}
	
	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
