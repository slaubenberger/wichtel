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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.laubenberger.wichtel.helper.HelperArray;
import net.laubenberger.wichtel.helper.HelperEnvironment;
import net.laubenberger.wichtel.helper.HelperIO;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.model.misc.Platform;

import org.junit.Test;

/**
 * JUnit test for {@link LauncherProcess}
 * 
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 */
public class LauncherProcessTest {
	private File fileOutput;
	private File fileError;
	private OutputStream osOutput;
	private OutputStream osError;

	{
		try {
			fileOutput = HelperIO.getTemporaryFile();
			fileError = HelperIO.getTemporaryFile();
			osOutput = new FileOutputStream(fileOutput);
			osError = new FileOutputStream(fileError);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testPassCreateAndStartProcess() {

		if (Platform.WINDOWS == HelperEnvironment.getPlatform()) {
			try {
				LauncherProcess.createAndStartProcess(new String[] { "dir" }, osOutput, osError); //$NON-NLS-1$
			} catch (IOException ex) {
				fail(ex.getMessage());
			}

			sleep();
			
			assertTrue(0 < fileOutput.length());

			try {
				LauncherProcess.createAndStartProcess(new String[] { "dir", "-ä" }, osOutput, osError); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (IOException ex) {
				fail(ex.getMessage());
			}

			sleep();
			
			assertTrue(0 < fileError.length());
		} else {
			try {
				LauncherProcess.createAndStartProcess(new String[] { "ls", "-l" }, osOutput, osError); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (IOException ex) {
				fail(ex.getMessage());
			}

			sleep();
			
			assertTrue(0 < fileOutput.length());

			try {
				LauncherProcess.createAndStartProcess(new String[] { "ls", "-ä" }, osOutput, osError); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (IOException ex) {
				fail(ex.getMessage());
			}

			sleep();

			assertTrue(0 < fileError.length());
		}
	}

	@Test
	public void testFailCreateAndStartProcess() {
		try {
			LauncherProcess.createAndStartProcess(null);
			fail("commands is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			LauncherProcess.createAndStartProcess(HelperArray.EMPTY_ARRAY_STRING);
			fail("commands is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			LauncherProcess.createAndStartProcess(null, osOutput, osError);
			fail("commands is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			LauncherProcess.createAndStartProcess(HelperArray.EMPTY_ARRAY_STRING, osOutput, osError);
			fail("commands is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherProcess.createAndStartProcess(new String[]{"ls"}, null, osError); //$NON-NLS-1$
			fail("outputStream is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			LauncherProcess.createAndStartProcess(new String[]{"ls"}, osOutput, null); //$NON-NLS-1$
			fail("errorStream is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	private static void sleep() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			fail(ex.getMessage());
		}
	}
}
