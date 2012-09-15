package org.hispanos.btnetworkingmatcher.app.bluetooth;

import org.hispanos.btnetworkingmatcher.app.storage.BluetoothDAO;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class Sniffer {

	protected static final String _TAG = "Bluetooth Sniffer";

	protected final Context context;
	protected BluetoothAdapter btAdpater;
	protected final LocalBroadcastManager lBroadcastMng;
	protected final BluetoothDAO dao;

	public Sniffer(Context ctx, BluetoothDAO btDAO) {
		context = ctx;
		dao = btDAO;
		btAdpater = BluetoothAdapter.getDefaultAdapter();

		// Register the local BroadcastReceivers
		lBroadcastMng = LocalBroadcastManager.getInstance(context);
		lBroadcastMng.registerReceiver(disStartedRecvr, new IntentFilter(
				BluetoothAdapter.ACTION_DISCOVERY_STARTED));
		lBroadcastMng.registerReceiver(disFinishedRecvr, new IntentFilter(
				BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
		lBroadcastMng.registerReceiver(foundRecvr, new IntentFilter(
				BluetoothDevice.ACTION_FOUND));

	}

	public void fetchDeveices() throws BluetoothException {

		if ((!btAdpater.cancelDiscovery()) || (!btAdpater.startDiscovery())) {
			BluetoothException ex = new BluetoothException(
					"The bluetooth device is turned off");
			Log.e(_TAG, "Bluetooth device is turned off", ex);
			throw ex;
		}

	}


	/*
	 * LOCAL BroadcastReceivers
	 */

	private final BroadcastReceiver disStartedRecvr = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

		}

	};

	private final BroadcastReceiver disFinishedRecvr = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

		}

	};

	private final BroadcastReceiver foundRecvr = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

		}

	};

	/************* LOCAL BroadcastReceivers *************/
}
