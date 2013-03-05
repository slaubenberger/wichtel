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

package net.laubenberger.wichtel.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.junit.Test;


/**
 * JUnit test for {@link HelperLog}
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 */
public class HelperLogTest {
	@Test
	public void testApplicationStart() {
//		System.err.println(HelperLog.applicationStart().length());
		assertTrue(6000 < HelperLog.applicationStart("Test", "1.0").length());
	}

	@Test
	public void testApplicationExit() {
		assertEquals("--- 0", HelperLog.applicationExit(0)); //$NON-NLS-1$
	}

	@Test
	public void testMethodStart() {
		assertEquals(">>>", HelperLog.methodStart()); //$NON-NLS-1$
		assertEquals(">>> Hi, method", HelperLog.methodStart("Hi", "method"));  //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
	}

	@Test
	public void testMethodExit() {
		assertEquals("<<<", HelperLog.methodExit()); //$NON-NLS-1$
		assertEquals("<<< Bye method", HelperLog.methodExit("Bye method")); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@Test
	public void testConstructor() {
		assertEquals("***", HelperLog.constructor()); //$NON-NLS-1$
		assertEquals("*** Hi, constructor", HelperLog.constructor("Hi", "constructor")); //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
	}
}


