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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for compress operations.
 * 
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public abstract class HelperCompress {
	private static final Logger log = LoggerFactory.getLogger(HelperCompress.class);

	/**
	 * Writes a ZIP {@link File} containing a list of {@link File}.
	 * 
	 * @param file
	 *           for writing
	 * @param files
	 *           for the ZIP file
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static void writeZip(final File file, final File... files) throws IOException { // $JUnit$
		if (log.isDebugEnabled())
			log.debug(HelperLog.methodStart(file, files));

		writeZip(file, files, Constants.DEFAULT_FILE_BUFFER_SIZE);

		if (log.isDebugEnabled())
			log.debug(HelperLog.methodExit());
	}

	/**
	 * Writes a ZIP {@link File} containing a list of {@link File}.
	 * 
	 * @param file
	 *           for writing
	 * @param files
	 *           for the ZIP file
	 * @param bufferSize
	 *           in bytes
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static void writeZip(final File file, final File[] files, final int bufferSize) throws IOException { // $JUnit$
		if (log.isDebugEnabled())
			log.debug(HelperLog.methodStart(file, files, bufferSize));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		if (null == files) {
			throw new RuntimeExceptionIsNull("files"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(files)) {
			throw new RuntimeExceptionIsEmpty("files"); //$NON-NLS-1$
		}
		if (1 > bufferSize) {
			throw new RuntimeExceptionMustBeGreater("bufferSize", bufferSize, 1); //$NON-NLS-1$
		}
		// //check all list entries
		// for (final File entry : files) {
		// if (null == entry) {
		//				throw new RuntimeExceptionIsNull("entry"); //$NON-NLS-1$
		// }
		// if (!entry.exists()) {
		//				throw new RuntimeExceptionFileNotExist("entry", entry); //$NON-NLS-1$
		// }
		// }

		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file))){
			// create a ZipOutputStream to zip the data to

			for (final File entry : files) {
				addEntry(zos, entry, bufferSize);
			}
		}
		if (log.isDebugEnabled())
			log.debug(HelperLog.methodExit());
	}

	/**
	 * Extracts a ZIP {@link File} to a destination directory.
	 * 
	 * @param file
	 *           to extract
	 * @param destinationDirectory
	 *           for the ZIP file
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static void extractZip(final File file, final File destinationDirectory) throws IOException { // $JUnit$
		if (log.isDebugEnabled())
			log.debug(HelperLog.methodStart(file, destinationDirectory));

		extractZip(file, destinationDirectory, Constants.DEFAULT_FILE_BUFFER_SIZE);

		if (log.isDebugEnabled())
			log.debug(HelperLog.methodExit());
	}

	/**
	 * Extracts a ZIP {@link File} to a destination directory.
	 * 
	 * @param file
	 *           to extract
	 * @param destinationDirectory
	 *           for the ZIP file
	 * @param bufferSize
	 *           in bytes
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static void extractZip(final File file, final File destinationDirectory, final int bufferSize)
			throws IOException { // $JUnit$
		if (log.isDebugEnabled())
			log.debug(HelperLog.methodStart(file, destinationDirectory, bufferSize));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		if (null == destinationDirectory) {
			throw new RuntimeExceptionIsNull("destinationDirectory"); //$NON-NLS-1$
		}
		if (1 > bufferSize) {
			throw new RuntimeExceptionMustBeGreater("bufferSize", bufferSize, 1); //$NON-NLS-1$
		}

		try (ZipFile zf = new ZipFile(file)) {
			final Enumeration<? extends ZipEntry> zipEntryEnum = zf.entries();

			while (zipEntryEnum.hasMoreElements()) {
				final ZipEntry zipEntry = zipEntryEnum.nextElement();
				extractEntry(zf, zipEntry, destinationDirectory, bufferSize);
			}
		}

		if (log.isDebugEnabled())
			log.debug(HelperLog.methodExit());
	}

	/*
	 * Private methods
	 */

	private static void addEntry(final ZipOutputStream zos, final File file, final int bufferSize) throws IOException {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart(zos, file, bufferSize));
		final byte[] buffer = new byte[bufferSize];

		// create a new zip entry
		final ZipEntry entry = new ZipEntry(file.getPath() + (file.isDirectory() ? "/" : HelperString.EMPTY_STRING)); //$NON-NLS-1$
		
		// place the zip entry in the ZipOutputStream object
		zos.putNextEntry(entry);

		if(!file.isDirectory()) {
			try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
				int offset;
	
				// now write the content of the file to the ZipOutputStream
				while (-1 != (offset = bis.read(buffer))) {
					zos.write(buffer, 0, offset);
				}
			}
		}
		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}

	private static void extractEntry(final ZipFile zipFile, final ZipEntry entry, final File destDir,
			final int bufferSize) throws IOException {
		if (log.isTraceEnabled())
			log.trace(HelperLog.methodStart(zipFile, entry, destDir, bufferSize));
		final File file = new File(destDir, entry.getName());

		if (entry.isDirectory()) {
			file.mkdirs();
		} else {
			new File(file.getParent()).mkdirs();

			final byte[] buffer = new byte[bufferSize];

			try (BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {

				int offset;
				while (-1 != (offset = bis.read(buffer))) {
					bos.write(buffer, 0, offset);
				}
			}
		}
		if (log.isTraceEnabled())
			log.trace(HelperLog.methodExit());
	}
}
