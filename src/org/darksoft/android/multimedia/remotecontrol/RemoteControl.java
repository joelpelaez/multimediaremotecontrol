package org.darksoft.android.multimedia.remotecontrol;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog;

/**
 * Main Activity for Multimedia Remote Control. Manage all the application.
 * 
 * @author Joel Pelaez Jorge
 * @see BluetoothClient
 * @see BluetoothList
 * 
 */
public class RemoteControl extends Activity {

	private static final int REQUEST_ENABLE_BT = 1;
	private static final int REQUEST_BT_LIST_DEV = 2;

	@SuppressWarnings("unused")
	private boolean mBluetoothEnableHere = false;
	private ConnectClient client = null;
	private Button button_play;
	private Button button_stop;
	private Button button_prev;
	private Button button_next;
	private SeekBar seekBar_vol;

	private OnClickListener mListenerPlay = new OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
				client.sendPlay();
			} catch (IOException e) {
				connectionServiceError();
			} catch (NullPointerException e) {
				notCurrentConnection();
			}
		}
	};

	private OnClickListener mListenerStop = new OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
				client.sendStop();
			} catch (IOException e) {
				connectionServiceError();
			} catch (NullPointerException e) {
				notCurrentConnection();
			}
		}
	};
	private OnClickListener mListenerPrev = new OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
				client.sendPrev();
			} catch (IOException e) {
				connectionServiceError();
			} catch (NullPointerException e) {
				notCurrentConnection();
			}
		}
	};
	private OnClickListener mListenerNext = new OnClickListener() {
		@Override
		public void onClick(View v) {
			try {
				client.sendNext();
			} catch (IOException e) {
				connectionServiceError();
			} catch (NullPointerException e) {
				notCurrentConnection();
			}
		}
	};

	private OnSeekBarChangeListener mListenerVol = new OnSeekBarChangeListener() {
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			try {
				client.sendVol(progress);
			} catch (IOException e) {
				connectionServiceError();
			} catch (NullPointerException e) {
				notCurrentConnection();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remote_control);

		button_play = (Button) findViewById(R.id.button_play);
		button_stop = (Button) findViewById(R.id.button_stop);
		button_prev = (Button) findViewById(R.id.button_prev);
		button_next = (Button) findViewById(R.id.button_next);
		seekBar_vol = (SeekBar) findViewById(R.id.seekBar_vol);

		button_play.setOnClickListener(mListenerPlay);
		button_stop.setOnClickListener(mListenerStop);
		button_prev.setOnClickListener(mListenerPrev);
		button_next.setOnClickListener(mListenerNext);
		seekBar_vol.setOnSeekBarChangeListener(mListenerVol);
	}

	/**
	 * Set a fail connection state and close the client interface and free it.
	 */
	public void failConnection() {
		client.closeConnection();
		client = null;
	}

	protected void notCurrentConnection() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.not_connection_message);
		builder.setTitle(R.string.not_connection_title);
		builder.setNeutralButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_remote_control, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_connect_bluetooth:
			BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
					.getDefaultAdapter();
			if (mBluetoothAdapter == null) {
				Toast.makeText(getApplicationContext(), R.string.non_bluetooth,
						Toast.LENGTH_LONG).show();
			} else if (!mBluetoothAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			} else {
				// ((BluetoothClient) client).getDeviceList(this);
				Intent list = new Intent(this, BluetoothList.class);
				startActivityForResult(list, REQUEST_BT_LIST_DEV);
			}
			break;
		case R.id.menu_connect_inet:
			break;
		case R.id.menu_close_connection:
			if (client != null)
				closeConnection();
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Close the current connection and invalidate it if exist.
	 */
	private void closeConnection() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.close_connection_message);
		builder.setTitle(R.string.close_connection_title);
		builder.setPositiveButton(android.R.string.yes,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						client.closeConnection();
						client = null;
					}
				});
		builder.setNegativeButton(android.R.string.no,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT) {
			if (resultCode == RESULT_OK) {
				Intent list = new Intent(this, BluetoothList.class);
				startActivityForResult(list, REQUEST_BT_LIST_DEV);
			}
		} else if (requestCode == REQUEST_BT_LIST_DEV) {
			if (resultCode == RESULT_OK) {
				String address = data.getExtras().getString("address");
				client = new BluetoothClient();
				((BluetoothClient) client).connectToDevice(address, this);
			}
		}
	}

	/**
	 * Send {@link android.app.AlertDialog AlertDialog} with a Service
	 * Connection Error. Use for local methods.
	 */
	private void connectionServiceError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.connection_error_message);
		builder.setTitle(R.string.connection_error_title);
		builder.setNeutralButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

}
