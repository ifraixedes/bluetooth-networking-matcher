package org.hispanos.btnetworkingmatcher.app.storage;

import java.util.ArrayList;
import java.util.HashMap;

import android.bluetooth.BluetoothDevice;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BluetoothDAO {

	protected final SQLiteDatabase database;

	protected static final String _BT_DEVICES_TABLE_NAME = "BluetoothDevices";
	protected static final String _BT_DEVICES_COL_MAC_ADDR = "macAddr";
	protected static final String _BT_DEVICES_COL_PUBLIC_NAME = "pubName";
	protected static final String _BT_DEVICES_COL_MATCHES_COUNTER = "matches";

	public BluetoothDAO(SQLiteDatabase ddbb) {
		database = ddbb;
	}

	// TODO Implemented to arrive to the hackathon end-time -- REFACTORING
	public ArrayList<HashMap<String, String>> getMatchedDevicesInfo() {

		Cursor queryCursor = database.query(_BT_DEVICES_TABLE_NAME, null, null,
				null, null, null, null);

		ArrayList<HashMap<String, String>> devicesInfo = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> deviceInfo;		
		
		while (queryCursor.moveToNext()) {
			deviceInfo = new HashMap<String, String>();
			deviceInfo.put("MAC ADDRESS", queryCursor.getString(0));
			deviceInfo.put("PUBLIC NAME", queryCursor.getString(1));
			deviceInfo.put("NUMBER OF MATCHES", Integer.toString(queryCursor.getInt(2)));
		}
		
		return devicesInfo;
	}

	public int getDevicesMatches(BluetoothDevice device) {
		Cursor queryCursor = database.query(_BT_DEVICES_TABLE_NAME,
				new String[] { "_BT_DEVICES_COL_MATCHES_COUNTER" },
				_BT_DEVICES_COL_MAC_ADDR + "='?'",
				new String[] { device.getAddress() }, null, null, null);

		if (queryCursor.getCount() == 0) {
			return 0;
		} else {
			queryCursor.moveToNext();
			return queryCursor.getInt(0);
		}
	}

	public void updateDeviceInfo(BluetoothDevice device) {

		int deviceMatches = getDevicesMatches(device);

		ContentValues btFieldsAndValues = new ContentValues();
		btFieldsAndValues.put(_BT_DEVICES_COL_MAC_ADDR, device.getAddress());
		btFieldsAndValues.put(_BT_DEVICES_COL_PUBLIC_NAME, device.getName());

		if (deviceMatches > 0) { // Update

			btFieldsAndValues.put(_BT_DEVICES_COL_MATCHES_COUNTER,
					++deviceMatches);

			database.update(_BT_DEVICES_TABLE_NAME, btFieldsAndValues,
					_BT_DEVICES_COL_MAC_ADDR + "='?'",
					new String[] { device.getAddress() });

		} else { // New device, so register it
			btFieldsAndValues.put(_BT_DEVICES_COL_MATCHES_COUNTER,
					++deviceMatches);

			// TODO check if error happens
			database.insert(_BT_DEVICES_TABLE_NAME, null, btFieldsAndValues);
		}

	}

}
