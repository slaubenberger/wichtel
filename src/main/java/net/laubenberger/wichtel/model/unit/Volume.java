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
 * Volume units
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
@XmlRootElement(name = "volume")
public enum Volume implements Unit<Volume> {
	MM3(Constants.FACTOR_MM3_TO_CM3.multiply(Constants.FACTOR_CM3_TO_L, Constants.DEFAULT_MATHCONTEXT)), //$JUnit$
	CM3(Constants.FACTOR_CM3_TO_L), //$JUnit$
	L(BigDecimal.ONE),
	M3(BigDecimal.ONE.divide(Constants.FACTOR_L_TO_M3, Constants.DEFAULT_MATHCONTEXT)), //$JUnit$
	PINT(Constants.FACTOR_PINT_TO_CM3.multiply(Constants.FACTOR_CM3_TO_L, Constants.DEFAULT_MATHCONTEXT)), //$JUnit$
	QUART(Constants.FACTOR_QUART_TO_L), //$JUnit$
	GALLON_US(Constants.FACTOR_GALLON_US_TO_L), //$JUnit$
	BARREL(Constants.FACTOR_BARREL_TO_L); //$JUnit$

	final BigDecimal factor;

	Volume(final BigDecimal factor) {
		this.factor = factor;
	}

	BigDecimal getFactor() {
		return factor;
	}


	/*
	 * Implemented methods
	 */

	@Override
	public BigDecimal convertTo(final Volume toUnit, final Number value) { //$JUnit$
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

