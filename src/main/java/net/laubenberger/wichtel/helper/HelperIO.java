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

package net.laubenberger.wichtel.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionExceedsVmMemory;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEquals;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for disk I/O.
 * 
 * @author Stefan Laubenberger
 * @version 0.2.0, 2014-05-12
 * @since 0.0.1
 */
public final class HelperIO {
	private static final Logger log = LoggerFactory.getLogger(HelperIO.class);

	public static final String FILE_SEPARATOR = System.getProperty("file.separator"); //$NON-NLS-1$
	public static final String PATH_SEPARATOR = System.getProperty("path.separator"); //$NON-NLS-1$

    private HelperIO() {
        //do nothing
    }

	/**
	 * Returns a temporary {@link File} with a given name and extension.
	 * 
	 * @param name
	 *           of the {@link File}
	 * @param extension
	 *           of the {@link File} (e.g. ".java")
	 * @return temporary {@link File}
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static File getTemporaryFile(final String name, final String extension) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(name, extension));
		if (null == name) {
			throw new RuntimeExceptionIsNull("name"); //$NON-NLS-1$
		}
		if (!HelperString.isValid(name)) {
			throw new RuntimeExceptionIsEmpty("name"); //$NON-NLS-1$
		}
		if (null == extension) {
			throw new RuntimeExceptionIsNull("extension"); //$NON-NLS-1$
		}
		if (!HelperString.isValid(extension)) {
			throw new RuntimeExceptionIsEmpty("extension"); //$NON-NLS-1$
		}

		// Create temp file
		final File result = extension.startsWith(HelperString.PERIOD) ? File.createTempFile(name, extension) : File
				.createTempFile(name, HelperString.PERIOD + extension);

		// Delete temp file when program exits
		result.deleteOnExit();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns a temporary {@link File} with a given extension.
	 * 
	 * @param extension
	 *           of the {@link File} (e.g. ".java")
	 * @return temporary {@link File}
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static File getTemporaryFile(final String extension) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(extension));

		final File result = getTemporaryFile(Constants.WICHTEL.getName(), extension);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns a temporary {@link File} which will be deleted on program exit.
	 * 
	 * @return temporary {@link File}
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static File getTemporaryFile() throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		final File result = getTemporaryFile(Constants.WICHTEL.getName(), "tmp"); //$NON-NLS-1$

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Copy a file or directory.
	 * 
	 * @param source
	 *           directory to copy
	 * @param dest
	 *           directory destination
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static void copy(final File source, final File dest) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(source, dest));
		if (null == source) {
			throw new RuntimeExceptionIsNull("source"); //$NON-NLS-1$
		}
		if (null == dest) {
			throw new RuntimeExceptionIsNull("dest"); //$NON-NLS-1$
		}
		if (HelperObject.isEquals(source, dest)) {
			throw new RuntimeExceptionIsEquals("source", "dest"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		if (source.isDirectory()) {
			copyDirectory(source, dest);
		} else {
			copyFile(source, dest);
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Move a file or directory.
	 * 
	 * @param source
	 *           file/directory to move
	 * @param dest
	 *           file/directory
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static void move(final File source, final File dest) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(source, dest));
		if (null == source) {
			throw new RuntimeExceptionIsNull("source"); //$NON-NLS-1$
		}
		if (null == dest) {
			throw new RuntimeExceptionIsNull("dest"); //$NON-NLS-1$
		}
		if (HelperObject.isEquals(source, dest)) {
			throw new RuntimeExceptionIsEquals("source", "dest"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		if (source.isDirectory()) {
			copyDirectory(source, dest);
		} else {
			copyFile(source, dest);
		}
		delete(source);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Delete files or directories.
	 * 
	 * @param files
	 *           to delete (files/directories)
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static void delete(final File... files) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(new Object[]{files}));
		if (null == files) {
			throw new RuntimeExceptionIsNull("files"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(files)) {
			throw new RuntimeExceptionIsEmpty("files"); //$NON-NLS-1$
		}
		
		for (final File target : files) {
			if (null == target) {
				throw new RuntimeExceptionIsNull("target"); //$NON-NLS-1$
			}
			if (target.isDirectory()) {
				final File[] childFiles = target.listFiles();
				for (final File child : childFiles) {
					delete(child);
				}
			}
			target.delete();
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Rename a file or directory.
	 * 
	 * @param source
	 *           file/directory to rename
	 * @param dest
	 *           file/directory
	 * @return true/false
	 * @see File
	 * @since 0.0.1
	 */
	public static boolean rename(final File source, final File dest) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(source, dest));
		if (null == source) {
			throw new RuntimeExceptionIsNull("source"); //$NON-NLS-1$
		}
		if (null == dest) {
			throw new RuntimeExceptionIsNull("dest"); //$NON-NLS-1$
		}
		if (HelperObject.isEquals(source, dest)) {
			throw new RuntimeExceptionIsEquals("source", "dest"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		final boolean result = source.renameTo(dest);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Writes a text line in a {@link File} with the chosen encoding.
	 * 
	 * @param file
	 *           for writing
	 * @param encoding
	 *           of the {@link File}
	 * @param line
	 *           containing the text to write
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static void writeLine(final File file, final String encoding, final String line) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, encoding, line));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		if (null == encoding) {
			throw new RuntimeExceptionIsNull("encoding"); //$NON-NLS-1$
		}
		if (!HelperString.isValid(encoding)) {
			throw new RuntimeExceptionIsEmpty("encoding"); //$NON-NLS-1$
		}
		if (null == line) {
			throw new RuntimeExceptionIsNull("line"); //$NON-NLS-1$
		}

		try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), encoding))) {
			pw.println(line);
			pw.flush();
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Writes a text line in a {@link File} with the default encoding (UTF-8).
	 * 
	 * @param file
	 *           for writing
	 * @param line
	 *           containing the text to write
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static void writeLine(final File file, final String line) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, line));
		writeLine(file, Constants.ENCODING_DEFAULT, line);
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Writes a byte-array into a new {@link File}.
	 * 
	 * @param file
	 *           for writing
	 * @param data
	 *           byte-array to write
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static void writeFile(final File file, final byte... data) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, data));
		writeFile(file, data, false);
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Writes a byte-array into a {@link File}.
	 * 
	 * @param file
	 *           for writing
	 * @param data
	 *           byte-array to write
	 * @param append
	 *           to {@link File}?
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static void writeFile(final File file, final byte[] data, final boolean append) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, data, append));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		if (null == data) {
			throw new RuntimeExceptionIsNull("data"); //$NON-NLS-1$
		}

		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file, append))) {
			bos.write(data);
			bos.flush();
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Writes a {@link String} into a new {@link File} with the chosen encoding.
	 * 
	 * @param file
	 *           for writing
	 * @param data
	 *           {@link String} to write
	 * @param encoding
	 *           of the {@link File}
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static void writeFile(final File file, final String data, final String encoding) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, data));
		writeFile(file, data, encoding, false);
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Writes a {@link String} into a {@link File} with the chosen encoding.
	 * 
	 * @param file
	 *           for writing
	 * @param data
	 *           {@link String} to write
	 * @param encoding
	 *           of the {@link File}
	 * @param append
	 *           to {@link File}?
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static void writeFile(final File file, final String data, final String encoding, final boolean append)
			throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, data, append));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		if (null == encoding) {
			throw new RuntimeExceptionIsNull("encoding"); //$NON-NLS-1$
		}
		if (!HelperString.isValid(encoding)) {
			throw new RuntimeExceptionIsEmpty("encoding"); //$NON-NLS-1$
		}
		if (null == data) {
			throw new RuntimeExceptionIsNull("data"); //$NON-NLS-1$
		}

		try (Writer writer = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file, append)), encoding)){
			if (append) {
				writer.append(data);
			} else {
				writer.write(data);
			}
			writer.flush();
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Writes a {@link String} into a new {@link File} with the default encoding
	 * (UTF-8).
	 * 
	 * @param file
	 *           for writing
	 * @param data
	 *           {@link String} to write
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static void writeFile(final File file, final String data) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, data));
		writeFile(file, data, Constants.ENCODING_DEFAULT, false);
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Writes a {@link String} into a {@link File} with the default encoding
	 * (UTF-8).
	 * 
	 * @param file
	 *           for writing
	 * @param data
	 *           {@link String} to write
	 * @param append
	 *           to {@link File}?
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static void writeFile(final File file, final String data, final boolean append) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, data, append));
		writeFile(file, data, Constants.ENCODING_DEFAULT, append);
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Writes an {@link InputStream} into a new {@link File}.
	 * 
	 * @param file
	 *           for writing
	 * @param is
	 *           {@link InputStream} to write
	 * @throws IOException
	 * @see File
	 * @see InputStream
	 * @since 0.0.1
	 */
	public static void writeFile(final File file, final InputStream is) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, is));
		writeFile(file, readStream(is), false);
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Writes an {@link InputStream} into a {@link File}.
	 * 
	 * @param file
	 *           for writing
	 * @param is
	 *           {@link InputStream} to write
	 * @param append
	 *           to {@link File}?
	 * @throws IOException
	 * @see File
	 * @see InputStream
	 * @since 0.0.1
	 */
	public static void writeFile(final File file, final InputStream is, final boolean append) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, is, append));
		writeFile(file, readStream(is), append);
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Writes a byte array to an {@link OutputStream}.
	 * 
	 * @param os
	 *           {@link OutputStream} for writing
	 * @param data
	 *           byte-array for the {@link OutputStream}
	 * @throws IOException
	 * @see OutputStream
	 * @since 0.0.1
	 */
	public static void writeStream(final OutputStream os, final byte... data) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(os, data));
		if (null == os) {
			throw new RuntimeExceptionIsNull("os"); //$NON-NLS-1$
		}
		if (null == data) {
			throw new RuntimeExceptionIsNull("data"); //$NON-NLS-1$
		}

		os.write(data);
		os.flush();
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Reads an {@link InputStream} in a byte-array.
	 * 
	 * @param is
	 *           {@link InputStream} for reading
	 * @return byte-array containing the {@link InputStream} content
	 * @throws IOException
	 * @see InputStream
	 * @since 0.0.1
	 */
	public static byte[] readStream(final InputStream is) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(is));

		final byte[] result = readStream(is, Constants.DEFAULT_FILE_BUFFER_SIZE);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Reads an {@link InputStream} in a byte-array.
	 * 
	 * @param is
	 *           {@link InputStream} for reading
	 * @param bufferSize
	 *           in bytes
	 * @return byte-array containing the {@link InputStream} content
	 * @throws IOException
	 * @see InputStream
	 * @since 0.0.1
	 */
	public static byte[] readStream(final InputStream is, final int bufferSize) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(is, bufferSize));
		if (null == is) {
			throw new RuntimeExceptionIsNull("is"); //$NON-NLS-1$
		}
		if (1 > bufferSize) {
			throw new RuntimeExceptionMustBeGreater("bufferSize", bufferSize, 1); //$NON-NLS-1$
		}
		if (bufferSize > HelperEnvironment.getMemoryFree()) {
			throw new RuntimeExceptionExceedsVmMemory("bufferSize", bufferSize); //$NON-NLS-1$
		}

		final byte[] buffer = new byte[bufferSize];

		final byte[] result;

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			int x;

			while (-1 != (x = is.read(buffer, 0, bufferSize))) {
				baos.write(buffer, 0, x);
			}
			baos.flush();

			result = baos.toByteArray();
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Reads a {@link File} in a byte-array.
	 * 
	 * @param file
	 *           for reading
	 * @return byte-array containing the {@link File} content
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static byte[] readFile(final File file) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		// if (!file.isFile()) {
		//			throw new IllegalArgumentException("file is not a file: " + file); //$NON-NLS-1$
		// }

		final long length = file.length();

		if (Integer.MAX_VALUE < length) {
			throw new IllegalArgumentException(
					"length of file (" + length + ") is to large to process (" + Integer.MAX_VALUE + ')'); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (length > HelperEnvironment.getMemoryFree()) {
			throw new RuntimeExceptionExceedsVmMemory("file", file.length()); //$NON-NLS-1$
		}

		final byte[] result;

		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))){
			result = new byte[(int) length];
			bis.read(result, 0, (int) length);
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Reads a {@link File} in a {@link String} with the chosen encoding.
	 * 
	 * @param file
	 *           for reading
	 * @param encoding
	 *           of the {@link File}
	 * @return {@link String} containing the file content
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static String readFileAsString(final File file, final String encoding) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, encoding));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		// if (!file.isFile()) {
		//			throw new IllegalArgumentException("file is not a file: " + file); //$NON-NLS-1$
		// }
		if (null == encoding) {
			throw new RuntimeExceptionIsNull("encoding"); //$NON-NLS-1$
		}
		if (!HelperString.isValid(encoding)) {
			throw new RuntimeExceptionIsEmpty("encoding"); //$NON-NLS-1$
		}

		final long length = file.length();

		if (length > HelperEnvironment.getMemoryFree()) {
			throw new RuntimeExceptionExceedsVmMemory("file", file.length()); //$NON-NLS-1$
		}

		final StringBuilder sb = new StringBuilder();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding))) {
			String line = null;

			while (null != (line = br.readLine())) {
				if (0 < sb.length()) {
					sb.append(HelperString.NEW_LINE);
				}
				sb.append(line);
			}

			final String result = sb.toString();

			if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
			return result;
		}
	}

	/**
	 * Reads a {@link File} in a {@link String} with the default encoding
	 * (UTF-8).
	 * 
	 * @param file
	 *           for reading
	 * @return {@link String} containing the {@link File} content
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static String readFileAsString(final File file) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));

		final String result = readFileAsString(file, Constants.ENCODING_DEFAULT);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Reads a {@link File} in a {@link List} with the chosen encoding.
	 * 
	 * @param file
	 *           for reading
	 * @param encoding
	 *           of the {@link File}
	 * @return {@link List} containing the {@link File} content
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static List<String> readFileAsList(final File file, final String encoding) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, encoding));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		// if (!file.isFile()) {
		//			throw new IllegalArgumentException("file is not a file: " + file); //$NON-NLS-1$
		// }
		if (null == encoding) {
			throw new RuntimeExceptionIsNull("encoding"); //$NON-NLS-1$
		}
		if (!HelperString.isValid(encoding)) {
			throw new RuntimeExceptionIsEmpty("encoding"); //$NON-NLS-1$
		}
		if (file.length() > HelperEnvironment.getMemoryFree()) {
			throw new RuntimeExceptionExceedsVmMemory("file", file.length()); //$NON-NLS-1$
		}

		final List<String> result = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding))) {
			String line = null;

			while (null != (line = br.readLine())) {
				result.add(line);
			}

			if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
			return result;
		}
	}

	/**
	 * Reads a {@link File} in a {@link List} with the default encoding (UTF-8).
	 * 
	 * @param file
	 *           for reading
	 * @return {@link List} containing the {@link File} content
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static List<String> readFileAsList(final File file) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));

		final List<String> result = readFileAsList(file, Constants.ENCODING_DEFAULT);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Reads a {@link File} into an {@link OutputStream}.
	 * 
	 * @param file
	 *           for reading
	 * @param os
	 *           {@link OutputStream} for the {@link File} content
	 * @throws IOException
	 * @see File
	 * @see OutputStream
	 * @since 0.0.1
	 */
	public static void readFileAsStream(final File file, final OutputStream os) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, os));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		// if (!file.isFile()) {
		//			throw new IllegalArgumentException("file is not a file: " + file); //$NON-NLS-1$
		// }
		if (null == os) {
			throw new RuntimeExceptionIsNull("os"); //$NON-NLS-1$
		}

		writeStream(os, readFile(file));
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Concatenates many files to one output {@link File}.
	 * 
	 * @param fileOutput
	 *           Output {@link File}
	 * @param files
	 *           to concatenate
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static void concatenateFiles(final File fileOutput, final File... files) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(fileOutput, files));
		if (null == fileOutput) {
			throw new RuntimeExceptionIsNull("fileOutput"); //$NON-NLS-1$
		}
		if (null == files) {
			throw new RuntimeExceptionIsNull("files"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(files)) {
			throw new RuntimeExceptionIsEmpty("files"); //$NON-NLS-1$
		}

		try (PrintWriter pw = new PrintWriter(new FileOutputStream(fileOutput))) { //TODO PrintWriter correct?

			// Process all files that are not the destination file
			for (final File file : files) {
				if (null == file) {
					throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
				}
				if (file.isFile()) {
					try (BufferedReader br = new BufferedReader(new FileReader(file))) {
						// Read each line from the input file
						String line = br.readLine();

						while (null != line) {
							pw.println(line);
							line = br.readLine();
						}
					}
				}
			}
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Returns the {@link URL} representation of a given {@link File}.
	 * 
	 * @param file
	 *           to get the URL
	 * @return {@link URL} representation of a given {@link File}
	 * @throws MalformedURLException
	 * @see File
	 * @see URL
	 * @since 0.0.1
	 */
	public static URL getURL(final File file) throws MalformedURLException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}

		final URL result = file.toURI().toURL();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns a {@link List} containing all drive names of the current system.
	 * 
	 * @return {@link List} containing all drive names of the current system
	 * @since 0.0.1
	 */
	public static List<String> getDriveNames() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		final List<String> result = new ArrayList<>(getAvailableDrives().size());
		final FileSystemView view = FileSystemView.getFileSystemView();

		for (final File file : getAvailableDrives()) {
			result.add(view.getSystemDisplayName(file));
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns a {@link List} containing all available drives of the current
	 * system.
	 * 
	 * @return {@link List} containing all drive names of the current system
	 * @see File
	 * @since 0.0.1
	 */
	public static List<File> getAvailableDrives() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		final List<File> result = Arrays.asList(File.listRoots());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	// /**
	// * Returns all drive names of the current system.
	// *
	// * @return list containing all drive names of the current system
	// */
	// public static Collection<String> getAvailable() {
	// return Charset.availableCharsets();
	// }

	/**
	 * Returns the total space of a given {@link File} root location in bytes.
	 * 
	 * @param file
	 *           location
	 * @return total space in bytes
	 * @see File
	 * @since 0.0.1
	 */
	public static long getSpaceTotal(final File file) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}

		final long result = file.getTotalSpace();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns the free space of a given {@link File} root location in bytes.
	 * 
	 * @param file
	 *           location
	 * @return free space in bytes
	 * @see File
	 * @since 0.0.1
	 */
	public static long getSpaceFree(final File file) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}

		final long result = file.getFreeSpace();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns the usable space of a given {@link File} root location in bytes.
	 * 
	 * @param file
	 *           location
	 * @return usable space in bytes
	 * @see File
	 * @since 0.0.1
	 */
	public static long getSpaceUsable(final File file) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}

		final long result = file.getUsableSpace();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns the used space of a given {@link File} root location in bytes.
	 * 
	 * @param file
	 *           location
	 * @return used space in bytes
	 * @see File
	 * @since 0.0.1
	 */
	public static long getSpaceUsed(final File file) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));

		final long result = getSpaceTotal(file) - getSpaceFree(file);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Checks a given {@link File} if it is a drive.
	 * 
	 * @param file
	 *           location
	 * @return true/false
	 * @see File
	 * @since 0.0.1
	 */
	public static boolean isDrive(final File file) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}

		final boolean result = FileSystemView.getFileSystemView().isDrive(file);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Checks a given {@link File} if it is a removable drive.
	 * 
	 * @param file
	 *           location
	 * @return true/false
	 * @see File
	 * @since 0.0.1
	 */
	public static boolean isRemovableDrive(final File file) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}

		final boolean result = FileSystemView.getFileSystemView().isFloppyDrive(file);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Checks a given {@link File} if it is a network drive.
	 * 
	 * @param file
	 *           location
	 * @return true/false
	 * @see File
	 * @since 0.0.1
	 */
	public static boolean isNetworkDrive(final File file) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}

		final boolean result = FileSystemView.getFileSystemView().isComputerNode(file);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Converts a given {@link ByteArrayOutputStream} to an {@link InputStream}.
	 * 
	 * @param baos
	 *           byte-array output stream
	 * @return {@link InputStream}
	 * @see ByteArrayOutputStream
	 * @see InputStream
	 * @since 0.0.1
	 */
	public static InputStream convertOutputToInputStream(final ByteArrayOutputStream baos) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(baos));
		if (null == baos) {
			throw new RuntimeExceptionIsNull("baos"); //$NON-NLS-1$
		}

		final InputStream result = new ByteArrayInputStream(baos.toByteArray());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Converts a given {@link Writer} to a {@link Reader}.
	 * 
	 * @param writer
	 *           to convert
	 * @return {@link Reader} containing the data of the {@link Writer}
	 * @see Writer
	 * @see Reader
	 * @since 0.0.1
	 */
	public static Reader convertWriterToReader(final Writer writer) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(writer));
		if (null == writer) {
			throw new RuntimeExceptionIsNull("writer"); //$NON-NLS-1$
		}

		final Reader result = new StringReader(writer.toString());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Searchs in a path (directory) for files and directories via
	 * {@link FileFilter} and returns a {@link List} containing all {@link File}.
	 * 
	 * @param path
	 *           for searching
	 * @param filter
	 *           for the match criterias. No filter (== null) will return all
	 *           files.
	 * @param recurseDepth
	 *           defines how many folder levels the recursion would pass. >= -1
	 *           always recurse, 0 only the current folder and any other value
	 *           will continue recursion until 0 is hit.
	 * @return {@link List} containing the matched files
	 * @see File
	 * @see FileFilter
	 * @since 0.0.1
	 */
	public static List<File> getFiles(final File path, final FileFilter filter, final int recurseDepth) { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(path, filter, recurseDepth));
		if (null == path) {
			throw new RuntimeExceptionIsNull("path"); //$NON-NLS-1$
		}

		final List<File> result = new ArrayList<>();
		final File[] entries = path.listFiles();
		int recurse = recurseDepth;

		if (null != entries) {
			for (final File entry : entries) {

				if (null == filter || filter.accept(entry)) {
					result.add(entry);
				}

				if (-1 >= recurse || (0 < recurse && entry.isDirectory())) {
					recurse--;
					result.addAll(getFiles(entry, filter, recurse));
					recurse++;
				}
			}
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Searchs in a path (directory) for files and directories via
	 * {@link FileFilter} and returns a {@link List} containing all {@link File}.
	 * 
	 * @param path
	 *           for searching
	 * @param filter
	 *           for the match criterias. No filter (== null) will return all
	 *           {@link File}
	 * @return {@link List} containing the matched files
	 * @see File
	 * @see FileFilter
	 * @since 0.0.1
	 */
	public static List<File> getFiles(final File path, final FileFilter filter) { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(path, filter));

		final List<File> result = getFiles(path, filter, -1);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Searchs in a path (directory) for all files and directories and returns a
	 * {@link List} containing all {@link File}.
	 * 
	 * @param path
	 *           for searching
	 * @return {@link List} containing the found {@link File}
	 * @see File
	 * @since 0.0.1
	 */
	public static List<File> getFiles(final File path) { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(path));

		final FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(final File file) {
				return true;
			}
		};

		final List<File> result = getFiles(path, filter, -1);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Get the file without extension from a given {@link String}.
	 * 
	 * @param fileName
	 *           to remove the extension
	 * @return {@link String} without extension
	 * @since 0.0.1
	 */
	public static String getFileWithoutExtension(final String fileName) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(fileName));
		if (null == fileName) {
			throw new RuntimeExceptionIsNull("fileName"); //$NON-NLS-1$
		}

		final String result = fileName.contains(HelperString.PERIOD) ? fileName
				.substring(0, fileName.lastIndexOf(HelperString.PERIOD)) : fileName;

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Get the file extension from a given {@link String}.
	 * 
	 * @param fileName
	 *           to get the extension
	 * @return {@link String} extension
	 * @since 0.0.1
	 */
	public static String getFileExtension(final String fileName) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(fileName));
		if (null == fileName) {
			throw new RuntimeExceptionIsNull("fileName"); //$NON-NLS-1$
		}

		final String result;

		result = fileName.contains(HelperString.PERIOD) ? fileName.substring(fileName.lastIndexOf(HelperString.PERIOD))
				: fileName;

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
	
	/**
	 * Returns the number of files in a path.
	 * 
	 * @param path
	 *           for searching
	 * @return number of files
	 * @see File
	 * @since 0.0.1
	 */
	public static int getNumberOfFiles(final File path) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(path));
		if (null == path) {
			throw new RuntimeExceptionIsNull("path"); //$NON-NLS-1$
		}

		final FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(final File file) {
				return !file.isDirectory();
			}
		};

		final int result = getFiles(path, filter).size();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
	
	/**
	 * Returns the number of directories in a path.
	 * 
	 * @param path
	 *           for searching
	 * @return number of directories
	 * @see File
	 * @since 0.0.1
	 */
	public static int getNumberOfDirectories(final File path) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(path));
		if (null == path) {
			throw new RuntimeExceptionIsNull("path"); //$NON-NLS-1$
		}

		final FileFilter filter = new FileFilter() {
			@Override
			public boolean accept(final File file) {
				return file.isDirectory();
			}
		};

		final int result = getFiles(path, filter).size();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns the used space of a given {@link File} path in bytes.
	 * 
	 * @param path  Desired path
	 * @return used space in bytes
	 * @see File
	 * @since 0.0.1
	 */
	public static long getSpaceUsedInPath(final File path) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(path));
		if (null == path) {
			throw new RuntimeExceptionIsNull("path"); //$NON-NLS-1$
		}

//		final FileFilter filter = new FileFilter() {
//			@Override
//			public boolean accept(final File file) {
//				return !file.isDirectory();
//			}
//		};
		
		long result = 0L;
		
//		List<File> list = HelperIO.getFiles(new File(path), filter);
		final List<File> list = getFiles(path);
		
		for (final File tempFile : list) {
			result += tempFile.length();
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
	
	/**
	 * Returns the path of a given file.
	 * 
	 * @param file
	 * @return path of the given file
	 * @see File
	 * @since 0.0.1
	 */
	public static String getPath(final File file) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}

		String result = file.getAbsolutePath();
		
		if (file.isFile()) {
			result = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length());
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
	
	/**
	 * Returns the path of a given file and name.
	 * 
	 * @param file
	 * @param name
	 * @return path of the given file and name
	 * @see File
	 * @since 0.0.1
	 */
	public static String getPath(final File file, final String name) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		if (null == name) {
			throw new RuntimeExceptionIsNull("name"); //$NON-NLS-1$
		}

		final String result = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - name.length());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}	
	
	
	/*
	 * Private methods
	 */
	
	/**
	 * Copy a directory.
	 * 
	 * @param source
	 *           directory to copy
	 * @param dest
	 *           directory destination
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	private static void copyDirectory(final File source, final File dest) throws IOException {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart(source, dest));

		if (!dest.exists()) {
			dest.mkdir();
		}

		final File[] children = source.listFiles();

		for (final File sourceChild : children) {
			final String name = sourceChild.getName();
			final File destChild = new File(dest, name);

			if (sourceChild.isDirectory()) {
				copyDirectory(sourceChild, destChild);
			} else {
				copyFile(sourceChild, destChild);
			}
		}
		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}
	
	/**
	 * Copy a {@link File}.
	 * 
	 * @param source
	 *           {@link File} to copy
	 * @param dest
	 *           {@link File} destination
	 * @param bufferSize
	 *           in bytes
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	private static void copyFile(final File source, final File dest) throws IOException {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart(source, dest));

		if (!dest.exists()) {
			dest.mkdirs();
			dest.delete();
			dest.createNewFile();
		}

		try (InputStream is = new FileInputStream(source);
				OutputStream os = new FileOutputStream(dest)) {
			int len;

			final byte[] buffer = new byte[Constants.DEFAULT_FILE_BUFFER_SIZE];

			while (0 < (len = is.read(buffer))) {
				os.write(buffer, 0, len);
			}
			os.flush();
		}
		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}
}
