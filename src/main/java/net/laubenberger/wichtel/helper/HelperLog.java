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

package net.laubenberger.wichtel.helper;


/**
 * Helper class for logging.
 * 
 * @author Stefan Laubenberger
 * @version 0.1.0, 2013-07-31
 * @since 0.0.1
 */
public abstract class HelperLog {
	private static final String ID_APPLICATION_START = "+++"; //$NON-NLS-1$
	private static final String ID_APPLICATION_EXIT = "---"; //$NON-NLS-1$
	private static final String ID_METHOD_START = ">>>"; //$NON-NLS-1$
	private static final String ID_METHOD_EXIT = "<<<"; //$NON-NLS-1$
	private static final String ID_CONSTRUCTOR = "***"; //$NON-NLS-1$

	private static final int LINE_LENGTH = 80;

	private static final String NULL = " null"; //$NON-NLS-1$
	private static final String EMPTY = " empty"; //$NON-NLS-1$

	public static String applicationStart(final String name, final String version) { // $JUnit$
		final StringBuilder sb = new StringBuilder();

		sb.append(ID_APPLICATION_START);
		sb.append(HelperString.NEW_LINE);

		if (null != name || null != version) {
			// Application
			final String application = "### Application: "; //$NON-NLS-1$
			sb.append(application);
			sb.append(HelperString.fill('#', LINE_LENGTH - application.length()));

			if (null != name) {
				sb.append(HelperString.NEW_LINE);
				sb.append("name=" + name); //$NON-NLS-1$
			}
			if (null != name) {
				sb.append(HelperString.NEW_LINE);
				sb.append("version=" + version); //$NON-NLS-1$
			}
			sb.append(HelperString.NEW_LINE);
		}

		sb.append(getReport());

		sb.append(HelperString.fill('-', LINE_LENGTH));

		return sb.toString();
	}

	public static String applicationExit(final int returnCode) { // $JUnit$
		return ID_APPLICATION_EXIT + HelperString.SPACE + returnCode;
	}

	public static String methodStart() {
		return ID_METHOD_START;
	}

	public static String methodStart(final Object... args) { // $JUnit$
		final StringBuilder sb = new StringBuilder();
		sb.append(ID_METHOD_START);

		sb.append(getArgsAsString(args));

		return sb.toString();
	}

	public static String methodExit() { // $JUnit$
		return ID_METHOD_EXIT;
	}

	public static String methodExit(final Object arg) { // $JUnit$
		return ID_METHOD_EXIT + HelperString.SPACE + arg;
	}

	public static String constructor() {
		return ID_CONSTRUCTOR;
	}

	public static String constructor(final Object... args) { // $JUnit$
		final StringBuilder sb = new StringBuilder();
		sb.append(ID_CONSTRUCTOR);

		sb.append(getArgsAsString(args));

		return sb.toString();
	}

	/*
	 * Private methods
	 */

	private static String getArgsAsString(final Object... args) {
		if (null != args) {
			final StringBuilder sb = new StringBuilder();
			if (0 == args.length) {
				sb.append(EMPTY);
			} else {
				for (final Object obj : args) {
					if (0 < sb.length()) {
						sb.append(HelperString.COMMA);
					}
					sb.append(HelperString.SPACE);
					sb.append(String.valueOf(obj));
				}
			}
			return sb.toString();
		}

		return NULL;
	}

	private static String getReport() {
		final StringBuilder sb = new StringBuilder();

		// Java
		final String java = "### Java: "; //$NON-NLS-1$
		sb.append(java);
		sb.append(HelperString.fill('#', LINE_LENGTH - java.length()));
		sb.append(HelperString.NEW_LINE);
		sb.append(HelperMap.dump(HelperEnvironment.getReportJava()));
		sb.append(HelperString.NEW_LINE);

		// OS
		final String os = "### Operating system: "; //$NON-NLS-1$
		sb.append(os);
		sb.append(HelperString.fill('#', LINE_LENGTH - os.length()));
		sb.append(HelperString.NEW_LINE);
		sb.append(HelperMap.dump(HelperEnvironment.getReportOS()));
		sb.append(HelperString.NEW_LINE);

		// User
		final String user = "### User: "; //$NON-NLS-1$
		sb.append(user);
		sb.append(HelperString.fill('#', LINE_LENGTH - user.length()));
		sb.append(HelperString.NEW_LINE);
		sb.append(HelperMap.dump(HelperEnvironment.getReportUser()));
		sb.append(HelperString.NEW_LINE);

		// System
		final String system = "### System: "; //$NON-NLS-1$
		sb.append(system);
		sb.append(HelperString.fill('#', LINE_LENGTH - system.length()));
		sb.append(HelperString.NEW_LINE);
		sb.append(HelperMap.dump(HelperEnvironment.getReportSystem()));

		return sb.toString();
	}
}