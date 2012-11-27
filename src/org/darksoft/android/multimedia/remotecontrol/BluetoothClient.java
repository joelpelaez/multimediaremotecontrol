/*
 *  This file is part of Multimedia Remote Control
 *  BluetoothClient.java - Bluetooth client connection interface.
 *  Copyright (C) 2012  Joel Peláez Jorge <joelpelaez@gmail.com>
 * 
 *  Multimedia Remote Control is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Multimedia Remote Control is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Multimedia Remote Control.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.darksoft.android.multimedia.remotecontrol;

import java.io.IOException;
import java.util.UUID;
import java.util.Set;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;

/**
 * It's a subclass of
 * {@link org.darksoft.android.multimedia.remotecontrol.ConnectClient
 * ConnectClient} for manage Bluetooth connections to Remote Control Server.
 * 
 * @author Joel Pelaez Jorge
 * @see ConnectClient
 */

public class BluetoothClient extends ConnectClient {

	// final public UUID mUUIDService =
	// UUID.fromString("3d84ccaa-c215-43a2-8511-0e7cbb40f96c");
	final public UUID mUUIDService = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");
	final public String mNameService = "Open Remote Control";
	private BluetoothDevice mClient = null;
	private BluetoothSocket mSocket = null;
	protected Activity mRootActivity = null;
	protected int mDevNum = -1;
	protected ProgressDialog mProgress;
	private String mClientAddress;

	private Runnable mConnectionRunable = new Runnable() {
		public void run() {
			startConnection(mClientAddress);
		}
	};

	/**
	 * Start a connection a Bluetooth device using a specific MAC Address. This
	 * method execute {@link #startConnection(String)} in another thread.
	 * 
	 * @param address
	 *            A {@link java.lang.String String} with the MAC Address
	 * @param activity
	 *            Actually {@link android.app.Activity Activity}, used for
	 *            invoke {@link android.app.AlertDialog AlertDialogs}.
	 */
	public void connectToDevice(String address, Activity activity) {
		mClientAddress = address;
		mRootActivity = activity;
		new Thread(mConnectionRunable).start();
	}

	/**
	 * Create a simple instance of {@link BluetoothClient}.
	 */
	public BluetoothClient() {
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

	/**
	 * Start a connection a Bluetooth device using a specific MAC Address. This
	 * method is only for use in this class and subclases. For direct call use
	 * {@link #connectToDevice(String, Activity)}.
	 * 
	 * @param address
	 *            A {@link java.lang.String String} with MAC Address of
	 *            Bluetooth's Server Device.
	 */
	protected void startConnection(String address) {
		mClient = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(address);
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
			mSocket = mClient
					.createInsecureRfcommSocketToServiceRecord(mUUIDService);
		} catch (IOException e) {
			mRootActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mProgress.cancel();
					undiscoverServiceError();
				}
			});
			mSocket = null;
			mClient = null;
			return;
		}
		try {
			mSocket.connect();
		} catch (IOException e) {
			mRootActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mProgress.cancel();
					connectionServiceError();
				}
			});
			mSocket = null;
			mClient = null;
			return;
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
	 * Get a {@link java.util.Set Set} object with
	 * {@link android.bluetooth.BluetoothDevice BluetoothDevices} objects with
	 * information of the current paired Bluetooth devices.
	 * 
	 * @return A {@link java.util.Set Set} of paired
	 *         {@link android.bluetooth.BluetooothDevice BluetoothDevice}
	 *         objects.
	 */
	static public Set<BluetoothDevice> getDeviceList() {
		BluetoothAdapter bluetooothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		Set<BluetoothDevice> devices = bluetooothAdapter.getBondedDevices();
		return devices;
	}

	/**
	 * Start a discovery of Bluetooth devices. Use the
	 * {@link android.bluetooth.BluetoothAdapter#startDiscovery()
	 * startDiscovery()} method, so necessary use a {@link android.}
	 * BroadcastReceiver to get a list of the discovered Bluetooth devices.
	 */
	static public void getDiscoveredDevices() {
		BluetoothAdapter bluetooothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		bluetooothAdapter.startDiscovery();
	}

	/**
	 * Cancel a discovery of devices. This method check if a discovery is
	 * running. If true will cancel the discovery, else will do nothing.
	 */
	static public void cancelDiscoveredDevices() {
		BluetoothAdapter bluetooothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		if (bluetooothAdapter.isDiscovering())
			bluetooothAdapter.cancelDiscovery();
	}

	private void undiscoverServiceError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mRootActivity);
		builder.setMessage(R.string.undiscover_service_message);
		builder.setTitle(R.string.undiscover_service_title);
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

	@Override
	public void sendPlay() throws IOException {
		if (mSocket == null)
			return;
		byte[] buffer = null;
		buffer = stringToBytesASCII("PLAY");
		mSocket.getOutputStream().write(buffer);
	}

	@Override
	public void sendPause() throws IOException {
		if (mSocket == null)
			return;
		byte[] buffer = null;
		buffer = stringToBytesASCII("PAUSE");
		mSocket.getOutputStream().write(buffer);
	}

	@Override
	public void sendStop() throws IOException {
		if (mSocket == null)
			return;
		byte[] buffer = null;
		buffer = stringToBytesASCII("STOP");
		mSocket.getOutputStream().write(buffer);
	}

	@Override
	public void sendPrev() throws IOException {
		if (mSocket == null)
			return;
		byte[] buffer = null;
		buffer = stringToBytesASCII("PREV");
		mSocket.getOutputStream().write(buffer);
	}

	@Override
	public void sendNext() throws IOException {
		if (mSocket == null)
			return;
		byte[] buffer = null;
		buffer = stringToBytesASCII("NEXT");
		mSocket.getOutputStream().write(buffer);
	}

	@Override
	public void sendVol(int vol) throws IOException {
		if (mSocket == null)
			return;
		byte[] buffer = null;
		buffer = stringToBytesASCII("VOL " + vol);
		mSocket.getOutputStream().write(buffer);
	}

	@Override
	void closeConnection() {
		if (mSocket == null)
			return;
		try {
			mSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
