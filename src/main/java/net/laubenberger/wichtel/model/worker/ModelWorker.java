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

import java.util.List;

import net.laubenberger.wichtel.model.Model;
import net.laubenberger.wichtel.view.swing.worker.Worker;


/**
 * The interface for the worker model.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public interface ModelWorker extends Model {
	String MEMBER_WORKERS 		= "workers"; //$NON-NLS-1$
	String METHOD_ADD 			= "add"; //$NON-NLS-1$
	String METHOD_REMOVE 		= "remove"; //$NON-NLS-1$
	String METHOD_REMOVE_ALL 	= "removeAll"; //$NON-NLS-1$

	List<Worker> getWorkers();

	void add(Worker worker);

	void remove(Worker worker);

	void removeAll();

	int count();
}