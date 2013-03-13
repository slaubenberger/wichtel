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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Arrays;

import net.laubenberger.wichtel.helper.HelperArray;
import net.laubenberger.wichtel.helper.HelperCrypto;
import net.laubenberger.wichtel.helper.HelperEnvironment;
import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionExceedsVmMemory;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;
import net.laubenberger.wichtel.model.crypto.HashCodeAlgo;
import net.laubenberger.wichtel.service.ServiceAbstract;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is an implementation for hash code generation.
 *
 * @author Stefan Laubenberger
 * @version 0.0.2, 2013-03-13
 * @since 0.0.1
 */
public class HashCodeGeneratorImpl extends ServiceAbstract implements HashCodeGenerator {
	private static final Logger log = LoggerFactory.getLogger(HashCodeGeneratorImpl.class);

	private static final int DEFAULT_PARTS = 32;
	private static final int DEFAULT_PARTSIZE = Constants.DEFAULT_FILE_BUFFER_SIZE;

	private final MessageDigest md;

	public HashCodeGeneratorImpl(final Provider provider, final HashCodeAlgo algorithm) throws NoSuchAlgorithmException {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(provider, algorithm));

		if (null == provider) {
			throw new RuntimeExceptionIsNull("provider"); //$NON-NLS-1$
		}
		if (null == algorithm) {
			throw new RuntimeExceptionIsNull("algorithm"); //$NON-NLS-1$
		}
		
		md = MessageDigest.getInstance(algorithm.getAlgorithm(), provider);
	}
	
	public HashCodeGeneratorImpl(final HashCodeAlgo algorithm) throws NoSuchAlgorithmException {
		this(HelperCrypto.DEFAULT_PROVIDER, algorithm);
	}

	
	/*
	 * Implemented methods
	 */

	@Override
	public byte[] getHash(final byte... input) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(input)) {
			throw new RuntimeExceptionIsEmpty("input"); //$NON-NLS-1$
		}

		md.reset();
		md.update(input);

		final byte[] result = md.digest();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public byte[] getHash(final File input) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));

		final byte[] result = getHash(input, Constants.DEFAULT_FILE_BUFFER_SIZE);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public byte[] getHash(final File input, final int bufferSize) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, bufferSize));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}

		BufferedInputStream bis = null;

		try {
			bis = new BufferedInputStream(new FileInputStream(input));

			final byte[] result = getHash(bis, bufferSize);

			if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
			return result;
		} finally {
			if (null != bis) {
				bis.close();
			}
		}
	}

	@Override
	public byte[] getHash(final InputStream is, final int bufferSize) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(is, bufferSize));
		if (null == is) {
			throw new RuntimeExceptionIsNull("is"); //$NON-NLS-1$
		}
		if (1 > bufferSize) {
			throw new RuntimeExceptionMustBeGreater("bufferSize", bufferSize, 1); //$NON-NLS-1$
		}
		if (bufferSize > HelperEnvironment.getMemoryFree()) {
			throw new RuntimeExceptionExceedsVmMemory("bufferSize", bufferSize); //$NON-NLS-1$
		}

		md.reset();

		final byte[] buffer = new byte[bufferSize];

		int offset = is.read(buffer);
		while (0 < offset) {
			md.update(buffer, 0, offset);
			offset = is.read(buffer);
		}

		final byte[] result = md.digest();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public byte[] getHash(final InputStream is) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(is));

		final byte[] result = getHash(is, Constants.DEFAULT_FILE_BUFFER_SIZE);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public byte[] getFastHash(final byte[] input, final int parts, final int partSize) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, parts, partSize));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(input)) {
			throw new RuntimeExceptionIsEmpty("input"); //$NON-NLS-1$
		}
		if (0 > parts) {
			throw new RuntimeExceptionMustBeGreater("parts", parts, 0); //$NON-NLS-1$
		}
		if (0 > partSize) {
			throw new RuntimeExceptionMustBeGreater("partSize", partSize, 0); //$NON-NLS-1$
		}


		if (input.length < parts * partSize) {
			return getHash(input);
		}

		byte[] temp = Integer.toString(input.length).getBytes();
		final int offset = input.length / parts - partSize;
		int position = 0;

		for (int ii = 0; ii < parts; ii++) {

			temp = HelperArray.concatenate(temp, Arrays.copyOfRange(input, position, position + partSize));

			position += offset + partSize;
		}
		final byte[] result = getHash(temp);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public byte[] getFastHash(final byte... input) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));

		final byte[] result = getFastHash(input, DEFAULT_PARTS, DEFAULT_PARTSIZE);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public byte[] getFastHash(final File input, final int parts, final int partSize) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, parts, partSize));

		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (0 > parts) {
			throw new RuntimeExceptionMustBeGreater("parts", parts, 0); //$NON-NLS-1$
		}
		if (0 > partSize) {
			throw new RuntimeExceptionMustBeGreater("partSize", partSize, 0); //$NON-NLS-1$
		}


		if (input.length() < parts * partSize) {
			return getHash(input);
		}

		final byte[] buffer = new byte[partSize];
		byte[] temp = Long.toString(input.length()).getBytes();
		final int offset = (int) (input.length() / parts - partSize);

		RandomAccessFile raf = null;

		try {
			raf = new RandomAccessFile(input, "r");  //$NON-NLS-1$

			for (int ii = 0; ii < parts; ii++) {
				raf.read(buffer);

				temp = HelperArray.concatenate(temp, buffer);

				raf.seek(offset);
			}
		} finally {
			if (null != raf) {
				raf.close();
			}
		}
		final byte[] result = getHash(temp);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public byte[] getFastHash(final File input) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));

		final byte[] result = getFastHash(input, DEFAULT_PARTS, DEFAULT_PARTSIZE);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}