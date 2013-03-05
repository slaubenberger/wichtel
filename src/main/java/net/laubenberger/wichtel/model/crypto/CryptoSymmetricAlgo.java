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

package net.laubenberger.wichtel.model.crypto;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * Symmetric crypto algorithms available in BouncyCastle
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
@XmlRootElement(name = "cryptoSymmetricAlgo")
public enum CryptoSymmetricAlgo implements CryptoAlgo {
	AES("AES", "AES/CFB/NoPadding", 192, 16), //$NON-NLS-1$ //$NON-NLS-2$
//	AES("AES", "AES/CFB/NoPadding", 128, 16), //$NON-NLS-1$ //$NON-NLS-2$
	DES("DES", "DES/CFB/NoPadding", 64, 8), //$NON-NLS-1$ //$NON-NLS-2$
	DESEDE("DESEDE", "DESEDE/CFB/NoPadding", 128, 8), //$NON-NLS-1$ //$NON-NLS-2$
	BLOWFISH("BLOWFISH", "BLOWFISH/CFB/NoPadding", 448, 8), //$NON-NLS-1$ //$NON-NLS-2$
	CAMELLIA("CAMELLIA", "CAMELLIA/CFB/NoPadding", 256, 16), //$NON-NLS-1$ //$NON-NLS-2$
	CAST5("CAST5", "CAST5/CFB/NoPadding", 128, 8), //$NON-NLS-1$ //$NON-NLS-2$
	CAST6("CAST6", "CAST6/CFB/NoPadding", 256, 16), //$NON-NLS-1$ //$NON-NLS-2$
	NOEKEON("NOEKEON", "NOEKEON/CFB/NoPadding", 128, 16), //$NON-NLS-1$ //$NON-NLS-2$
	RC2("RC2", "RC2/CFB/NoPadding", 128, 8), //$NON-NLS-1$ //$NON-NLS-2$
	RC5("RC5", "RC5/CFB/NoPadding", 128, 8), //$NON-NLS-1$ //$NON-NLS-2$
	RC6("RC6", "RC6/CFB/NoPadding", 128, 16), //$NON-NLS-1$ //$NON-NLS-2$
	RIJNDAEL("RIJNDAEL", "RIJNDAEL/CFB/NoPadding", 192, 16), //$NON-NLS-1$ //$NON-NLS-2$
	SEED("SEED", "SEED/CFB/NoPadding", 128, 16), //$NON-NLS-1$ //$NON-NLS-2$
	SERPENT("SERPENT", "SERPENT/CFB/NoPadding", 256, 16), //$NON-NLS-1$ //$NON-NLS-2$
	SKIPJACK("SKIPJACK", "SKIPJACK/CFB/NoPadding", 128, 8), //$NON-NLS-1$ //$NON-NLS-2$
	TWOFISH("TWOFISH", "TWOFISH/CFB/NoPadding", 256, 16), //$NON-NLS-1$ //$NON-NLS-2$
	TEA("TEA", "TEA/CFB/NoPadding", 128, 8), //$NON-NLS-1$ //$NON-NLS-2$
	XTEA("XTEA", "XTEA/CFB/NoPadding", 128, 8); //$NON-NLS-1$ //$NON-NLS-2$

	private final String algorithm;
	private final String xform;
	private final int defaultKeysize;
	private final int ivSize;

	CryptoSymmetricAlgo(final String algorithm, final String xform, final int defaultKeysize, final int ivSize) {
		this.algorithm = algorithm;
		this.xform = xform;
		this.defaultKeysize = defaultKeysize;
		this.ivSize = ivSize;
	}

	public int getIvSize() {
		return ivSize;
	}

	/*
	 * Implemented methods
	 */

	@Override
	public String getAlgorithm() {
		return algorithm;
	}

	@Override
	public String getXform() {
		return xform;
	}

	@Override
	public int getDefaultKeysize() {
		return defaultKeysize;
	}
}	

