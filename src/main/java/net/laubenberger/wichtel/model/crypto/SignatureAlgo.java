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
 * Signature algoritms available in BouncyCastle
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
@XmlRootElement(name = "signatureAlgo")
public enum SignatureAlgo implements Algorithm {
	MD2_WITH_RSA("MD2WithRSAEncryption"), //$NON-NLS-1$
	MD5_WITH_RSA("MD5WithRSAEncryption"), //$NON-NLS-1$
	SHA1_WITH_RSA("SHA1WithRSAEncryption"), //$NON-NLS-1$
//	SHA1_WITH_DSA("SHA1withDSA"), //$NON-NLS-1$
	SHA224_WITH_RSA("SHA224WithRSAEncryption"), //$NON-NLS-1$
	SHA256_WITH_RSA("SHA256WithRSAEncryption"), //$NON-NLS-1$
	SHA384_WITH_RSA("SHA384WithRSAEncryption"), //$NON-NLS-1$
	SHA512_WITH_RSA("SHA512WithRSAEncryption"), //$NON-NLS-1$
	RIPEMD128_WITH_RSA("RIPEMD128WithRSAEncryption"), //$NON-NLS-1$
	RIPEMD160_WITH_RSA("RIPEMD160WithRSAEncryption"), //$NON-NLS-1$
	RIPEMD256_WITH_RSA("RIPEMD256WithRSAEncryption"); //$NON-NLS-1$

	private final String algorithm;

	SignatureAlgo(final String algorithm) {
		this.algorithm = algorithm;
	}


	/*
	 * Implemented methods
	 */

	@Override
	public String getAlgorithm() {
		return algorithm;
	}
}	

