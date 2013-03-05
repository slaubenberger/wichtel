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

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import net.laubenberger.wichtel.model.crypto.SignatureAlgo;
import net.laubenberger.wichtel.service.Service;

/**
 * This is an interface for asymmetric cryptology.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public interface CryptoAsymmetric extends Service { //$Example$

	/**
	 * Generates a public and a private {@link KeyPair} with the algorithms standard key size.
	 *
	 * @return generated {@link KeyPair}
	 * @see KeyPair
	 * @since 0.0.1
	 */
	KeyPair generateKeyPair();

	/**
	 * Generates a public and a private {@link KeyPair} with a given key size.
	 *
	 * @param keysize in bits (normally modulo 16 = 0, e.g. 1024, 2048)
	 * @return generated {@link KeyPair}
	 * @see KeyPair
	 * @since 0.0.1
	 */
	KeyPair generateKeyPair(int keysize);

	/**
	 * Generates a signature from the data and the given {@link PrivateKey}.
	 *
	 * @param algoritm for the signature
	 * @param input	 data for the signature as a byte-array
	 * @param key		{@link PrivateKey} for the signature
	 * @return generated signature as byte-array
	 * @throws Exception
	 * @see PrivateKey
	 * @since 0.0.1
	 */
	byte[] generateSignature(SignatureAlgo algoritm, byte[] input, PrivateKey key) throws Exception;

	/**
	 * Verifys an signature with a given input and {@link PublicKey}.
	 *
	 * @param algoritm  of the signature
	 * @param signature to verify
	 * @param input	  data to verify with the signature as a byte-array
	 * @param key		 {@link PublicKey} to verify the signature
	 * @return true/false
	 * @throws Exception
	 * @see PublicKey
	 * @since 0.0.1
	 */
	boolean isValidSignature(SignatureAlgo algoritm, byte[] signature, byte[] input, PublicKey key) throws Exception;

	/**
	 * Encrypt the data (byte-array) with a given {@link PublicKey} and the implementations algorithm.
	 * Use this method only, if the key has the algorithms standard key size.
	 *
	 * @param input data to encrypt as a byte-array
	 * @param key	for the encryption
	 * @return encrypted byte-array
	 * @throws Exception
	 * @see PublicKey
	 * @since 0.0.1
	 */
	byte[] encrypt(byte[] input, PublicKey key) throws Exception;

	/**
	 * Encrypt the data (byte-array) with a given {@link PublicKey}, key size and the implementations algorithm.
	 *
	 * @param input	data to encrypt as a byte-array
	 * @param key	  for the encryption
	 * @param keySize in bits (normally modulo 16 = 0, e.g. 1024, 2048)
	 * @return encrypted byte-array
	 * @throws Exception
	 * @see PublicKey
	 * @since 0.0.1
	 */
	byte[] encrypt(byte[] input, PublicKey key, int keySize) throws Exception;

	/**
	 * Decrypt the data (byte-array) with a given {@link PrivateKey} and the implementations algorithm.
	 * Use this method only, if the key has the algorithms standard key size.
	 *
	 * @param input encrypted data as a byte-array
	 * @param key	for the decryption
	 * @return decrypted byte-array
	 * @throws Exception
	 * @see PrivateKey
	 * @since 0.0.1
	 */
	byte[] decrypt(byte[] input, PrivateKey key) throws Exception;

	/**
	 * Decrypt the data (byte-array) with a given {@link PrivateKey}, key size and the implementations algorithm.
	 *
	 * @param input	encrypted data as a byte-array
	 * @param key	  for the decryption
	 * @param keySize in bits (normally modulo 16 = 0, e.g. 1024, 2048)
	 * @return decrypted byte-array
	 * @throws Exception
	 * @see PrivateKey
	 * @since 0.0.1
	 */
	byte[] decrypt(byte[] input, PrivateKey key, int keySize) throws Exception;

}
