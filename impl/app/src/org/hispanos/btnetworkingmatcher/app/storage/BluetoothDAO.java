package org.hispanos.btnetworkingmatcher.app.storage;

import android.bluetooth.BluetoothDevice;
import android.database.sqlite.SQLiteDatabase;

public class BluetoothDAO {
	
	protected final SQLiteDatabase database;
	
	public BluetoothDAO(SQLiteDatabase ddbb) {
		database = ddbb;
	}
	
	public void updateDeviceInfo(BluetoothDevice device) {
		
	}

}
