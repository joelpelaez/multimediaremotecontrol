/*
 *   This file is part of Multimedia Remote Control
 *   BluetoothExpandList.java - ExpandList subclass.
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

import org.darksoft.android.lib.widget.ExpandListChild;

/**
 * A app-specific subclass of ExpandListChild used for contain Bluetooth Address
 * information.
 * 
 * @author Joel Pelaez Jorge
 * 
 */
public class BluetoothExpandListChild extends ExpandListChild {
	private String address;

	/**
	 * Set a MAC Address of a Bluetooth Device to reference.
	 * 
	 * @param addr
	 *            A Bluetooth MAC Address.
	 */
	public void setAddress(String addr) {
		address = addr;
	}

	/**
	 * Get the MAC Address of the {@link BluetoothExpandListChild} object.
	 * 
	 * @return The MAC Address.
	 */
	public String getAddress() {
		return address;
	}
}
