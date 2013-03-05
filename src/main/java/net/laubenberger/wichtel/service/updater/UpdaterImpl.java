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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.bind.JAXBException;

import net.laubenberger.wichtel.helper.HelperEnvironment;
import net.laubenberger.wichtel.helper.HelperIO;
import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.helper.HelperNet;
import net.laubenberger.wichtel.helper.HelperObject;
import net.laubenberger.wichtel.helper.HelperXml;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEquals;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.model.misc.Platform;
import net.laubenberger.wichtel.model.updater.ModelUpdater;
import net.laubenberger.wichtel.model.updater.ModelUpdaterImpl;
import net.laubenberger.wichtel.service.ServiceAbstract;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is the updater for documents.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public class UpdaterImpl extends ServiceAbstract implements Updater {
	private static final Logger log = LoggerFactory.getLogger(UpdaterImpl.class);


	public UpdaterImpl() {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor());
	}


	/*
	 * Implemented methods
	 */

	@Override
	public ModelUpdater getDocument(final File file) throws JAXBException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(file));

		final ModelUpdater result = HelperXml.deserialize(file, ModelUpdaterImpl.class);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public ModelUpdater getDocument(final InputStream is) throws JAXBException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(is));

		final ModelUpdater result = HelperXml.deserialize(is, ModelUpdaterImpl.class);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public void update(final ModelUpdater document, final Platform platform, final File dest) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(document, platform, dest));
		if (null == document) {
			throw new RuntimeExceptionIsNull("document"); //$NON-NLS-1$
		}
		if (null == platform) {
			throw new RuntimeExceptionIsNull("platform"); //$NON-NLS-1$
		}
		if (null == dest) {
			throw new RuntimeExceptionIsNull("dest"); //$NON-NLS-1$
		}

		URL location = document.getLocation(platform);

		if (null == location && Platform.ANY != platform) {
			location = document.getLocation(Platform.ANY);
		}

		if (null == location) {
			throw new IllegalArgumentException("no valid location found"); //$NON-NLS-1$
		}

		final File source = new File(location.getFile());

		if (source.exists()) {
			if (HelperObject.isEquals(source, dest)) {
				throw new RuntimeExceptionIsEquals("location", "dest"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			HelperIO.copy(source, dest);
		} else {
			HelperIO.writeFile(dest, HelperNet.readUrl(location), false);
		}
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void update(final ModelUpdater document, final File dest) throws IOException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(document, dest));
		if (null == document) {
			throw new RuntimeExceptionIsNull("document"); //$NON-NLS-1$
		}

		update(document, null == document.getLocation(HelperEnvironment.getPlatform()) ? Platform.ANY : HelperEnvironment.getPlatform(), dest);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
}
