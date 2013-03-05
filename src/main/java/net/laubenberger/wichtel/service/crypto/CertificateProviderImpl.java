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
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import net.laubenberger.wichtel.helper.HelperCrypto;
import net.laubenberger.wichtel.helper.HelperIO;
import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.helper.HelperString;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeBefore;
import net.laubenberger.wichtel.service.ServiceAbstract;

import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class generates, reads and writes X.509 certificates.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public class CertificateProviderImpl extends ServiceAbstract implements CertificateProvider {
	private static final Logger log = LoggerFactory.getLogger(CertificateProviderImpl.class);

	private final Provider provider;

	
	public CertificateProviderImpl(final Provider provider) {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(provider));
		
		if (null == provider) {
			throw new RuntimeExceptionIsNull("provider"); //$NON-NLS-1$
		}
		
		this.provider = provider;
	}
	
	public CertificateProviderImpl() {
		this(HelperCrypto.DEFAULT_PROVIDER);
	}


	/*
	 * Implemented methods
	 */

	@Override
	public X509Certificate readCertificate(final File file) throws CertificateException, NoSuchProviderException, IOException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}

		BufferedInputStream bis = null;

		try {
			bis = new BufferedInputStream(new FileInputStream(file));

			final X509Certificate result = readCertificate(new BufferedInputStream(new FileInputStream(file)));

			if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
			return result;
		} finally {
			if (null != bis) {
				bis.close();
			}
		}
	}

	@Override
	public X509Certificate readCertificate(final InputStream is) throws CertificateException, NoSuchProviderException, IOException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(is));
		if (null == is) {
			throw new RuntimeExceptionIsNull("is"); //$NON-NLS-1$
		}

		try {
			// Generate the certificate factory
			final CertificateFactory cf = CertificateFactory.getInstance("X.509", provider); //$NON-NLS-1$

			// get the certificate
			final X509Certificate result = (X509Certificate) cf.generateCertificate(is);

			if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
			return result;
		} finally {
			is.close();
		}
	}

	@Override
	public void writeCertificate(final OutputStream os, final Certificate cert) throws CertificateEncodingException, IOException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(os, cert));
		if (null == os) {
			throw new RuntimeExceptionIsNull("os"); //$NON-NLS-1$
		}
		if (null == cert) {
			throw new RuntimeExceptionIsNull("cert"); //$NON-NLS-1$
		}

		HelperIO.writeStream(os, cert.getEncoded());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void writeCertificate(final File file, final Certificate cert) throws CertificateEncodingException, IOException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, cert));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		if (null == cert) {
			throw new RuntimeExceptionIsNull("cert"); //$NON-NLS-1$
		}

		HelperIO.writeFile(file, cert.getEncoded(), false);
	}

	@Override
	public X509Certificate generateCertificate(final KeyPair pair, final String issuerDN, final String subjectDN, final String generalName, final Date start, final Date end) throws NoSuchAlgorithmException, IllegalStateException, CertificateEncodingException, InvalidKeyException, NoSuchProviderException, SecurityException, SignatureException { //$JUnit$
		if (null == pair) {
			throw new RuntimeExceptionIsNull("pair"); //$NON-NLS-1$
		}
		if (null == issuerDN) {
			throw new RuntimeExceptionIsNull("issuerDN"); //$NON-NLS-1$
		}
		if (!HelperString.isValid(issuerDN)) {
			throw new RuntimeExceptionIsEmpty("issuerDN"); //$NON-NLS-1$
		}
		if (null == subjectDN) {
			throw new RuntimeExceptionIsNull("subjectDN"); //$NON-NLS-1$
		}
		if (!HelperString.isValid(subjectDN)) {
			throw new RuntimeExceptionIsEmpty("subjectDN"); //$NON-NLS-1$
		}		if (null == generalName) {
			throw new RuntimeExceptionIsNull("generalName"); //$NON-NLS-1$
		}
		if (!HelperString.isValid(generalName)) {
			throw new RuntimeExceptionIsEmpty("generalName"); //$NON-NLS-1$
		}
		if (null == start) {
			throw new RuntimeExceptionIsNull("start"); //$NON-NLS-1$
		}
		if (null == end) {
			throw new RuntimeExceptionIsNull("end"); //$NON-NLS-1$
		}
		if (start.after(end)) {
			throw new RuntimeExceptionMustBeBefore("start", start, end); //$NON-NLS-1$
		}

		// generate the certificate
		final X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();

		certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
		certGen.setIssuerDN(new X500Principal(issuerDN));
		certGen.setNotBefore(start);
		certGen.setNotAfter(end);
		certGen.setSubjectDN(new X500Principal(subjectDN));
		certGen.setPublicKey(pair.getPublic());
		certGen.setSignatureAlgorithm("SHA256WithRSAEncryption"); //$NON-NLS-1$

		certGen.addExtension(X509Extensions.BasicConstraints, true, new BasicConstraints(false));
		certGen.addExtension(X509Extensions.KeyUsage, true, new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
		certGen.addExtension(X509Extensions.ExtendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));
		certGen.addExtension(X509Extensions.SubjectAlternativeName, false, new GeneralNames(new GeneralName(GeneralName.rfc822Name, generalName)));

		return certGen.generate(pair.getPrivate(), provider.getName());
	}
}
