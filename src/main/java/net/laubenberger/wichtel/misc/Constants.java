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

package net.laubenberger.wichtel.misc;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import net.laubenberger.wichtel.helper.HelperNumber;
import net.laubenberger.wichtel.helper.HelperTime;
import net.laubenberger.wichtel.model.misc.Document;
import net.laubenberger.wichtel.model.misc.DocumentImpl;
import net.laubenberger.wichtel.model.misc.Language;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Collected constants of very general utility.
 *
 * @author Stefan Laubenberger
 * @version 0.0.2, 2013-03-13
 * @since 0.0.1
 */
public abstract class Constants {
	private static final Logger log = LoggerFactory.getLogger(Constants.class);

	//wichtel specific
	public static final Document WICHTEL = new DocumentImpl();
	
	//defaults
//	public static final MathContext DEFAULT_MATHCONTEXT = MathContext.DECIMAL128;
	public static final MathContext DEFAULT_MATHCONTEXT = new MathContext(34, RoundingMode.DOWN);
	public static final int DEFAULT_FILE_BUFFER_SIZE = HelperNumber.NUMBER_65536.intValue(); //64kB
	public static final BigDecimal DEFAULT_DPI = new BigDecimal("72"); //$NON-NLS-1$

//	//algebraic signs
//	public static final int POSITIVE = 1;
//	public static final int NEGATIVE = -1;
//	public static final String PLUS_SIGN = "+"; //$NON-NLS-1$
//	public static final String NEGATIVE_SIGN = "-"; //$NON-NLS-1$

	//constants
	public static final BigDecimal SPEED_OF_LIGHT = new BigDecimal("299792458"); //speed of light in m/s //$NON-NLS-1$
	public static final BigDecimal ABSOLUTE_ZERO = new BigDecimal("-273.15"); //absolute zero in Celsius //$NON-NLS-1$
	public static final BigDecimal GRAVITY_ON_EARTH = new BigDecimal("9.806"); //gravity on earth in m/s^2 //$NON-NLS-1$

	//encodings
	public static final String ENCODING_UTF8 = "UTF-8"; //$NON-NLS-1$
	public static final String ENCODING_UTF16 = "UTF-16"; //$NON-NLS-1$
	public static final String ENCODING_ISO8859_1 = "ISO-8859-1"; //$NON-NLS-1$
	public static final String ENCODING_ASCII = "US-ASCII"; //$NON-NLS-1$
	public static final String ENCODING_DEFAULT = ENCODING_UTF8;

	/*
	 * factors
	 */
	//various
	public static final BigDecimal FACTOR_GOLDEN_RATIO_A_TO_B = new BigDecimal("1.6180339887"); //golden ratio between a and b //$NON-NLS-1$
	public static final BigDecimal FACTOR_KCAL_TO_KJ = new BigDecimal("4.184"); //kilogram calorie to kilojoule //$NON-NLS-1$

	//area
	public static final BigDecimal FACTOR_MM2_TO_CM2 = HelperNumber.NUMBER_100; //millimeters^2 to centimeters^2
	public static final BigDecimal FACTOR_CM2_TO_M2 = HelperNumber.NUMBER_10000; //centimeters^2 to meters^2
	public static final BigDecimal FACTOR_M2_TO_AREA = HelperNumber.NUMBER_100; //meters^2 to area
	public static final BigDecimal FACTOR_AREA_TO_HECTARE = HelperNumber.NUMBER_100; //area to hectare
	public static final BigDecimal FACTOR_HECTARE_TO_KM2 = HelperNumber.NUMBER_100; //hectare to kilometers^2
	public static final BigDecimal FACTOR_FOOT2_TO_M2 = new BigDecimal("0.09290304"); //square foot to meters^2 //$NON-NLS-1$
	public static final BigDecimal FACTOR_YARD2_TO_M2 = new BigDecimal("0.83612736"); //square yard to meters^2 //$NON-NLS-1$
	public static final BigDecimal FACTOR_PERCH_TO_M2 = new BigDecimal("25.2928526"); //square perch to meters^2 //$NON-NLS-1$
	public static final BigDecimal FACTOR_ACRE_TO_M2 = new BigDecimal("4046.8564224"); //acre to meters^2 //$NON-NLS-1$
	public static final BigDecimal FACTOR_MILE2_TO_KM2 = new BigDecimal("2.5899881103"); //square mile (terrestrial) to kilometers^2 //$NON-NLS-1$

	//bit
	public static final BigDecimal FACTOR_BIT_TO_BYTE = HelperNumber.NUMBER_8; //bit to byte
	public static final BigDecimal FACTOR_BIT_TO_KILOBIT = new BigDecimal("10E2"); //$NON-NLS-1$
	public static final BigDecimal FACTOR_BIT_TO_MEGABIT = new BigDecimal("10E5"); //$NON-NLS-1$
	public static final BigDecimal FACTOR_BIT_TO_GIGABIT = new BigDecimal("10E8"); //$NON-NLS-1$
	public static final BigDecimal FACTOR_BIT_TO_TERABIT = new BigDecimal("10E11"); //$NON-NLS-1$
	public static final BigDecimal FACTOR_BIT_TO_PETABIT = new BigDecimal("10E14"); //$NON-NLS-1$
	public static final BigDecimal FACTOR_BIT_TO_EXABIT = new BigDecimal("10E17"); //$NON-NLS-1$
	public static final BigDecimal FACTOR_BIT_TO_ZETTABIT = new BigDecimal("10E20"); //$NON-NLS-1$
	public static final BigDecimal FACTOR_BIT_TO_YOTTABIT = new BigDecimal("10E23"); //$NON-NLS-1$
	public static final BigDecimal FACTOR_BIT_TO_KILOBYTE = FACTOR_BIT_TO_BYTE.multiply(FACTOR_BIT_TO_KILOBIT);
	public static final BigDecimal FACTOR_BIT_TO_MEGABYTE = FACTOR_BIT_TO_BYTE.multiply(FACTOR_BIT_TO_MEGABIT);
	public static final BigDecimal FACTOR_BIT_TO_GIGABYTE = FACTOR_BIT_TO_BYTE.multiply(FACTOR_BIT_TO_GIGABIT);
	public static final BigDecimal FACTOR_BIT_TO_TERABYTE = FACTOR_BIT_TO_BYTE.multiply(FACTOR_BIT_TO_TERABIT);
	public static final BigDecimal FACTOR_BIT_TO_PETABYTE = FACTOR_BIT_TO_BYTE.multiply(FACTOR_BIT_TO_PETABIT);
	public static final BigDecimal FACTOR_BIT_TO_EXABYTE = FACTOR_BIT_TO_BYTE.multiply(FACTOR_BIT_TO_EXABIT);
	public static final BigDecimal FACTOR_BIT_TO_ZETTABYTE = FACTOR_BIT_TO_BYTE.multiply(FACTOR_BIT_TO_ZETTABIT);
	public static final BigDecimal FACTOR_BIT_TO_YOTTABYTE = FACTOR_BIT_TO_BYTE.multiply(FACTOR_BIT_TO_YOTTABIT);
	public static final BigDecimal FACTOR_BIT_TO_KIBIBIT = HelperNumber.NUMBER_1024;
	public static final BigDecimal FACTOR_BIT_TO_MEBIBIT = FACTOR_BIT_TO_KIBIBIT.multiply(HelperNumber.NUMBER_1024);
	public static final BigDecimal FACTOR_BIT_TO_GIBIBIT = FACTOR_BIT_TO_MEBIBIT.multiply(HelperNumber.NUMBER_1024);
	public static final BigDecimal FACTOR_BIT_TO_TEBIBIT = FACTOR_BIT_TO_GIBIBIT.multiply(HelperNumber.NUMBER_1024);
	public static final BigDecimal FACTOR_BIT_TO_PEBIBIT = FACTOR_BIT_TO_TEBIBIT.multiply(HelperNumber.NUMBER_1024);
	public static final BigDecimal FACTOR_BIT_TO_EXBIBIT = FACTOR_BIT_TO_PEBIBIT.multiply(HelperNumber.NUMBER_1024);
	public static final BigDecimal FACTOR_BIT_TO_ZEBIBIT = FACTOR_BIT_TO_EXBIBIT.multiply(HelperNumber.NUMBER_1024);
	public static final BigDecimal FACTOR_BIT_TO_YOBIBIT = FACTOR_BIT_TO_ZEBIBIT.multiply(HelperNumber.NUMBER_1024);
	public static final BigDecimal FACTOR_BIT_TO_KIBIBYTE = FACTOR_BIT_TO_BYTE.multiply(FACTOR_BIT_TO_KIBIBIT);
	public static final BigDecimal FACTOR_BIT_TO_MEBIBYTE = FACTOR_BIT_TO_BYTE.multiply(FACTOR_BIT_TO_MEBIBIT);
	public static final BigDecimal FACTOR_BIT_TO_GIBIBYTE = FACTOR_BIT_TO_BYTE.multiply(FACTOR_BIT_TO_GIBIBIT);
	public static final BigDecimal FACTOR_BIT_TO_TEBIBYTE = FACTOR_BIT_TO_BYTE.multiply(FACTOR_BIT_TO_TEBIBIT);
	public static final BigDecimal FACTOR_BIT_TO_PEBIBYTE = FACTOR_BIT_TO_BYTE.multiply(FACTOR_BIT_TO_PEBIBIT);
	public static final BigDecimal FACTOR_BIT_TO_EXBIBYTE = FACTOR_BIT_TO_BYTE.multiply(FACTOR_BIT_TO_EXBIBIT);
	public static final BigDecimal FACTOR_BIT_TO_ZEBIBYTE = FACTOR_BIT_TO_BYTE.multiply(FACTOR_BIT_TO_ZEBIBIT);
	public static final BigDecimal FACTOR_BIT_TO_YOBIBYTE = FACTOR_BIT_TO_BYTE.multiply(FACTOR_BIT_TO_YOBIBIT);

	//length
	public static final BigDecimal FACTOR_INCH_TO_CM = new BigDecimal("2.54"); //inch to centimeters //$NON-NLS-1$
	public static final BigDecimal FACTOR_FOOT_TO_M = new BigDecimal("0.3048"); //foot to meters //$NON-NLS-1$
	public static final BigDecimal FACTOR_YARD_TO_M = new BigDecimal("0.9144"); //yard to meters //$NON-NLS-1$
	public static final BigDecimal FACTOR_MILE_TO_M = new BigDecimal("1609.344"); //mile (terrestrial) to meters //$NON-NLS-1$
	public static final BigDecimal FACTOR_NAUTICAL_MILE_TO_M = new BigDecimal("1852"); //nautical mile to meters //$NON-NLS-1$
	public static final BigDecimal FACTOR_MM_TO_CM = BigDecimal.TEN; //millimeters to centimeters
	public static final BigDecimal FACTOR_CM_TO_M = HelperNumber.NUMBER_100; //centimeters to meters
	public static final BigDecimal FACTOR_M_TO_KM = HelperNumber.NUMBER_1000; //meters to kilometers

	//time
	public static final BigDecimal FACTOR_NANOSECOND_TO_SECOND = new BigDecimal("1000000000"); //nanoseconds to seconds //$NON-NLS-1$
	public static final BigDecimal FACTOR_MICROSECOND_TO_SECOND = HelperNumber.NUMBER_1000000; //microseconds to seconds
	public static final BigDecimal FACTOR_MILLISECOND_TO_SECOND = HelperNumber.NUMBER_1000; //milliseconds to seconds
	public static final BigDecimal FACTOR_SECOND_TO_MINUTE = new BigDecimal("60"); //seconds to minutes //$NON-NLS-1$
	public static final BigDecimal FACTOR_MINUTE_TO_HOUR = new BigDecimal("60"); //minutes to hours //$NON-NLS-1$
	public static final BigDecimal FACTOR_HOUR_TO_DAY = new BigDecimal("24"); //hours to days //$NON-NLS-1$
	public static final BigDecimal FACTOR_DAY_TO_WEEK = new BigDecimal("7"); //days to weeks //$NON-NLS-1$
	public static final BigDecimal FACTOR_DAY_TO_MONTH = new BigDecimal("30"); //days to months //$NON-NLS-1$
	public static final BigDecimal FACTOR_DAY_TO_YEAR = new BigDecimal("365"); //days to years //$NON-NLS-1$

	//volume
	public static final BigDecimal FACTOR_MM3_TO_CM3 = HelperNumber.NUMBER_1000; //millimeters^3 to centimeters^3
	public static final BigDecimal FACTOR_CM3_TO_L = HelperNumber.NUMBER_1000; //centimeters^3 to liter
	public static final BigDecimal FACTOR_L_TO_M3 = HelperNumber.NUMBER_1000; //liter to m^3
	public static final BigDecimal FACTOR_PINT_TO_CM3 = new BigDecimal("473.176473"); //pint to centimeters^3 //$NON-NLS-1$
	public static final BigDecimal FACTOR_QUART_TO_L = new BigDecimal("0.946326"); //quart to liter //$NON-NLS-1$
	public static final BigDecimal FACTOR_GALLON_US_TO_L = new BigDecimal("3.785411784"); //gallon to liter //$NON-NLS-1$
	public static final BigDecimal FACTOR_BARREL_TO_L = new BigDecimal("158.987294928"); //barrel to liter //$NON-NLS-1$

	//weight
	public static final BigDecimal FACTOR_MILLIGRAM_TO_GRAM = HelperNumber.NUMBER_1000; //milligram to gram
	public static final BigDecimal FACTOR_GRAM_TO_KILOGRAM = HelperNumber.NUMBER_1000; //gram to kilogram
	public static final BigDecimal FACTOR_OUNCE_TO_GRAM = new BigDecimal("28.34952"); //ounce to gram //$NON-NLS-1$
	public static final BigDecimal FACTOR_POUND_TO_KILOGRAM = new BigDecimal("0.453592"); //pound to kilogram //$NON-NLS-1$
	public static final BigDecimal FACTOR_TON_TO_KILOGRAM = new BigDecimal("907.1847"); //ton to kilogram //$NON-NLS-1$

	static {
		try {
			WICHTEL.setName("wichtel"); //$NON-NLS-1$
			WICHTEL.setVersion("0.0.2"); //$NON-NLS-1$
			WICHTEL.setBuild(1);
			WICHTEL.setCreated(HelperTime.getDate(2013, 3, 5, 22, 50, 0));
			WICHTEL.setChanged(HelperTime.getDate(2013, 3, 13, 18, 40, 0));
			WICHTEL.setLanguage(Language.ENGLISH);
			WICHTEL.setUUID(UUID.fromString("4d8f7b88-2a1e-4f74-98ca-99d8a0cf97a5")); //$NON-NLS-1$
			WICHTEL.setUrl(new URL("http://www.laubenberger.net/")); //$NON-NLS-1$
		} catch (MalformedURLException ex) {
			// should never happen!
			log.error("URL invalid", ex); //$NON-NLS-1$
		}
	}
}
