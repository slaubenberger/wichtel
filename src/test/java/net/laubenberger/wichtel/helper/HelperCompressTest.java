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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.resource.Resource;
import net.laubenberger.wichtel.misc.resource.ResourceImage;
import net.laubenberger.wichtel.misc.resource.ResourceMisc;
import net.laubenberger.wichtel.misc.resource.ResourceOffice;
import net.laubenberger.wichtel.misc.resource.ResourceSound;

import org.junit.Test;

/**
 * JUnit test for {@link HelperCompress}
 * 
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 */
public class HelperCompressTest {

	private File fileZip;
	
	private final List<File> files = new ArrayList<>();
	private File fileZipExtractDir;

	{
		try {
			fileZip = HelperIO.getTemporaryFile("test", "zip"); //$NON-NLS-1$ //$NON-NLS-2$
			
			for (final Resource resource : ResourceImage.values()) {
				files.add(resource.getResourceAsFile());
			}
			for (final Resource resource : ResourceMisc.values()) {
				files.add(resource.getResourceAsFile());
			}
			for (final Resource resource : ResourceOffice.values()) {
				files.add(resource.getResourceAsFile());
			}
			for (final Resource resource : ResourceSound.values()) {
				files.add(resource.getResourceAsFile());
			}
			
			fileZipExtractDir = new File(HelperEnvironment.getOsTempDirectory(), "HelperCompressTest_extract"); //$NON-NLS-1$
			if (fileZipExtractDir.exists()) {
				HelperIO.delete(fileZipExtractDir);
			}
			fileZipExtractDir.mkdir();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testPassWriteAndExtractZip() {

		try {
			HelperCompress.writeZip(fileZip, HelperCollection.toArray(files));
		} catch (IOException ex) {
			fail(ex.getMessage());
		}

		try {
			HelperCompress.extractZip(fileZip, fileZipExtractDir);
		} catch (IOException ex) {
			fail(ex.getMessage());
		}

		for (final File file : files) {
			assertEquals(file.length(), new File(fileZipExtractDir + HelperEnvironment.getOsTempDirectory().getAbsolutePath(), file.getName()).length());
		}
	}

	@Test
	public void testFailWriteZip() {

		try {
			HelperCompress.writeZip(null, HelperCollection.toArray(files));
			fail("file is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	
		try {
			HelperCompress.writeZip(fileZip, null);
			fail("files is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperCompress.writeZip(fileZip, new File[0]);
			fail("files is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
//		try {
//			HelperCompress.writeZip(fileZip, HelperIO.getTemporaryFile(), null);
//			fail("entry is null"); //$NON-NLS-1$
//		} catch (RuntimeExceptionIsNull ex) {
//			// nothing to do
//		} catch (Exception ex) {
//			fail(ex.getMessage());
//		}
		
//		try {
//			HelperCompress.writeZip(fileZip, new File("file1"), new File("file2"));
//			fail("entry does not exist"); //$NON-NLS-1$
//		} catch (IllegalArgumentException ex) {
//			// nothing to do
//		} catch (Exception ex) {
//			fail(ex.getMessage());
//		}
	}

	@Test
	public void testFailExtractZip() {

		try {
			HelperCompress.extractZip(null, fileZipExtractDir);
			fail("file is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperCompress.extractZip(fileZip, null);
			fail("destinationDirectory is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}
