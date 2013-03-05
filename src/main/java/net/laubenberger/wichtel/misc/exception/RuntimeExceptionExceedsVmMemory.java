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

import net.laubenberger.wichtel.helper.HelperEnvironment;

/**
 * This runtime exception is thrown if an argument exceeds the VM memory (e.g. large Byte arrays).
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public class RuntimeExceptionExceedsVmMemory extends IllegalArgumentException {
	private static final long serialVersionUID = 1150311302870054754L;

	public RuntimeExceptionExceedsVmMemory(final String argument, final long size) {
		super(argument + " (" + size + ") exceeds the free VM memory (" + HelperEnvironment.getMemoryFree() + ')'); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
