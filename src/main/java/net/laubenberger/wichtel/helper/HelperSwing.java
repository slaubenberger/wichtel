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

package net.laubenberger.wichtel.helper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JSlider;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.model.misc.Platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Helper class for Swing.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public abstract class HelperSwing {
	private static final Logger log = LoggerFactory.getLogger(HelperSwing.class);

	/**
	 * Sets the menu on MacOSX to the standard style.
	 * This method should be used before creating a new frame and after setting the L&F.
	 *
	 * @since 0.0.1
	 */
	public static void setMacOSXMenu() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		if (Platform.MAC_OSX == HelperEnvironment.getPlatform()) {
			//display the menu in MacOS X style
			try {
				System.setProperty("apple.laf.useScreenMenuBar", "true"); //$NON-NLS-1$ //$NON-NLS-2$

				final LookAndFeel laf = UIManager.getLookAndFeel();
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

				final Map<Object, Object> map = new HashMap<Object, Object>(6);

				map.put("MenuBarUI", UIManager.get("MenuBarUI")); //$NON-NLS-1$ //$NON-NLS-2$
				map.put("MenuUI", UIManager.get("MenuUI"));  //$NON-NLS-1$//$NON-NLS-2$
				map.put("MenuItemUI", UIManager.get("MenuItemUI")); //$NON-NLS-1$ //$NON-NLS-2$
				map.put("CheckBoxMenuItemUI", UIManager.get("CheckBoxMenuItemUI"));  //$NON-NLS-1$//$NON-NLS-2$
				map.put("RadioButtonMenuItemUI", UIManager.get("RadioButtonMenuItemUI")); //$NON-NLS-1$ //$NON-NLS-2$
				map.put("PopupMenuUI", UIManager.get("PopupMenuUI")); //$NON-NLS-1$ //$NON-NLS-2$

				UIManager.setLookAndFeel(laf);

				for (final Entry<?, ?> pair : map.entrySet()) {
					UIManager.put(pair.getKey(), pair.getValue());
				}
			} catch (Exception ex) {
				log.error("Could not set MacOS X menu bar", ex); //$NON-NLS-1$
			}
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Calculates the value for a {@link JSlider} with a given tick.
	 *
	 * @param value for the {@link JSlider}
	 * @param tick  of the {@link JSlider}
	 * @return calculated value
	 * @see JSlider
	 * @since 0.0.1
	 */
	public static int calculateValueForSlider(final Number value, final Number tick) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(value, tick));
		if (null == value) {
			throw new RuntimeExceptionIsNull("value"); //$NON-NLS-1$
		}
		if (null == tick) {
			throw new RuntimeExceptionIsNull("tick"); //$NON-NLS-1$
		}

		final BigDecimal internalValue = new BigDecimal(value.toString());
		final BigDecimal internalTick = new BigDecimal(tick.toString());

		final int result = internalValue.divide(internalTick, Constants.DEFAULT_MATHCONTEXT).intValue();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Calculates the current value of a {@link JSlider} with a given tick.
	 *
	 * @param slider with the current value
	 * @param tick	of the {@link JSlider}
	 * @return calculated current value
	 * @see JSlider
	 * @since 0.0.1
	 */
	public static BigDecimal calculateValueFromSlider(final JSlider slider, final Number tick) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(slider, tick));
		if (null == slider) {
			throw new RuntimeExceptionIsNull("slider"); //$NON-NLS-1$
		}
		if (null == tick) {
			throw new RuntimeExceptionIsNull("tick"); //$NON-NLS-1$
		}

		final BigDecimal internalTick = new BigDecimal(tick.toString());

		final BigDecimal result = new BigDecimal(slider.getValue()).multiply(internalTick, Constants.DEFAULT_MATHCONTEXT);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns a {@link List} containing all available system {@link LookAndFeelInfo}.
	 *
	 * @return {@link List} containing all look and feels
	 * @see LookAndFeelInfo
	 * @since 0.0.1
	 */
	public static List<LookAndFeelInfo> getAvailableLookAndFeels() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		final List<LookAndFeelInfo> result = Arrays.asList(UIManager.getInstalledLookAndFeels());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}