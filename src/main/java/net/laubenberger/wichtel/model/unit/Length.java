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
 * Length units
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
@XmlRootElement(name = "length")
public enum Length implements Unit<Length> {
	MM(Constants.FACTOR_MM_TO_CM.multiply(Constants.FACTOR_CM_TO_M, Constants.DEFAULT_MATHCONTEXT)), //$JUnit$
	CM(Constants.FACTOR_CM_TO_M), //$JUnit$
	M(BigDecimal.ONE),
	KM(BigDecimal.ONE.divide(Constants.FACTOR_M_TO_KM, Constants.DEFAULT_MATHCONTEXT)), //$JUnit$
	INCH(Constants.FACTOR_CM_TO_M.divide(Constants.FACTOR_INCH_TO_CM, Constants.DEFAULT_MATHCONTEXT)), //$JUnit$
	FOOT(BigDecimal.ONE.divide(Constants.FACTOR_FOOT_TO_M, Constants.DEFAULT_MATHCONTEXT)), //$JUnit$
	YARD(BigDecimal.ONE.divide(Constants.FACTOR_YARD_TO_M, Constants.DEFAULT_MATHCONTEXT)), //$JUnit$
	MILE(BigDecimal.ONE.divide(Constants.FACTOR_MILE_TO_M, Constants.DEFAULT_MATHCONTEXT)), //$JUnit$
	NAUTICAL_MILE(BigDecimal.ONE.divide(Constants.FACTOR_NAUTICAL_MILE_TO_M, Constants.DEFAULT_MATHCONTEXT)); //$JUnit$

	final BigDecimal factor;

	Length(final BigDecimal factor) {
		this.factor = factor;
	}

	BigDecimal getFactor() {
		return factor;
	}


	/*
	 * Implemented methods
	 */

	@Override
	public BigDecimal convertTo(final Length toUnit, final Number value) { //$JUnit$
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

