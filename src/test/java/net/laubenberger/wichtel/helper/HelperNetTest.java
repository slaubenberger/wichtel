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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeSmaller;

import org.junit.Test;


/**
 * JUnit test for {@link HelperNet}
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 */
public class HelperNetTest {
	@Test
	public void testEnableAndDisableProxyHttp() {
		final String host = "192.168.1.1"; //$NON-NLS-1$
		final int port = 8080;
		final String user = "Wichtel"; //$NON-NLS-1$
		final String pw = "1234"; //$NON-NLS-1$

		HelperNet.enableProxyHttp(host, port, user, pw);

		assertEquals(host, System.getProperty(HelperNet.PROPERTY_HTTP_PROXY_HOST));
		assertEquals(Integer.toString(port), System.getProperty(HelperNet.PROPERTY_HTTP_PROXY_PORT));

		HelperNet.disableProxyHttp();

		assertNull(System.getProperty(HelperNet.PROPERTY_HTTP_PROXY_HOST));
		assertNull(System.getProperty(HelperNet.PROPERTY_HTTP_PROXY_PORT));

		try {
			HelperNet.enableProxyHttp(null, port, user, pw);
			fail("host is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperNet.enableProxyHttp(host, 0, user, pw);
			fail("port must be greater than 0"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperNet.enableProxyHttp(host, HelperNumber.NUMBER_65536.intValue(), user, pw);
			fail("port must be smaller than 65536"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeSmaller ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperNet.enableProxyHttp(host, port, null, pw);
			fail("username is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperNet.enableProxyHttp(host, port, user, null);
			fail("password is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testEnableAndDisableProxyHttps() {
		final String host = "192.168.1.1"; //$NON-NLS-1$
		final int port = 8080;
		final String user = "Wichtel"; //$NON-NLS-1$
		final String pw = "1234"; //$NON-NLS-1$

		HelperNet.enableProxyHttps(host, port, user, pw);

		assertEquals(host, System.getProperty(HelperNet.PROPERTY_HTTPS_PROXY_HOST));
		assertEquals(Integer.toString(port), System.getProperty(HelperNet.PROPERTY_HTTPS_PROXY_PORT));

		HelperNet.disableProxyHttps();

		assertNull(System.getProperty(HelperNet.PROPERTY_HTTPS_PROXY_HOST));
		assertNull(System.getProperty(HelperNet.PROPERTY_HTTPS_PROXY_PORT));

		try {
			HelperNet.enableProxyHttps(null, port, user, pw);
			fail("host is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperNet.enableProxyHttps(host, 0, user, pw);
			fail("port must be greater than 0"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperNet.enableProxyHttps(host, HelperNumber.NUMBER_65536.intValue(), user, pw);
			fail("port must be smaller than 65536"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeSmaller ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperNet.enableProxyHttps(host, port, null, pw);
			fail("username is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperNet.enableProxyHttps(host, port, user, null);
			fail("password is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testEnableDisableProxyFtp() {
		final String host = "192.168.1.1"; //$NON-NLS-1$
		final int port = 8080;
		final String user = "Wichtel"; //$NON-NLS-1$
		final String pw = "1234"; //$NON-NLS-1$

		HelperNet.enableProxyFtp(host, port, user, pw);

		assertEquals(host, System.getProperty(HelperNet.PROPERTY_FTP_PROXY_HOST));
		assertEquals(Integer.toString(port), System.getProperty(HelperNet.PROPERTY_FTP_PROXY_PORT));

		HelperNet.disableProxyFtp();

		assertNull(System.getProperty(HelperNet.PROPERTY_FTP_PROXY_HOST));
		assertNull(System.getProperty(HelperNet.PROPERTY_FTP_PROXY_PORT));

		try {
			HelperNet.enableProxyFtp(null, port, user, pw);
			fail("host is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperNet.enableProxyFtp(host, 0, user, pw);
			fail("port must be greater than 0"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperNet.enableProxyFtp(host, HelperNumber.NUMBER_65536.intValue(), user, pw);
			fail("port must be smaller than 65536"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeSmaller ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperNet.enableProxyFtp(host, port, null, pw);
			fail("username is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperNet.enableProxyFtp(host, port, user, null);
			fail("password is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testIsPingable() {
		try {
			assertTrue(HelperNet.isPingable("github.com")); //$NON-NLS-1$
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			assertFalse(HelperNet.isPingable("192.168.255.255")); //$NON-NLS-1$
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperNet.isPingable(null);
			fail("host is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperNet.isPingable(HelperString.EMPTY_STRING);
			fail("host is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}


	@Test
	public void testGetIp() {
		try {
//			System.err.println(HelperNet.getIp("www.laubenberger.net"));
////			System.err.println(HelperNet.getIp("www.laubenberger.net"));
//			System.err.println(HelperNet.getIp("www.katzenferien.ch"));
			assertEquals("207.97.227.239", HelperNet.getIp("github.com")); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperNet.getIp(null);
			fail("host is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperNet.getIp(HelperString.EMPTY_STRING);
			fail("host is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testGetHostname() {
		try {
//			System.err.println(HelperNet.getHostname("78.46.88.137"));
			assertEquals("github.com", HelperNet.getHostname("207.97.227.239")); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperNet.getHostname(null);
			fail("ip is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperNet.getHostname(HelperString.EMPTY_STRING);
			fail("ip is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetLocalHostname() {
		try {
//			System.err.println(HelperNet.getLocalHostname());
			assertNotNull(HelperNet.getLocalHostname());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetLocalIp() {
		try {
//			System.err.println(HelperNet.getLocalIp());
			assertNotNull(HelperNet.getLocalIp());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetLocalIps() { 
		try {
			assertTrue(!HelperNet.getLocalIps().isEmpty());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetNetworkInterfaces() { 
		try {
			assertTrue(!HelperNet.getNetworkInterfaces().isEmpty());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetMacAddress() {
		try {
			assertNotNull(HelperNet.getMacAddress(HelperNet.getNetworkInterfaces().get(0)));
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperNet.getMacAddress(null);
			fail("ni is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testReadUrl() {
		try {
//			System.err.println(HelperIO.readStream(HelperNet.readUrl(Constants.wichtel.getUrl())).length);
			assertTrue(2000 < HelperIO.readStream(HelperNet.readUrl(Constants.WICHTEL.getUrl())).length);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperNet.readUrl(null);
			fail("url is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}
