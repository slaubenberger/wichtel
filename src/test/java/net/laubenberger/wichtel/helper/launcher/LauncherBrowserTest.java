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

package net.laubenberger.wichtel.helper.launcher;

import static org.junit.Assert.fail;

import java.net.URI;

import net.laubenberger.wichtel.helper.HelperString;
import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.junit.Test;

/**
 * JUnit test for {@link LauncherBrowser}
 * 
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 */
public class LauncherBrowserTest {
	@Test
	public void testPassBrowse() {
		try {
			LauncherBrowser.browse(Constants.WICHTEL.getUrl().toURI());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			LauncherBrowser.browse("laubenberger.net"); //$NON-NLS-1$
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testFailBrowse() {
		try {
			LauncherBrowser.browse((URI)null);
			fail("uri is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

	try {
			LauncherBrowser.browse((String)null);
			fail("url is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			LauncherBrowser.browse(HelperString.EMPTY_STRING);
			fail("url is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}
