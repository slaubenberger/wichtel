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

import java.io.File;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import net.laubenberger.wichtel.helper.HelperIO;
import net.laubenberger.wichtel.model.crypto.CryptoSymmetricAlgo;

import org.junit.Test;


/**
 * JUnit test for {@link KeyWrapperImpl}
 *
 * @author Stefan Laubenberger
 * @version 0.0.2, 2013-03-14
 */
public class KeyWrapperTest {

	//@Test
	public void testGenerateKey() {
		try {
			final CryptoSymmetric cs = new CryptoSymmetricImpl(CryptoSymmetricAlgo.SERPENT);
			final KeyWrapper kw = new KeyWrapperImpl();
			
			final Key keyA = cs.generateKey("TEST A".getBytes()); //$NON-NLS-1$
			final Key keyB = cs.generateKey("TEST B".getBytes()); //$NON-NLS-1$
			final Key keyC = cs.generateKey("TEST C".getBytes()); //$NON-NLS-1$
			
			final byte[] wrappedKeyA = kw.wrap(keyB, keyA);
			final byte[] wrappedKeyB = kw.wrap(keyC, keyB);
			
			
			HelperIO.writeFile(new File("/Users/slaubenberger/Desktop/KeyA.txt"), wrappedKeyA); //$NON-NLS-1$
			HelperIO.writeFile(new File("/Users/slaubenberger/Desktop/KeyB.txt"), wrappedKeyB); //$NON-NLS-1$
			HelperIO.writeFile(new File("/Users/slaubenberger/Desktop/TextA.txt"), cs.encrypt("Hi Stefan".getBytes(), keyA)); //$NON-NLS-1$ //$NON-NLS-2$

			System.out.println(new String(cs.decrypt(HelperIO.readFile(new File("/Users/slaubenberger/Desktop/TextA.txt")), keyA))); //$NON-NLS-1$

			final Key unwrappedKeyA = kw.unwrap(keyB, wrappedKeyA, CryptoSymmetricAlgo.SERPENT.getAlgorithm(), Cipher.SECRET_KEY);
			
			System.out.println(new String(cs.decrypt(HelperIO.readFile(new File("/Users/slaubenberger/Desktop/TextA.txt")), unwrappedKeyA))); //$NON-NLS-1$

			final CryptoSymmetric cs2 = new CryptoSymmetricImpl(CryptoSymmetricAlgo.SERPENT);
			final KeyWrapper kw2 = new KeyWrapperImpl();

//			Key keyA2 = cs2.generateKey("TEST A".getBytes());
//			Key keyB2 = cs2.generateKey("TEST B".getBytes());
			final Key keyC3 = cs2.generateKey("TEST C".getBytes()); //$NON-NLS-1$

			final Key unwarppedKeyB = kw2.unwrap(keyC3, wrappedKeyB, CryptoSymmetricAlgo.SERPENT.getAlgorithm(), Cipher.SECRET_KEY);
			final Key unwarppedKeyA = kw2.unwrap(unwarppedKeyB, wrappedKeyA, CryptoSymmetricAlgo.SERPENT.getAlgorithm(), Cipher.SECRET_KEY);
			
			System.out.println(new String(cs2.decrypt(HelperIO.readFile(new File("/Users/slaubenberger/Desktop/TextA.txt")), unwarppedKeyA))); //$NON-NLS-1$

//			assertEquals(keyB, kw.unwrap(keyC, wrappedKeyB, CryptoSymmetricAlgo.SERPENT.getAlgorithm(), Cipher.SECRET_KEY));
//			assertEquals(keyB, kw.unwrap(kw.unwrap(keyC, wrappedKeyB, CryptoSymmetricAlgo.SERPENT.getAlgorithm(), Cipher.SECRET_KEY), wrappedKeyA, CryptoSymmetricAlgo.SERPENT.getAlgorithm(), Cipher.SECRET_KEY));
			
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
//			fail(ex.getMessage());
		} catch (NoSuchPaddingException ex) {
			ex.printStackTrace();
//			fail(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
//			fail(ex.getMessage());
		}
	}
	
	//@Test
	public void testReadKey() {
		try {
//			CryptoSymmetric cs = new CryptoSymmetricImpl(CryptoSymmetricAlgo.SERPENT);
//			KeyWrapper kw = new KeyWrapperImpl(CryptoSymmetricAlgo.SERPENT); 
//			
//			Key keyA = cs.generateKey("TEST A".getBytes());
//			Key keyB = cs.generateKey("TEST B".getBytes());
//			Key keyC = cs.generateKey("TEST C".getBytes());
			
			final byte[] wrappedKeyA = HelperIO.readFile(new File("/Users/slaubenberger/Desktop/KeyA.txt")); //$NON-NLS-1$
			final byte[] wrappedKeyB = HelperIO.readFile(new File("/Users/slaubenberger/Desktop/KeyB.txt")); //$NON-NLS-1$
		
			
			final CryptoSymmetric cs2 = new CryptoSymmetricImpl(CryptoSymmetricAlgo.SERPENT);
			final KeyWrapper kw2 = new KeyWrapperImpl();

//			Key keyA2 = cs2.generateKey("TEST A".getBytes());
//			Key keyB2 = cs2.generateKey("TEST B".getBytes());
			final Key keyC3 = cs2.generateKey("TEST C".getBytes()); //$NON-NLS-1$

			final Key unwarppedKeyB = kw2.unwrap(keyC3, wrappedKeyB, CryptoSymmetricAlgo.SERPENT.getAlgorithm(), Cipher.SECRET_KEY);
			final Key unwarppedKeyA = kw2.unwrap(unwarppedKeyB, wrappedKeyA, CryptoSymmetricAlgo.SERPENT.getAlgorithm(), Cipher.SECRET_KEY);
			
			System.out.println(new String(cs2.decrypt(HelperIO.readFile(new File("/Users/slaubenberger/Desktop/TextA.txt")), unwarppedKeyA))); //$NON-NLS-1$

//			assertEquals(keyB, kw.unwrap(keyC, wrappedKeyB, CryptoSymmetricAlgo.SERPENT.getAlgorithm(), Cipher.SECRET_KEY));
//			assertEquals(keyB, kw.unwrap(kw.unwrap(keyC, wrappedKeyB, CryptoSymmetricAlgo.SERPENT.getAlgorithm(), Cipher.SECRET_KEY), wrappedKeyA, CryptoSymmetricAlgo.SERPENT.getAlgorithm(), Cipher.SECRET_KEY));
			
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
//			fail(ex.getMessage());
		} catch (NoSuchPaddingException ex) {
			ex.printStackTrace();
//			fail(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
//			fail(ex.getMessage());
		}
	}
	
}


