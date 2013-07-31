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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.TimeZone;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.model.unit.Bit;

import org.junit.Test;


/**
 * JUnit test for {@link HelperEnvironment}
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 */
public class HelperEnvironmentTest {
	@Test
	public void testGetMemoryUsed() {
		assertTrue(HelperEnvironment.getMemoryUsed() > Bit.MEGABYTE.convertTo(Bit.BYTE, 1).longValue());
		assertEquals(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), HelperEnvironment.getMemoryUsed());
		assertTrue(HelperEnvironment.getMemoryMax() > HelperEnvironment.getMemoryUsed());
	}
	
	@Test
	public void testGetMemoryFree() {
		assertTrue(HelperEnvironment.getMemoryFree() > Bit.MEGABYTE.convertTo(Bit.BYTE, 1).longValue());
		assertEquals(Runtime.getRuntime().maxMemory() - (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()), HelperEnvironment.getMemoryFree());
		assertTrue(HelperEnvironment.getMemoryMax() > HelperEnvironment.getMemoryFree());
	}
	
	@Test
	public void testGetMemoryTotal() {
		assertTrue(HelperEnvironment.getMemoryTotal() > Bit.MEGABYTE.convertTo(Bit.BYTE, 2).longValue());
		assertEquals(Runtime.getRuntime().totalMemory(), HelperEnvironment.getMemoryTotal());
		assertTrue(HelperEnvironment.getMemoryMax() > HelperEnvironment.getMemoryTotal());
	}
	
	@Test
	public void testGetMemoryMax() {
		assertTrue(HelperEnvironment.getMemoryMax() > Bit.MEGABYTE.convertTo(Bit.BYTE, 4).longValue());
		assertEquals(Runtime.getRuntime().maxMemory(), HelperEnvironment.getMemoryMax());
		assertTrue(HelperEnvironment.getMemoryMax() > HelperEnvironment.getMemoryTotal());
	}
	
	@Test
	public void testGetJavaVersion() {
		assertNotNull(HelperEnvironment.getJavaVersion());
		assertEquals(System.getProperties().getProperty("java.version"), HelperEnvironment.getJavaVersion()); //$NON-NLS-1$
	}

	@Test
	public void testGetJavaVendor() {
		assertNotNull(HelperEnvironment.getJavaVendor());
		assertEquals(System.getProperties().getProperty("java.vendor"), HelperEnvironment.getJavaVendor()); //$NON-NLS-1$
	}

	@Test
	public void testGetJavaVmName() {
		assertNotNull(HelperEnvironment.getJavaVmName());
		assertEquals(System.getProperties().getProperty("java.vm.name"), HelperEnvironment.getJavaVmName()); //$NON-NLS-1$
	}
	
	@Test
	public void testGetJavaVmVersion() {
		assertNotNull(HelperEnvironment.getJavaVmVersion());
		assertEquals(System.getProperties().getProperty("java.vm.version"), HelperEnvironment.getJavaVmVersion()); //$NON-NLS-1$
	}
	
	@Test
	public void testGetJavaProperties() {
//		System.err.println(HelperEnvironment.getJavaProperties().size());
		assertTrue(50 < HelperEnvironment.getJavaProperties().size());
		assertEquals(System.getProperties(), HelperEnvironment.getJavaProperties());
	}
	
	@Test
	public void testGetClassPath() {
		assertNotNull(HelperEnvironment.getClassPath());
		assertEquals(System.getProperties().getProperty("java.class.path"), HelperEnvironment.getClassPath()); //$NON-NLS-1$
	}

	@Test
	public void testGetLibraryPath() {
		assertNotNull(HelperEnvironment.getLibraryPath());
		assertEquals(System.getProperties().getProperty("java.library.path"), HelperEnvironment.getLibraryPath()); //$NON-NLS-1$
	}

	@Test
	public void testGetAvailableProcessors() {
		assertTrue(0 < HelperEnvironment.getAvailableProcessors());
		assertEquals(Runtime.getRuntime().availableProcessors(), HelperEnvironment.getAvailableProcessors());
	}

	@Test
	public void testGetOsArch() {
		assertNotNull(HelperEnvironment.getOsArch());
		assertEquals(System.getProperties().getProperty("os.arch"), HelperEnvironment.getOsArch()); //$NON-NLS-1$
	}

	@Test
	public void testGetOsName() {
		assertNotNull(HelperEnvironment.getOsName());
		assertEquals(System.getProperties().getProperty("os.name"), HelperEnvironment.getOsName()); //$NON-NLS-1$
	}

	@Test
	public void testGetOsVersion() {
		assertNotNull(HelperEnvironment.getOsVersion());
		assertEquals(System.getProperties().getProperty("os.version"), HelperEnvironment.getOsVersion()); //$NON-NLS-1$
	}

	@Test
	public void testGetOsEnvironmentVariables() {
//		System.err.println(HelperEnvironment.getOsEnvironmentVariables().size());
		assertTrue(5 < HelperEnvironment.getOsEnvironmentVariables().size());
		assertEquals(System.getenv(), HelperEnvironment.getOsEnvironmentVariables());
	}

	@Test
	public void testGetOsEnvironmentVariable() {
		final String variable = "PATH"; //$NON-NLS-1$

		assertNotNull(HelperEnvironment.getOsEnvironmentVariable(variable));
		assertNull(HelperEnvironment.getOsEnvironmentVariable(HelperString.EMPTY_STRING));
		assertEquals(System.getenv(variable), HelperEnvironment.getOsEnvironmentVariable(variable));
		
		try {
			HelperEnvironment.getOsEnvironmentVariable(null);
			fail("variable is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetOsTempDirectory() {
		assertNotNull(HelperEnvironment.getOsTempDirectory());
		assertEquals(new File(System.getProperty("java.io.tmpdir")), HelperEnvironment.getOsTempDirectory()); //$NON-NLS-1$
	}

	@Test
	public void testGetUserHomeDirectory() {
//		System.out.println(HelperEnvironment.getUserHomeDirectory());
		assertNotNull(HelperEnvironment.getUserHomeDirectory());
		assertEquals(new File(System.getProperty("user.home")), HelperEnvironment.getUserHomeDirectory()); //$NON-NLS-1$
	}

	@Test
	public void testGetUserDirectory() {
		assertNotNull(HelperEnvironment.getUserDirectory());
		assertEquals(new File(System.getProperty("user.dir")), HelperEnvironment.getUserDirectory()); //$NON-NLS-1$
	}

	@Test
	public void testGetUserName() {
		assertNotNull(HelperEnvironment.getUserName());
		assertEquals(System.getProperty("user.name"), HelperEnvironment.getUserName()); //$NON-NLS-1$
	}

	@Test
	public void testGetUserCountry() {
		assertNotNull(HelperEnvironment.getUserCountry());
		assertEquals(System.getProperty("user.country"), HelperEnvironment.getUserCountry().getCode()); //$NON-NLS-1$
	}

	@Test
	public void testGetUserLanguage() {
		assertNotNull(HelperEnvironment.getUserLanguage());
		assertEquals(System.getProperty("user.language"), HelperEnvironment.getUserLanguage().getCode()); //$NON-NLS-1$
	}

	@Test
	public void testGetUserTimezone() {
		assertNotNull(HelperEnvironment.getUserTimezone());
		assertEquals(TimeZone.getDefault(), HelperEnvironment.getUserTimezone());
	}
	
	@Test
	public void testGetPlatform() {
		assertNotNull(HelperEnvironment.getPlatform());
	}
	
	@Test
	public void testGetReportJava() {
		assertEquals(7, HelperEnvironment.getReportJava().size());
	}
	
	@Test
	public void testGetReportOS() {
		assertEquals(6,  HelperEnvironment.getReportOS().size());
	}
	
	@Test
	public void testGetReportUser() {
		assertEquals(6,  HelperEnvironment.getReportUser().size());
	}	
	
	@Test
	public void testGetReportSystem() {
		assertTrue(6 < HelperEnvironment.getReportSystem().size());
	}
}


