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

import net.laubenberger.wichtel.AllTests;
import net.laubenberger.wichtel.helper.HelperArray;
import net.laubenberger.wichtel.helper.HelperString;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.junit.Test;

/**
 * JUnit test for {@link LauncherMail}
 * 
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 */
public class LauncherMailTest {
	@Test
	public void testPassMail() {
		try {
			LauncherMail.mail();
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			LauncherMail.mail(new URI("mailto:yourname@yourmail.com")); //$NON-NLS-1$
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			LauncherMail.mail("mailto:yourname@yourmail.com"); //$NON-NLS-1$
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherMail.mail("yourname@yourmail.com"); //$NON-NLS-1$
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherMail
					.mail(
							"Hi there!", "This is a test:" + HelperString.NEW_LINE + AllTests.DATA  + HelperString.NEW_LINE + "Yeah, all lines are here! :-)", "yourname@oäöü.com", "anothername@anotherMail.com"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherMail
					.mail(
							"Hello again!", null, "yourname@oäöü.com", "anothername@anotherMail.com"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testFailMail() {
		try {
			LauncherMail.mail((URI)null);
			fail("uri is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherMail.mail((String)null);
			fail("emailAddress is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherMail.mail(HelperString.EMPTY_STRING);
			fail("emailAddress is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherMail
					.mail(
							null, "This is a test:" + HelperString.NEW_LINE + AllTests.DATA  + HelperString.NEW_LINE + "Yeah, all lines are here! :-)", "yourname@oäöü.com", "anothername@anotherMail.com"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			fail("subject is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherMail
					.mail(
							HelperString.EMPTY_STRING, "This is a test:" + HelperString.NEW_LINE + AllTests.DATA  + HelperString.NEW_LINE + "Yeah, all lines are here! :-)", "yourname@oäöü.com", "anothername@anotherMail.com"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			fail("subject is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherMail
					.mail(
							"Hi there!", null, null); //$NON-NLS-1$
			fail("emailAddresses is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherMail
					.mail(
							"Hello again!", null, HelperArray.EMPTY_ARRAY_STRING); //$NON-NLS-1$
			fail("emailAddresses is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherMail
					.mail(
							"Hello again!", null, "yourname@oäöü.com", null, "anothername@anotherMail.com"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			fail("address is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}
