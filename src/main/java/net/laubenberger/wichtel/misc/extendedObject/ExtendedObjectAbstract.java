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

package net.laubenberger.wichtel.misc.extendedObject;

import java.util.Date;

/**
 * This is the skeleton for all extended objects.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public abstract class ExtendedObjectAbstract implements ExtendedObject {
	private final Date instantiationDate = new Date();


	/*
	 * Overridden methods
	 */
	
	@Override
	public String toString() {
		return getClass().getName() + "[instantiationDate=" + instantiationDate + ']'; //$NON-NLS-1$
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((null == instantiationDate) ? 0 : instantiationDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) return true;
		if (null == obj) return false;
		if (getClass() != obj.getClass()) return false;
		final ExtendedObjectAbstract other = (ExtendedObjectAbstract) obj;
		if (null == instantiationDate) {
			if (null != other.instantiationDate) return false;
		} else if (!instantiationDate.equals(other.instantiationDate)) return false;
		return true;
	}

	
	/*
	 * Implemented methods
	 */

	@Override
	public Date getInstantiated() {
		return instantiationDate;
	}
}