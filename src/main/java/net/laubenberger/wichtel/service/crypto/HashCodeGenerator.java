/*
 * Copyright (c) 2007-2013 by Stefan Laubenberger.
 *
 * "wichtel" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License v3.0.
 *
 * "wichtel" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * See the GNU Lesser General Public License for more details:
 * -----------------------------------------------------------
 * http://www.gnu.org/licenses
 *
 *
 * This distribution is available at:
 * ----------------------------------
 * https://github.com/slaubenberger/wichtel/
 *
 *
 * Contact information:
 * --------------------
 * Stefan Laubenberger
 * Bullingerstrasse 53
 * CH-8004 Zuerich
 *
 * http://www.laubenberger.net
 * laubenberger@gmail.com
 */

package net.laubenberger.wichtel.service.crypto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import net.laubenberger.wichtel.model.crypto.HashCodeAlgo;
import net.laubenberger.wichtel.service.Service;

/**
 * This is an interface for hash code generation.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public interface HashCodeGenerator extends Service {

	/**
	 * Generates a hash code for a byte-array.
	 *
	 * @param input byte-array for the hash code
	 * @return generated hash code
	 * @since 0.0.1
	 */
	byte[] getHash(byte... input);

	/**
	 * Generates a hash code for an {@link InputStream}.
	 *
	 * @param is {@link InputStream} for the hash code
	 * @return generated hash code
	 * @throws IOException
	 * @see InputStream
	 * @since 0.0.1
	 */
	byte[] getHash(InputStream is) throws IOException;

	/**
	 * Generates a hash code for an {@link InputStream}.
	 *
	 * @param is			{@link InputStream} for the hash code
	 * @param bufferSize in bytes
	 * @return generated hash code
	 * @throws IOException
	 * @see InputStream
	 * @since 0.0.1
	 */
	byte[] getHash(InputStream is, int bufferSize) throws IOException;

	/**
	 * Generates a hash code for an input {@link File}.
	 *
	 * @param input {@link File} for the hash code
	 * @return generated hash code
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	byte[] getHash(File input) throws IOException;

	/**
	 * Generates a hash code for an input {@link File} with the given {@link HashCodeAlgo}.
	 *
	 * @param input		{@link File} for the hash code
	 * @param bufferSize in bytes
	 * @return generated hash code
	 * @throws IOException
	 * @see File
	 * @see HashCodeAlgo
	 * @since 0.0.1
	 */
	byte[] getHash(File input, int bufferSize) throws IOException;

	/**
	 * Generates a hash code for a byte-array.
	 * This method generates a fast hash code over the data (e.g. compare different data)
	 * <p/>
	 * <strong>Warning:</strong> Don't use this method to verify the integrity of data. It doesn't use the complete data to generate the hash code.
	 *
	 * @param input	 byte-array for the hash code
	 * @param parts	 for the hash code data
	 * @param partSize of the hash code data
	 * @return generated hash code
	 * @since 0.0.1
	 */
	byte[] getFastHash(byte[] input, int parts, int partSize);

	/**
	 * Generates a hash code for a byte-array with the standard settings (error rate less than 0.02%).
	 * This method generates a fast hash code over the data (e.g. compare different data)
	 * <p>
	 * <strong>Warning:</strong> Don't use this method to verify the integrity of data. It doesn't use the complete data to generate the hash code.
	 *
	 * @param input byte-array for the hash code
	 * @return generated hash code
	 * @since 0.0.1
	 */
	byte[] getFastHash(byte... input);

	/**
	 * Generates a fast hash code for an input {@link File}.
	 * This method generates a fast hash code over the {@link File} (e.g. find same file containing the same data)
	 * <p>
	 * <strong>Warning:</strong> Don't use this method to verify the integrity of data. It doesn't use the complete data to generate the hash code.
	 *
	 * @param input	 {@link File} for the hash code
	 * @param parts	 for the hash code data
	 * @param partSize of the hash code data
	 * @return generated hash code
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	byte[] getFastHash(File input, int parts, int partSize) throws IOException;

	/**
	 * Generates a fast hash code for an input {@link File} with standard settings (error rate less than 0.02%).
	 * This method generates a fast hash code over the {@link File} (e.g. find same file containing the same data)
	 * <p>
	 * <strong>Warning:</strong> Don't use this method to verify the integrity of data. It doesn't use the complete data to generate the hash code.
	 *
	 * @param input {@link File} for the hash code
	 * @return generated hash code
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	byte[] getFastHash(File input) throws IOException;
}
