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

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsInvalid;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeSmaller;
import net.laubenberger.wichtel.view.swing.factory.FormatFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Helper class for time operations.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public abstract class HelperTime {
	private static final Logger log = LoggerFactory.getLogger(HelperTime.class);

	public static final int MAX_SECOND_VALUE = 59;
	public static final int MAX_MINUTE_VALUE = 59;
	public static final int MAX_HOUR_VALUE = 23;
	public static final int MAX_DAY_VALUE = 31;
	public static final int MAX_MONTH_VALUE = 12;
	public static final int MIN_YEAR_VALUE = -290000000;
	public static final int MAX_YEAR_VALUE = 290000000;

	public static final int HOURS_PER_DAY = 24;
	public static final int DAYS_PER_WEEK = 7;
	public static final int DAYS_PER_YEAR = 365;

	public static final long MILLISECONDS_PER_MINUTE = 60L * HelperNumber.NUMBER_1000.longValue();
	public static final long MILLISECONDS_PER_HOUR = 60L * MILLISECONDS_PER_MINUTE;
	public static final long MILLISECONDS_PER_DAY = HOURS_PER_DAY * MILLISECONDS_PER_HOUR;
	public static final long MILLISECONDS_PER_WEEK = DAYS_PER_WEEK * MILLISECONDS_PER_DAY;
	public static final long MILLISECONDS_PER_YEAR = DAYS_PER_YEAR * MILLISECONDS_PER_DAY;
	public static final long SECONDS_BETWEEN_1900_AND_1970 = 2208988800L;

	public static final int TIME_SERVER_PORT = 37;
	public static final String DEFAULT_TIME_SERVER = "ptbtime1.ptb.de"; //$NON-NLS-1$

	private static final int TIMEOUT = 5000;

	
	/**
	 * Returns the current atomic time of the default time server.
	 * Uses the time protocol specified in Internet time standard RFC-868.
	 * 
	 * @return atomic time of the default time server
	 * @throws IOException
	 * @see Date
	 * @since 0.0.1
	 */
	public static Date getAtomicTime() throws IOException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		final Date result = getAtomicTime(DEFAULT_TIME_SERVER);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns the current atomic time of the given time server.
	 * Uses the time protocol specified in Internet time standard RFC-868.
	 * 
	 * @param host of the time server
	 * @return atomic time of the given time server
	 * @throws IOException
	 * @see Date
	 * @since 0.0.1
	 */
	public static Date getAtomicTime(final String host) throws IOException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(host));
		if (null == host) {
			throw new RuntimeExceptionIsNull("host"); //$NON-NLS-1$
		}
		if (!HelperString.isValid(host)) {
			throw new RuntimeExceptionIsEmpty("host"); //$NON-NLS-1$
		}

		try (Socket socket = new Socket(host, TIME_SERVER_PORT);
				InputStream is = socket.getInputStream()) {
			socket.setSoTimeout(TIMEOUT);

			long time = 0L;

			for (int ii = 3; 0 <= ii; ii--) {
				time ^= (long) is.read() << ii * 8;
			}

			final Date result = new Date((time - SECONDS_BETWEEN_1900_AND_1970) * HelperNumber.NUMBER_1000.longValue());

			if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
			return result;
		}
	}

	/**
	 * Create a {@link Date} with year, month and day as parameters.
	 *
	 * @param year range between -290000000 - 290000000
	 * @param month range between 1-12
	 * @param day  range between 1-31
	 * @return created {@link Date}
	 * @see Date
	 * @since 0.0.1
	 */
	public static Date getDate(final int year, final int month, final int day) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(year, month, day));
		if (MIN_YEAR_VALUE > year) {
			throw new RuntimeExceptionMustBeGreater("year", year, MIN_YEAR_VALUE); //$NON-NLS-1$
		}
		if (MAX_YEAR_VALUE < year) {
			throw new RuntimeExceptionMustBeSmaller("year", year, MAX_YEAR_VALUE); //$NON-NLS-1$
		}
		if (1 > month) {
			throw new RuntimeExceptionMustBeGreater("month", month, 0); //$NON-NLS-1$
		}
		if (MAX_MONTH_VALUE < month) {
			throw new RuntimeExceptionMustBeSmaller("month", month, MAX_MONTH_VALUE); //$NON-NLS-1$
		}
		if (1 > day) {
			throw new RuntimeExceptionMustBeGreater("day", day, 0); //$NON-NLS-1$
		}
		if (MAX_DAY_VALUE < day) {
			throw new RuntimeExceptionMustBeSmaller("day", day, MAX_DAY_VALUE); //$NON-NLS-1$
		}

		final Calendar calendar = Calendar.getInstance();
		
		calendar.set(year, month - 1, day, 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		//has day changed?
		if (calendar.get(Calendar.DAY_OF_MONTH) != day) {
			throw new RuntimeExceptionIsInvalid("day", day); //$NON-NLS-1$
		}
		
		final Date result = calendar.getTime();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;	
	}

	/**
	 * Create a {@link Date} with year, month, day, minutes and seconds as parameters.
	 *
	 * @param year	range between -290000000 - 290000000
	 * @param month  range between 1-12
	 * @param day	range between 1-31
	 * @param hour	range between 0-23 (24-hour notation)
	 * @param minute range between 0-59
	 * @param second range between 0-59
	 * @return created {@link Date}
	 * @see Date
	 * @since 0.0.1
	 */
	public static Date getDate(final int year, final int month, final int day, final int hour, final int minute, final int second) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(year, month, day, hour, minute, second));
		if (MIN_YEAR_VALUE > year) {
			throw new RuntimeExceptionMustBeGreater("year", year, MIN_YEAR_VALUE); //$NON-NLS-1$
		}
		if (MAX_YEAR_VALUE < year) {
			throw new RuntimeExceptionMustBeSmaller("year", year, MAX_YEAR_VALUE); //$NON-NLS-1$
		}
		if (1 > month) {
			throw new RuntimeExceptionMustBeGreater("month", month, 0); //$NON-NLS-1$
		}
		if (MAX_MONTH_VALUE < month) {
			throw new RuntimeExceptionMustBeSmaller("month", month, MAX_MONTH_VALUE); //$NON-NLS-1$
		}
		if (1 > day) {
			throw new RuntimeExceptionMustBeGreater("day", day, 0); //$NON-NLS-1$
		}
		if (MAX_DAY_VALUE < day) {
			throw new RuntimeExceptionMustBeSmaller("day", day, MAX_DAY_VALUE); //$NON-NLS-1$
		}
		if (0 > hour) {
			throw new RuntimeExceptionMustBeGreater("hour", hour, 0); //$NON-NLS-1$
		}
		if (MAX_HOUR_VALUE < hour) {
			throw new RuntimeExceptionMustBeSmaller("hour", hour, MAX_HOUR_VALUE); //$NON-NLS-1$
		}
		if (0 > minute) {
			throw new RuntimeExceptionMustBeGreater("minute", minute, 0); //$NON-NLS-1$
		}
		if (MAX_MINUTE_VALUE < minute) {
			throw new RuntimeExceptionMustBeSmaller("minute", minute, MAX_MINUTE_VALUE); //$NON-NLS-1$
		}
		if (0 > second) {
			throw new RuntimeExceptionMustBeGreater("second", second, 0); //$NON-NLS-1$
		}
		if (MAX_SECOND_VALUE < second) {
			throw new RuntimeExceptionMustBeSmaller("second", second, MAX_SECOND_VALUE); //$NON-NLS-1$
		}
		
		final Calendar calendar = Calendar.getInstance();

		calendar.set(year, month - 1, day, hour, minute, second);
		calendar.set(Calendar.MILLISECOND, 0);
		
		//has day changed?
		if (calendar.get(Calendar.DAY_OF_MONTH) != day) {
			throw new RuntimeExceptionIsInvalid("day", day); //$NON-NLS-1$
		}

		final Date result = calendar.getTime();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;	
	}
	
	/**
	 * Returns a {@link Date} containing only the date from a given {@link Date}. The values of hours, minutes, seconds and milliseconds are set to 0.
	 *
	 * @param date for the absolute date
	 * @return created {@link Date}
	 * @see Date
	 * @since 0.0.1
	 */
	public static Date getDateAsAbsoluteDate(final Date date) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(date));
		if (null == date) {
			throw new RuntimeExceptionIsNull("date"); //$NON-NLS-1$
		}
		
		final Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		final Date result = calendar.getTime();

//		final DateFormat df = FormatFactory.createDateFormat(FormatFactory.PATTERN_DATE_DAY_MONTH_YEAR);
//
//		Date result = null;
//		try {
//			result = df.parse(df.format(date));
//		} catch (ParseException ex) {
//			// should never happen!
//			log.error("Could not parse date", ex);
//		}
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
	
	/**
	 * Returns a {@link Date} containing only the time from a given {@link Date}. The values of years are set to 0 and months/days set to 1.
	 *
	 * @param date for the absolute time
	 * @return created {@link Date}
	 * @see Date
	 * @since 0.0.1
	 */
	public static Date getDateAsAbsoluteTime(final Date date) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(date));
		if (null == date) {
			throw new RuntimeExceptionIsNull("date"); //$NON-NLS-1$
		}
		
//		final Calendar calendar = Calendar.getInstance();
//		
//		calendar.setTime(date);
//
//		// Gregorian or Julian calendar?
//		if (calendar.get(Calendar.YEAR) > 1582) {
//			calendar.set(Calendar.YEAR, 1970);
//		} else {
//			calendar.set(Calendar.YEAR, 1);
//		}
//
//		calendar.set(Calendar.MONTH, 0);
//		calendar.set(Calendar.DAY_OF_MONTH, 1);
//		calendar.set(Calendar.MILLISECOND, 0);
//		
//		final Date result = calendar.getTime();

		final DateFormat df = FormatFactory.createDateFormat(FormatFactory.PATTERN_DATE_HOUR_MINUTE_SECOND_MILLISECOND);

		Date result = null;
		try {
			result = df.parse(df.format(date));
		} catch (ParseException ex) {
			// should never happen!
			log.error("Could not parse date", ex); //$NON-NLS-1$
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}
