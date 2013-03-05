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
package net.laubenberger.wichtel.model.unit;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

/**
 * Bit units
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
@XmlRootElement(name = "bit")
public enum Bit implements Unit<Bit> {
	BIT(BigDecimal.ONE, "bit"), //$NON-NLS-1$
	BYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_BYTE, Constants.DEFAULT_MATHCONTEXT), "B"), //$NON-NLS-1$
	KILOBIT(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_KILOBIT, Constants.DEFAULT_MATHCONTEXT), "kbit"), //$NON-NLS-1$
	MEGABIT(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_MEGABIT, Constants.DEFAULT_MATHCONTEXT), "Mbit"), //$NON-NLS-1$
	GIGABIT(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_GIGABIT, Constants.DEFAULT_MATHCONTEXT), "Gbit"), //$NON-NLS-1$
	TERABIT(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_TERABIT, Constants.DEFAULT_MATHCONTEXT), "Tbit"), //$NON-NLS-1$
	PETABIT(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_PETABIT, Constants.DEFAULT_MATHCONTEXT), "Pbit"), //$NON-NLS-1$
	EXABIT(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_EXABIT, Constants.DEFAULT_MATHCONTEXT), "Ebit"), //$NON-NLS-1$
	ZETTABIT(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_ZETTABIT, Constants.DEFAULT_MATHCONTEXT), "Zbit"), //$NON-NLS-1$
	YOTTABIT(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_YOTTABIT, Constants.DEFAULT_MATHCONTEXT), "Ybit"), //$NON-NLS-1$
	KILOBYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_KILOBYTE, Constants.DEFAULT_MATHCONTEXT), "kB"), //$NON-NLS-1$
	MEGABYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_MEGABYTE, Constants.DEFAULT_MATHCONTEXT), "MB"), //$NON-NLS-1$
	GIGABYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_GIGABYTE, Constants.DEFAULT_MATHCONTEXT), "GB"), //$NON-NLS-1$
	TERABYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_TERABYTE, Constants.DEFAULT_MATHCONTEXT), "TB"), //$NON-NLS-1$
	PETABYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_PETABYTE, Constants.DEFAULT_MATHCONTEXT), "PB"), //$NON-NLS-1$
	EXABYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_EXABYTE, Constants.DEFAULT_MATHCONTEXT), "EB"), //$NON-NLS-1$
	ZETTABYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_ZETTABYTE, Constants.DEFAULT_MATHCONTEXT), "ZB"), //$NON-NLS-1$
	YOTTABYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_YOTTABYTE, Constants.DEFAULT_MATHCONTEXT), "YB"), //$NON-NLS-1$
	KIBIBIT(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_KIBIBIT, Constants.DEFAULT_MATHCONTEXT), "Kibit"), //$NON-NLS-1$
	MEBIBIT(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_MEBIBIT, Constants.DEFAULT_MATHCONTEXT), "Mibit"), //$NON-NLS-1$
	GIBIBIT(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_GIBIBIT, Constants.DEFAULT_MATHCONTEXT), "Gibit"), //$NON-NLS-1$
	TEBIBIT(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_TEBIBIT, Constants.DEFAULT_MATHCONTEXT), "Tibit"), //$NON-NLS-1$
	PEBIBIT(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_PEBIBIT, Constants.DEFAULT_MATHCONTEXT), "Pibit"), //$NON-NLS-1$
	EXBIBIT(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_EXBIBIT, Constants.DEFAULT_MATHCONTEXT), "Eibit"), //$NON-NLS-1$
	ZEBIBIT(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_ZEBIBIT, Constants.DEFAULT_MATHCONTEXT), "Zibit"), //$NON-NLS-1$
	YOBIBIT(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_YOBIBIT, Constants.DEFAULT_MATHCONTEXT), "Yibit"), //$NON-NLS-1$
	KIBIBYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_KIBIBYTE, Constants.DEFAULT_MATHCONTEXT), "KiB"), //$NON-NLS-1$
	MEBIBYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_MEBIBYTE, Constants.DEFAULT_MATHCONTEXT), "MiB"), //$NON-NLS-1$
	GIBIBYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_GIBIBYTE, Constants.DEFAULT_MATHCONTEXT), "GiB"), //$NON-NLS-1$
	TEBIBYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_TEBIBYTE, Constants.DEFAULT_MATHCONTEXT), "TiB"), //$NON-NLS-1$
	PEBIBYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_PEBIBYTE, Constants.DEFAULT_MATHCONTEXT), "PiB"), //$NON-NLS-1$
	EXBIBYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_EXBIBYTE, Constants.DEFAULT_MATHCONTEXT), "EiB"), //$NON-NLS-1$
	ZEBIBYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_ZEBIBYTE, Constants.DEFAULT_MATHCONTEXT), "ZiB"), //$NON-NLS-1$
	YOBIBYTE(BigDecimal.ONE.divide(Constants.FACTOR_BIT_TO_YOBIBYTE, Constants.DEFAULT_MATHCONTEXT), "YiB"); //$NON-NLS-1$

	final BigDecimal factor;
	final String symbol;

	Bit(final BigDecimal factor, final String symbol) {
		this.factor = factor;
		this.symbol = symbol;
	}

	BigDecimal getFactor() {
		return factor;
	}

	public String getSymbol() {
		return symbol;
	}


	/*
	 * Implemented methods
	 */

	@Override
	public BigDecimal convertTo(final Bit toUnit, final Number value) { //$JUnit$
		if (null == toUnit) {
			throw new RuntimeExceptionIsNull("toUnit"); //$NON-NLS-1$
		}
		if (null == value) {
			throw new RuntimeExceptionIsNull("value"); //$NON-NLS-1$
		}

		final BigDecimal internalValue = new BigDecimal(value.toString());

		return toUnit == this ? internalValue : internalValue.divide(factor, Constants.DEFAULT_MATHCONTEXT).multiply(toUnit.factor, Constants.DEFAULT_MATHCONTEXT);
	}
}	

