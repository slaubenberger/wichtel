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

package net.laubenberger.wichtel.model.misc;

import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.xml.bind.annotation.XmlRootElement;

import net.laubenberger.wichtel.helper.HelperString;

/**
 * Possible weekdays.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
@XmlRootElement(name = "weekday")
public enum Weekday {
	SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

	public String getName() {
		return getName(Locale.getDefault());
	}

	public String getName(final Locale locale) {
		return new SimpleDateFormat(HelperString.EMPTY_STRING, locale).getDateFormatSymbols().getWeekdays()[ordinal() + 1];
	}
}
