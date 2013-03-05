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

import java.io.File;
import java.io.IOException;

import net.laubenberger.wichtel.service.Service;


/**
 * This is an interface for scrambling data.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public interface Scrambler extends Service {
	/**
	 * Scramble the data.
	 *
	 * @param input	data (byte-array) to scramble
	 * @param pattern for scrambling (region: -128 - 127)
	 * @return scrambled byte-array
	 * @since 0.0.1
	 */
	byte[] scramble(byte[] input, byte pattern);

	/**
	 * Unscramble the data.
	 *
	 * @param input	data (byte-array) to unscramble
	 * @param pattern for unscrambling (region: -128 - 127)
	 * @return unscrambled byte-array
	 * @since 0.0.1
	 */
	byte[] unscramble(byte[] input, byte pattern);

	/**
	 * Scramble an input {@link File} to an output {@link File}.
	 *
	 * @param input	{@link File} to scramble
	 * @param output  {@link File} for the scrambled data
	 * @param pattern for scrambling (region: -128 - 127)
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	void scramble(File input, File output, byte pattern) throws IOException;

	/**
	 * Scramble an input {@link File} to an output {@link File}.
	 *
	 * @param input		{@link File} to scramble
	 * @param output	  {@link File} for the scrambled data
	 * @param pattern	 for scrambling (region: -128 - 127)
	 * @param bufferSize in bytes
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	void scramble(File input, File output, byte pattern, int bufferSize) throws IOException;

	/**
	 * Unscramble an input {@link File} to an output {@link File}.
	 *
	 * @param input	{@link File} to unscramble
	 * @param output  {@link File} for the unscrambled data
	 * @param pattern for unscrambling (region: -128 - 127)
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	void unscramble(File input, File output, byte pattern) throws IOException;

	/**
	 * Unscramble an input {@link File} to an output {@link File}.
	 *
	 * @param input		{@link File} to unscramble
	 * @param output	  {@link File} for the unscrambled data
	 * @param pattern	 for unscrambling (region: -128 - 127)
	 * @param bufferSize in bytes
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	void unscramble(File input, File output, byte pattern, int bufferSize) throws IOException;
}
