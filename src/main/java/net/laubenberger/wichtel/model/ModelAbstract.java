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

package net.laubenberger.wichtel.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.helper.HelperObject;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.xml.adapter.MapAdapterString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is the skeleton for all models.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
@XmlRootElement(name = "model")
@XmlType(propOrder = {Model.MEMBER_INSTANTIATED, Model.MEMBER_UUID, Model.MEMBER_TAGS})
public abstract class ModelAbstract extends Observable implements Model {
	private static final long serialVersionUID = 3491320587479082917L;

	private static final Logger log = LoggerFactory.getLogger(ModelAbstract.class);

	private Map<String, String> mapTag;

	private boolean isNotifyEnabled = true;
	private Date instantiated = new Date();
	private UUID uuid;
	
	protected ModelAbstract() {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor());
	}

//	protected ModelAbstract(final UUID uuid, final Map<String, String> mapTag) {
//		super();
//		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(uuid, mapTag));
//		this.uuid = uuid;
//		this.mapTag = mapTag;
//	}


	/*
	 * Overridden methods
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((null == instantiated) ? 0 : instantiated.hashCode());
		result = prime * result + (isNotifyEnabled ? 1231 : 1237);
		result = prime * result + ((null == mapTag) ? 0 : mapTag.hashCode());
		result = prime * result + ((null == uuid) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) return true;
		if (null == obj) return false;
		if (getClass() != obj.getClass()) return false;
		final ModelAbstract other = (ModelAbstract) obj;
		if (null == instantiated) {
			if (null != other.instantiated) return false;
		} else if (!instantiated.equals(other.instantiated)) return false;
		if (isNotifyEnabled != other.isNotifyEnabled) return false;
		if (null == mapTag) {
			if (null != other.mapTag) return false;
		} else if (!mapTag.equals(other.mapTag)) return false;
		if (null == uuid) {
			if (null != other.uuid) return false;
		} else if (!uuid.equals(other.uuid)) return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getName() + "[instantiated=" + instantiated +  //$NON-NLS-1$
			", isNotifyEnabled=" + isNotifyEnabled + //$NON-NLS-1$
			", uuid=" + uuid +  //$NON-NLS-1$
			", mapTag=" + mapTag +  //$NON-NLS-1$
			']';
	}
	
	@Override
	public void notifyObservers() {
		if (isNotifyEnabled) {
			super.notifyObservers();
		}
	}

	@Override
	public void notifyObservers(final Object arg) {
		if (isNotifyEnabled) {
			super.notifyObservers(arg);
		}
	}


	/*
	 * Implemented methods
	 */

	@Override
	@XmlTransient
	public boolean isNotifyEnabled() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(isNotifyEnabled));
		return isNotifyEnabled;
	}

	@Override
	@XmlElement
	public Date getInstantiated() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(instantiated));
		return instantiated;
	}

	@Override
	@XmlElement
	@XmlJavaTypeAdapter(MapAdapterString.class)
	public Map<String, String> getTags() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(mapTag));
		return mapTag;
	}

	@Override
	public String getTag(final String key) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(key));
		if (null == key) {
			throw new RuntimeExceptionIsNull("key"); //$NON-NLS-1$
		}

		String result = null;
		if (null != mapTag) {
			result = mapTag.get(key);
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	@XmlElement
	public UUID getUUID() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(uuid));
		return uuid;
	}
	
	@Override
	public void setNotifyEnabled(final boolean enabled) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(enabled));

		isNotifyEnabled = enabled;

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void setInstantiated(final Date instantiated) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(instantiated));

		if (!HelperObject.isEquals(this.instantiated, instantiated)) {
			this.instantiated = instantiated;
			setChanged();
			notifyObservers(MEMBER_INSTANTIATED);
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void setTags(final Map<String, String> tags) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(tags));

		if (!HelperObject.isEquals(tags, mapTag)) {
			mapTag = tags;
			setChanged();
			notifyObservers(MEMBER_TAGS);
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void setUUID(final UUID uuid) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(uuid));

		if (!HelperObject.isEquals(uuid, this.uuid)) {
			this.uuid = uuid;
			setChanged();
			notifyObservers(MEMBER_UUID);
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
	
	@Override
	public void addTag(final String key, final String value) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(key, value));
		if (null == key) {
			throw new RuntimeExceptionIsNull("key"); //$NON-NLS-1$
		}

		if (null == mapTag) {
			mapTag = new HashMap<>();
		}
		mapTag.put(key, value);
		setChanged();
		notifyObservers(METHOD_ADD_TAG);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
//
//	@Override
//	public void removeTag(final String key) {
//		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(key));
//		if (null == key) {
//			throw new RuntimeExceptionIsNull("key"); //$NON-NLS-1$
//		}
//
//		if (null != mapTag) {
//			mapTag.remove(key);
//			setChanged();
//			notifyObservers(METHOD_REMOVE_TAG);
//		}
//
//		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
//	}
}