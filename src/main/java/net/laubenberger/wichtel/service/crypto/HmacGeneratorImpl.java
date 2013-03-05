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
import java.security.NoSuchAlgorithmException;
import java.security.Provider;

import javax.crypto.Mac;

import net.laubenberger.wichtel.helper.HelperCrypto;
import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.model.crypto.HmacAlgo;
import net.laubenberger.wichtel.service.ServiceAbstract;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is an implementation for hmac generation.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public class HmacGeneratorImpl extends ServiceAbstract implements HmacGenerator {
	private static final Logger log = LoggerFactory.getLogger(HmacGeneratorImpl.class);

	private final Mac mac;

	public HmacGeneratorImpl(final Provider provider, final HmacAlgo algorithm) throws NoSuchAlgorithmException {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(provider, algorithm));

		if (null == provider) {
			throw new RuntimeExceptionIsNull("provider"); //$NON-NLS-1$
		}
		if (null == algorithm) {
			throw new RuntimeExceptionIsNull("algorithm"); //$NON-NLS-1$
		}
		
		mac = Mac.getInstance(algorithm.getAlgorithm(), provider);
	}
	
	public HmacGeneratorImpl(final HmacAlgo algorithm) throws NoSuchAlgorithmException {
		this(HelperCrypto.DEFAULT_PROVIDER, algorithm);
	}
	

	/*
	 * Implemented methods
	 */

	@Override
	public byte[] getHmac(final byte[] input, final Key key) throws InvalidKeyException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, key));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		if (null == key) {
			throw new RuntimeExceptionIsNull("key"); //$NON-NLS-1$
		}

		mac.init(key);
		mac.update(input);

		final byte[] result = mac.doFinal();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}