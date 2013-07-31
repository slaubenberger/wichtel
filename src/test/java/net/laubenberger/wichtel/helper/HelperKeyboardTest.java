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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


/**
 * JUnit test for {@link HelperKeyboard}
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 */
public class HelperKeyboardTest {
	@Test
	public void testIsKeyPrintable() {
		assertTrue(HelperKeyboard.isKeyPrintable(65)); //A

		assertFalse(HelperKeyboard.isKeyPrintable(-1)); //keyCode couldn't be negative
		assertFalse(HelperKeyboard.isKeyPrintable(8)); // backspace
		assertFalse(HelperKeyboard.isKeyPrintable(16)); // shift
		assertFalse(HelperKeyboard.isKeyPrintable(17)); // ctrl
		assertFalse(HelperKeyboard.isKeyPrintable(18)); // alt
		assertFalse(HelperKeyboard.isKeyPrintable(20)); // Caps lock
		assertFalse(HelperKeyboard.isKeyPrintable(27)); // escape
		assertFalse(HelperKeyboard.isKeyPrintable(33)); //PgUp
		assertFalse(HelperKeyboard.isKeyPrintable(40)); //Arrow
		assertFalse(HelperKeyboard.isKeyPrintable(112)); //F1
		assertFalse(HelperKeyboard.isKeyPrintable(123)); //F12
		assertFalse(HelperKeyboard.isKeyPrintable(127)); // delete
		assertFalse(HelperKeyboard.isKeyPrintable(144)); // Num lock
		assertFalse(HelperKeyboard.isKeyPrintable(145)); // Scroll lock
		assertFalse(HelperKeyboard.isKeyPrintable(154)); // print
		assertFalse(HelperKeyboard.isKeyPrintable(155)); // insert
		assertFalse(HelperKeyboard.isKeyPrintable(157)); // Mac cmd
		assertFalse(HelperKeyboard.isKeyPrintable(524)); // Windows cmd
		assertFalse(HelperKeyboard.isKeyPrintable(525)); // Windows context menu
	}
}


