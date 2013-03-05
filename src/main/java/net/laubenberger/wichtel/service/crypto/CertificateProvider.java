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

import net.laubenberger.wichtel.service.Service;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;


/**
 * This interface is to generate, read and write X.509 certificates.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public interface CertificateProvider extends Service { //$Example$

	/**
	 * Reads the {@link X509Certificate} out of the given {@link File}.
	 *
	 * @param file containing the {@link X509Certificate}
	 * @return {@link X509Certificate}
	 * @throws Exception
	 * @see File
	 * @see X509Certificate
	 * @since 0.0.1
	 */
	X509Certificate readCertificate(File file) throws Exception;

	/**
	 * Reads the {@link X509Certificate} out of the given {@link InputStream}.
	 *
	 * @param is {@link InputStream} containing the {@link X509Certificate}
	 * @return {@link X509Certificate}
	 * @throws Exception
	 * @see InputStream
	 * @see X509Certificate
	 * @since 0.0.1
	 */
	X509Certificate readCertificate(InputStream is) throws Exception;

	/**
	 * Writes the {@link Certificate} on an {@link OutputStream}.
	 *
	 * @param os	{@link OutputStream} for the {@link Certificate}
	 * @param cert {@link Certificate}
	 * @throws Exception
	 * @see OutputStream
	 * @see Certificate
	 * @since 0.0.1
	 */
	void writeCertificate(OutputStream os, Certificate cert) throws Exception;

	/**
	 * Writes the {@link Certificate} in a {@link File}.
	 *
	 * @param file for the {@link Certificate}
	 * @param cert {@link Certificate}
	 * @throws Exception
	 * @see File
	 * @see Certificate
	 * @since 0.0.1
	 */
	void writeCertificate(File file, Certificate cert) throws Exception;

	/**
	 * Generate a {@link X509Certificate} out of the given {@link KeyPair} and parameters.
	 *
	 * @param pair		  {@link KeyPair} for the {@link X509Certificate}
	 * @param issuerDN	 e.g. "CN=Test Certificate"
	 * @param subjectDN	e.g. "CN=Test Certificate"
	 * @param generalName of the {@link X509Certificate} owner (e.g. yourmail@gmail.com)
	 * @param start		 date of the {@link X509Certificate}
	 * @param end			date of the {@link X509Certificate}
	 * @return {@link X509Certificate}
	 * @throws Exception
	 * @see KeyPair
	 * @see X509Certificate
	 * @since 0.0.1
	 */
	X509Certificate generateCertificate(KeyPair pair, String issuerDN, String subjectDN, String generalName, Date start, Date end) throws Exception;
}
