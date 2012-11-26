package org.darksoft.android.multimedia.remotecontrol;

import org.darksoft.android.lib.widget.ExpandListChild;

/**
 * A app-specific subclass of ExpandListChild used for contain Bluetooth Address information.
 * @author Joel Pelaez Jorge
 *
 */
public class BluetoothExpandListChild extends ExpandListChild {
	private String address;
	
	/**
	 * Set a MAC Address of a Bluetooth Device to reference.
	 * @param addr A Bluetooth MAC Address.
	 */
	public void setAddress(String addr) {
		address = addr;
	}
	
	/**
	 * Get the MAC Address of the {@link BluetoothExpandListChild} object.
	 * @return The MAC Address.
	 */
	public String getAddress() {
		return address;
	}
}
