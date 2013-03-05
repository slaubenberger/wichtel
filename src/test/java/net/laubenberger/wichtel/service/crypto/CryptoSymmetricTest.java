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

package net.laubenberger.wichtel.service.crypto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import javax.crypto.SecretKey;

import net.laubenberger.wichtel.AllTests;
import net.laubenberger.wichtel.helper.HelperArray;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;
import net.laubenberger.wichtel.model.crypto.CryptoSymmetricAlgo;

import org.junit.Test;


/**
 * JUnit test for {@link CryptoSymmetricImpl}
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 */
public class CryptoSymmetricTest {

	@Test
	public void testGenerateKey() {
		for (final CryptoSymmetricAlgo algo : CryptoSymmetricAlgo.values()) {
			try {
				final CryptoSymmetric cryptoSymm = new CryptoSymmetricImpl(algo);
				
				try {
					assertNotNull(cryptoSymm.generateKey());
				} catch (Exception ex) {
					fail(ex.getMessage());
				}
		
				try {
					cryptoSymm.generateKey(0);
					fail("keysize is 0"); //$NON-NLS-1$
				} catch (RuntimeExceptionMustBeGreater ex) {
					//nothing to do
				} catch (Exception ex) {
					fail(ex.getMessage());
				}
		
				try {
					cryptoSymm.generateKey(Integer.MIN_VALUE);
					fail("keysize is -1024"); //$NON-NLS-1$
				} catch (RuntimeExceptionMustBeGreater ex) {
					//nothing to do
				} catch (Exception ex) {
					fail(ex.getMessage());
				}
			} catch (Exception ex) {
				fail(ex.getMessage());
			}
		}
	}

	@Test
	public void testEncrypt() {
		for (final CryptoSymmetricAlgo algo : CryptoSymmetricAlgo.values()) {
			try {
				final CryptoSymmetric cryptoSymm = new CryptoSymmetricImpl(algo);
				final SecretKey secretKey = cryptoSymm.generateKey();
				
				try {
					assertNotNull(cryptoSymm.encrypt(AllTests.DATA.getBytes(), secretKey));
				} catch (Exception ex) {
					fail(ex.getMessage());
				}
		
//				try {
//					assertNotNull(cryptoSymm.encrypt(AllTests.DATA.getBytes(), secretKeyDefault));
//				} catch (Exception ex) {
//					fail(ex.getMessage());
//				}
				
				try {
					cryptoSymm.encrypt(null, null);
					fail("input is null"); //$NON-NLS-1$
				} catch (RuntimeExceptionIsNull ex) {
					//nothing to do
				} catch (Exception ex) {
					fail(ex.getMessage());
				}
		
				try {
					cryptoSymm.encrypt(HelperArray.EMPTY_ARRAY_BYTE, null);
					fail("input is empty"); //$NON-NLS-1$
				} catch (RuntimeExceptionIsEmpty ex) {
					//nothing to do
				} catch (Exception ex) {
					fail(ex.getMessage());
				}
		
				try {
					cryptoSymm.encrypt(AllTests.DATA.getBytes(), null);
					fail("key is null"); //$NON-NLS-1$
				} catch (RuntimeExceptionIsNull ex) {
					//nothing to do
				} catch (Exception ex) {
					fail(ex.getMessage());
				}
			} catch (Exception ex) {
				fail(ex.getMessage());
			}
		}
	}

	@Test
	public void testDecrypt() {
		for (final CryptoSymmetricAlgo algo : CryptoSymmetricAlgo.values()) {
			try {
				final CryptoSymmetric cryptoSymm = new CryptoSymmetricImpl(algo);
				final SecretKey secretKey = cryptoSymm.generateKey();

				try {
					assertEquals(AllTests.DATA, new String(cryptoSymm.decrypt(cryptoSymm.encrypt(AllTests.DATA.getBytes(), secretKey), secretKey)));
				} catch (Exception ex) {
					fail(ex.getMessage());
				}
		
		//		try {
		//			assertEquals(AllTests.DATA, new String(cryptoSymm.decrypt(cryptoSymm.encrypt(AllTests.DATA.getBytes(), secretKeyDefault), secretKeyDefault)));
		//		} catch (Exception ex) {
		//			fail(ex.getMessage());
		//		}
				
				try {
					cryptoSymm.decrypt(null, null);
					fail("input is null"); //$NON-NLS-1$
				} catch (RuntimeExceptionIsNull ex) {
					//nothing to do
				} catch (Exception ex) {
					fail(ex.getMessage());
				}
		
				try {
					cryptoSymm.decrypt(HelperArray.EMPTY_ARRAY_BYTE, null);
					fail("input is empty"); //$NON-NLS-1$
				} catch (RuntimeExceptionIsEmpty ex) {
					//nothing to do
				} catch (Exception ex) {
					fail(ex.getMessage());
				}
		
				try {
					cryptoSymm.decrypt(AllTests.DATA.getBytes(), null);
					fail("key is null"); //$NON-NLS-1$
				} catch (RuntimeExceptionIsNull ex) {
					//nothing to do
				} catch (Exception ex) {
					fail(ex.getMessage());
				}
			} catch (Exception ex) {
				fail(ex.getMessage());
			}
		}
	}
}


