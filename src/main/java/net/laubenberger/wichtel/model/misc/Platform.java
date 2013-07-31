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

package net.laubenberger.wichtel.model.misc;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Possible platforms
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
@XmlRootElement(name = "platform")
public enum Platform {
	ANY("Any platform", "any"), //$NON-NLS-1$ //$NON-NLS-2$
	MAC_OSX("Apple Mac OSX", "mac"), //$NON-NLS-1$ //$NON-NLS-2$
	WINDOWS("Microsoft Windows", "win"), //$NON-NLS-1$ //$NON-NLS-2$
	UNIX("UNIX/Linux", "unix"); //$NON-NLS-1$ //$NON-NLS-2$

	private final String name;
	private final String code;

	Platform(final String name, final String code) {
		this.name = name;
		this.code = code;
	}


	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
}	

