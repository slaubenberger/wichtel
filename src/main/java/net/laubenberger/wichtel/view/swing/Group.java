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

package net.laubenberger.wichtel.view.swing;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import net.laubenberger.wichtel.helper.HelperLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is a group to add 1-n components (e.g. useful with JButtons).
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public class Group extends JPanel {
	private static final long serialVersionUID = -3557759501854611930L;

	private static final Logger log = LoggerFactory.getLogger(Group.class);


	public Group(final Insets insets, final JComponent... data) {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(insets, data));

		createLayout(data, insets, false);
	}

	public Group(final Insets insets, final JComponent[] data, final boolean isVertical) {
		super();
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(insets, data, isVertical));

		createLayout(data, insets, isVertical);
	}

	public void addActionListener(final ActionListener listener) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(listener));

		final Component[] components = getComponents();
		for (final Component component : components) {
			if (component instanceof AbstractButton) {
				((AbstractButton) component).addActionListener(listener);
			}
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}

	public void removeActionListener(final ActionListener listener) {
		if (log.isDebugEnabled()) log.debug(HelperLog.methodStart(listener));

		final Component[] components = getComponents();
		for (final Component component : components) {
			if (component instanceof AbstractButton) {
				((AbstractButton) component).removeActionListener(listener);
			}
		}

		if (log.isDebugEnabled()) log.debug(HelperLog.methodExit());
	}


	/*
	 * Private methods
	 */

	private void createLayout(final JComponent[] data, final Insets insets, final boolean isVertical) {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());

		if (isVertical) {
			createLayoutVertical(data, insets);
		} else {
			createLayoutHorizontal(data, insets);
		}

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}

	private void createLayoutVertical(final JComponent[] data, final Insets insets) {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());

		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = insets;

		if (null != data) {
			for (final JComponent component : data) {
				gbc.gridy++;
				add(component, gbc);
			}
		}

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}

	private void createLayoutHorizontal(final JComponent[] data, final Insets insets) {
		if (log.isTraceEnabled()) log.trace(HelperLog.methodStart());

		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = insets;

		if (null != data) {
			for (final JComponent component : data) {
				gbc.gridx++;
				add(component, gbc);
			}
		}

		if (log.isTraceEnabled()) log.trace(HelperLog.methodExit());
	}


	/*
	 * Overridden methods
	 */

	@Override
	public void setFont(final Font font) {
		super.setFont(font);

		for (final Component component : getComponents()) {
			component.setFont(font);
		}
	}
}