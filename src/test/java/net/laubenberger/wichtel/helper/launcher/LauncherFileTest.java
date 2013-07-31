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

package net.laubenberger.wichtel.helper.launcher;

import static org.junit.Assert.fail;
import net.laubenberger.wichtel.AllTests;
import net.laubenberger.wichtel.helper.HelperString;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.resource.ResourceMisc;

import org.junit.Test;

/**
 * JUnit test for {@link LauncherFile}
 * 
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 */
public class LauncherFileTest {
//	@BeforeClass
//	public static void init() {
//		try {
//			LauncherFile.deleteTemporaryFiles();
//		} catch (IOException ex) {
//			fail(ex.getMessage());
//		}
//	}
	
	@Test
	public void testPassOpen() {
		try {
			LauncherFile.open(ResourceMisc.TXT.getResourceAsFile());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testPassEdit() {
		try {
			LauncherFile.edit(ResourceMisc.TXT.getResourceAsFile());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testPassPrint() {
		try {
			LauncherFile.print(ResourceMisc.TXT.getResourceAsFile());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testFailOpen() {
		try {
			LauncherFile.open(null);
			fail("file is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherFile.open(ResourceMisc.TXT.getResourceAsStream(), null);
			fail("extension is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherFile.open(ResourceMisc.TXT.getResourceAsStream(), HelperString.EMPTY_STRING);
			fail("extension is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		// Byte-array
		try {
			LauncherFile.open(AllTests.DATA.getBytes(), null);
			fail("extension is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherFile.open(AllTests.DATA.getBytes(), HelperString.EMPTY_STRING);
			fail("extension is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testFailEdit() {
		try {
			LauncherFile.edit(null);
			fail("file is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherFile.edit(ResourceMisc.TXT.getResourceAsStream(), null);
			fail("extension is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherFile.edit(ResourceMisc.TXT.getResourceAsStream(), HelperString.EMPTY_STRING);
			fail("extension is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		// Byte-array
		try {
			LauncherFile.edit(AllTests.DATA.getBytes(), null);
			fail("extension is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherFile.edit(AllTests.DATA.getBytes(), HelperString.EMPTY_STRING);
			fail("extension is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testFailPrint() {
		try {
			LauncherFile.print(null);
			fail("file is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherFile.print(ResourceMisc.TXT.getResourceAsStream(), null);
			fail("extension is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherFile.print(ResourceMisc.TXT.getResourceAsStream(), HelperString.EMPTY_STRING);
			fail("extension is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		// Byte-array
		try {
			LauncherFile.print(AllTests.DATA.getBytes(), null);
			fail("extension is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherFile.print(AllTests.DATA.getBytes(), HelperString.EMPTY_STRING);
			fail("extension is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}
