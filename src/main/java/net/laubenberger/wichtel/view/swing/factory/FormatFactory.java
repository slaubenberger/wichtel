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

package net.laubenberger.wichtel.view.swing.factory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.helper.HelperNumber;
import net.laubenberger.wichtel.helper.HelperString;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is a format factory.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public abstract class FormatFactory {
	private static final Logger log = LoggerFactory.getLogger(FormatFactory.class);

	public static final String PATTERN_DATE_ISO8601 = "yyyy-MM-dd'T'HH:mm:ssz"; //$NON-NLS-1$
    public static final String PATTERN_DATE_RSS2 = "EEE, dd MMM yyyy HH:mm:ss z"; //$NON-NLS-1$
	public static final String PATTERN_DATE_DAY_MONTH_YEAR = "dd.MM.yyyy"; //$NON-NLS-1$
	public static final String PATTERN_DATE_YEAR_MONTH_DAY = "yyyy.MM.dd"; //$NON-NLS-1$
	public static final String PATTERN_DATE_YEAR_MONTH_DAY_HOUR_MINUTE = "yyyy.MM.dd HH:mm"; //$NON-NLS-1$
	public static final String PATTERN_DATE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy.MM.dd HH:mm:ss"; //$NON-NLS-1$
	public static final String PATTERN_DATE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_MILLISECOND = "yyyy.MM.dd HH:mm:ss.SSS"; //$NON-NLS-1$
	public static final String PATTERN_DATE_DAY_MONTH_YEAR_HOUR_MINUTE = "dd.MM.yyyy HH:mm"; //$NON-NLS-1$
	public static final String PATTERN_DATE_DAY_MONTH_YEAR_HOUR_MINUTE_SECOND = "dd.MM.yyyy HH:mm:ss"; //$NON-NLS-1$
	public static final String PATTERN_DATE_DAY_MONTH_YEAR_HOUR_MINUTE_SECOND_MILLISECOND = "dd.MM.yyyy HH:mm:ss.SSS"; //$NON-NLS-1$
	public static final String PATTERN_DATE_HOUR_MINUTE = "HH:mm"; //$NON-NLS-1$
	public static final String PATTERN_DATE_HOUR_MINUTE_SECOND = "HH:mm:ss"; //$NON-NLS-1$
	public static final String PATTERN_DATE_HOUR_MINUTE_SECOND_MILLISECOND = "HH:mm:ss.SSS"; //$NON-NLS-1$
	public static final String PATTERN_DATE_TEXT = "EEEEE, dd. MMMMM yyyy"; //$NON-NLS-1$

	/**
	 * Creates a default {@link NumberFormat}.
	 *
	 * @return created {@link NumberFormat}
	 * @see NumberFormat
	 * @since 0.0.1
	 */
	public static NumberFormat createNumberFormat() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		final NumberFormat result = NumberFormat.getNumberInstance(Locale.getDefault());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Creates a {@link NumberFormat} with a given pattern.
	 *
	 * @param pattern for the format
	 * @return created {@link NumberFormat}
	 * @see NumberFormat
	 * @since 0.0.1
	 */
	public static NumberFormat createNumberFormat(final String pattern) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(pattern));
		if (null == pattern) {
			throw new RuntimeExceptionIsNull("pattern"); //$NON-NLS-1$
		}
		if (!HelperString.isValid(pattern)) {
			throw new RuntimeExceptionIsEmpty("pattern"); //$NON-NLS-1$
		}
		
		final NumberFormat result = new DecimalFormat(pattern);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Creates a default {@link NumberFormat} for percent values.
	 *
	 * @return created {@link NumberFormat}
	 * @see NumberFormat
	 * @since 0.0.1
	 */
	public static NumberFormat createPercentFormat() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		final NumberFormat result = NumberFormat.getPercentInstance(Locale.getDefault());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Creates a default {@link NumberFormat} for currency values.
	 *
	 * @return created {@link NumberFormat}
	 * @see NumberFormat
	 * @since 0.0.1
	 */
	public static NumberFormat createCurrencyFormat() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		final NumberFormat result = NumberFormat.getCurrencyInstance(Locale.getDefault());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Creates a {@link DateFormat} with a given pattern.
	 *
	 * @param pattern for the format
	 * @return created {@link DateFormat}
	 * @see DateFormat
	 * @since 0.0.1
	 */
	public static DateFormat createDateFormat(final String pattern) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(pattern));
		if (null == pattern) {
			throw new RuntimeExceptionIsNull("pattern"); //$NON-NLS-1$
		}
		if (!HelperString.isValid(pattern)) {
			throw new RuntimeExceptionIsEmpty("pattern"); //$NON-NLS-1$
		}

		final DateFormat result = new SimpleDateFormat(pattern, Locale.getDefault());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Creates a {@link NumberFormatter} for display-mode from a given {@link NumberFormat}.
	 *
	 * @param format for the formatter
	 * @return created {@link NumberFormatter}
	 * @see NumberFormat
	 * @see NumberFormatter
	 * @since 0.0.1
	 */
	public static NumberFormatter createNumberFormatterDisplay(final NumberFormat format) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(format));
		if (null == format) {
			throw new RuntimeExceptionIsNull("format"); //$NON-NLS-1$
		}

		final NumberFormatter result = new NumberFormatter(format) {
			private static final long serialVersionUID = -8279122736069976223L;

			{
				setCommitsOnValidEdit(true);
			}
		};

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Creates a {@link NumberFormatter} for edit-mode from a given {@link NumberFormat}.
	 *
	 * @param format for the formatter
	 * @return created {@link NumberFormatter}
	 * @see NumberFormat
	 * @see NumberFormatter
	 * @since 0.0.1
	 */
	public static NumberFormatter createNumberFormatterEdit(final NumberFormat format) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(format));
		if (null == format) {
			throw new RuntimeExceptionIsNull("format"); //$NON-NLS-1$
		}

		final NumberFormatter result = new NumberFormatter(format) {
			private static final long serialVersionUID = 1315478587371973936L;

			{
				setCommitsOnValidEdit(true);
				setValueClass(BigDecimal.class);
			}

			@Override
			public Object stringToValue(final String string) throws ParseException {
				final String text = HelperString.getNumericString(string);

				return null == text ? null : super.stringToValue(text);
			}
		};

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Creates a {@link NumberFormatter} (percent) for display-mode from a given {@link NumberFormat}.
	 *
	 * @param format for the formatter
	 * @return created {@link NumberFormatter}
	 * @see NumberFormat
	 * @see NumberFormatter
	 * @since 0.0.1
	 */
	public static NumberFormatter createPercentFormatterDisplay(final NumberFormat format) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(format));
		if (null == format) {
			throw new RuntimeExceptionIsNull("format"); //$NON-NLS-1$
		}

		final NumberFormatter result = new NumberFormatter(format) {
			private static final long serialVersionUID = 1104014354379282093L;

			{
				setCommitsOnValidEdit(true);
			}
		};

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Creates a {@link NumberFormatter} (percent) for edit-mode from a given {@link NumberFormat}.
	 *
	 * @param format for the formatter
	 * @return created {@link NumberFormatter}
	 * @see NumberFormat
	 * @see NumberFormatter
	 * @since 0.0.1
	 */
	public static NumberFormatter createPercentFormatterEdit(final NumberFormat format) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(format));
		if (null == format) {
			throw new RuntimeExceptionIsNull("format"); //$NON-NLS-1$
		}

		final NumberFormatter result = new NumberFormatter(format) {
			private static final long serialVersionUID = 1315478587371973936L;

			{
				setCommitsOnValidEdit(true);
				setValueClass(BigDecimal.class);
			}

			@Override
			public String valueToString(final Object o)
					throws ParseException {
				Number number = (Number) o;
				if (null != number) {
					number = number.doubleValue() * HelperNumber.NUMBER_100.doubleValue();
				}
				return super.valueToString(number);
			}

			@Override
			public Object stringToValue(final String s) {
				final String text = HelperString.getNumericString(s);

				if (null != text) {
					final Number number = new BigDecimal(HelperString.getNumericString(s));

					return number.doubleValue() / HelperNumber.NUMBER_100.doubleValue();
				}
				return null;
			}
		};

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Creates a {@link DateFormatter} for display-mode from a given {@link DateFormat}.
	 *
	 * @param format for the formatter
	 * @return created {@link DateFormatter}
	 * @see DateFormat
	 * @see DateFormatter
	 * @since 0.0.1
	 */
	public static DateFormatter createDateFormatterDisplay(final DateFormat format) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(format));
		if (null == format) {
			throw new RuntimeExceptionIsNull("format"); //$NON-NLS-1$
		}

		final DateFormatter result = new DateFormatter(format) {
			private static final long serialVersionUID = 8705680761187261160L;

			{
				setCommitsOnValidEdit(true);
			}
		};

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Creates a {@link DateFormatter} for edit-mode from a given {@link DateFormat}.
	 *
	 * @param format for the formatter
	 * @return created {@link DateFormatter}
	 * @see DateFormat
	 * @see DateFormatter
	 * @since 0.0.1
	 */
	public static DateFormatter createDateFormatterEdit(final DateFormat format) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(format));
		if (null == format) {
			throw new RuntimeExceptionIsNull("format"); //$NON-NLS-1$
		}

		final DateFormatter result = new DateFormatter(format) {
			private static final long serialVersionUID = 8705680761187261160L;

			{
				setCommitsOnValidEdit(true);
			}
		};

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Creates a {@link MaskFormatter} from a given pattern.
	 *
	 * @param format for the formatter
	 * @return created {@link MaskFormatter}
	 * @throws ParseException
	 * @see MaskFormatter
	 * @since 0.0.1
	 */
	public static MaskFormatter createMaskFormatter(final String format) throws ParseException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(format));
		if (null == format) {
			throw new RuntimeExceptionIsNull("format"); //$NON-NLS-1$
		}

		final MaskFormatter result = new MaskFormatter(format) {
			private static final long serialVersionUID = 5212947957420446007L;

			{
				setCommitsOnValidEdit(true);
				setPlaceholderCharacter('_');
			}
		};

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Creates a {@link DefaultFormatter} from a given regex pattern.
	 *
	 * @param regex for the formatter
	 * @return created {@link DefaultFormatter}
	 * @see Pattern
	 * @see DefaultFormatter
	 * @since 0.0.1
	 */
	public static DefaultFormatter createRegexFormatter(final Pattern regex) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(regex));
		if (null == regex) {
			throw new RuntimeExceptionIsNull("regex"); //$NON-NLS-1$
		}

		final DefaultFormatter result = new DefaultFormatter() {
			private static final long serialVersionUID = 2665033244806980400L;

			final transient Matcher matcher;

			{
				setCommitsOnValidEdit(true);
				setOverwriteMode(false);
				matcher = regex.matcher(HelperString.EMPTY_STRING);
			}


			@Override
			public Object stringToValue(final String string) throws ParseException {
				if (null == string) {
					return null;
				}

				matcher.reset(string);

				if (!matcher.matches()) {
					throw new ParseException("does not match regex", 0); //$NON-NLS-1$
				}
				return super.stringToValue(string);
			}
		};

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}
