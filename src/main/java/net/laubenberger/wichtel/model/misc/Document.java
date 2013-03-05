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

package net.laubenberger.wichtel.model.misc;

import java.net.URL;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.laubenberger.wichtel.model.Model;
import net.laubenberger.wichtel.model.misc.DocumentImpl.XmlAdapterDocument;

/**
 * The interface for the document model.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
@XmlJavaTypeAdapter(XmlAdapterDocument.class)
public interface Document extends Model {
	String MEMBER_NAME 					= "name"; //$NON-NLS-1$
	String MEMBER_VERSION 				= "version"; //$NON-NLS-1$
	String MEMBER_BUILD 					= "build"; //$NON-NLS-1$
	String MEMBER_CREATED 				= "created"; //$NON-NLS-1$
	String MEMBER_CHANGED 				= "changed"; //$NON-NLS-1$
	String MEMBER_LANGUAGE				= "language"; //$NON-NLS-1$
	String MEMBER_URL = "url"; //$NON-NLS-1$
	
	String getName();

	void setName(String name);

	String getVersion();

	void setVersion(String version);

	int getBuild();

	void setBuild(int build);

	Date getCreated();

	void setCreated(Date changed);
	
	Date getChanged();

	void setChanged(Date changed);

	Language getLanguage();

	void setLanguage(Language language);

	URL getUrl();

	void setUrl(URL website);
}
