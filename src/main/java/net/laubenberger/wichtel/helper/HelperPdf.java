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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEquals;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfEncryptor;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.parser.PdfTextExtractor;

/**
 * Helper class for PDF (Portable Document Format) operations.
 * <p>
 * <strong>Note:</strong> This class needs <a
 * href="http://itextpdf.com/">iText</a> to work.
 * 
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public abstract class HelperPdf {
	private static final Logger log = LoggerFactory.getLogger(HelperPdf.class);

	private static final String IMAGE_TYPE = HelperImage.TYPE_PNG;

	//TODO write PDF from word/excel etc.
	
//	/**
//	 * Writes a PDF from multiple (X)HTML files to a {@link File}.
//	 * <strong>Note:</strong> This method needs <a
//	 * href="https://xhtmlrenderer.dev.java.net/">XHTML</a> to work.
//	 * 
//	 * @param file
//	 *           output as PDF
//	 * @param files
//	 *           in (X)HTML format for the PDF
//	 * @throws DocumentException
//	 * @throws IOException
//	 * @see File
//	 * @since 0.0.1
//	 */
//	public static void writePdfFromHTML(final File file, final File... files) throws IOException, DocumentException { // $JUnit$
//		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, files));
//		if (null == file) {
//			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
//		}
//		if (!HelperArray.isValid(files)) {
//			throw new RuntimeExceptionIsNullOrEmpty("files"); //$NON-NLS-1$
//		}
//
//		final FilterOutputStream fos = new BufferedOutputStream(new FileOutputStream(file));
//
//		try {
//			final ITextRenderer renderer = new ITextRenderer();
//
//			renderer.setDocument(files[0]);
//			renderer.layout();
//			renderer.createPDF(fos, false);
//
//			for (final File inputFile : files) {
//				if (null == inputFile) {
//					throw new RuntimeExceptionIsNull("inputFile"); //$NON-NLS-1$
//				}
//				renderer.setDocument(inputFile);
//				renderer.layout();
//				renderer.writeNextDocument();
//			}
//			renderer.finishPDF();
//		} finally {
//			fos.close();
//		}
//		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
//	}

	/**
	 * Writes a PDF from multiple image files to a {@link File}.
	 * 
	 * @param pageSize
	 *           of the PDF
	 * @param scale
	 *           images to fit the page size
	 * @param file
	 *           output as PDF
	 * @param files
	 *           for the PDF
	 * @throws DocumentException
	 * @throws IOException
	 * @see File
	 * @see Rectangle
	 * @since 0.0.1
	 */
	public static void writePdfFromImages(final Rectangle pageSize, final boolean scale, final File file,
			final File... files) throws DocumentException, IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(pageSize, scale, file, files));
		if (null == pageSize) {
			throw new RuntimeExceptionIsNull("pageSize"); //$NON-NLS-1$
		}
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		if (null == files) {
			throw new RuntimeExceptionIsNull("files"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(files)) {
			throw new RuntimeExceptionIsEmpty("files"); //$NON-NLS-1$
		}

		final Document document = new Document(pageSize);
		document.setMargins(0.0F, 0.0F, 0.0F, 0.0F);

		try (FilterOutputStream fos = new BufferedOutputStream(new FileOutputStream(file))) {
			PdfWriter.getInstance(document, fos);
			document.open();

			for (final File inputFile : files) {
				if (null == inputFile) {
					throw new RuntimeExceptionIsNull("inputFile"); //$NON-NLS-1$
				}
				final Image image = Image.getInstance(inputFile.getAbsolutePath());

				if (scale) {
					image.scaleToFit(pageSize.getWidth(), pageSize.getHeight());
				}
				document.add(image);
				document.newPage();
			}
			
			document.close();
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Writes a PDF from multiple {@link java.awt.Image} to a {@link File}.
	 * 
	 * @param pageSize
	 *           of the PDF
	 * @param scale
	 *           images to fit the page size
	 * @param file
	 *           output as PDF
	 * @param images
	 *           for the PDF
	 * @throws DocumentException
	 * @throws IOException
	 * @see File
	 * @see java.awt.Image
	 * @see Rectangle
	 * @since 0.0.1
	 */
	public static void writePdfFromImages(final Rectangle pageSize, final boolean scale, final File file,
			final java.awt.Image... images) throws DocumentException, IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(pageSize, scale, file, images));
		if (null == pageSize) {
			throw new RuntimeExceptionIsNull("pageSize"); //$NON-NLS-1$
		}
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		if (null == images) {
			throw new RuntimeExceptionIsNull("images"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(images)) {
			throw new RuntimeExceptionIsEmpty("images"); //$NON-NLS-1$
		}

		final Document document = new Document(pageSize);
		document.setMargins(0.0F, 0.0F, 0.0F, 0.0F);

		try (FilterOutputStream fos = new BufferedOutputStream(new FileOutputStream(file))) {
			PdfWriter.getInstance(document, fos);
			document.open();

			for (final java.awt.Image tempImage : images) {
				if (null == tempImage) {
					throw new RuntimeExceptionIsNull("tempImage"); //$NON-NLS-1$
				}
				final Image image = Image.getInstance(tempImage, null);

				if (scale) {
					image.scaleToFit(pageSize.getWidth(), pageSize.getHeight());
				}
				document.add(image);
				document.newPage();
			}
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
	/**
	 * Writes a PDF from a {@link SlideShow} to a {@link File}.
	 * 
	 * <strong>Note:</strong> This method needs <a
	 * href="http://poi.apache.org/">Apache POI</a> to work.
	 * 
	 * @param pageSize
	 *           of the PDF
	 * @param scale
	 *           should PPT fit the page size
	 * @param file
	 *           output as PDF
	 * @param ppt
	 *           for the PDF
	 * @throws IOException
	 * @throws DocumentException
	 * @see File
	 * @see SlideShow
	 * @see Rectangle
	 * @since 0.0.1
	 */
	public static void writePdfFromPpt(final Rectangle pageSize, final boolean scale, final File file,
			final SlideShow ppt) throws IOException, DocumentException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(pageSize, scale, file, ppt));
		if (null == ppt) {
			throw new RuntimeExceptionIsNull("ppt"); //$NON-NLS-1$
		}
		
		final File[] files = new File[ppt.getSlides().length];
		for (int ii = 0; ii < ppt.getSlides().length; ii++) {
			files[ii] = HelperIO.getTemporaryFile(HelperPdf.class.getSimpleName() + "-slide", IMAGE_TYPE); //$NON-NLS-1$
		}

		// export ppt as images
		exportAsImages(ppt, files);

		// process exported images to pdf
		writePdfFromImages(pageSize, scale, file, files);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Writes a PDF from a {@link SlideShow} file to a {@link File}.
	 * 
	 * <strong>Note:</strong> This method needs <a
	 * href="http://poi.apache.org/">Apache POI</a> to work.
	 * 
	 * @param pageSize
	 *           of the PDF
	 * @param scale
	 *           should PPT fit the page size
	 * @param file
	 *           output as PDF
	 * @param pptFile
	 *           for the PDF
	 * @throws IOException
	 * @throws DocumentException
	 * @see File
	 * @see SlideShow
	 * @see Rectangle
	 * @since 0.0.1
	 */
	public static void writePdfFromPpt(final Rectangle pageSize, final boolean scale, final File file, final File pptFile)
			throws IOException, DocumentException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(pageSize, scale, file, pptFile));
		if (null == pptFile) {
			throw new RuntimeExceptionIsNull("pptFile"); //$NON-NLS-1$
		}
		
		try (InputStream is = new FileInputStream(pptFile)) {
			writePdfFromPpt(pageSize, scale, file, new SlideShow(is));
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Returns the text of a given PDF as {@link String}.
	 * 
	 * @param file
	 *           input as PDF
	 * @return text of the given PDF
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	public static String getText(final File file) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		
		final PdfReader pdfReader = new PdfReader(file.getAbsolutePath());
		final PdfTextExtractor pdfExtractor = new PdfTextExtractor(pdfReader);

		final StringBuilder sb = new StringBuilder();

		for (int page = 1; page <= pdfReader.getNumberOfPages(); page++) {
			sb.append(pdfExtractor.getTextFromPage(page));
			sb.append(HelperString.NEW_LINE);
		}
		final String result = sb.toString();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
	
	/**
	 * Modifies the meta data of given PDF and stores it in a new {@link File}.
	 * <strong>Meta data example:"</strong> metadata.put("Title", "Example");
	 * metadata.put("Author", "Stefan Laubenberger"); metadata.put("Subject",
	 * "Example PDF meta data"); metadata.put("Keywords", "Example, PDF");
	 * metadata.put("Creator", "http://www.laubenberger.net/");
	 * metadata.put("Producer", "Silvan Spross");
	 * 
	 * @param source
	 *           {@link File}
	 * @param dest
	 *           {@link File} for the modified PDF
	 * @param metadata
	 *           list with the new meta data informations
	 * @throws DocumentException
	 * @throws IOException
	 * @since 0.0.1
	 */
	@SuppressWarnings("unchecked")
	public static void setMetaData(final File source, final File dest, final Map<String, String> metadata)
			throws IOException, DocumentException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(source, dest, metadata));
		if (null == source) {
			throw new RuntimeExceptionIsNull("source"); //$NON-NLS-1$
		}
		if (null == dest) {
			throw new RuntimeExceptionIsNull("dest"); //$NON-NLS-1$
		}
		if (HelperObject.isEquals(source, dest)) {
			throw new RuntimeExceptionIsEquals("source", "dest"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (null == metadata) {
			throw new RuntimeExceptionIsNull("metadata"); //$NON-NLS-1$
		}

		try (FileOutputStream fos = new FileOutputStream(dest)){
			final PdfReader reader = new PdfReader(source.getAbsolutePath());
			final PdfStamper stamper = new PdfStamper(reader, fos);

			final HashMap<String, String> info = reader.getInfo();
			info.putAll(metadata);

			stamper.setMoreInfo(info);
			
			stamper.close();
			reader.close();
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
	
	/**
	 * Returns the metadata of a given PDF as {@link Map}.
	 * 
	 * @param file
	 *           input as PDF
	 * @return {@link Map} containinfg the metadata of the given PDF
	 * @throws IOException
	 * @see File
	 * @since 0.0.1
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getMetaData(final File file) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		
		PdfReader reader = null;
		Map<String, String> result;
		
		try {
			reader = new PdfReader(file.getAbsolutePath());

			result = reader.getInfo();
		} finally {
			if (null != reader) {
				reader.close();
			}
		}
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
	
	/**
	 * Encrypt a PDF with a given user and password.
	 * 
	 * @param source
	 *           {@link File}
	 * @param dest
	 *           {@link File} for the encrypted PDF
	 * @param user
	 *           of the dest {@link File}
	 * @param password
	 *           of the dest {@link File}
	 * @throws DocumentException
	 * @throws IOException
	 * @since 0.0.1
	 */
	public static void encrypt(final File source, final File dest, final byte[] user, final byte... password)
			throws IOException, DocumentException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(source, dest, user, password));
		if (null == source) {
			throw new RuntimeExceptionIsNull("source"); //$NON-NLS-1$
		}
		if (null == dest) {
			throw new RuntimeExceptionIsNull("dest"); //$NON-NLS-1$
		}
		if (HelperObject.isEquals(source, dest)) {
			throw new RuntimeExceptionIsEquals("source", "dest"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (null == user) {
			throw new RuntimeExceptionIsNull("user"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(user)) {
			throw new RuntimeExceptionIsEmpty("user"); //$NON-NLS-1$
		}
		if (null == password) {
			throw new RuntimeExceptionIsNull("password"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(password)) {
			throw new RuntimeExceptionIsEmpty("password"); //$NON-NLS-1$
		}
		
		PdfReader reader = null;

		try (OutputStream os = new FileOutputStream(dest)) {
			reader = new PdfReader(source.getAbsolutePath());

			PdfEncryptor.encrypt(reader, os, user, password, PdfWriter.ALLOW_ASSEMBLY | PdfWriter.ALLOW_COPY | PdfWriter.ALLOW_DEGRADED_PRINTING
					| PdfWriter.ALLOW_FILL_IN | PdfWriter.ALLOW_MODIFY_ANNOTATIONS | PdfWriter.ALLOW_MODIFY_CONTENTS
					| PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_SCREENREADERS, true);
		} finally {
			if (null != reader) {
				reader.close();
			}
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Decrypt a PDF with a given password.
	 * 
	 * @param source
	 *           {@link File}
	 * @param dest
	 *           {@link File} for the decrypted PDF
	 * @param password
	 *           of the source {@link File}
	 * @throws DocumentException
	 * @throws IOException
	 * @since 0.0.1
	 */
	public static void decrypt(final File source, final File dest, final byte... password) throws IOException,
			DocumentException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(source, dest, password));

		unlock(source, dest, password);
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
	
	/**
	 * Adds a all restrictions to a PDF with a given password.
	 * 
	 * @param source
	 *           {@link File}
	 * @param dest
	 *           {@link File} for the locked PDF
	 * @param password
	 *           of the dest {@link File}
	 * @throws DocumentException
	 * @throws IOException
	 * @since 0.0.1
	 */
	public static void lock(final File source, final File dest, final byte... password)
			throws IOException, DocumentException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(source, dest, password));
		if (null == source) {
			throw new RuntimeExceptionIsNull("source"); //$NON-NLS-1$
		}
		if (null == dest) {
			throw new RuntimeExceptionIsNull("dest"); //$NON-NLS-1$
		}
		if (HelperObject.isEquals(source, dest)) {
			throw new RuntimeExceptionIsEquals("source", "dest"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (null == password) {
			throw new RuntimeExceptionIsNull("password"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(password)) {
			throw new RuntimeExceptionIsEmpty("password"); //$NON-NLS-1$
		}
		
		PdfReader reader = null;

		try (OutputStream os = new FileOutputStream(dest)) {
			reader = new PdfReader(source.getAbsolutePath());

			PdfEncryptor.encrypt(reader, os, null , password, 0, true);
		} finally {
			if (null != reader) {
				reader.close();
			}
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Removes all restrictions from a PDF with a given password.
	 * 
	 * @param source
	 *           {@link File}
	 * @param dest
	 *           {@link File} for the unlocked PDF
	 * @param password
	 *           of the source {@link File}
	 * @throws DocumentException
	 * @throws IOException
	 * @since 0.0.1
	 */
	public static void unlock(final File source, final File dest, final byte... password) throws IOException,
			DocumentException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(source, dest, password));
		if (null == source) {
			throw new RuntimeExceptionIsNull("source"); //$NON-NLS-1$
		}
		if (null == dest) {
			throw new RuntimeExceptionIsNull("dest"); //$NON-NLS-1$
		}
		if (HelperObject.isEquals(source, dest)) {
			throw new RuntimeExceptionIsEquals("source", "dest"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (null == password) {
			throw new RuntimeExceptionIsNull("password"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(password)) {
			throw new RuntimeExceptionIsEmpty("password"); //$NON-NLS-1$
		}
		
		PdfReader reader = null;

		try (OutputStream os = new FileOutputStream(dest)) {
			reader = new PdfReader(source.getAbsolutePath(), password);

			PdfEncryptor.encrypt(reader, os, null, null, PdfWriter.ALLOW_ASSEMBLY | PdfWriter.ALLOW_COPY
					| PdfWriter.ALLOW_DEGRADED_PRINTING | PdfWriter.ALLOW_FILL_IN | PdfWriter.ALLOW_MODIFY_ANNOTATIONS
					| PdfWriter.ALLOW_MODIFY_CONTENTS | PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_SCREENREADERS, true);
		} finally {
			if (null != reader) {
				reader.close();
			}
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	
	/*
	 * Private methods
	 */

	private static void exportAsImages(final SlideShow ppt, final File... files) throws IOException {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart(ppt, files));

		final Dimension size = ppt.getPageSize();
		final Slide[] slides = ppt.getSlides();

		for (int ii = 0; ii < slides.length; ii++) {

			final BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
			final Graphics2D g2d = image.createGraphics();

			// clear the drawing area
			g2d.setPaint(Color.WHITE);
			g2d.fill(new Rectangle2D.Float(0, 0, size.width, size.height));

			// render
			slides[ii].draw(g2d);

			HelperImage.writeImage(files[ii], IMAGE_TYPE, image);
		}

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}

}