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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

import javax.imageio.ImageIO;

import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsInvalid;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for image operations.
 * 
 * @author Stefan Laubenberger
 * @version 0.2.0, 2014-05-12
 * @since 0.0.1
 */
public final class HelperImage {
	private static final Logger log = LoggerFactory.getLogger(HelperImage.class);

	public static final String TYPE_JPG = "jpg"; //$NON-NLS-1$
	public static final String TYPE_PNG = "png"; //$NON-NLS-1$
	public static final String TYPE_GIF = "gif"; //$NON-NLS-1$
	public static final String TYPE_BMP = "bmp"; //$NON-NLS-1$

    private HelperImage() {
        //do nothing
    }

	/**
	 * Reads an image from a {@link File} to a {@link BufferedImage}.
	 * 
	 * @param file
	 *           for the image
	 * @return {@link BufferedImage}
	 * @throws IOException
	 * @see File
	 * @see BufferedImage
	 * @since 0.0.1
	 */
	public static BufferedImage readImage(final File file) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}

		final BufferedImage result = ImageIO.read(file);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Reads an image from an {@link InputStream} to a {@link BufferedImage}.
	 * 
	 * @param is
	 *           input stream for the image
	 * @return {@link BufferedImage}
	 * @throws IOException
	 * @see InputStream
	 * @see BufferedImage
	 * @since 0.0.1
	 */
	public static BufferedImage readImage(final InputStream is) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(is));
		if (null == is) {
			throw new RuntimeExceptionIsNull("is"); //$NON-NLS-1$
		}

		final BufferedImage result = ImageIO.read(is);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Writes an image from a {@link RenderedImage} to a {@link File}.
	 * 
	 * @param file
	 *           for the image
	 * @param type
	 *           of the image (e.g. "jpg")
	 * @param image
	 *           {@link RenderedImage} for the image
	 * @throws IOException
	 * @see File
	 * @see RenderedImage
	 * @since 0.0.1
	 */
	public static void writeImage(final File file, final String type, final RenderedImage image) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, type, image));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		if (null == image) {
			throw new RuntimeExceptionIsNull("image"); //$NON-NLS-1$
		}
		if (null == type) {
			throw new RuntimeExceptionIsNull("type"); //$NON-NLS-1$
		}
		if (!getAvailableImageWriteFormats().contains(type)) {
			throw new RuntimeExceptionIsInvalid("type", type); //$NON-NLS-1$
		}

		ImageIO.write(image, type, file);
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Writes an image from a {@link RenderedImage} to an {@link OutputStream}.
	 * 
	 * @param os
	 *           output stream for the image
	 * @param type
	 *           of the image (e.g. "jpg")
	 * @param image
	 *           {@link RenderedImage} for the image
	 * @throws IOException
	 * @see OutputStream
	 * @see RenderedImage
	 * @since 0.0.1
	 */
	public static void writeImage(final OutputStream os, final String type, final RenderedImage image) throws IOException { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(os, type, image));
		if (null == image) {
			throw new RuntimeExceptionIsNull("image"); //$NON-NLS-1$
		}
		if (null == type) {
			throw new RuntimeExceptionIsNull("type"); //$NON-NLS-1$
		}
		if (!getAvailableImageWriteFormats().contains(type)) {
			throw new RuntimeExceptionIsInvalid("type", type); //$NON-NLS-1$
		}
		if (null == os) {
			throw new RuntimeExceptionIsNull("os"); //$NON-NLS-1$
		}

		ImageIO.write(image, type, os);
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Gets a {@link BufferedImage} from a {@link Component}.
	 * 
	 * @param component
	 *           for the image
	 * @return {@link Component} as {@link BufferedImage}
	 * @see BufferedImage
	 * @see Component
	 * @since 0.0.1
	 */
	public static BufferedImage getImage(final Component component) { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(component));
		if (null == component) {
			throw new RuntimeExceptionIsNull("component"); //$NON-NLS-1$
		}

		final Dimension size = component.getSize();
		final BufferedImage result = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D g2d = result.createGraphics();

		component.paint(g2d);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Scales a {@link BufferedImage} to an {@link Image}.
	 * 
	 * @param image
	 *           to scale
	 * @param scale
	 *           for the new image
	 * @return scaled {@link BufferedImage}
	 * @see BufferedImage
	 * @since 0.0.1
	 */
	public static BufferedImage getScaledImage(final BufferedImage image, final double scale) { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(image, scale));
		if (null == image) {
			throw new RuntimeExceptionIsNull("image"); //$NON-NLS-1$
		}

		final Dimension size = HelperGraphic.getScaledSize(new Dimension(image.getWidth(), image.getHeight()), scale);
		final BufferedImage result = getScaledImage(image, size);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Scales a {@link BufferedImage} to an {@link Image} with the given
	 * {@link Dimension}. If the width or height is 0, the image will be scaled
	 * to fit the given parameter.
	 * 
	 * @param image
	 *           to scale
	 * @param size
	 *           of the new image
	 * @return scaled {@link BufferedImage}
	 * @see BufferedImage
	 * @see Dimension
	 * @since 0.0.1
	 */
	public static BufferedImage getScaledImage(final BufferedImage image, final Dimension size) { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(image, size));
		if (null == image) {
			throw new RuntimeExceptionIsNull("image"); //$NON-NLS-1$
		}
		if (null == size) {
			throw new RuntimeExceptionIsNull("size"); //$NON-NLS-1$
		}
		if (0 > size.width) {
			throw new RuntimeExceptionMustBeGreater("size.width", size.width, 0); //$NON-NLS-1$
		}
		if (0 > size.height) {
			throw new RuntimeExceptionMustBeGreater("size.height", size.height, 0); //$NON-NLS-1$
		}

		final BufferedImage result;
		if (HelperObject.isEquals(size, new Dimension(image.getWidth(), image.getHeight()))) {
			result = image;
		} else {
			if (0 == size.width || 0 == size.height) {
				final Dimension sizeNew = HelperGraphic.getScaledSize(new Dimension(image.getWidth(), image.getHeight()),
						size);

				result = new BufferedImage(sizeNew.width, sizeNew.height, image.getType());

				final Graphics2D g2d = result.createGraphics();
				if (size.width > image.getWidth() || size.height > image.getHeight()) {
					g2d.setRenderingHints(getHQRenderingHintsForUpscale());
				} else {
					g2d.setRenderingHints(getHQRenderingHintsForDownscale());
				}
				g2d.drawImage(image, 0, 0, sizeNew.width, sizeNew.height, 0, 0, image.getWidth(), image.getHeight(), null);
				g2d.dispose();
			} else {
				result = new BufferedImage(size.width, size.height, image.getType());

				final Graphics2D g2d = result.createGraphics();
				if (size.width > image.getWidth() || size.height > image.getHeight()) {
					g2d.setRenderingHints(getHQRenderingHintsForUpscale());
				} else {
					g2d.setRenderingHints(getHQRenderingHintsForDownscale());
				}
				g2d.drawImage(image, 0, 0, size.width, size.height, 0, 0, image.getWidth(), image.getHeight(), null);
				g2d.dispose();

			}
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns a {@link Collection} of all available read formats (e.g. "png",
	 * "jpg").
	 * 
	 * @return {@link Collection} containing all available read formats
	 * @since 0.0.1
	 */
	public static Collection<String> getAvailableImageReadFormats() { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		final Collection<String> result = unique(ImageIO.getReaderFormatNames());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns a {@link Collection} of all available write formats (e.g. "png",
	 * "jpeg").
	 * 
	 * @return {@link Collection} containing all available write formats
	 * @since 0.0.1
	 */
	public static Collection<String> getAvailableImageWriteFormats() { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		final Collection<String> result = unique(ImageIO.getWriterFormatNames());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns a {@link Collection} of all available MIME types that can be read
	 * (e.g. "image/png", "image/jpeg").
	 * 
	 * @return {@link Collection} containing all available MIME types that can be
	 *         read
	 * @since 0.0.1
	 */
	public static Collection<String> getAvailableImageReadMIMETypes() { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		final Collection<String> result = unique(ImageIO.getReaderMIMETypes());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns a {@link Collection} of all available MIME types that can be
	 * written (e.g. "image/png", "image/jpg").
	 * 
	 * @return {@link Collection} containing all available MIME types that can be
	 *         written
	 * @since 0.0.1
	 */
	public static Collection<String> getAvailableImageWriteMIMETypes() { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		final Collection<String> result = unique(ImageIO.getWriterMIMETypes());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Returns a set of high quality {@link RenderingHints} for basic transformations.
	 * 
	 * @return set of high quality {@link RenderingHints} for basic transformations.
	 * @see RenderingHints
	 * @since 0.0.1
	 */
	public static RenderingHints getHQRenderingHints() { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		final RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		hints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(hints));
		return hints;
	}
	
	/**
	 * Returns a set of high quality {@link RenderingHints} for upscale transformations.
	 * 
	 * @return set of high quality {@link RenderingHints} for upscale transformations.
	 * @see RenderingHints
	 * @since 0.0.1
	 */
	public static RenderingHints getHQRenderingHintsForUpscale() { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		final RenderingHints hints = getHQRenderingHints();
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(hints));
		return hints;
	}
	
	/**
	 * Returns a set of high quality {@link RenderingHints} for downscale transformations.
	 * 
	 * @return set of high quality {@link RenderingHints} for downscale transformations.
	 * @see RenderingHints
	 * @since 0.0.1
	 */
	public static RenderingHints getHQRenderingHintsForDownscale() { // $JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		final RenderingHints hints = getHQRenderingHints();
		hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(hints));
		return hints;
	}

	/**
	 * Returns a {@link BufferedImage} from a given {@link Image}.
	 * 
	 * @param image
	 *           to convert
	 * @param type
	 *           BufferedImage.TYPE_xxx (e.g. BufferedImage.TYPE_INT_RGB,
	 *           BufferedImage.TYPE_INT_ARGB)
	 * @return {@link BufferedImage} from the given {@link Image}
	 * @see BufferedImage
	 * @see Image
	 * @since 0.0.1
	 */
	public static BufferedImage getBufferedImage(final Image image, final int type) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(image));
		if (null == image) {
			throw new RuntimeExceptionIsNull("image"); //$NON-NLS-1$
		}

		final BufferedImage result;
		if (image instanceof BufferedImage) {
			result = (BufferedImage) image;
		} else if (image instanceof VolatileImage) {
			result = ((VolatileImage) image).getSnapshot();
		} else {
			loadImage(image);

			result = new BufferedImage(image.getWidth(null), image.getHeight(null), type);

			final Graphics2D g2 = result.createGraphics();
			g2.drawImage(image, null, null);
			g2.dispose();
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
	
	/**
	 * Rotates a {@link BufferedImage} with the given angle.
	 * 
	 * @param image to rotate with the given angle
	 * @param angle for the rotation
	 * @return rotated {@link BufferedImage}
	 * @see BufferedImage
	 * @since 0.0.1
	 */
	public static BufferedImage getRotatedImage(final BufferedImage image, final int angle) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(image, angle));
		
		if (null == image) {
			throw new RuntimeExceptionIsNull("image"); //$NON-NLS-1$
		}

		final int w = image.getWidth();
		final int h = image.getHeight();
		
		final BufferedImage result = new BufferedImage(w, h, image.getType());
		
		final Graphics2D g2d = result.createGraphics();
		g2d.setRenderingHints(getHQRenderingHints());
		g2d.rotate(Math.toRadians(angle), w / 2, h / 2);
		g2d.drawImage(image, null, 0, 0);
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Flips a {@link BufferedImage} around the vertical axis.
	 * 
	 * @param image to flip around the vertical axis
	 * @return flipped {@link BufferedImage}
	 * @see BufferedImage
	 * @since 0.0.1
	 */
	public static BufferedImage getVerticalFlippedImage(final BufferedImage image) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(image));
		
		if (null == image) {
			throw new RuntimeExceptionIsNull("image"); //$NON-NLS-1$
		}

		final int w = image.getWidth();
		final int h = image.getHeight();
		
		final BufferedImage result = new BufferedImage(w, h, image.getColorModel().getTransparency());
		
		final Graphics2D g2d = result.createGraphics();
		g2d.setRenderingHints(getHQRenderingHints());
		g2d.drawImage(image, 0, 0, w, h, 0, h, w, 0, null);
		g2d.dispose();
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Flips a {@link BufferedImage} around the horizontal axis.
	 * 
	 * @param image to flip around the horizontal axis
	 * @return flipped {@link BufferedImage}
	 * @see BufferedImage
	 * @since 0.0.1
	 */
	public static BufferedImage getHorizontalFlippedImage(final BufferedImage image) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(image));
		
		if (null == image) {
			throw new RuntimeExceptionIsNull("image"); //$NON-NLS-1$
		}

		final int w = image.getWidth();
		final int h = image.getHeight();
		
		final BufferedImage result = new BufferedImage(w, h, image.getType());
		
		final Graphics2D g2d = result.createGraphics();
		g2d.setRenderingHints(getHQRenderingHints());
		g2d.drawImage(image, 0, 0, w, h, w, 0, 0, h, null);
		g2d.dispose();
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Makes a {@link Color} in a {@link BufferedImage} transparent.
	 * 
	 * @param image to process
	 * @param color to be transparent 
	 * @return {@link BufferedImage} with the transparent {@link Color}
	 * @see BufferedImage
	 * @see Color
	 * @since 0.0.1
	 */
	public static BufferedImage getImageWithTransparentColor(final BufferedImage image, final Color color) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(image, color));
		
		if (null == image) {
			throw new RuntimeExceptionIsNull("image"); //$NON-NLS-1$
		}
		if (null == color) {
			throw new RuntimeExceptionIsNull("color"); //$NON-NLS-1$
		}

		final BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

		final Graphics2D g2d = result.createGraphics();
		g2d.setRenderingHints(getHQRenderingHints());
		g2d.setComposite(AlphaComposite.Src);
		g2d.drawImage(image, null, 0, 0);
		g2d.dispose();
		
		for (int ii = 0; ii < result.getHeight(); ii++) {
			for (int xx = 0; xx < result.getWidth(); xx++) {
				if (result.getRGB(xx, ii) == color.getRGB()) {
					result.setRGB(xx, ii, 0x8F1C1C);
				}
			}
		}
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Makes a {@link BufferedImage} translucent.
	 * 
	 * @param image to process
	 * @param transparency between 0.0 and 1.0 
	 * @return {@link BufferedImage} with the given transparency
	 * @see BufferedImage
	 * @since 0.0.1
	 */
	public static BufferedImage getTranslucentImage(final BufferedImage image, final float transparency) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(image, transparency));
		
		if (null == image) {
			throw new RuntimeExceptionIsNull("image"); //$NON-NLS-1$
		}

		final BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), Transparency.TRANSLUCENT);

		final Graphics2D g2d = result.createGraphics();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
		g2d.drawImage(image, null, 0, 0);
		g2d.dispose();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
	
	
	/*
	 * Private methods
	 */

	/**
	 * Converts all {@link String} to lower case and returns a {@link Collection}
	 * containing the unique values.
	 * 
	 * @param strings
	 *           as array
	 * @return {@link Collection} containing the unique values
	 * @since 0.0.1
	 */
	private static Collection<String> unique(final String... strings) {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart(new Object[]{strings}));
		final Collection<String> result = new HashSet<>(strings.length);

		for (final String str : strings) {
			result.add(str.toLowerCase(Locale.getDefault()));
		}

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit(result));
		return result;
	}

	private static void loadImage(final Image image) {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart(image));

		class StatusObserver implements ImageObserver {
			boolean imageLoaded;

			@Override
			public boolean imageUpdate(final Image img, final int infoflags, final int x, final int y, final int width,
					final int height) {
				if (ALLBITS == infoflags) {
					synchronized (this) {
						imageLoaded = true;
						notifyAll();
					}
					return true;
				}
				return false;
			}
		}

		final StatusObserver imageStatus = new StatusObserver();
		synchronized (imageStatus) {
			if (-1 == image.getWidth(imageStatus) || -1 == image.getHeight(imageStatus)) {
				while (!imageStatus.imageLoaded) {
					try {
						imageStatus.wait();
					} catch (InterruptedException ex) {
						log.error("Could not process image", ex); //$NON-NLS-1$
					}
				}
			}
		}

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}
}