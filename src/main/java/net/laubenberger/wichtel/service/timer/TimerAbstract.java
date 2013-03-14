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

package net.laubenberger.wichtel.service.timer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.misc.Event;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.service.ServiceAbstract;

/**
 * This is a timer which informs all added listeners about its state.
 * 
 * @author Stefan Laubenberger
 * @version 0.0.2, 2013-03-14
 * @since 0.0.1
 */
public abstract class TimerAbstract extends ServiceAbstract implements TimeMachine {
	private static final Logger log = LoggerFactory.getLogger(TimerAbstract.class);

	static final long DEFAULT_INTERVAL = 1000L;
	
	private final Collection<ListenerTimer> listeners = new HashSet<>();

	private final Event<TimeMachine> event = new Event<TimeMachine>(this);

	private Timer timer = new Timer();
	private long interval;

	private boolean isRunning;

	// protected TimerAbstract() {
	// super();
	// }

	/**
	 * Returns the current {@link Timer}.
	 * 
	 * @return current timer
	 * @see Timer
	 * @since 0.0.1
	 */
	public Timer getTimer() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(timer));
		return timer;
	}

	/**
	 * Sets the current {@link Timer}.
	 * 
	 * @param timer
	 *           for the implementation
	 * @see Timer
	 * @since 0.0.1
	 */
	public void setTimer(final Timer timer) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(timer));
		if (null == timer) {
			throw new RuntimeExceptionIsNull("timer"); //$NON-NLS-1$
		}

		this.timer = timer;

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/*
	 * Private methods
	 */

	protected void fireTimeChanged() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());

		for (final ListenerTimer listener : listeners) {
			listener.timeChanged(event);
		}

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}

	protected void fireStarted() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());

		isRunning = true;

		for (final ListenerTimer listener : listeners) {
			listener.timerStarted(event);
		}

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}

	protected void fireStopped() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());

		isRunning = false;

		for (final ListenerTimer listener : listeners) {
			listener.timerStopped(event);
		}

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}

	protected void setInterval(final long interval) {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart(interval));

		this.interval = interval;

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}

	/*
	 * Implemented methods
	 */

	@Override
	public boolean isRunning() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(isRunning));
		return isRunning;
	}

	@Override
	public long getInterval() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(interval));
		return interval;
	}

	@Override
	public void addListener(final ListenerTimer listener) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(listener));
		if (null == listener) {
			throw new RuntimeExceptionIsNull("listener"); //$NON-NLS-1$
		}

		listeners.add(listener);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void deleteListener(final ListenerTimer listener) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(listener));
		if (null == listener) {
			throw new RuntimeExceptionIsNull("listener"); //$NON-NLS-1$
		}

		listeners.remove(listener);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
}
