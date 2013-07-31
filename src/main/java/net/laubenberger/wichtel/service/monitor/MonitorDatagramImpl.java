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

package net.laubenberger.wichtel.service.monitor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Collection;
import java.util.HashSet;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.helper.HelperNumber;
import net.laubenberger.wichtel.misc.Event;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeGreater;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionMustBeSmaller;
import net.laubenberger.wichtel.service.ServiceAbstract;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Monitor implementation to analyse network packets (UDP) on a given port.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public class MonitorDatagramImpl extends ServiceAbstract implements MonitorDatagram {
	private static final Logger log = LoggerFactory.getLogger(MonitorDatagramImpl.class);
	
	private final Event<MonitorDatagram> event = new Event<MonitorDatagram>(this);

	private Thread thread;

	private final Collection<ListenerDatagram> listeners = new HashSet<>();

	private int port;
	private DatagramSocket socket;
	final byte[] buffer = new byte[Short.MAX_VALUE];
	final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

	private boolean isRunning;


	public MonitorDatagramImpl() {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor());
	}

	public MonitorDatagramImpl(final int port) {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(port));

		setPort(port);
	}

	/**
	 * Returns the current {@link Thread} of the controller.
	 *
	 * @return thread of the controller
	 * @since 0.0.1
	 */
	public Thread getThread() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		return thread;
	}


	/*
	 * Private methods
	 */

	protected void firePacketReceived() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());
		
		for (final ListenerDatagram listener : listeners) {
			listener.packetReceived(event);
		}
		
		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}

	protected void fireStarted() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());
		
		isRunning = true;

		for (final ListenerDatagram listener : listeners) {
			listener.monitorStarted(event);
		}
		
		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}

	protected void fireStopped() {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());
		
		isRunning = false;

		for (final ListenerDatagram listener : listeners) {
			listener.monitorStopped(event);
		}
		
		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}


	/*
	 * Implemented methods
	 */
	
	@Override
	public void run() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		try {
			while (!thread.isInterrupted()) {
				socket.receive(packet);
				
				firePacketReceived();
			}
		} catch (IOException ex) {
			log.error("Could not receive datagrams", ex); //$NON-NLS-1$
		}
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public DatagramPacket getPacket() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(packet));
		return packet;
	}

	@Override
	public int getPort() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(port));
		return port;
	}

	@Override
	public void setPort(final int port) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(port));
		
		if (0 >= port) {
			throw new RuntimeExceptionMustBeGreater("port", port, 0); //$NON-NLS-1$
		}
		if (HelperNumber.NUMBER_65536.intValue() <= port) {
			throw new RuntimeExceptionMustBeSmaller("port", port, 65535); //$NON-NLS-1$
		}

		this.port = port;
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void start() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			throw new RuntimeException(e); //TODO improve this!
		}

		thread = new Thread(this);
		thread.start();

		fireStarted();
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void stop() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		fireStopped();

		if (null != socket && !socket.isClosed()) {
			socket.close();
		}

		if (null != thread) {
			if (thread.isAlive()) {
				thread.interrupt();
//			} else {
//				thread = null;
			}
		}
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public boolean isRunning() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(isRunning));
		return isRunning;
	}

	@Override
	public void addListener(final ListenerDatagram listener) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(listener));
		
		if (null == listener) {
			throw new RuntimeExceptionIsNull("listener"); //$NON-NLS-1$
		}

		listeners.add(listener);
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	@Override
	public void deleteListener(final ListenerDatagram listener) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(listener));
		
		if (null == listener) {
			throw new RuntimeExceptionIsNull("listener"); //$NON-NLS-1$
		}

		listeners.remove(listener);
		
		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}
}
