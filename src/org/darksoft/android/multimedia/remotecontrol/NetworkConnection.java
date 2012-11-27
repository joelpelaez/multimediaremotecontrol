/*
 *   This file is part of Multimedia Remote Control
 *   NetworkConnection.java - Network server connection Activity.
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

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NetworkConnection extends Activity implements View.OnClickListener {

	private Button mButton = null;
	private EditText mAddress = null;
	private EditText mPort = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_network_connection);
		
		mButton = (Button) findViewById(R.id.button_connect);
		mAddress = (EditText) findViewById(R.id.edit_text_address);
		mPort = (EditText) findViewById(R.id.edit_text_port);
		
		mButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_network_connection, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		String addr, port;
		int portnum;
		addr = mAddress.getText().toString();
		port = mPort.getText().toString();
		if (addr.contentEquals("") || port.contentEquals("")) {
			// TODO: Add a AlertDialog than notify 
			setResult(RESULT_CANCELED);
			finish();
			return;
		}
		portnum = Integer.parseInt(port);
		Intent intent = new Intent();
		intent.putExtra("address", addr);
		intent.putExtra("port", portnum);
		setResult(RESULT_OK, intent);
		finish();
	}

}
