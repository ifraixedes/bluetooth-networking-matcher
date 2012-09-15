package org.hispanos.btnetworkingmatcher.app;

import org.hispanos.btnetworkingmatcher.app.service.BluetoothService;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnItemSelectedListener {

	ToggleButton tb;
	BluetoothAdapter mBluetoothAdapter;
	
	SharedPreferences preferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		preferences = getApplicationContext().getSharedPreferences(getString(R.string.prefs_name),0);
		
		setupBluetoothToggleButton();
		setupFrenquencySpinner();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		setButtonSearchingStatus((Button) findViewById(R.id.searching_button));
		
		setBluetoothToggleStatus();
	}
	
	private void setBluetoothToggleStatus() {
		if(bluetoothIsEnabled()) {
			tb.setChecked(true);
		} else {
			tb.setChecked(false);
		}
	}

	private void setupBluetoothToggleButton() {
		tb = (ToggleButton) findViewById(R.id.bluetooth_toggle_button);
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		if (mBluetoothAdapter == null) {
			//@TODO close the App and explain is not possible to use without Bluetooth
		}
	}

	private void setupFrenquencySpinner() {
		Spinner spinner = (Spinner) findViewById(R.id.frequency_spinner);
		spinner.setOnItemSelectedListener(this);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.frequency_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onClickToggleBluetooth(View v) {
		if (!bluetoothIsEnabled()) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, 5);
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
			startActivity(discoverableIntent);
		} else {
			mBluetoothAdapter.disable();
		}
		
		setBluetoothToggleStatus();
	}
	
	private boolean bluetoothIsEnabled() {
		return mBluetoothAdapter.isEnabled();
	}
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		String spinnerText = (String) parent.getItemAtPosition(pos);
		int delay = Integer.parseInt(spinnerText.split(" ")[0]) * 60 * 1000;
		
		preferences.edit().putInt(getString(R.string.pref_delay), delay).commit();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}
	
	public void onClickSearchingButton(View v) {
		if(isServiceRunning()) {
			stopBluetoothService();
		} else {
			startBluetoothService();
		}
		
		setButtonSearchingStatus((Button) v);
	}
	
	private void setButtonSearchingStatus(Button b) {
		if(isServiceRunning()) {
			b.setText(R.string.searching_button_text_start);
		} else {
			b.setText(R.string.searching_button_text_stop);
		}
	}
	
	private boolean isServiceRunning() {
		return preferences.getBoolean(getString(R.string.prefs_name), false);
	}
	
	private void startBluetoothService() {
		startService(new Intent(this, BluetoothService.class));
	}
	
	private void stopBluetoothService() {
		stopService(new Intent(this, BluetoothService.class));
	}
}
