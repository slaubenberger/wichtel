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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBException;

import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.model.misc.DocumentImpl;

import org.junit.Test;


/**
 * JUnit test for {@link HelperXml}
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 */
public class HelperXmlTest {
	private static final String INVALID_XML = new String(new char[]{(char) 127, (char) 144, (char) 145, (char) 154, (char) 155, (char) 157, (char) 524, (char) 525, (char) 9, (char) 10, (char) 11, (char) 13, (char) 40, (char) 41, (char) 111, (char) 112, (char) 123, (char) 124, (char) 55295, (char) 55296, (char) 57343, (char) 57344, (char) 65533, (char) 65534});
	private static final String VALID_XML = new String(new char[]{(char) 9, (char) 10, (char) 13, (char) 41, (char) 111, (char) 124, (char) 55295, (char) 57344, (char) 65533});

	@Test
	public void testGetValidXmlString() {
		assertEquals(VALID_XML, HelperXml.getValidXmlString(INVALID_XML));
		assertEquals(HelperString.EMPTY_STRING, HelperXml.getValidXmlString(HelperString.EMPTY_STRING));
		
		try {
			HelperXml.getValidXmlString(null);
			fail("input is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testSerialize() {
		File file = null;
		
		try {
			file = HelperIO.getTemporaryFile();
		} catch (IOException ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperXml.serialize(file, Constants.WICHTEL);
			assertEquals(Constants.WICHTEL, HelperXml.deserialize(file, DocumentImpl.class));
		} catch (JAXBException ex) {
			fail(ex.getMessage());
		}
		
		try {
			assertEquals(Constants.WICHTEL, HelperXml.deserialize(HelperXml.serialize(Constants.WICHTEL), DocumentImpl.class));
		} catch (Exception ex) {
			fail(ex.getMessage());
		}		
		
		try {
			HelperXml.serialize((File)null, Constants.WICHTEL);
			fail("file is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	
		try {
			HelperXml.serialize(file, null);
			fail("data is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperXml.serialize((OutputStream)null, Constants.WICHTEL);
			fail("os is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	
		try {
			HelperXml.serialize(new ByteArrayOutputStream(), null);
			fail("data is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperXml.serialize(null);
			fail("data is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}

	@Test
	public void testDeserialize() {
		File file = null;
		
		try {
			file = HelperIO.getTemporaryFile();
		} catch (IOException ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperXml.serialize(file, Constants.WICHTEL);
			assertEquals(Constants.WICHTEL, HelperXml.deserialize(file, DocumentImpl.class));
		} catch (JAXBException ex) {
			fail(ex.getMessage());
		}
		
		try {
			assertEquals(Constants.WICHTEL, HelperXml.deserialize(HelperXml.serialize(Constants.WICHTEL), DocumentImpl.class));
		} catch (Exception ex) {
			fail(ex.getMessage());
		}		
		
		try {
			HelperXml.deserialize((File)null, DocumentImpl.class);
			fail("file is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	
		try {
			HelperXml.deserialize(file, null);
			fail("clazz is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperXml.deserialize((InputStream)null, DocumentImpl.class);
			fail("os is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	
		try {
			HelperXml.deserialize(new ByteArrayInputStream(HelperString.EMPTY_STRING.getBytes()), null);
			fail("clazz is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperXml.deserialize((String)null, DocumentImpl.class);
			fail("input is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			HelperXml.deserialize(HelperString.EMPTY_STRING, null);
			fail("clazz is null"); //$NON-NLS-1$
		} catch (RuntimeExceptionIsNull ex) {
			// nothing to do
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
	}
}


