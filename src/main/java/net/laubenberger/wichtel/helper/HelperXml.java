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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Helper class for XML operations.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public abstract class HelperXml {
	private static final Logger log = LoggerFactory.getLogger(HelperXml.class);

	/**
	 * This method ensures that the output String has only valid XML unicode characters as specified by the XML 1.0 standard.
	 * For reference, please see the <a href="http://www.w3.org/TR/2000/REC-xml-20001006#NT-Char">standard</a>.
	 *
	 * @param input {@link String} to remove non-valid characters
	 * @return stripped {@link String}
	 * @since 0.0.1
	 */
	public static String getValidXmlString(final String input) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}

		final StringBuilder sb = new StringBuilder(input.length());

		for (final int current : input.toCharArray()) {
			if (127 != current && // delete
					144 != current && // Num lock
					145 != current && // Scroll lock
					154 != current && // print
					155 != current && // insert
					157 != current && // Mac cmd
					524 != current && // Windows cmd
					525 != current) { // Windows context menu)
				if (9 == current || 
						10 == current || 
						13 == current || 
						(41 <= current && 111 >= current) || 
						(124 <= current && 55295 >= current) || 
						(57344 <= current && 65533 >= current)) {
					sb.append((char)current);
				}
			}
		}
		final String result = sb.toString();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Serialize data to XML and store it into a {@link File}.
	 *
	 * @param file to store the serialized data
	 * @param data (object) to serialize as XML
	 * @see File
	 * @throws JAXBException
	 * @since 0.0.1
	 */
	public static <T> void serialize(final File file, final T data) throws JAXBException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, data));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		if (null == data) {
			throw new RuntimeExceptionIsNull("data"); //$NON-NLS-1$
		}

		getMarshaller(data.getClass()).marshal(data, file);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Serialize data to XML and store it to an {@link OutputStream}.
	 *
	 * @param os	{@link OutputStream} to store the serialized data
	 * @param data (object) to serialize as XML
	 * @see OutputStream
	 * @throws JAXBException
	 * @since 0.0.1
	 */
	public static <T> void serialize(final OutputStream os, final T data) throws JAXBException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(os, data));
		if (null == os) {
			throw new RuntimeExceptionIsNull("os"); //$NON-NLS-1$
		}
		if (null == data) {
			throw new RuntimeExceptionIsNull("data"); //$NON-NLS-1$
		}

		getMarshaller(data.getClass()).marshal(data, os);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Serialize data to XML and return it as {@link String}.
	 *
	 * @param data (object) to serialize as XML
	 * @see String
	 * @throws JAXBException
	 * @throws IOException 
	 * @since 0.0.1
	 */
	public static <T> String serialize(final T data) throws JAXBException, IOException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(data));

		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		serialize(os, data);

		final String result = new String(HelperIO.readStream(HelperIO.convertOutputToInputStream(os)), Constants.ENCODING_DEFAULT);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
	
	/**
	 * Deserialize data from a XML {@link File}.
	 *
	 * @param file  containing the serialized data
	 * @param clazz for the serialized data
	 * @return data as object
	 * @see File
	 * @throws JAXBException
	 * @since 0.0.1
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(final File file, final Class<T> clazz) throws JAXBException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file, clazz));
		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}
		if (null == clazz) {
			throw new RuntimeExceptionIsNull("clazz"); //$NON-NLS-1$
		}

		final T result = (T) getUnmarshaller(clazz).unmarshal(file);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Deserialize data from A XML {@link InputStream}.
	 *
	 * @param is	 {@link InputStream} containing the serialized data
	 * @param clazz for the serialized data
	 * @return data as object
	 * @see InputStream
	 * @throws JAXBException
	 * @since 0.0.1
	 */
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(final InputStream is, final Class<T> clazz) throws JAXBException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(is, clazz));
		if (null == is) {
			throw new RuntimeExceptionIsNull("is"); //$NON-NLS-1$
		}
		if (null == clazz) {
			throw new RuntimeExceptionIsNull("clazz"); //$NON-NLS-1$
		}

		final T result = (T) getUnmarshaller(clazz).unmarshal(is);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}


	/**
	 * Deserialize data from a XML {@link String}.
	 *
	 * @param input {@link String} containing the serialized data
	 * @param clazz for the serialized data
	 * @return data as object
	 * @see String
	 * @throws JAXBException
	 * @since 0.0.1
	 */
	public static <T> T deserialize(final String input, final Class<T> clazz) throws JAXBException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(input, clazz));
		if (null == input) {
			throw new RuntimeExceptionIsNull("input"); //$NON-NLS-1$
		}
		
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(input.getBytes(Constants.ENCODING_DEFAULT));
		} catch (UnsupportedEncodingException ex) {
			// should never happen!
			log.error("Encoding invalid", ex); //$NON-NLS-1$
		}

		final T result = deserialize(is, clazz);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
	
	
	/*
	 * Private methods
	 */

	private static <T> Marshaller getMarshaller(final Class<T> clazz) throws JAXBException {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart(clazz));
		final JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		final Marshaller result = jaxbContext.createMarshaller();
//		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
		result.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit(result));
		return result;
	}

	private static <T> Unmarshaller getUnmarshaller(final Class<T> clazz) throws JAXBException {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart(clazz));

		final JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		final Unmarshaller result = jaxbContext.createUnmarshaller();

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit(result));
		return result;
	}
}