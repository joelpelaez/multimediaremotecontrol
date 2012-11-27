/*
 *   This file is part of Multimedia Remote Control
 *   InetClient.java - Network client connection interface.
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
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

/**
 * It's a subclass of
 * {@link org.darksoft.android.multimedia.remotecontrol.ConnectClient
 * ConnectClient} for manage network connections to Remote Control Server
 * 
 * @author Joel Pelaez Jorge
 * 
 */
public class InetClient extends ConnectClient {

	private Activity mRootActivity;
	private Socket mSocket;
	private String mHostAddr;
	private int mHostPort;
	private ProgressDialog mProgress;

	private Runnable mConenctionRunnable = new Runnable() {
		@Override
		public void run() {
			startConnection();
		}
	};

	public InetClient() {
	}

	/**
	 * Start a TCP/IP connection to Remote Control Server. This method is only
	 * for use in the class and subclass. For direct call use
	 * {@link #connectToServer(String, int, Activity)}.
	 */
	protected void startConnection() {
		mRootActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				String title = mRootActivity.getResources().getString(
						R.string.loading_title);
				String message = mRootActivity.getResources().getString(
						R.string.loading_message);
				mProgress = ProgressDialog.show(mRootActivity, title, message,
						true);
			}
		});
		try {
			mSocket = new Socket(mHostAddr, mHostPort);
		} catch (UnknownHostException e) {
			mRootActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mProgress.cancel();
					unknownHostnameError();
				}
			});
			mSocket = null;
		} catch (IOException e) {
			mRootActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mProgress.cancel();
					connectionServiceError();
				}
			});
			mSocket = null;
		}
		mRootActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mProgress.cancel();
				connectionSuccessful();
			}
		});
	}

	/**
	 * Connect to a specific server using IP address or hostname. This method
	 * execute {@link #startConnection()} in another thread.
	 * 
	 * @param addr
	 *            A IP address or hostname of the server
	 * @param port
	 *            Port number used for the server.
	 * @param activity
	 *            Root {@link android.app.Activity Activity} used to show
	 *            {@link android.app.AlertDialog AlertDialogs}.
	 */
	public void connectToServer(String addr, int port, Activity activity) {
		this.mHostAddr = addr;
		this.mHostPort = port;
		this.mRootActivity = activity;
		new Thread(mConenctionRunnable).start();
	}

	/**
	 * Convert a {@link java.lang.String String} to a byte array for send on a
	 * socket.
	 * 
	 * @param str
	 *            A simple {@link java.lang.String String} object.
	 * @return A raw byte array using a {@link java.lang.String String}.
	 */
	public static byte[] stringToBytesASCII(String str) {
		char[] buffer = str.toCharArray();
		byte[] b = new byte[buffer.length];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) buffer[i];
		}
		return b;
	}

	@Override
	void sendPlay() throws IOException {
		if (mSocket == null)
			return;
		byte[] buffer = null;
		buffer = stringToBytesASCII("PLAY");
		mSocket.getOutputStream().write(buffer);
	}

	@Override
	void sendPause() throws IOException {
		if (mSocket == null)
			return;
		byte[] buffer = null;
		buffer = stringToBytesASCII("PAUSE");
		mSocket.getOutputStream().write(buffer);
	}

	@Override
	void sendStop() throws IOException {
		if (mSocket == null)
			return;
		byte[] buffer = null;
		buffer = stringToBytesASCII("STOP");
		mSocket.getOutputStream().write(buffer);
	}

	@Override
	void sendNext() throws IOException {
		if (mSocket == null)
			return;
		byte[] buffer = null;
		buffer = stringToBytesASCII("NEXT");
		mSocket.getOutputStream().write(buffer);
	}

	@Override
	void sendPrev() throws IOException {
		if (mSocket == null)
			return;
		byte[] buffer = null;
		buffer = stringToBytesASCII("PREV");
		mSocket.getOutputStream().write(buffer);
	}

	@Override
	void sendVol(int vol) throws IOException {
		if (mSocket == null)
			return;
		byte[] buffer = null;
		buffer = stringToBytesASCII("VOL " + vol);
		mSocket.getOutputStream().write(buffer);
	}

	@Override
	void closeConnection() {
		try {
			mSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void connectionServiceError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mRootActivity);
		builder.setMessage(R.string.connection_error_message);
		builder.setTitle(R.string.connection_error_title);
		builder.setNeutralButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						((RemoteControl) mRootActivity).failConnection();
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void unknownHostnameError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mRootActivity);
		builder.setMessage(R.string.unknown_hostname_message);
		builder.setTitle(R.string.unknown_hostname_title);
		builder.setNeutralButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						((RemoteControl) mRootActivity).failConnection();
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void connectionSuccessful() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mRootActivity);
		builder.setMessage(R.string.successful_message);
		builder.setTitle(R.string.successful_title);
		builder.setNeutralButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
