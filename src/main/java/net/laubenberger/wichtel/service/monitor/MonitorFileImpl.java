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
package net.laubenberger.wichtel.service.monitor;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.misc.Event;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;
import net.laubenberger.wichtel.model.crypto.HashCodeAlgo;
import net.laubenberger.wichtel.service.ServiceAbstract;
import net.laubenberger.wichtel.service.crypto.HashCodeGenerator;
import net.laubenberger.wichtel.service.crypto.HashCodeGeneratorImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Monitor implementation for files.
 * <strong>Note:</strong> This class needs <a href="http://www.bouncycastle.org/">BouncyCastle</a> to work.
 * 
 * @author Stefan Laubenberger
 * @version 0.0.2, 2013-03-14
 * @since 0.0.1
 */
public class MonitorFileImpl extends ServiceAbstract implements MonitorFile {
	private static final Logger log = LoggerFactory.getLogger(MonitorFileImpl.class);

	private long interval = 5000L;

	private final Collection<ListenerFileChanged> listeners = new HashSet<>();

	private final Event<MonitorFile> event = new Event<MonitorFile>(this);

	final File file;
	private Timer timer = new Timer();

	final HashCodeGenerator hcg;
	
	private boolean isRunning = false;
	
	
	public MonitorFileImpl(final File file) throws NoSuchAlgorithmException {
		super();
		if (log.isTraceEnabled()) {
			log.trace(HelperLog.constructor(file));
		}

		if (null == file) {
			throw new RuntimeExceptionIsNull("file"); //$NON-NLS-1$
		}

		this.file = file;
		hcg = new HashCodeGeneratorImpl(HashCodeAlgo.SHA256);
	}

	
	/*
	 * Private methods
	 */

	protected void fireFileChanged() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());

		for (final ListenerFileChanged listener : listeners) {
			listener.fileChanged(event);
		}

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}

	protected void fireFileNotFound() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());

		for (final ListenerFileChanged listener : listeners) {
			listener.fileNotFound(event);
		}

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}	
	
	protected void fireStarted() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());
		
		isRunning = true;

		for (final ListenerFileChanged listener : listeners) {
			listener.monitorStarted(event);
		}
		
		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}

	protected void fireStopped() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());
		
		isRunning = false;

		for (final ListenerFileChanged listener : listeners) {
			listener.monitorStopped(event);
		}
		
		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}	
	
	/*
	 * Implemented methods
	 */
	@Override
	public void start(final long delay, final long interval) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(delay, interval));
		if (0L > delay) {
			throw new RuntimeExceptionMustBeGreater("delay", delay, 0); //$NON-NLS-1$
		}
		if (0L > interval) {
			throw new RuntimeExceptionMustBeGreater("interval", interval, 0); //$NON-NLS-1$
		}
		
		this.interval = interval;
		
		if (isRunning()) {
			timer.cancel();
		}
		
		timer = new Timer();

		final FileMonitorTask task = new FileMonitorTask();
		timer.schedule(task, delay, interval);

		fireStarted();
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
	
	@Override
	public void start(final long interval) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(interval));
		
		start(0L, interval);
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());	
	}

	@Override
	public void start() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		start(interval);
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());	
	}

	@Override
	public void stop() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (isRunning) {
			timer.cancel();
		}
		fireStopped();
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
	
	@Override
	public boolean isRunning() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(isRunning));
		return isRunning;
	}

	@Override
	public File getFile() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(file));
		return file;
	}

	@Override
	public long getInterval() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(interval));
		return interval;
	}
	
	@Override
	public void addListener(final ListenerFileChanged listener) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(listener));
		if (null == listener) {
			throw new RuntimeExceptionIsNull("listener"); //$NON-NLS-1$
		}

		listeners.add(listener);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void deleteListener(final ListenerFileChanged listener) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(listener));
		if (null == listener) {
			throw new RuntimeExceptionIsNull("listener"); //$NON-NLS-1$
		}

		listeners.remove(listener);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	
	/*
	 * Inner classes
	 */
	
	class FileMonitorTask extends TimerTask {
		byte[] hash;
		
		FileMonitorTask() {
			super();
			try {
				hash = hcg.getFastHash(file);
			} catch (IOException ex) {
//				ex.printStackTrace();
//				fireFileNotFound();
			}
		}

		@Override
		public void run() {
			try {
				final byte[] hash = hcg.getFastHash(file);
				
				if (!Arrays.equals(this.hash, hash)) {
					this.hash = hash;
					fireFileChanged();
				}
			} catch (IOException ex) {
//				ex.printStackTrace();
				fireFileNotFound();
			}
		}
	}
}