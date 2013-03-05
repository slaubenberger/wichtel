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

package net.laubenberger.wichtel.view.swing.worker;

import java.util.Collection;
import java.util.HashSet;

import javax.swing.SwingWorker;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.misc.Event;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents a skeleton for the worker.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public abstract class WorkerAbstract<T, V> extends SwingWorker<T, V> implements Worker {
	private static final Logger log = LoggerFactory.getLogger(WorkerAbstract.class);

	private final Collection<ListenerWorker> listeners = new HashSet<ListenerWorker>();

	private final Event<Worker> event = new Event<Worker>(this);

	protected WorkerAbstract() {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor());
	}

   @Override
   protected void done() {
      fireWorkerDone(); 
   }
   
   
	/*
	 * Private methods
	 */

	protected void fireWorkerStart() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());

		for (final ListenerWorker listener : listeners) {
			listener.start(event);
		}

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}

	protected void fireWorkerDone() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());

		for (final ListenerWorker listener : listeners) {
			listener.done(event);
		}

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}

	
	/*
	 * Overridden methods
	 */

	@Override
	public String toString() {
		return getClass().getName() + "[listeners=" + listeners + ']'; //$NON-NLS-1$
	}
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((event == null) ? 0 : event.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) return true;
//		if (obj == null) return false;
//		if (getClass() != obj.getClass()) return false;
//		WorkerAbstract other = (WorkerAbstract) obj;
//		if (event == null) {
//			if (other.event != null) return false;
//		} else if (!event.equals(other.event)) return false;
//		return true;
//	}

	/*
	 * Implemented methods
	 */
	@Override
	public void addListener(final ListenerWorker listener) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(listener));
		if (null == listener) {
			throw new RuntimeExceptionIsNull("listener"); //$NON-NLS-1$
		}

		listeners.add(listener);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void deleteListener(final ListenerWorker listener) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(listener));
		if (null == listener) {
			throw new RuntimeExceptionIsNull("listener"); //$NON-NLS-1$
		}

		listeners.remove(listener);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
}
