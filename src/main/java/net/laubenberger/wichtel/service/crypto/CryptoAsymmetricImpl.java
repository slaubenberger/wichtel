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

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import net.laubenberger.wichtel.helper.HelperArray;
import net.laubenberger.wichtel.helper.HelperCrypto;
import net.laubenberger.wichtel.helper.HelperEnvironment;
import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionExceedsVmMemory;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;
import net.laubenberger.wichtel.model.crypto.CryptoAsymmetricAlgo;
import net.laubenberger.wichtel.model.crypto.SignatureAlgo;
import net.laubenberger.wichtel.service.ServiceAbstract;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a class for asymmetric cryptology via RSA.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public class CryptoAsymmetricImpl extends ServiceAbstract implements CryptoAsymmetric {
	private static final Logger log = LoggerFactory.getLogger(CryptoAsymmetricImpl.class);

	private final Provider provider;
	private final CryptoAsymmetricAlgo algorithm;

	private final Cipher cipher;
	private final KeyPairGenerator kpg;

	public CryptoAsymmetricImpl(final Provider provider, final CryptoAsymmetricAlgo algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(provider, algorithm));

		if (null == provider) {
			throw new RuntimeExceptionIsNull("provider"); //$NON-NLS-1$
		}
		if (null == algorithm) {
			throw new RuntimeExceptionIsNull("algorithm"); //$NON-NLS-1$
		}
		
		this.provider = provider;
		this.algorithm = algorithm;
		
		cipher = Cipher.getInstance(algorithm.getXform(), provider);
		kpg = KeyPairGenerator.getInstance(algorithm.getAlgorithm(), provider);
	}
	
	public CryptoAsymmetricImpl(final CryptoAsymmetricAlgo algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException {
		this(HelperCrypto.DEFAULT_PROVIDER, algorithm);
	}

	/*
	 * Private methods
	 */

	/**
	 * Encrypt the data with a given {@link Key}.
	 *
	 * @param input data (byte-array) to encrypt
	 * @param key	for the encryption
	 * @return encrypted byte-array
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @since 0.0.1
	 */
	private byte[] encryptInternal(final byte[] input, final Key key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart(input, key));

		cipher.init(Cipher.ENCRYPT_MODE, key);
		final byte[] result = cipher.doFinal(input);

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Decrypt the data with a given {@link Key}.
	 *
	 * @param input data (byte-array) to decrypt
	 * @param key	for the decryption
	 * @return decrypted byte-array
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @since 0.0.1
	 */
	private byte[] decryptInternal(final byte[] input, final Key key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart(input, key));

		cipher.init(Cipher.DECRYPT_MODE, key);
		final byte[] result = cipher.doFinal(input);

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit(result));
		return result;
	}


	/*
	 * Implemented methods
	 */

	/**
	 * Generates a public and a private {@link KeyPair} with the {@link CryptoAsymmetricAlgo} standard key size.
	 *
	 * @return generated key pair
	 * @see KeyPair
	 * @see CryptoAsymmetricAlgo
	 * @since 0.0.1
	 */
	@Override
	public KeyPair generateKeyPair() { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		final KeyPair result = generateKeyPair(algorithm.getDefaultKeysize());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public KeyPair generateKeyPair(final int keySize) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(keySize));
		if (0 >= keySize) {
			throw new RuntimeExceptionMustBeGreater("keySize", keySize, 0); //$NON-NLS-1$
		}
		if (0 != keySize % 8) {
			throw new IllegalArgumentException("keySize is not a multiple of 8"); //$NON-NLS-1$
		}

		// Generate a key-pair
		kpg.initialize(keySize);

		final KeyPair result = kpg.generateKeyPair();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public byte[] generateSignature(final SignatureAlgo algoritm, final byte[] input, final PrivateKey key) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(algoritm, input, key));
		if (null == algoritm) {
			throw new RuntimeExceptionIsNull("algoritm"); //$NON-NLS-1$
		}
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(input)) {
			throw new RuntimeExceptionIsEmpty("input"); //$NON-NLS-1$
		}
		if (null == key) {
			throw new RuntimeExceptionIsNull("key"); //$NON-NLS-1$
		}

		final Signature sig = Signature.getInstance(algoritm.getAlgorithm(), provider);
		sig.initSign(key);
		sig.update(input);

		final byte[] result = sig.sign();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public boolean isValidSignature(final SignatureAlgo algoritm, final byte[] signature, final byte[] input, final PublicKey key) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException, NoSuchProviderException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(algoritm, signature, input, key));
		if (null == algoritm) {
			throw new RuntimeExceptionIsNull("algoritm"); //$NON-NLS-1$
		}
		if (null == signature) {
			throw new RuntimeExceptionIsNull("signature"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(signature)) {
			throw new RuntimeExceptionIsEmpty("signature"); //$NON-NLS-1$
		}
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(input)) {
			throw new RuntimeExceptionIsEmpty("input"); //$NON-NLS-1$
		}
		if (null == key) {
			throw new RuntimeExceptionIsNull("key"); //$NON-NLS-1$
		}

		final Signature sig = Signature.getInstance(algoritm.getAlgorithm(), provider);
		sig.initVerify(key);
		sig.update(input);

		boolean result = false;
		try {
			if (sig.verify(signature)) {
				result = true;
			}
		} catch (SignatureException ex) {
			//do nothing
//			if (log.isInfoEnabled()) log.info("Signature invalid", ex); //$NON-NLS-1$
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Encrypt the data (byte-array) with a given {@link PublicKey}.
	 * Use this method only, if the key has the {@link CryptoAsymmetricAlgo} standard key size.
	 *
	 * @param input data to encrypt as a byte-array
	 * @param key	for the encryption
	 * @return encrypted byte-array
	 * @see PublicKey
	 * @see CryptoAsymmetricAlgo
	 * @since 0.0.1
	 */
	@Override
	public byte[] encrypt(final byte[] input, final PublicKey key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, key));

		final byte[] result = encrypt(input, key, algorithm.getDefaultKeysize());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public byte[] encrypt(final byte[] input, final PublicKey key, final int keySize) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, key, keySize));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(input)) {
			throw new RuntimeExceptionIsEmpty("input"); //$NON-NLS-1$
		}
		if (null == key) {
			throw new RuntimeExceptionIsNull("key"); //$NON-NLS-1$
		}
		if (0 >= keySize) {
			throw new RuntimeExceptionMustBeGreater("keySize", keySize, 0); //$NON-NLS-1$
		}
		if (0 != keySize % 8) {
			throw new IllegalArgumentException("keySize is not a multiple of 8"); //$NON-NLS-1$
		}
		if (input.length * 2 > HelperEnvironment.getMemoryFree()) {
			throw new RuntimeExceptionExceedsVmMemory("input", input.length * 2); //$NON-NLS-1$
		}

		final int space = keySize / 8 - 11;
		byte[] result = HelperArray.EMPTY_ARRAY_BYTE;
		final byte[] temp = new byte[space];

		if (space < input.length) {

			int tempCounter = 0;
			for (int ii = 0; ii < input.length; ii++) {

				temp[tempCounter] = input[ii];

				if (tempCounter == space - 1) {
					result = HelperArray.concatenate(result, encryptInternal(temp, key));
					tempCounter = 0;
				} else {
					if (ii == input.length - 1) { // last byte
						final byte[] usedBytes = new byte[tempCounter + 1];

						System.arraycopy(input, ii - tempCounter, usedBytes, 0, tempCounter + 1);
						result = HelperArray.concatenate(result, encryptInternal(usedBytes, key));
					}
					tempCounter++;
				}
			}
		} else {
			result = encryptInternal(input, key);
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Decrypt the data (byte-array) with a given {@link PrivateKey}.
	 * Use this method only, if the key has the {@link CryptoAsymmetricAlgo} standard key size.
	 *
	 * @param input encrypted data as a byte-array
	 * @param key	for the decryption
	 * @return decrypted byte-array
	 * @see PrivateKey
	 * @see CryptoAsymmetricAlgo
	 * @since 0.0.1
	 */
	@Override
	public byte[] decrypt(final byte[] input, final PrivateKey key) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, key));

		final byte[] result = decrypt(input, key, algorithm.getDefaultKeysize());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public byte[] decrypt(final byte[] input, final PrivateKey key, final int keySize) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, key, keySize));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(input)) {
			throw new RuntimeExceptionIsEmpty("input"); //$NON-NLS-1$
		}
		if (null == key) {
			throw new RuntimeExceptionIsNull("key"); //$NON-NLS-1$
		}
		if (0 >= keySize) {
			throw new RuntimeExceptionMustBeGreater("keySize", keySize, 0); //$NON-NLS-1$
		}
		if (0 != keySize % 8) {
			throw new IllegalArgumentException("keySize is not a multiple of 8"); //$NON-NLS-1$
		}
		if (input.length * 2 > HelperEnvironment.getMemoryFree()) {
			throw new RuntimeExceptionExceedsVmMemory("input", input.length * 2); //$NON-NLS-1$
		}

		final int space = keySize / 8;
		byte[] result = HelperArray.EMPTY_ARRAY_BYTE;
		final byte[] temp = new byte[space];

		if (space < input.length) {

			int tempCounter = 0;
			for (final byte data : input) {
				temp[tempCounter] = data;

				if (tempCounter == space - 1) {
					result = HelperArray.concatenate(result, decryptInternal(temp, key));
					tempCounter = 0;
				} else {
					tempCounter++;
				}
			}
		} else {
			result = decryptInternal(input, key);
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}
