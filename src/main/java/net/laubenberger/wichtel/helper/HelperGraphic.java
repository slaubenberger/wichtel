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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Helper class for graphic operations.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public abstract class HelperGraphic {
	private static final Logger log = LoggerFactory.getLogger(HelperGraphic.class);

	/**
	 * Calculates the center of given a {@link Dimension}.
	 *
	 * @param size {@link Dimension} for the calculation
	 * @return {@link Dimension} with center coordinates
	 * @see Dimension
	 * @since 0.0.1
	 */
	public static Dimension getCenter(final Dimension size) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(size));
		if (null == size) {
			throw new RuntimeExceptionIsNull("size"); //$NON-NLS-1$
		}

		final Dimension result = new Dimension(size.width / 2, size.height / 2);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Calculates the scale to fit an input {@link Dimension} to an output {@link Dimension}.
	 *
	 * @param input  {@link Dimension} for the calculation
	 * @param output {@link Dimension} for the calculation
	 * @return scale to fit the input {@link Dimension}
	 * @see Dimension
	 * @since 0.0.1
	 */
	public static double getScale(final Dimension input, final Dimension output) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, output));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (0 > input.width) {
			throw new RuntimeExceptionMustBeGreater("input.width", input.width, 0); //$NON-NLS-1$
		}
		if (0 > input.height) {
			throw new RuntimeExceptionMustBeGreater("input.height", input.height, 0); //$NON-NLS-1$
		}
		if (null == output) {
			throw new RuntimeExceptionIsNull("output"); //$NON-NLS-1$
		}
		if (0 > output.width) {
			throw new RuntimeExceptionMustBeGreater("output.width", output.width, 0); //$NON-NLS-1$
		}
		if (0 > output.height) {
			throw new RuntimeExceptionMustBeGreater("output.height", output.height, 0); //$NON-NLS-1$
		}

		double scaleWidth = 0.0D;
		double scaleHeight = 0.0D;

		if (0 == output.width) {
			if (0 != output.height) {
				scaleHeight = input.getHeight() / output.getHeight();
			}
		} else {
			scaleWidth = input.getWidth() / output.getWidth();
		}
		if (0 == output.height) {
			if (0 != output.width) {
				scaleWidth = input.getWidth() / output.getWidth();
			}
		} else {
			scaleHeight = input.getHeight() / output.getHeight();
		}

		final double result = 0.0D == scaleWidth && 0.0D == scaleHeight ? 1.0D : scaleWidth > scaleHeight ? 1.0D / scaleWidth : 1.0D / scaleHeight;

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Calculates the scaled size to fit an input {@link Dimension} to an output {@link Dimension}.
	 *
	 * @param input  {@link Dimension} for the calculation
	 * @param output {@link Dimension} for the calculation
	 * @return scaled {@link Dimension} to fit the input {@link Dimension}
	 * @see Dimension
	 * @since 0.0.1
	 */
	public static Dimension getScaledSize(final Dimension input, final Dimension output) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, output));

		final Dimension result = getScaledSize(input, getScale(input, output));

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Calculates the scaled size to for an input {@link Dimension} with a given scale.
	 *
	 * @param input {@link Dimension} for the calculation
	 * @param scale for the new {@link Dimension}
	 * @return scaled {@link Dimension} of the input {@link Dimension}
	 * @see Dimension
	 * @since 0.0.1
	 */
	public static Dimension getScaledSize(final Dimension input, final double scale) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, scale));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}

		final Dimension result = new Dimension((int) (input.getWidth() * scale), (int) (input.getHeight() * scale));

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Calculates the size of a text in a {@link Graphics} container with its current {@link Font}.
	 *
	 * @param text	  for the calculation
	 * @param graphics {@link Graphics} container for the calculation
	 * @return {@link Dimension} with text size
	 * @see Graphics
	 * @see Dimension
	 * @see Font
	 * @since 0.0.1
	 */
	public static Dimension getTextSize(final String text, final Graphics graphics) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(text, graphics));
		if (null == text) {
			throw new RuntimeExceptionIsNull("text"); //$NON-NLS-1$
		}
		if (null == graphics) {
			throw new RuntimeExceptionIsNull("graphics"); //$NON-NLS-1$
		}

		final FontMetrics fm = graphics.getFontMetrics(graphics.getFont());
		final Rectangle2D rect = fm.getStringBounds(text, graphics);

		final Dimension result = new Dimension((int) rect.getWidth(), (int) rect.getHeight());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}


	/**
	 * Returns a {@link List} containing all available system {@link Font}.
	 *
	 * @return {@link List} containing all fonts
	 * @see Font
	 * @since 0.0.1
	 */
	public static List<Font> getAvailableFonts() { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		final List<Font> result = Arrays.asList(ge.getAllFonts());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns the hex value of a {@link Color} (RGB).
	 * Used e.g. for HTML.
	 *
	 * @param color for the hex value
	 * @return hex value of the color
	 * @since 0.0.1
	 */
	public static String getColorHex(final Color color) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(color));
		if (null == color) {
			throw new RuntimeExceptionIsNull("color"); //$NON-NLS-1$
		}

		final String result = Integer.toHexString(color.getRGB() & 0x00ffffff);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}
