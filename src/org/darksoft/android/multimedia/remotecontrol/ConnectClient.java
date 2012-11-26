package org.darksoft.android.multimedia.remotecontrol;

import java.io.IOException;

/**
 * Abstract class for encapsulate a connection object.
 * Define generic functions for manage connections and send petitions.
 * @author Joel Pelaez Jorge
 *
 */
public abstract class ConnectClient {
	
	/**
	 * Send a PLAY command.
	 * @throws IOException If the connection fail or is closed.
	 */
	abstract void sendPlay() throws IOException;
	
	/**
	 * Send a PAUSE command.
	 * @throws IOException If the connection fail or is closed.
	 */
	abstract void sendPause() throws IOException;
	
	/**
	 * Send a STOP command.
	 * @throws IOException If the connection fail or is closed.
	 */
	abstract void sendStop() throws IOException;
	
	/**
	 * Send a NEXT command.
	 * @throws IOException If the connection fail or is closed.
	 */
	abstract void sendNext() throws IOException;
	
	/**
	 * Send a PREV command.
	 * @throws IOException If the connection fail or is closed.
	 */
	abstract void sendPrev() throws IOException;
	
	/**
	 * Send a "VOL vol" command.
	 * @param vol A int value than represent Volume (0-100);
	 * @throws IOException If the connection fail or is closed.
	 */
	abstract void sendVol(int vol) throws IOException;
	/**
	 * Close actually connection. Used for generic objects.
	 */
	abstract void closeConnection();
}
