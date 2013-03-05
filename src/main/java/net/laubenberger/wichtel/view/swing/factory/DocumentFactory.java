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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.helper.HelperString;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is a document factory.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public abstract class DocumentFactory {
	private static final Logger log = LoggerFactory.getLogger(DocumentFactory.class);

	private static final Pattern PATTERN = Pattern.compile("[-%'0-9.]+"); //$NON-NLS-1$

	/**
	 * Creates a {@link PlainDocument} (text) with a given length.
	 *
	 * @param length of the input
	 * @return created {@link PlainDocument}
	 * @see PlainDocument
	 * @since 0.0.1
	 */
	public static PlainDocument createTextDocument(final int length) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(length));
		if (0 >= length) {
			throw new RuntimeExceptionMustBeGreater("length", length, 0); //$NON-NLS-1$
		}

		final PlainDocument result = new PlainDocument() {
			private static final long serialVersionUID = -5008928912535075396L;

			@Override
			public void insertString(final int offs, final String str, final AttributeSet a) throws BadLocationException {
				if (null != str && str.length() + offs <= length) {
					super.insertString(offs, str, a);
				}
			}
		};

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Creates a {@link PlainDocument} (numeric) with a given length.
	 *
	 * @param length of the input
	 * @return created {@link PlainDocument}
	 * @see PlainDocument
	 * @since 0.0.1
	 */
	public static PlainDocument createNumberDocument(final int length) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(length));
		if (0 >= length) {
			throw new RuntimeExceptionMustBeGreater("length", length, 0); //$NON-NLS-1$
		}

		final PlainDocument result = new PlainDocument() {
			private static final long serialVersionUID = 3766889554419497713L;

			@Override
			public void insertString(final int offs, final String str, final AttributeSet a) throws BadLocationException {
				if (null != str && str.length() + offs <= length && isStringNumeric(str)) {
					super.insertString(offs, str, a);
				}
			}
		};

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}


	/*
	 * Private methods
	 */

	static boolean isStringNumeric(final CharSequence arg) {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart(arg));

		boolean result = false;

		if (HelperString.isValid(arg)) {
			final Matcher matcher = PATTERN.matcher(arg);

			if (matcher.matches()) {
				result = true;
			}
		}

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit(result));
		return result;
	}
}
