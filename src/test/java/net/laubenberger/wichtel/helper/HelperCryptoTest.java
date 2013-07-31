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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;
import net.laubenberger.wichtel.model.crypto.CryptoAsymmetricAlgo;
import net.laubenberger.wichtel.model.crypto.CryptoSymmetricAlgo;
import net.laubenberger.wichtel.model.crypto.HashCodeAlgo;
import net.laubenberger.wichtel.model.crypto.HmacAlgo;
import net.laubenberger.wichtel.model.crypto.SignatureAlgo;

import org.junit.Test;


/**
 * JUnit test for {@link HelperCrypto}
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 */
public class HelperCryptoTest {

    private static final int ITERATION = 1000;
    
	@Test
	public void testGetRandomKey() {
		assertEquals(16, HelperCrypto.getRandomKey(16, '1', '2', '3', '4', '5', '6').length());
		
		final Set<String> set = new HashSet<>(ITERATION);
		
		for (int ii = 0; ITERATION > ii; ii++) {
		    final String key = HelperCrypto.getRandomKey(16, '1', '2', '3', '4', '5', '6');
		    assertFalse(set.contains(key));
		    set.add(key);
		}

		try {
			HelperCrypto.getRandomKey(0, '1', '2', '3');
			fail("digits must be greater than 0"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperCrypto.getRandomKey(16, null);
			fail("data is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	
		try {
			HelperCrypto.getRandomKey(16, HelperArray.EMPTY_ARRAY_CHAR);
			fail("data is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetRandomKeyDefault() {
		assertEquals(16, HelperCrypto.getRandomKey(16).length());

		final Set<String> set = new HashSet<>(ITERATION);
	        
        for (int ii = 0; ITERATION > ii; ii++) {
            final String key = HelperCrypto.getRandomKey(8);
            assertFalse(set.contains(key));
            set.add(key);
        }

		try {
			HelperCrypto.getRandomKey(0);
			fail("digits must be greater than 0"); //$NON-NLS-1$
		} catch (RuntimeExceptionMustBeGreater ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetUUID() {
	    assertNotNull(HelperCrypto.getUUID());
	    
       final Set<UUID> set = new HashSet<>(ITERATION);
        
        for (int ii = 0; ITERATION > ii; ii++) {
            final UUID key = HelperCrypto.getUUID();
            assertFalse(set.contains(key));
            set.add(key);
        }
	}
	
	@Test
	public void testGetProviders() {
//		System.err.println(HelperCrypto.getProviders().size());
		assertTrue(11 <= HelperCrypto.getProviders().size());
		assertTrue(HelperCrypto.getProviders().contains(HelperCrypto.DEFAULT_PROVIDER));
	}
	
	@Test
	public void testGetCiphers() {
//		System.err.println(HelperCrypto.getCiphers(HelperCrypto.DEFAULT_PROVIDER).size());
		assertTrue(75 <= HelperCrypto.getCiphers(HelperCrypto.DEFAULT_PROVIDER).size());
		
		for (final CryptoSymmetricAlgo algo : CryptoSymmetricAlgo.values()) {
			assertTrue(HelperCrypto.getCiphers(HelperCrypto.DEFAULT_PROVIDER).contains(algo.getAlgorithm()));
		}
		for (final CryptoAsymmetricAlgo algo : CryptoAsymmetricAlgo.values()) {
			assertTrue(HelperCrypto.getCiphers(HelperCrypto.DEFAULT_PROVIDER).contains(algo.getAlgorithm()));
		}		
		
		assertFalse(HelperCrypto.getCiphers(HelperCrypto.DEFAULT_PROVIDER).contains(HashCodeAlgo.SHA256.getAlgorithm()));
		assertFalse(HelperCrypto.getCiphers(HelperCrypto.DEFAULT_PROVIDER).contains(HmacAlgo.SHA256.getAlgorithm()));
		assertFalse(HelperCrypto.getCiphers(HelperCrypto.DEFAULT_PROVIDER).contains(SignatureAlgo.SHA256_WITH_RSA.getAlgorithm()));
		
		try {
			HelperCrypto.getCiphers(null);
			fail("provider is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetKeyAgreements() {
//		System.err.println(HelperCrypto.getKeyAgreements(HelperCrypto.DEFAULT_PROVIDER).size());
		assertTrue(4 <= HelperCrypto.getKeyAgreements(HelperCrypto.DEFAULT_PROVIDER).size());
		assertTrue(HelperCrypto.getKeyAgreements(HelperCrypto.DEFAULT_PROVIDER).contains("DH")); //$NON-NLS-1$
		
		try {
			HelperCrypto.getKeyAgreements(null);
			fail("provider is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testGetMacs() {
//		System.err.println(HelperCrypto.getMacs(HelperCrypto.DEFAULT_PROVIDER).size());
		assertTrue(33 <= HelperCrypto.getMacs(HelperCrypto.DEFAULT_PROVIDER).size());
		
		for (final HmacAlgo algo : HmacAlgo.values()) {
			assertTrue(HelperCrypto.getMacs(HelperCrypto.DEFAULT_PROVIDER).contains(algo.getAlgorithm()));
		}
		
		assertFalse(HelperCrypto.getMacs(HelperCrypto.DEFAULT_PROVIDER).contains(CryptoSymmetricAlgo.AES.getAlgorithm()));
		assertFalse(HelperCrypto.getMacs(HelperCrypto.DEFAULT_PROVIDER).contains(CryptoAsymmetricAlgo.RSA.getAlgorithm()));
		assertFalse(HelperCrypto.getMacs(HelperCrypto.DEFAULT_PROVIDER).contains(HashCodeAlgo.SHA256.getAlgorithm()));
		assertFalse(HelperCrypto.getMacs(HelperCrypto.DEFAULT_PROVIDER).contains(SignatureAlgo.SHA256_WITH_RSA.getAlgorithm()));
		
		try {
			HelperCrypto.getMacs(null);
			fail("provider is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetMessageDigests() {
//		System.err.println(HelperCrypto.getMessageDigests(HelperCrypto.DEFAULT_PROVIDER).size());
		assertTrue(15 <= HelperCrypto.getMessageDigests(HelperCrypto.DEFAULT_PROVIDER).size());
		
		for (final HashCodeAlgo algo : HashCodeAlgo.values()) {
			assertTrue(HelperCrypto.getMessageDigests(HelperCrypto.DEFAULT_PROVIDER).contains(algo.getAlgorithm()));
		}
		
		assertFalse(HelperCrypto.getMessageDigests(HelperCrypto.DEFAULT_PROVIDER).contains(CryptoSymmetricAlgo.AES.getAlgorithm()));
		assertFalse(HelperCrypto.getMessageDigests(HelperCrypto.DEFAULT_PROVIDER).contains(CryptoAsymmetricAlgo.RSA.getAlgorithm()));
		assertFalse(HelperCrypto.getMessageDigests(HelperCrypto.DEFAULT_PROVIDER).contains(HmacAlgo.SHA256.getAlgorithm()));
		assertFalse(HelperCrypto.getMessageDigests(HelperCrypto.DEFAULT_PROVIDER).contains(SignatureAlgo.SHA256_WITH_RSA.getAlgorithm()));
		
		try {
			HelperCrypto.getMessageDigests(null);
			fail("provider is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetSignatures() {
//		System.err.println(HelperCrypto.getSignatures(HelperCrypto.DEFAULT_PROVIDER).size());
		assertTrue(45 <= HelperCrypto.getSignatures(HelperCrypto.DEFAULT_PROVIDER).size());
		
		for (final SignatureAlgo algo : SignatureAlgo.values()) {
			assertTrue(HelperCrypto.getSignatures(HelperCrypto.DEFAULT_PROVIDER).contains(algo.getAlgorithm()));
		}
		
		assertFalse(HelperCrypto.getSignatures(HelperCrypto.DEFAULT_PROVIDER).contains(CryptoSymmetricAlgo.AES.getAlgorithm()));
		assertFalse(HelperCrypto.getSignatures(HelperCrypto.DEFAULT_PROVIDER).contains(HashCodeAlgo.SHA256.getAlgorithm()));
		assertFalse(HelperCrypto.getSignatures(HelperCrypto.DEFAULT_PROVIDER).contains(HmacAlgo.SHA256.getAlgorithm()));
		
		try {
			HelperCrypto.getSignatures(null);
			fail("provider is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testGetKeyPairGenerators() {
//		System.err.println(HelperCrypto.getKeyPairGenerators(HelperCrypto.DEFAULT_PROVIDER).size());
		assertTrue(12 <= HelperCrypto.getKeyPairGenerators(HelperCrypto.DEFAULT_PROVIDER).size());
		
		for (final CryptoAsymmetricAlgo algo : CryptoAsymmetricAlgo.values()) {
			assertTrue(HelperCrypto.getKeyPairGenerators(HelperCrypto.DEFAULT_PROVIDER).contains(algo.getAlgorithm()));
		}		

		assertFalse(HelperCrypto.getKeyPairGenerators(HelperCrypto.DEFAULT_PROVIDER).contains(CryptoSymmetricAlgo.AES.getAlgorithm()));
		assertFalse(HelperCrypto.getKeyPairGenerators(HelperCrypto.DEFAULT_PROVIDER).contains(HashCodeAlgo.SHA256.getAlgorithm()));
		assertFalse(HelperCrypto.getKeyPairGenerators(HelperCrypto.DEFAULT_PROVIDER).contains(HmacAlgo.SHA256.getAlgorithm()));
		assertFalse(HelperCrypto.getKeyPairGenerators(HelperCrypto.DEFAULT_PROVIDER).contains(SignatureAlgo.SHA256_WITH_RSA.getAlgorithm()));
		
		try {
			HelperCrypto.getKeyPairGenerators(null);
			fail("provider is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testGetKeyFactories() {
//		System.err.println(HelperCrypto.getKeyFactories(HelperCrypto.DEFAULT_PROVIDER).size());
		assertTrue(13 <= HelperCrypto.getKeyFactories(HelperCrypto.DEFAULT_PROVIDER).size());
		
		for (final CryptoAsymmetricAlgo algo : CryptoAsymmetricAlgo.values()) {
			assertTrue(HelperCrypto.getKeyFactories(HelperCrypto.DEFAULT_PROVIDER).contains(algo.getAlgorithm()));
		}		
		
		assertFalse(HelperCrypto.getKeyFactories(HelperCrypto.DEFAULT_PROVIDER).contains(CryptoSymmetricAlgo.AES.getAlgorithm()));
		assertFalse(HelperCrypto.getKeyFactories(HelperCrypto.DEFAULT_PROVIDER).contains(HashCodeAlgo.SHA256.getAlgorithm()));
		assertFalse(HelperCrypto.getKeyFactories(HelperCrypto.DEFAULT_PROVIDER).contains(HmacAlgo.SHA256.getAlgorithm()));
		assertFalse(HelperCrypto.getKeyFactories(HelperCrypto.DEFAULT_PROVIDER).contains(SignatureAlgo.SHA256_WITH_RSA.getAlgorithm()));
		
		try {
			HelperCrypto.getKeyFactories(null);
			fail("provider is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testGetKeyGenerators() {
//		System.err.println(HelperCrypto.getKeyGenerators(HelperCrypto.DEFAULT_PROVIDER).size());
		assertTrue(41 <= HelperCrypto.getKeyGenerators(HelperCrypto.DEFAULT_PROVIDER).size());
		
		for (final CryptoSymmetricAlgo algo : CryptoSymmetricAlgo.values()) {
//            System.err.println(algo.getAlgorithm());
			assertTrue(HelperCrypto.getKeyGenerators(HelperCrypto.DEFAULT_PROVIDER).contains(algo.getAlgorithm()));
		}		
		for (final HmacAlgo algo : HmacAlgo.values()) {
			assertTrue(HelperCrypto.getKeyGenerators(HelperCrypto.DEFAULT_PROVIDER).contains(algo.getAlgorithm()));
		}	
		
		assertFalse(HelperCrypto.getKeyGenerators(HelperCrypto.DEFAULT_PROVIDER).contains(CryptoAsymmetricAlgo.RSA.getAlgorithm()));
		assertFalse(HelperCrypto.getKeyGenerators(HelperCrypto.DEFAULT_PROVIDER).contains(HashCodeAlgo.SHA256.getAlgorithm()));
		assertFalse(HelperCrypto.getKeyGenerators(HelperCrypto.DEFAULT_PROVIDER).contains(SignatureAlgo.SHA256_WITH_RSA.getAlgorithm()));
		
		try {
			HelperCrypto.getKeyGenerators(null);
			fail("provider is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}
