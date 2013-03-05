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

package net.laubenberger.wichtel.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEquals;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.resource.Resource;
import net.laubenberger.wichtel.misc.resource.ResourceImage;
import net.laubenberger.wichtel.misc.resource.ResourceMisc;
import net.laubenberger.wichtel.misc.resource.ResourceOffice;

import org.apache.poi.hslf.usermodel.SlideShow;
import org.junit.Test;

import com.lowagie.text.PageSize;
import com.lowagie.text.exceptions.BadPasswordException;
import com.lowagie.text.pdf.PdfReader;


/**
 * JUnit test for {@link HelperPdf}
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 */
public class HelperPdfTest {
	private static final byte[] USER = "admin".getBytes(); //$NON-NLS-1$
	private static final byte[] PASSWORD = "1234".getBytes(); //$NON-NLS-1$
	
	@Test
	public void testWritePdfFromImages() {
		final List<File> files = new ArrayList<File>(ResourceImage.values().length);
		final List<Image> images = new ArrayList<Image>(ResourceImage.values().length);
		
		for (final Resource res : ResourceImage.values()) {
			files.add(res.getResourceAsFile());
			try {
				images.add(HelperImage.readImage(res.getResourceAsFile()));
			} catch (IOException ex) {
				fail(ex.getMessage());
			}
		}
		
		File pdf = null;
		try {
			pdf = HelperIO.getTemporaryFile("pdf"); //$NON-NLS-1$
			
			HelperPdf.writePdfFromImages(PageSize.A4, true, pdf, HelperCollection.toArray(files));
			assertTrue(9000 < pdf.length());

			HelperPdf.writePdfFromImages(PageSize.A4, true, pdf, HelperCollection.toArray(images));
			assertTrue(9000 < pdf.length());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.writePdfFromImages(null, true, pdf, HelperCollection.toArray(files));
			fail("pageSize is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.writePdfFromImages(PageSize.A4, true, null, HelperCollection.toArray(files));
			fail("file is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}		
		
		try {
			HelperPdf.writePdfFromImages(PageSize.A4, true, pdf, (File[])null);
			fail("files is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.writePdfFromImages(PageSize.A4, true, pdf, new File[0]);
			fail("files is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}	
		
		try {
			HelperPdf.writePdfFromImages(null, true, pdf, HelperCollection.toArray(images));
			fail("pageSize is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.writePdfFromImages(PageSize.A4, true, null, HelperCollection.toArray(images));
			fail("file is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}		
		
		try {
			HelperPdf.writePdfFromImages(PageSize.A4, true, pdf, (Image[])null);
			fail("images is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.writePdfFromImages(PageSize.A4, true, pdf, new Image[0]);
			fail("images is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}	
	}
	
	@Test
	public void testWritePdfFromPpt() {
		
		File pdf = null;
		try {
			pdf = HelperIO.getTemporaryFile("pdf"); //$NON-NLS-1$
			
			HelperPdf.writePdfFromPpt(PageSize.A4, true, pdf, ResourceOffice.PPT.getResourceAsFile());
			assertTrue(4000 < pdf.length());
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.writePdfFromPpt(null, true, pdf, ResourceOffice.PPT.getResourceAsFile());
			fail("pageSize is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.writePdfFromPpt(PageSize.A4, true, null, ResourceOffice.PPT.getResourceAsFile());
			fail("file is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}		
		
		try {
			HelperPdf.writePdfFromPpt(PageSize.A4, true, pdf, (File)null);
			fail("pptFile is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.writePdfFromPpt(PageSize.A4, true, pdf, (SlideShow)null);
			fail("ppt is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testGetText() {
		try {
			assertEquals("Hello world!\nThis is a „wichtel“  PDF  test file.\n", HelperPdf.getText(ResourceMisc.PDF.getResourceAsFile())); //$NON-NLS-1$
		} catch (IOException ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.getText(null);
			fail("file is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testSetAndGetMetaData() {
		final Map<String, String> metadata = new HashMap<String, String>();
		metadata.put("myInfo", "This is my additional info"); //$NON-NLS-1$ //$NON-NLS-2$
		metadata.put("myMetaData", "This is my additional metadata"); //$NON-NLS-1$ //$NON-NLS-2$
		
		File dest = null;
		
		try {
			dest = HelperIO.getTemporaryFile("pdf"); //$NON-NLS-1$

			final Map<String, String> old_metadata = HelperPdf.getMetaData(ResourceMisc.PDF.getResourceAsFile());
//			System.err.println(HelperMap.dump(old_metadata));

			
			HelperPdf.setMetaData(ResourceMisc.PDF.getResourceAsFile(), dest, metadata);
			
			final Map<String, String> new_metadata = HelperPdf.getMetaData(dest);
//			System.err.println(HelperMap.dump(new_metadata));

			assertEquals(old_metadata.size() + 3, new_metadata.size()); // +3 is composed of 2 new entries and the ModDate (automatically added by iTest)
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			final Map<String, String> old_metadata = HelperPdf.getMetaData(ResourceMisc.PDF.getResourceAsFile());

			HelperPdf.setMetaData(ResourceMisc.PDF.getResourceAsFile(), dest, new HashMap<String, String>());
			
			final Map<String, String> new_metadata = HelperPdf.getMetaData(dest);

			assertEquals(old_metadata.size() + 1, new_metadata.size()); // + 1 because of the ModDate (automatically added by iTest)
		} catch (Exception ex) {
			fail(ex.getMessage());
		}

		try {
			HelperPdf.setMetaData(null, dest, metadata);
			fail("source is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.setMetaData(ResourceMisc.PDF.getResourceAsFile(), null, metadata);
			fail("dest is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.setMetaData(ResourceMisc.PDF.getResourceAsFile(), ResourceMisc.PDF.getResourceAsFile(), metadata);
			fail("source is equals dest"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEquals ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.setMetaData(ResourceMisc.PDF.getResourceAsFile(), dest, null);
			fail("metadata is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.getMetaData(null);
			fail("file is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testEncryptAndDecrypt() {
		File encrypted = null;
		File decrypted = null;
		
		try {
			encrypted = HelperIO.getTemporaryFile("pdf"); //$NON-NLS-1$
			decrypted = HelperIO.getTemporaryFile("pdf"); //$NON-NLS-1$

			HelperPdf.encrypt(ResourceMisc.PDF.getResourceAsFile(), encrypted, USER, PASSWORD);
			
			try {
				HelperPdf.getText(encrypted);
				fail("file is encrypted"); //$NON-NLS-1$
			} catch (BadPasswordException ex) {
				//nothing to do
			}
			
			HelperPdf.decrypt(encrypted, decrypted, PASSWORD);
			
			assertEquals("Hello world!\nThis is a „wichtel“  PDF  test file.\n", HelperPdf.getText(decrypted)); //$NON-NLS-1$
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.encrypt(null, encrypted, USER, PASSWORD);
			fail("source is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.encrypt(ResourceMisc.PDF.getResourceAsFile(), null, USER, PASSWORD);
			fail("dest is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.encrypt(ResourceMisc.PDF.getResourceAsFile(), ResourceMisc.PDF.getResourceAsFile(), USER, PASSWORD);
			fail("source is equals dest"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEquals ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.encrypt(ResourceMisc.PDF.getResourceAsFile(), encrypted, null, PASSWORD);
			fail("user is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.encrypt(ResourceMisc.PDF.getResourceAsFile(), encrypted, HelperArray.EMPTY_ARRAY_BYTE, PASSWORD);
			fail("user is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.encrypt(ResourceMisc.PDF.getResourceAsFile(), encrypted, USER, null);
			fail("password is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.encrypt(ResourceMisc.PDF.getResourceAsFile(), encrypted, USER, HelperArray.EMPTY_ARRAY_BYTE);
			fail("password is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.decrypt(null, encrypted, PASSWORD);
			fail("source is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.decrypt(ResourceMisc.PDF.getResourceAsFile(), null, PASSWORD);
			fail("dest is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.decrypt(ResourceMisc.PDF.getResourceAsFile(), ResourceMisc.PDF.getResourceAsFile(), PASSWORD);
			fail("source is equals dest"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEquals ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.decrypt(ResourceMisc.PDF.getResourceAsFile(), encrypted, null);
			fail("password is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.decrypt(ResourceMisc.PDF.getResourceAsFile(), encrypted, HelperArray.EMPTY_ARRAY_BYTE);
			fail("password is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
	@Test
	public void testLockAndUnlock() {
		File locked = null;
		File unlocked = null;
		PdfReader reader = null;
		
		try {
			locked = HelperIO.getTemporaryFile("pdf"); //$NON-NLS-1$
			unlocked = HelperIO.getTemporaryFile("pdf"); //$NON-NLS-1$

			HelperPdf.lock(ResourceMisc.PDF.getResourceAsFile(), locked, PASSWORD);
			
			reader = new PdfReader(locked.getAbsolutePath());
			assertEquals(-3904, reader.getPermissions());

			HelperPdf.unlock(locked, unlocked, PASSWORD);
			
			reader = new PdfReader(unlocked.getAbsolutePath());
			assertEquals(-4, reader.getPermissions());
		} catch (Exception ex) {
			ex.printStackTrace();
			fail(ex.getMessage());
		} finally {
			if (null != reader) {
				reader.close();
			}
		}
		
		try {
			HelperPdf.lock(null, locked, PASSWORD);
			fail("source is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.lock(ResourceMisc.PDF.getResourceAsFile(), null, PASSWORD);
			fail("dest is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.lock(ResourceMisc.PDF.getResourceAsFile(), ResourceMisc.PDF.getResourceAsFile(), PASSWORD);
			fail("source is equals dest"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEquals ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.lock(ResourceMisc.PDF.getResourceAsFile(), locked, null);
			fail("password is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.lock(ResourceMisc.PDF.getResourceAsFile(), locked, HelperArray.EMPTY_ARRAY_BYTE);
			fail("password is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.unlock(null, locked, PASSWORD);
			fail("source is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.unlock(ResourceMisc.PDF.getResourceAsFile(), null, PASSWORD);
			fail("dest is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.unlock(ResourceMisc.PDF.getResourceAsFile(), ResourceMisc.PDF.getResourceAsFile(), PASSWORD);
			fail("source is equals dest"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEquals ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.unlock(ResourceMisc.PDF.getResourceAsFile(), locked, null);
			fail("password is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperPdf.unlock(ResourceMisc.PDF.getResourceAsFile(), locked, HelperArray.EMPTY_ARRAY_BYTE);
			fail("password is empty"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsEmpty ex) {
			//nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
	
//	@Test
//	public void testWritePdfFromHTML() {
//		try {
//			File file = HelperIO.getTemporaryFile("test", "html");
//			HelperIO.writeFile(file, getClass().getResourceAsStream(HelperResource.RES_FILE_HTML));
//			HelperPdf.writePdfFromHTML(new File("html.pdf"), file);
//		} catch (Exception ex) {
//			ex.printStackTrace();
////			fail(ex.getMessage());
//		}
//		System.err.println("muh");
//		try {
//			HelperPdf.writePdfFromHTML(HelperIO.getTemporaryFile("wichtel_" + getClass().getSimpleName(), ".pdf"), null); //$NON-NLS-1$ //$NON-NLS-2$
//			fail("input is null!"); //$NON-NLS-1$
//		} catch (IllegalArgumentException ex) {
//			//nothing to do
//		} catch (Exception ex) {
//			fail(ex.getMessage());
//		}
//
//		try {
//			HelperPdf.writePdfFromHTML(null, HelperIO.getTemporaryFile("wichtel_" + getClass().getSimpleName(), ".html")); //$NON-NLS-1$ //$NON-NLS-2$
//			fail("file is null!"); //$NON-NLS-1$
//		} catch (IllegalArgumentException ex) {
//			//nothing to do
//		} catch (Exception ex) {
//			fail(ex.getMessage());
//		}
//	}
}