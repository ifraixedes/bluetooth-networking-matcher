package org.hispanos.btnetworkingmatcher.app.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Sniffer {
	
	protected Context appContext;
	protected BluetoothAdapter btAdpater;
	
	// Local BroadcastReceivers
	protected DiscoveryStartedReceiver disStartedRecvr = new DiscoveryStartedReceiver();
	protected DiscoveryFinishedReceiver disfinishedRecvr = new DiscoveryFinishedReceiver();
	protected FoundReceiver foundRecvr = new FoundReceiver();
	
	

	public Sniffer(Context context) {
		appContext = context;
		btAdpater = BluetoothAdapter.getDefaultAdapter();
		
		// Initialise the local BroadcastReceivers
		
		
	}
	
	public void fetchDeveices() throws BluetoothException {
		
		if ((!btAdpater.cancelDiscovery()) || (!btAdpater.startDiscovery())) {
			throw new BluetoothException("The bluetooth device is turn off");
		}
		
		
	}
	
	
	protected void initLocalBroadcastReceivers() {
		
	}
	
	protected class DiscoveryStartedReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	protected class DiscoveryFinishedReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	protected class FoundReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
}
