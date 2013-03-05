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
 * Hash codes algoritms available in BouncyCastle
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
@XmlRootElement(name = "hashCodeAlgo")
public enum HashCodeAlgo implements Algorithm {
	MD2("MD2"), //$NON-NLS-1$
	MD4("MD4"), //$NON-NLS-1$
	MD5("MD5"), //$NON-NLS-1$
	SHA1("SHA-1"), //$NON-NLS-1$
	SHA224("SHA-224"), //$NON-NLS-1$
	SHA256("SHA-256"), //$NON-NLS-1$
	SHA384("SHA-384"), //$NON-NLS-1$
	SHA512("SHA-512"), //$NON-NLS-1$
	RIPEMD128("RIPEMD128"), //$NON-NLS-1$
	RIPEMD160("RIPEMD160"), //$NON-NLS-1$
	RIPEMD256("RIPEMD256"), //$NON-NLS-1$
	RIPEMD320("RIPEMD320"), //$NON-NLS-1$
	TIGER("Tiger"), //$NON-NLS-1$
	GOST3411("GOST3411"), //$NON-NLS-1$
	WHIRLPOOL("WHIRLPOOL"); //$NON-NLS-1$

	private final String algorithm;

	HashCodeAlgo(final String algorithm) {
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

