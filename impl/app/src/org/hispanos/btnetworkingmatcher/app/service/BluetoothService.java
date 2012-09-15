package org.hispanos.btnetworkingmatcher.app.service;

import org.hispanos.btnetworkingmatcher.app.R;
import org.hispanos.btnetworkingmatcher.app.bluetooth.BluetoothException;
import org.hispanos.btnetworkingmatcher.app.bluetooth.Sniffer;
import org.hispanos.btnetworkingmatcher.app.storage.AppDAOFactory;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BluetoothService extends Service {

	private Sniffer btSniffer;
	private SharedPreferences preferences;

	static String LOG_TAG = "BT_SERVICE";

	/**
	 * Class for clients to access.  Because we know this service always
	 * runs in the same process as its clients, we don't need to deal with
	 * IPC.
	 */
	public class LocalBinder extends Binder {
		BluetoothService getService() {
			return BluetoothService.this;
		}
	}

	// This is the object that receives interactions from clients.  See
	// RemoteService for a more complete example.
	private final IBinder mBinder = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@Override
	public void onCreate() {
		AppDAOFactory appDAO = new AppDAOFactory(this.getApplicationContext());
		btSniffer = new Sniffer(this.getApplicationContext(), appDAO.getBluetoothDAO());
		preferences = getApplicationContext().getSharedPreferences(getString(R.string.prefs_name),0);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(LOG_TAG, "Received start id " + startId + ": " + intent);
		preferences.edit()
		.putBoolean(getString(R.string.service), true)
		.commit();

		final Resources res = getResources();

		new Thread(new Runnable(){
			public void run() {

				String var_delay = res.getString(R.string.pref_delay);
				int delay = preferences.getInt(var_delay, 60000);
				while(true)
				{
					try {
						btSniffer.fetchDeveices();
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BluetoothException bte) {
						bte.printStackTrace();
						killService();
						break;
					}
				}

			}
		}).start();

		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}
	
	public void killService()
	{
		preferences.edit()
		.putBoolean(getString(R.string.service), false)
		.commit();
		this.stopSelf();
	}

	@Override
	public void onDestroy() {
		Log.i(LOG_TAG, "Service Stoppped");
		preferences.edit()
		.putBoolean(getString(R.string.service), false)
		.commit();
	}
}
