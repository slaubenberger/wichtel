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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.helper.HelperObject;
import net.laubenberger.wichtel.model.ModelAbstract;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The implementation of the document model.
 *
 * @author Stefan Laubenberger
 * @version 0.0.2, 2013-03-14
 * @since 0.0.1
 */
@XmlRootElement(name = "document")
@XmlType(propOrder = {Document.MEMBER_NAME, Document.MEMBER_VERSION, Document.MEMBER_BUILD, Document.MEMBER_CREATED, Document.MEMBER_CHANGED, Document.MEMBER_LANGUAGE, Document.MEMBER_URL})
public class DocumentImpl extends ModelAbstract implements Document {
	private static final long serialVersionUID = 5505184629744108815L;

	private static final Logger log = LoggerFactory.getLogger(DocumentImpl.class);

	private String name;
	private String version;
	private int build;
	private Date created;
	private Date changed;
	private Language language;
	private URL url;

	public DocumentImpl() {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor());
	}

//	public DocumentImpl(final String name, final BigDecimal version, final int build,
//							  final Date created, final Language language, final URL url, final List<Organization> organizations, final List<Person> persons, final UUID uuid, final Map<String, String> mapTag) {
//		super(uuid, mapTag);
//		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(name, version, build, created, language, url, organizations, persons, uuid, mapTag));
//
//		this.name = name;
//		this.version = version;
//		this.build = build;
//		this.created = created;
//		this.language = language;
//		this.url = url;
//		this.organizations = organizations;
//		this.persons = persons;
//	}


	/*
	 * Overridden methods
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + build;
		result = prime * result + ((null == created) ? 0 : created.hashCode());
		result = prime * result + ((null == changed) ? 0 : changed.hashCode());
		result = prime * result + ((null == language) ? 0 : language.hashCode());
		result = prime * result + ((null == name) ? 0 : name.hashCode());
		result = prime * result + ((null == url) ? 0 : url.hashCode());
		result = prime * result + ((null == version) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) return true;
		if (!super.equals(obj)) return false;
		if (getClass() != obj.getClass()) return false;
		final DocumentImpl other = (DocumentImpl) obj;
		if (build != other.build) return false;
		if (null == created) {
			if (null != other.created) return false;
		} else if (!created.equals(other.created)) return false;
		if (null == changed) {
			if (null != other.changed) return false;
		} else if (!changed.equals(other.changed)) return false;
		if (language != other.language) return false;
		if (null == name) {
			if (null != other.name) return false;
		} else if (!name.equals(other.name)) return false;
		if (null == url) {
			if (null != other.url) return false;
		} else if (!url.equals(other.url)) return false;
		if (null == version) {
			if (null != other.version) return false;
		} else if (!version.equals(other.version)) return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getName() + "[instantiated=" + getInstantiated() +  //$NON-NLS-1$
			", isNotifyEnabled=" + isNotifyEnabled() + //$NON-NLS-1$
			", uuid=" + getUUID() +  //$NON-NLS-1$
			", mapTag=" + getTags() +  //$NON-NLS-1$
			", name=" + name + //$NON-NLS-1$ 
			", version=" + version + //$NON-NLS-1$ 
			", build=" + build + //$NON-NLS-1$ 
			", created=" + created + //$NON-NLS-1$ 
			", language=" + language + //$NON-NLS-1$ 
			", url=" + url + //$NON-NLS-1$ 
			']'; 
	}

	
	/*
	 * Implemented methods
	 */

	@Override
	@XmlElement
	public int getBuild() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(build));
		return build;
	}

	@Override
	@XmlElement
	public Date getCreated() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(created));
		return created;
	}
	
	@Override
	@XmlElement
	public Date getChanged() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(changed));
		return changed;
	}
	
	@Override
	@XmlElement
	public Language getLanguage() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(language));
		return language;
	}
	
	@Override
	@XmlElement
	public String getName() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(name));
		return name;
	}

	@Override
	@XmlElement
	public String getVersion() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(version));
		return version;
	}
	
	@Override
	@XmlElement
	public URL getUrl() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(url));
		return url;
	}
	
	@Override
	public void setBuild(final int build) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(build));

		if (build != this.build) {
			this.build = build;
			setChanged();
			notifyObservers(MEMBER_BUILD);
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void setCreated(final Date created) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(created));

		if (!HelperObject.isEquals(created, this.created)) {
			this.created = created;
			setChanged();
			notifyObservers(MEMBER_CREATED);
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
	
	@Override
	public void setChanged(final Date changed) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(changed));

		if (!HelperObject.isEquals(changed, this.changed)) {
			this.changed = changed;
			setChanged();
			notifyObservers(MEMBER_CREATED);
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
	
	@Override
	public void setLanguage(final Language language) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(language));

		if (!HelperObject.isEquals(language, this.language)) {
			this.language = language;
			setChanged();
			notifyObservers(MEMBER_LANGUAGE);
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
	
	@Override
	public void setName(final String name) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(name));

		if (!HelperObject.isEquals(name, this.name)) {
			this.name = name;
			setChanged();
			notifyObservers(MEMBER_NAME);
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void setVersion(final String version) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(version));

		if (!HelperObject.isEquals(version, this.version)) {
			this.version = version;
			setChanged();
			notifyObservers(MEMBER_VERSION);
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void setUrl(final URL url) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(url));

		if (!HelperObject.isEquals(this.url, url)) {
			this.url = url;
			setChanged();
			notifyObservers(MEMBER_URL);
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	
	/*
	 * Inner classes
	 */

	public static class XmlAdapterDocument extends javax.xml.bind.annotation.adapters.XmlAdapter<DocumentImpl, Document> {

		@Override
		public DocumentImpl marshal(final Document model) {
			return (DocumentImpl) model;
		}

		@Override
		public Document unmarshal(final DocumentImpl model) {
			return model;
		}
	}
}