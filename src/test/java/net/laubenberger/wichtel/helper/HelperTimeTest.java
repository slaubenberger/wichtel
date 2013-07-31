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

package net.laubenberger.wichtel.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.util.Date;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsInvalid;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeSmaller;
import net.laubenberger.wichtel.view.swing.factory.FormatFactory;

import org.junit.Test;


/**
 * JUnit test for {@link HelperTime}
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 */
public class HelperTimeTest {
	@Test
	public void testGetAtomicTime() {
		final DateFormat df = FormatFactory.createDateFormat(FormatFactory.PATTERN_DATE_YEAR_MONTH_DAY);

		try {
			assertEquals(df.format(new Date()), df.format(HelperTime.getAtomicTime()));
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			assertEquals(df.format(new Date()), df.format(HelperTime.getAtomicTime("time-a.nist.gov"))); //$NON-NLS-1$
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperTime.getAtomicTime(null);
			fail("host is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testGetDateYYMMDD() {
		final DateFormat df = FormatFactory.createDateFormat(FormatFactory.PATTERN_DATE_YEAR_MONTH_DAY);
		
		try {
			assertEquals(df.parse("0.1.1"), HelperTime.getDate(0, 1, 1)); //$NON-NLS-1$
			assertEquals(df.parse("-400.1.1"), HelperTime.getDate(-400, 1, 1)); //$NON-NLS-1$
			assertEquals(df.parse("-290000000.1.1"), HelperTime.getDate(-290000000, 1, 1)); //$NON-NLS-1$
			assertEquals(df.parse("290000000.1.1"), HelperTime.getDate(290000000, 1, 1)); //$NON-NLS-1$
			assertEquals(df.parse("2011.2.28"), HelperTime.getDate(2011, 2, 28)); //$NON-NLS-1$
			assertEquals(df.parse("2012.2.29"), HelperTime.getDate(2012, 2, 29)); //$NON-NLS-1$
			assertNotSame(df.parse("1976.12.31"), HelperTime.getDate(1976, 12, 30)); //$NON-NLS-1$
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperTime.getDate(2011, 2, 29); //not a leap year
			fail("day is invalid"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsInvalid ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperTime.getDate(-290000001, 2, 28);
			fail("year must be greater"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperTime.getDate(290000001, 2, 28);
			fail("year must be smaller"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeSmaller ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperTime.getDate(2011, 0, 28);
			fail("month must be greater"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperTime.getDate(2011, 13, 28);
			fail("month must be smaller"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeSmaller ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperTime.getDate(2011, 1, 0);
			fail("day must be greater"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	
		try {
			HelperTime.getDate(2011, 12, 32);
			fail("day must be smaller"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeSmaller ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetDateYYMMDDhhmmss() {
		final DateFormat df = FormatFactory.createDateFormat(FormatFactory.PATTERN_DATE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
		
		try {
			assertEquals(df.parse("0.1.1 1:2:3"), HelperTime.getDate(0, 1, 1, 1, 2, 3)); //$NON-NLS-1$
			assertEquals(df.parse("-400.1.1 1:2:3"), HelperTime.getDate(-400, 1, 1, 1, 2, 3)); //$NON-NLS-1$
			assertEquals(df.parse("-290000000.1.1 1:2:3"), HelperTime.getDate(-290000000, 1, 1, 1, 2, 3)); //$NON-NLS-1$
			assertEquals(df.parse("290000000.1.1 1:2:3"), HelperTime.getDate(290000000, 1, 1, 1, 2, 3)); //$NON-NLS-1$
			assertEquals(df.parse("2011.2.28 1:2:3"), HelperTime.getDate(2011, 2, 28, 1, 2, 3)); //$NON-NLS-1$
			assertEquals(df.parse("2012.2.29 1:2:3"), HelperTime.getDate(2012, 2, 29, 1, 2, 3)); //$NON-NLS-1$
			assertNotSame(df.parse("1976.12.31 1:2:3"), HelperTime.getDate(1976, 12, 30, 1, 2, 3)); //$NON-NLS-1$
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperTime.getDate(2011, 2, 29, 1, 2, 3); //not a leap year
			fail("day is invalid"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsInvalid ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperTime.getDate(-290000001, 2, 28, 1, 2, 3);
			fail("year must be greater"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperTime.getDate(290000001, 2, 28, 1, 2, 3);
			fail("year must be smaller"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeSmaller ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperTime.getDate(2011, 0, 28, 1, 2, 3);
			fail("month must be greater"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperTime.getDate(2011, 13, 28, 1, 2, 3);
			fail("month must be smaller"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeSmaller ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperTime.getDate(2011, 1, 0, 1, 2, 3);
			fail("day must be greater"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	
		try {
			HelperTime.getDate(2011, 12, 32, 1, 2, 3);
			fail("day must be smaller"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeSmaller ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperTime.getDate(2011, 1, 1, -1, 2, 3);
			fail("hour must be greater"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	
		try {
			HelperTime.getDate(2011, 12, 31, 24, 2, 3);
			fail("hour must be smaller"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeSmaller ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperTime.getDate(2011, 1, 1, 1, -1, 3);
			fail("minute must be greater"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	
		try {
			HelperTime.getDate(2011, 12, 31, 1, 60, 3);
			fail("minute must be smaller"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeSmaller ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperTime.getDate(2011, 1, 1, 1, 2, -1);
			fail("second must be greater"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	
		try {
			HelperTime.getDate(2011, 12, 31, 1, 2, 60);
			fail("second must be smaller"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeSmaller ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetDateAsAbsoluteDate() {
		final DateFormat df = FormatFactory.createDateFormat(FormatFactory.PATTERN_DATE_YEAR_MONTH_DAY);
		
		try {
			assertEquals(df.parse("0.1.1"), HelperTime.getDateAsAbsoluteDate(HelperTime.getDate(0, 1, 1, 1, 2, 3))); //$NON-NLS-1$
			assertEquals(df.parse("-400.1.1"), HelperTime.getDateAsAbsoluteDate(HelperTime.getDate(-400, 1, 1, 1, 2, 3))); //$NON-NLS-1$
			assertEquals(df.parse("-290000000.1.1"), HelperTime.getDateAsAbsoluteDate(HelperTime.getDate(-290000000, 1, 1, 1, 2, 3))); //$NON-NLS-1$
			assertEquals(df.parse("290000000.1.1"), HelperTime.getDateAsAbsoluteDate(HelperTime.getDate(290000000, 1, 1, 1, 2, 3))); //$NON-NLS-1$
			assertEquals(df.parse("2011.2.28"), HelperTime.getDateAsAbsoluteDate(HelperTime.getDate(2011, 2, 28, 1, 2, 3))); //$NON-NLS-1$
			assertEquals(df.parse("2012.2.29"), HelperTime.getDateAsAbsoluteDate(HelperTime.getDate(2012, 2, 29, 1, 2, 3))); //$NON-NLS-1$
			assertNotSame(df.parse("1976.12.31"), HelperTime.getDateAsAbsoluteDate(HelperTime.getDate(1976, 12, 30, 1, 2, 3))); //$NON-NLS-1$
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperTime.getDateAsAbsoluteDate(null);
			fail("date is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetDateAsAbsoluteTime() {
		try {
			assertEquals(123000L, HelperTime.getDateAsAbsoluteTime(HelperTime.getDate(0, 1, 1, 1, 2, 3)).getTime());
			assertEquals(123000L, HelperTime.getDateAsAbsoluteTime(HelperTime.getDate(-400, 1, 1, 1, 2, 3)).getTime());
			assertEquals(123000L, HelperTime.getDateAsAbsoluteTime(HelperTime.getDate(-290000000, 1, 1, 1, 2, 3)).getTime());
			assertEquals(123000L, HelperTime.getDateAsAbsoluteTime(HelperTime.getDate(290000000, 1, 1, 1, 2, 3)).getTime());
			assertEquals(123000L, HelperTime.getDateAsAbsoluteTime(HelperTime.getDate(2011, 2, 28, 1, 2, 3)).getTime());
			assertEquals(123000L, HelperTime.getDateAsAbsoluteTime(HelperTime.getDate(2012, 2, 29, 1, 2, 3)).getTime());
			assertNotSame(124000L, HelperTime.getDateAsAbsoluteTime(HelperTime.getDate(1976, 12, 30, 1, 2, 3)).getTime());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperTime.getDateAsAbsoluteTime(null);
			fail("date is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}
