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

package net.laubenberger.wichtel.misc.exception;

import java.util.Date;

/**
 * This runtime exception is thrown if a date value is before a min date.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public class RuntimeExceptionMustBeAfter extends IllegalArgumentException {
	private static final long serialVersionUID = -2933479118626382905L;

	public RuntimeExceptionMustBeAfter(final String argument, final Date currentDate, final Date minDate) {
		super(argument + " (" + currentDate + ") must be after " + minDate); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
