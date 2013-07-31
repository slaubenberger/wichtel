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

package net.laubenberger.wichtel.helper.encoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.laubenberger.wichtel.helper.HelperCollection;
import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;


/**
 * Encodes data to clean HTML-format.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public abstract class EncoderHtml {
	private static final Logger log = LoggerFactory.getLogger(HelperCollection.class);

	/**
	 * Encodes an input {@link String} to a HTML {@link String}.
	 *
	 * @param input {@link String} to encode to a HTML {@link String}
	 * @return encoded HTML {@link String}
	 * @since 0.0.1
	 */
	public static String encode(final String input) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}

		final StringBuilder sb = new StringBuilder(input.length());
		boolean lastWasBlankChar = false;

		for (final char c : input.toCharArray()) {
			if (' ' == c) {
				if (lastWasBlankChar) {
					lastWasBlankChar = false;
					sb.append("&nbsp;"); //$NON-NLS-1$
				} else {
					lastWasBlankChar = true;
					sb.append(' ');
				}
			} else {
				lastWasBlankChar = false;
				switch (c) {
					case '"':
						sb.append("&quot;"); //$NON-NLS-1$
						break;
					case '&':
						sb.append("&amp;"); //$NON-NLS-1$
						break;
					case '<':
						sb.append("&lt;"); //$NON-NLS-1$
						break;
					case '>':
						sb.append("&gt;"); //$NON-NLS-1$
						break;
					case '\n':
						sb.append("&lt;br/&gt;"); //$NON-NLS-1$
						break;
					default:
						final int ci = 0xffff & c;
						if (160 > ci) {
							// 7 Bit
							sb.append(c);
						} else {
							// not 7 Bit - replace with the unicode system
							sb.append("&#"); //$NON-NLS-1$
							sb.append(Integer.toString(ci));
							sb.append(';');
						}
						break;
				}
			}
		}
		final String result = sb.toString();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}
