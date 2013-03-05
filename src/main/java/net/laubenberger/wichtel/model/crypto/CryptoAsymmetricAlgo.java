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
 * Asymmetric crypto algorithms available in BouncyCastle
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
@XmlRootElement(name = "cryptoAsymmetricAlgo")
public enum CryptoAsymmetricAlgo implements CryptoAlgo {
	RSA("RSA", "RSA/NONE/PKCS1PADDING", 2048);  //$NON-NLS-1$//$NON-NLS-2$
//	ELGAMAL("ELGAMAL", "ELGAMAL/NONE/PKCS1Padding", 1024);  //$NON-NLS-1$//$NON-NLS-2$

	private final String algorithm;
	private final String xform;
	private final int defaultKeysize;

	CryptoAsymmetricAlgo(final String algorithm, final String xform, final int defaultKeysize) {
		this.algorithm = algorithm;
		this.xform = xform;
		this.defaultKeysize = defaultKeysize;
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

