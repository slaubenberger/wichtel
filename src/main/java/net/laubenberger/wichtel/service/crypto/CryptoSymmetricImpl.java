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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import net.laubenberger.wichtel.helper.HelperArray;
import net.laubenberger.wichtel.helper.HelperCrypto;
import net.laubenberger.wichtel.helper.HelperEnvironment;
import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.helper.HelperObject;
import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionExceedsVmMemory;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEquals;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeSmaller;
import net.laubenberger.wichtel.model.crypto.CryptoSymmetricAlgo;
import net.laubenberger.wichtel.model.crypto.HashCodeAlgo;
import net.laubenberger.wichtel.service.ServiceAbstract;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a class for symmetric cryptology via AES.
 *
 * @author Stefan Laubenberger
 * @version 0.0.2, 2013-03-14
 * @since 0.0.1
 */
public class CryptoSymmetricImpl extends ServiceAbstract implements CryptoSymmetric {
	private static final Logger log = LoggerFactory.getLogger(CryptoSymmetricImpl.class);

	private final CryptoSymmetricAlgo algorithm;

	private final Cipher cipher;
	private final KeyGenerator kg;
	private final HashCodeGenerator hcg;

	public CryptoSymmetricImpl(final Provider provider, final CryptoSymmetricAlgo algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(provider, algorithm));

		if (null == provider) {
			throw new RuntimeExceptionIsNull("provider"); //$NON-NLS-1$
		}
		if (null == algorithm) {
			throw new RuntimeExceptionIsNull("algorithm"); //$NON-NLS-1$
		}

		this.algorithm = algorithm;
		
		cipher = Cipher.getInstance(algorithm.getXform(), provider);
		kg = KeyGenerator.getInstance(algorithm.getAlgorithm(), provider);
		hcg = new HashCodeGeneratorImpl(HashCodeAlgo.SHA512);
	}
	
	public CryptoSymmetricImpl(final CryptoSymmetricAlgo algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException {
		this(HelperCrypto.DEFAULT_PROVIDER, algorithm);
	}
	
	
	/*
	 * Private methods
	 */
	private AlgorithmParameterSpec prepareIv() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());

		final byte[] ivBytes = new byte[algorithm.getIvSize()];

		for (int ii = 0; algorithm.getIvSize() > ii; ii++) {
			ivBytes[ii] = (byte) 0x5a;
		}
		final AlgorithmParameterSpec result = new IvParameterSpec(ivBytes);

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit(result));
		return result;
	}


	/*
	 * Implemented methods
	 */

	/**
	 * Generates a {@link SecretKey} with the {@link CryptoSymmetricAlgo} standard key size.
	 *
	 * @return generated secret key
	 * @see SecretKey
	 * @see CryptoSymmetricAlgo
	 * @since 0.0.1
	 */
	@Override
	public SecretKey generateKey() { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		final SecretKey result = generateKey(algorithm.getDefaultKeysize());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public SecretKey generateKey(final int keySize) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(keySize));
		if (0 >= keySize) {
			throw new RuntimeExceptionMustBeGreater("keySize", keySize, 0); //$NON-NLS-1$
		}
		if (0 != keySize % 8) {
			throw new IllegalArgumentException("keySize is not a multiple of 8"); //$NON-NLS-1$
		}

		// Generate a key
		kg.init(keySize);

		final SecretKey result = kg.generateKey();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public SecretKey generateKey(final byte... password) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(password));

		final SecretKey result = generateKey(password, algorithm.getDefaultKeysize());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public SecretKey generateKey(final byte[] password, final int keySize) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(password, keySize));
		if (null == password) {
			throw new RuntimeExceptionIsNull("password"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(password)) {
			throw new RuntimeExceptionIsEmpty("password"); //$NON-NLS-1$
		}
		if (0 >= keySize) {
			throw new RuntimeExceptionMustBeGreater("keySize", keySize, 0); //$NON-NLS-1$
		}
		if (512 < keySize) {
			throw new RuntimeExceptionMustBeSmaller("keySize", keySize, 512); //$NON-NLS-1$
		}
		if (0 != keySize % 8) {
			throw new IllegalArgumentException("keySize is not a multiple of 8"); //$NON-NLS-1$
		}

		final SecretKey result = new SecretKeySpec(Arrays.copyOfRange(hcg.getHash(password), 0, keySize / 8), algorithm.getAlgorithm());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public byte[] encrypt(final byte[] input, final Key key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, key));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(input)) {
			throw new RuntimeExceptionIsEmpty("input"); //$NON-NLS-1$
		}
		if (null == key) {
			throw new RuntimeExceptionIsNull("key"); //$NON-NLS-1$
		}
		if (input.length * 2 > HelperEnvironment.getMemoryFree()) {
			throw new RuntimeExceptionExceedsVmMemory("input", input.length * 2); //$NON-NLS-1$
		}

//		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(cipher.getIV()));
		cipher.init(Cipher.ENCRYPT_MODE, key, prepareIv());
		final byte[] result = cipher.doFinal(input);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public byte[] decrypt(final byte[] input, final Key key) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, key));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(input)) {
			throw new RuntimeExceptionIsEmpty("input"); //$NON-NLS-1$
		}
		if (null == key) {
			throw new RuntimeExceptionIsNull("key"); //$NON-NLS-1$
		}
		if (input.length * 2 > HelperEnvironment.getMemoryFree()) {
			throw new RuntimeExceptionExceedsVmMemory("input", input.length * 2); //$NON-NLS-1$
		}

//		cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(cipher.getIV()));
		cipher.init(Cipher.DECRYPT_MODE, key, prepareIv());
		final byte[] result = cipher.doFinal(input);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public void encrypt(final InputStream is, final OutputStream os, final Key key) throws InvalidKeyException, InvalidAlgorithmParameterException, IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(is, os, key));

		encrypt(is, os, key, Constants.DEFAULT_FILE_BUFFER_SIZE);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void encrypt(final InputStream is, OutputStream os, final Key key, final int bufferSize) throws InvalidKeyException, InvalidAlgorithmParameterException, IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(is, os, key, bufferSize));
		if (null == is) {
			throw new RuntimeExceptionIsNull("is"); //$NON-NLS-1$
		}
		if (null == os) {
			throw new RuntimeExceptionIsNull("os"); //$NON-NLS-1$
		}
		if (null == key) {
			throw new RuntimeExceptionIsNull("key"); //$NON-NLS-1$
		}
		if (1 > bufferSize) {
			throw new RuntimeExceptionMustBeGreater("bufferSize", bufferSize, 1); //$NON-NLS-1$
		}
		if (bufferSize > HelperEnvironment.getMemoryFree()) {
			throw new RuntimeExceptionExceedsVmMemory("bufferSize", bufferSize); //$NON-NLS-1$
		}

		final byte[] buffer = new byte[bufferSize];

//		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(cipher.getIV()));
		cipher.init(Cipher.ENCRYPT_MODE, key, prepareIv());
		os = new CipherOutputStream(os, cipher);

		try {
			int offset;
			while (0 <= (offset = is.read(buffer))) {
				os.write(buffer, 0, offset);
			}
		} finally {
			os.close();
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void decrypt(final InputStream is, final OutputStream os, final Key key) throws InvalidKeyException, InvalidAlgorithmParameterException, IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(is, os, key));

		decrypt(is, os, key, Constants.DEFAULT_FILE_BUFFER_SIZE);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void decrypt(final InputStream is, final OutputStream os, final Key key, final int bufferSize) throws InvalidKeyException, InvalidAlgorithmParameterException, IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(is, os, key, bufferSize));
		if (null == is) {
			throw new RuntimeExceptionIsNull("is"); //$NON-NLS-1$
		}
		if (null == os) {
			throw new RuntimeExceptionIsNull("os"); //$NON-NLS-1$
		}
		if (null == key) {
			throw new RuntimeExceptionIsNull("key"); //$NON-NLS-1$
		}
		if (1 > bufferSize) {
			throw new RuntimeExceptionMustBeGreater("bufferSize", bufferSize, 1); //$NON-NLS-1$
		}
		if (bufferSize > HelperEnvironment.getMemoryFree()) {
			throw new RuntimeExceptionExceedsVmMemory("bufferSize", bufferSize); //$NON-NLS-1$
		}

		final byte[] buffer = new byte[bufferSize];

		try (CipherInputStream cis = new CipherInputStream(is, cipher)) {
//			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(cipher.getIV()));
			cipher.init(Cipher.DECRYPT_MODE, key, prepareIv());

			int offset;
			while (0 <= (offset = cis.read(buffer))) {
				os.write(buffer, 0, offset);
			}
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void encrypt(final File input, final File output, final Key key) throws InvalidKeyException, InvalidAlgorithmParameterException, IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, output, key));

		encrypt(input, output, key, Constants.DEFAULT_FILE_BUFFER_SIZE);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void encrypt(final File input, final File output, final Key key, final int bufferSize) throws InvalidKeyException, InvalidAlgorithmParameterException, IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, output, key, bufferSize));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (null == output) {
			throw new RuntimeExceptionIsNull("output"); //$NON-NLS-1$
		}
		if (HelperObject.isEquals(input, output)) {
			throw new RuntimeExceptionIsEquals("input", "output"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(input));
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(output))) {
			encrypt(bis, bos, key, bufferSize);
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void decrypt(final File input, final File output, final Key key) throws InvalidKeyException, InvalidAlgorithmParameterException, IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, output, key));

		decrypt(input, output, key, Constants.DEFAULT_FILE_BUFFER_SIZE);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void decrypt(final File input, final File output, final Key key, final int bufferSize) throws InvalidKeyException, InvalidAlgorithmParameterException, IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, output, key, bufferSize));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (null == output) {
			throw new RuntimeExceptionIsNull("output"); //$NON-NLS-1$
		}
		if (HelperObject.isEquals(input, output)) {
			throw new RuntimeExceptionIsEquals("input", "output"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(input));
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(output))) {
			decrypt(bis, bos, key, bufferSize);
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
}
