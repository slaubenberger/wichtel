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

package net.laubenberger.wichtel.service.updater;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.laubenberger.wichtel.helper.HelperCrypto;
import net.laubenberger.wichtel.helper.HelperIO;
import net.laubenberger.wichtel.helper.HelperXml;
import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.model.crypto.HashCodeAlgo;
import net.laubenberger.wichtel.model.misc.Platform;
import net.laubenberger.wichtel.model.updater.ModelUpdater;
import net.laubenberger.wichtel.model.updater.ModelUpdaterImpl;

import org.junit.Before;
import org.junit.Test;


/**
 * Junit test
 *
 * @author Stefan Laubenberger
 * @version 0.0.2, 2013-03-14
 */
public class UpdaterTest {
	@Before
	public void setUp() throws Exception {
		final ModelUpdater doc = new ModelUpdaterImpl();

		final Map<Platform, URL> locations = new HashMap<>(3);
		locations.put(Platform.WINDOWS, new URL("http://www.ms.com")); //$NON-NLS-1$
		locations.put(Platform.MAC_OSX, new URL("http://www.apple.com")); //$NON-NLS-1$
		locations.put(Platform.UNIX, new URL("http://www.unix.com")); //$NON-NLS-1$
		doc.setLocations(locations);

		final Map<HashCodeAlgo, String> hashs = new HashMap<>(3);
		hashs.put(HashCodeAlgo.MD5, "MD5-Hashvalue"); //$NON-NLS-1$
		hashs.put(HashCodeAlgo.SHA256, "SHA256-Hashvalue"); //$NON-NLS-1$
		doc.setHashs(hashs);

		doc.setName(Constants.WICHTEL.getName());
		doc.setVersion(Constants.WICHTEL.getVersion());
		doc.setBuild(Constants.WICHTEL.getBuild());
		doc.setCreated(new Date());
		doc.setUUID(HelperCrypto.getUUID());

//		System.out.println(doc);

		try {
			final File file = HelperIO.getTemporaryFile();

			HelperXml.serialize(file, doc);

			final ModelUpdater doc2 = HelperXml.deserialize(file, ModelUpdaterImpl.class);

			assertEquals(doc, doc2);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

//	public static void main(final String[] args) {
//		try {
//			new UpdaterTest().setUp();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Test
	public void testGetDocuments() {
	}
}
