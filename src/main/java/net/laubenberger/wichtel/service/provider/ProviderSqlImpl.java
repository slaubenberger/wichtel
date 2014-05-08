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
package net.laubenberger.wichtel.service.provider;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.helper.HelperString;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsEmpty;
import net.laubenberger.wichtel.misc.exception.RuntimeExceptionIsNull;
import net.laubenberger.wichtel.service.ServiceAbstract;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class provides functions to connect and execute statements on SQL-Server.
 *
 * @author Stefan Laubenberger
 * @version 0.1.5, 2014-05-08
 * @since 0.0.1
 */
public class ProviderSqlImpl extends ServiceAbstract implements ProviderSql {
	private static final Logger log = LoggerFactory.getLogger(ProviderSqlImpl.class);

	// Server
	private String driver;
	private String url;
	private String user;
	private String password;


	public ProviderSqlImpl(final String driver, final String url, final String user, final String password) {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(driver, url, user, password));

		setDriver(driver);
		setUrl(url);
		setUser(user);
		setPassword(password);
	}

	/**
	 * Returns the current db driver class.
	 *
	 * @return db driver class
	 * @since 0.0.1
	 */
	public String getDriver() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(driver));
		return driver;
	}

	/**
	 * Returns the current db URL.
	 *
	 * @return db URL
	 * @since 0.0.1
	 */
	public String getUrl() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(url));
		return url;
	}

	/**
	 * Returns the current db user.
	 *
	 * @return db user
	 * @since 0.0.1
	 */
	public String getUser() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(user));
		return user;
	}

	/**
	 * Returns the current db user password.
	 *
	 * @return db user password
	 * @since 0.0.1
	 */
	public String getPassword() {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(password));
		return password;
	}

	/**
	 * Sets the current db driver class
	 *
	 * @param driver db driver class
	 * @since 0.0.1
	 */
	public void setDriver(final String driver) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(driver));
		if (null == driver) {
			throw new RuntimeExceptionIsNull("driver"); //$NON-NLS-1$
		}

		this.driver = driver;

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Sets the current db URL.
	 *
	 * @param url db URL
	 * @since 0.0.1
	 */
	public void setUrl(final String url) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(url));
		if (null == url) {
			throw new RuntimeExceptionIsNull("url"); //$NON-NLS-1$
		}
		this.url = url;

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Sets the current db user.
	 *
	 * @param user for the db
	 * @since 0.0.1
	 */
	public void setUser(final String user) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(user));
//		if (null == user) {
//			throw new RuntimeExceptionIsNull("user"); //$NON-NLS-1$
//		}

		this.user = user;

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	/**
	 * Sets the current db user password.
	 *
	 * @param password for the user/db
	 * @since 0.0.1
	 */
	public void setPassword(final String password) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(password));
//		if (null == password) {
//			throw new RuntimeExceptionIsNull("password"); //$NON-NLS-1$
//		}

		this.password = password;

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}


	/*
	 * Implemented methods
	 */

	@Override
	public Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchMethodException, InvocationTargetException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart());

		Class.forName(driver).getConstructor().newInstance();
		final Connection result = DriverManager.getConnection(url, user, password);

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
		return result;
	}

	@Override
	public int executeUpdate(final String statement) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchMethodException, InvocationTargetException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(statement));
		if (null == statement) {
			throw new RuntimeExceptionIsNull("statement"); //$NON-NLS-1$
		}
		if (!HelperString.isValid(statement)) {
			throw new RuntimeExceptionIsEmpty("statement"); //$NON-NLS-1$
		}

		try (Connection con = getConnection();
			Statement stmt = con.createStatement()) {
			final int result = stmt.executeUpdate(statement);

			if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
			return result;
		}
	}

	@Override
	public boolean execute(final String statement) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NoSuchMethodException, InvocationTargetException {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(statement));
		if (null == statement) {
			throw new RuntimeExceptionIsNull("statement"); //$NON-NLS-1$
		}
		if (!HelperString.isValid(statement)) {
			throw new RuntimeExceptionIsEmpty("statement"); //$NON-NLS-1$
		}

		try (Connection con = getConnection();
				Statement stmt = con.createStatement()) {
			final boolean result = stmt.execute(statement);

			if (log.isDebugEnabled()) log.debug(HelperLog.methodExit(result));
			return result;
		}
	}
}
