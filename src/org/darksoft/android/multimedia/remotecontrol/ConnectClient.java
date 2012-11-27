/*
 *   This file is part of Multimedia Remote Control
 *   ConnectClient.java - Generic client connection interface.
 *   Copyright (C) 2012  Joel Peláez Jorge <joelpelaez@gmail.com>
 * 
 *   Multimedia Remote Control is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 * 
 *   Multimedia Remote Control is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 * 
 *   You should have received a copy of the GNU General Public License
 *   along with Multimedia Remote Control.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.darksoft.android.multimedia.remotecontrol;

import java.io.IOException;

/**
 * Abstract class for encapsulate a connection object. Define generic functions
 * for manage connections and send petitions.
 * 
 * @author Joel Pelaez Jorge
 * 
 */
public abstract class ConnectClient {

	/**
	 * Send a PLAY command.
	 * 
	 * @throws IOException
	 *             If the connection fail or is closed.
	 */
	abstract void sendPlay() throws IOException;

	/**
	 * Send a PAUSE command.
	 * 
	 * @throws IOException
	 *             If the connection fail or is closed.
	 */
	abstract void sendPause() throws IOException;

	/**
	 * Send a STOP command.
	 * 
	 * @throws IOException
	 *             If the connection fail or is closed.
	 */
	abstract void sendStop() throws IOException;

	/**
	 * Send a NEXT command.
	 * 
	 * @throws IOException
	 *             If the connection fail or is closed.
	 */
	abstract void sendNext() throws IOException;

	/**
	 * Send a PREV command.
	 * 
	 * @throws IOException
	 *             If the connection fail or is closed.
	 */
	abstract void sendPrev() throws IOException;

	/**
	 * Send a "VOL vol" command.
	 * 
	 * @param vol
	 *            A int value than represent Volume (0-100);
	 * @throws IOException
	 *             If the connection fail or is closed.
	 */
	abstract void sendVol(int vol) throws IOException;

	/**
	 * Close actually connection. Used for generic objects.
	 */
	abstract void closeConnection();
}
