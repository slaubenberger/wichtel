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

package net.laubenberger.wichtel.helper.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.laubenberger.wichtel.helper.HelperArray;
import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.misc.Constants;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This launcher creates a new process and reads standard output and standard error.
 *
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public abstract class LauncherProcess {
	private static final Logger log = LoggerFactory.getLogger(LauncherProcess.class);

	/**
	 * Creates and starts a new process and reads the standard output and standard error.
	 *
	 * @param outputStream for the standard output of the process
	 * @param errorStream  for the standard error output of the process
	 * @param commands	  arguments to start the process
	 * @return created process
	 * @throws IOException
	 * @see OutputStream
	 * @since 0.0.1
	 */
	public static Process createAndStartProcess(final String[] commands, final OutputStream outputStream, final OutputStream errorStream) throws IOException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(commands, outputStream, errorStream));
//		if (!HelperArray.isValid(commands)) {
//			throw new RuntimeExceptionIsNullOrEmpty("commands"); //$NON-NLS-1$
//		}
		if (null == outputStream) {
			throw new RuntimeExceptionIsNull("outputStream"); //$NON-NLS-1$
		}
		if (null == errorStream) {
			throw new RuntimeExceptionIsNull("errorStream"); //$NON-NLS-1$
		}
		
		final Process result = createAndStartProcess(commands);

		readStandardOutput(result, outputStream, errorStream);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	/**
	 * Creates and starts a new process without reading the standard output and standard error ("fire and forget").
	 *
	 * @param commands arguments to start the process
	 * @return created process
	 * @throws IOException
	 * @since 0.0.1
	 */
	public static Process createAndStartProcess(final String... commands) throws IOException { //$JUnit$
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(new Object[]{commands}));
		if (null == commands) {
			throw new RuntimeExceptionIsNull("commands"); //$NON-NLS-1$
		}
		if (!HelperArray.isValid(commands)) {
			throw new RuntimeExceptionIsEmpty("commands"); //$NON-NLS-1$
		}

		final ProcessBuilder pb = new ProcessBuilder(commands);
		final Process result = pb.start();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}


	/*
	 * Private methods
	 */

	/**
	 * Starts two threads which read standard output and standard error from a process.
	 * <strong>Note:</strong> Standard output and standard error of the process returned by {@link Runtime#exec} must be read else the process might hang infinitly.
	 *
	 * @param process		to observe
	 * @param outputStream If null, output is discarded
	 * @param errorStream  If null, error is discarded
	 * @see Process
	 * @see OutputStream
	 * @since 0.0.1
	 */
	private static void readStandardOutput(final Process process, final OutputStream outputStream, final OutputStream errorStream) {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart(process, outputStream, errorStream));

		new Thread(new StreamReader(process.getErrorStream(), errorStream)).start();
		new Thread(new StreamReader(process.getInputStream(), outputStream)).start();

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}


	/*
	 * Inner classes
	 */

	private static class StreamReader implements Runnable {
		private final InputStream is;
		private final OutputStream os;

		/**
		 * Start new thread which reads from an {@link InputStream} until end-of-file is reached.
		 * The read data is written to an {@link OutputStream}.
		 *
		 * @param source may not be null
		 * @param target may be null
		 */
		StreamReader(final InputStream source, final OutputStream target) {
			super();
			is = source;
			os = target;
		}

		@Override
		public void run() {
			try {
				final byte[] buffer = new byte[Constants.DEFAULT_FILE_BUFFER_SIZE];
				int offset;
				while (-1 != (offset = is.read(buffer))) {
					if (null != os) {
						os.write(buffer, 0, offset);
					}
				}
			} catch (IOException ex) {
				throw new RuntimeException("Could not read the stream of the process", ex); //$NON-NLS-1$
			}
		}
	}
}
