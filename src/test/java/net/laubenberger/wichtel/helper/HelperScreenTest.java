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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Dimension;
import java.awt.Toolkit;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.junit.Test;


/**
 * JUnit test for {@link HelperScreen}
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 */
public class HelperScreenTest {
	@Test
	public void testGetCurrentScreenSize() {
		assertEquals(Toolkit.getDefaultToolkit().getScreenSize(), HelperScreen.getCurrentScreenSize());
	}

	@Test
	public void testGetCurrentColorModel() {
//		System.err.println(HelperScreen.getCurrentColorModel());
		assertEquals(Toolkit.getDefaultToolkit().getColorModel(), HelperScreen.getCurrentColorModel());
	}

//	@Test
//	public void testGetCurrentNumberOfColors() {
//		System.err.println(HelperScreen.getCurrentNumberOfColors().);
//		assertEquals(Toolkit.getDefaultToolkit().getColorModel().getColorSpace(), HelperScreen.getCurrentNumberOfColors());
//	}

	@Test
	public void testGetCurrentScreenResolution() {
//		System.err.println(HelperScreen.getCurrentScreenResolution());
		assertEquals(Toolkit.getDefaultToolkit().getScreenResolution(), HelperScreen.getCurrentScreenResolution());
	}

	@Test
	public void testIsValidScreenSize() {
		final Dimension currentSize = HelperScreen.getCurrentScreenSize();
		
		assertTrue(HelperScreen.isValidScreenSize(currentSize));
		assertTrue(HelperScreen.isValidScreenSize(new Dimension(currentSize.width - 10, currentSize.height - 10)));
		assertFalse(HelperScreen.isValidScreenSize(new Dimension(currentSize.width + 10, currentSize.height + 10)));
		assertTrue(HelperScreen.isValidScreenSize(new Dimension(currentSize.width, currentSize.height - 10)));
		assertFalse(HelperScreen.isValidScreenSize(new Dimension(currentSize.width, currentSize.height + 10)));
		assertTrue(HelperScreen.isValidScreenSize(new Dimension(currentSize.width - 10, currentSize.height)));
		assertFalse(HelperScreen.isValidScreenSize(new Dimension(currentSize.width + 10, currentSize.height)));

		try {
			HelperScreen.isValidScreenSize(null);
			fail("minSize is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}