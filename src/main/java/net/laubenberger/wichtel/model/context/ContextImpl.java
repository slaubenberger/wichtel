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

package net.laubenberger.wichtel.model.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.annotation.XmlTransient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.helper.HelperObject;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.model.ModelAbstract;


/**
 * Implementation of the context for applications.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public class ContextImpl extends ModelAbstract implements Context {
	private static final long serialVersionUID = 5570878557994873482L;

	private static final Logger log = LoggerFactory.getLogger(ContextImpl.class);

	private static final Context INSTANCE = new ContextImpl();

	private transient Map<Object, Object> contextData;


	private ContextImpl() {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor());
	}

	public static Context getInstance() {
		return INSTANCE;
	}


	/*
	 * Overridden methods
	 */
	@Override
	public String toString() {
		return getClass().getName() + "[instantiated=" + getInstantiated() + ", isNotifyEnabled=" + isNotifyEnabled() //$NON-NLS-1$ //$NON-NLS-2$
		+ ", uuid=" + getUUID() + ", mapTag=" + getTags() + ", contextData=" + contextData+ ']'; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/*
	 * Implemented methods
	 */

	@Override
	@XmlTransient
	public Map<Object, Object> getData() { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(contextData));
		return contextData;
	}

	@Override
	public void setData(final Map<Object, Object> data) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(data));

		if (!HelperObject.isEquals(data, contextData)) {
			contextData = data;
			setChanged();
			notifyObservers(MEMBER_DATA);
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void addValue(final Object key, final Object value) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(key, value));
		if (null == key) {
			throw new RuntimeExceptionIsNull("key"); //$NON-NLS-1$
		}
		if (null == value) {
			throw new RuntimeExceptionIsNull("value"); //$NON-NLS-1$
		}

		if (null == contextData) {
			contextData = new ConcurrentHashMap<>();
		}
		contextData.put(key, value);
		setChanged();
		notifyObservers(METHOD_ADD_VALUE);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void removeValue(final Object key) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(key));
		if (null == key) {
			throw new RuntimeExceptionIsNull("key"); //$NON-NLS-1$
		}

		if (null != contextData) {
			contextData.remove(key);
			setChanged();
			notifyObservers(METHOD_REMOVE_VALUE);
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public Object getValue(final Object key) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(key));
		if (null == key) {
			throw new RuntimeExceptionIsNull("key"); //$NON-NLS-1$
		}

		Object result = null;
		if (null != contextData) {
			result = contextData.get(key);
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getValue(final Object key, final Class<T> clazz) { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(key, clazz));
		if (null == clazz) {
			throw new RuntimeExceptionIsNull("clazz"); //$NON-NLS-1$
		}

		final Object obj = getValue(key);

		T result = null;

		if (null != obj) {
			if (HelperObject.isEquals(obj.getClass(), clazz)) {
				result = (T) obj;
			}
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}
}
