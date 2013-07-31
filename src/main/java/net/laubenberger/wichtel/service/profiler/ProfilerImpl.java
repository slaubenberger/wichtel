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

package net.laubenberger.wichtel.service.profiler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.service.ServiceAbstract;


/**
 * The implementation of a profiler.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public class ProfilerImpl<T> extends ServiceAbstract implements Profiler<T> {
	private static final Logger log = LoggerFactory.getLogger(ProfilerImpl.class);

	private final Map<T, Long> profiles = new ConcurrentHashMap<>();
	private long meanTime;
	private long elapsedTime;

	public ProfilerImpl() {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor());

		start();
	}


	/*
	 * Implemented methods
	 */

	@Override
	public long getElapsedTime() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		final long result;

        result = 0L == elapsedTime ? System.nanoTime() - meanTime : elapsedTime;
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public Map<T, Long> getProfiles() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(profiles));
		return profiles;
	}

	@Override
	public long profile(final T event) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(event));

		final long currentTime = System.nanoTime();
		final long result = System.nanoTime() - meanTime;
		profiles.put(event, result);

		elapsedTime += result;
		meanTime = currentTime;

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public void start() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		profiles.clear();
		elapsedTime = 0L;
		meanTime = System.nanoTime();
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
}
