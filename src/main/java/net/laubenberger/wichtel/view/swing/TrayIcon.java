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

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JPopupMenu;
import javax.swing.RootPaneContainer;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import net.laubenberger.wichtel.helper.HelperEnvironment;
import net.laubenberger.wichtel.helper.HelperLog;
import net.laubenberger.wichtel.model.misc.Platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is an extended {@link java.awt.TrayIcon} with {@link JPopupMenu} support.
 *
 * @author Stefan Laubenberger
 * @version 0.0.1, 2013-03-05
 * @since 0.0.1
 */
public class TrayIcon extends java.awt.TrayIcon {
	private static final Logger log = LoggerFactory.getLogger(TrayIcon.class);
	
	static final Platform PLATFORM = HelperEnvironment.getPlatform();
	private static final int MAC_MENUBAR_OFFSET = 21;
//	private static final int MAC_MENUBAR_OFFSET = 0;
	
	int counterMouseClicks = 1;
	private JDialog dialog;
	private JPopupMenu popup;
	private final PopupMenuListener popupListener = new PopupMenuListener() {
		@Override
		public void popupMenuWillBecomeVisible(final PopupMenuEvent e) {
			//do nothing
		}

		@Override
		public void popupMenuWillBecomeInvisible(final PopupMenuEvent e) {
			// hideJPopupMenu();
		}

		@Override
		public void popupMenuCanceled(final PopupMenuEvent e) {
			hideJPopupMenu();
		}
	};

	{
		init();
	}

	/*
	 * Superclass constructors
	 */

	public TrayIcon(final Image image, final String tooltip, final PopupMenu popup) {
		super(image, tooltip, popup);
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(image, tooltip, popup));
	}

	public TrayIcon(final Image image, final String tooltip) {
		super(image, tooltip);
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(image, tooltip));
	}

	public TrayIcon(final Image image) {
		super(image);
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(image));
	}
	
	/*
	 * Own constructors
	 */
	
	public TrayIcon(final Image image, final String tooltip, final JPopupMenu popup) {
		this(image, tooltip);
		if (log.isTraceEnabled()) log.trace(HelperLog.constructor(image, tooltip, popup));
		
		setJPopupMenu(popup);
	}

	/**
	 * Returns the {@link JPopupMenu}.
	 *
	 * @return used {@link JPopupMenu}
	 * @see JPopupMenu
	 * @since 0.0.1
	 */
	public JPopupMenu getJPopupMenu() {
		return popup;
	}

	/**
	 * Sets the {@link JPopupMenu}.
	 *
	 * @param popup to be used
	 * @see JPopupMenu
	 * @since 0.0.1
	 */
	public void setJPopupMenu(final JPopupMenu popup) {
		if (null != this.popup) {
			this.popup.removePopupMenuListener(popupListener);
		}
		
		this.popup = popup;

		if (null != this.popup) {
			this.popup.setInvoker(this.popup);
			this.popup.addPopupMenuListener(popupListener);
		}
	}
	
	
	/*
	 * Private methods
	 */
	
	private void init() {
		setImageAutoSize(true);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(final MouseEvent e) {
				if (Platform.MAC_OSX == PLATFORM) {
					if (0 == counterMouseClicks % 2) {
						hideJPopupMenu();
					} else {
						showJPopupMenu(e);
					}
	
					counterMouseClicks++;
				}
			}
			
			@Override
			public void mouseReleased(final MouseEvent e) {
				if (Platform.MAC_OSX != PLATFORM) {
					if (0 == counterMouseClicks % 2) {
						hideJPopupMenu();
					} else {
						showJPopupMenu(e);
					}
	
					counterMouseClicks++;
				}
			}
		});
	}

	void showJPopupMenu(final MouseEvent e) {
		if (null != popup) {
			if (null == dialog) {
				dialog = new JDialog((Frame) null);
				dialog.setUndecorated(true);
				dialog.setAlwaysOnTop(true);
				final Dimension size = popup.getPreferredSize();

				final Point centerPoint = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
				if (e.getY() > centerPoint.getY()) {
					dialog.setLocation(Platform.MAC_OSX == PLATFORM ? e.getXOnScreen() : e.getX(), Platform.MAC_OSX == PLATFORM ? MAC_MENUBAR_OFFSET : e
							.getY()
							- size.height);
				} else {
					dialog.setLocation(Platform.MAC_OSX == PLATFORM ? e.getXOnScreen() : e.getX(), Platform.MAC_OSX == PLATFORM ? MAC_MENUBAR_OFFSET : e
							.getY());
				}

				dialog.setVisible(true);

				popup.show(((RootPaneContainer) dialog).getContentPane(), 0, 0);

				dialog.toFront();
			}
		}
	}

	void hideJPopupMenu() {
		if (null != dialog) {
			dialog.dispose();
			dialog = null;
		}
	}
}
