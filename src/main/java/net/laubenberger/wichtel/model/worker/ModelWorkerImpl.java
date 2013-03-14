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

package net.laubenberger.wichtel.model.worker;


import java.util.ArrayList;
import java.util.List;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.model.ModelAbstract;
import net.laubenberger.wichtel.view.swing.worker.Worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The implementation of the worker model.
 *
 * @author Stefan Laubenberger
 * @version 0.0.2, 2013-03-14
 * @since 0.0.1
 */
public class ModelWorkerImpl extends ModelAbstract implements ModelWorker {
	private static final long serialVersionUID = -2826684498598090349L;

	private static final Logger log = LoggerFactory.getLogger(ModelWorkerImpl.class);

	private transient List<Worker> workers = new ArrayList<>();


	public ModelWorkerImpl() {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor());
	}

	/*
	 * Implemented methods
	 */

	@Override
	public List<Worker> getWorkers() {
		return workers;
	}

	@Override
	public void add(final Worker worker) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(worker));
		if (null == worker) {
			throw new RuntimeExceptionIsNull("worker"); //$NON-NLS-1$
		}

		workers.add(worker);
		setChanged();
		notifyObservers(METHOD_ADD);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void remove(final Worker worker) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(worker));
		if (null == worker) {
			throw new RuntimeExceptionIsNull("worker"); //$NON-NLS-1$
		}

		worker.cancel(true);
		workers.remove(worker);
		setChanged();
		notifyObservers(METHOD_REMOVE);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void removeAll() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		for (final Worker worker : workers) {
			worker.cancel(true);
		}

		workers = new ArrayList<>();
		setChanged();
		notifyObservers(METHOD_REMOVE_ALL);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public int count() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(workers.size()));
		return workers.size();
	}
}