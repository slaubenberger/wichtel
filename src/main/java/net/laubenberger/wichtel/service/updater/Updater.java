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

import net.laubenberger.wichtel.model.misc.Platform;
import net.laubenberger.wichtel.model.updater.ModelUpdater;
import net.laubenberger.wichtel.service.Service;


/**
 * Defines the methods for the implementation of the updater.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public interface Updater extends Service {

	ModelUpdater getDocument(File file) throws Exception;

	ModelUpdater getDocument(InputStream is) throws Exception;

	void update(ModelUpdater document, File dest) throws IOException;

	void update(ModelUpdater document, Platform platform, File dest) throws IOException;
}   

