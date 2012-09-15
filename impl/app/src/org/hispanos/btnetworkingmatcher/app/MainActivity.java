package org.hispanos.btnetworkingmatcher.app;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnClickListener {
	
	ToggleButton tb;
	BluetoothAdapter mBluetoothAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setupBluetoothToggleButton();
        setupFrenquencySpinner();
    }
    
    private void setupBluetoothToggleButton() {
    	tb = (ToggleButton) findViewById(R.id.toggleButton1);
        tb.setOnClickListener(this);
    }
    
    private void setupFrenquencySpinner() {
    	Spinner spinner = (Spinner) findViewById(R.id.frequency_spinner);
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
    
    public void onClick(View v) {
         switch(v.getId())
         {
             case R.id.toggleButton1:
                 if((tb).isChecked())
                 {
                     mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                     if (mBluetoothAdapter == null) {
                         // Device does not support Bluetooth
                     }
                     if (!mBluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, 5);
                     }
                     Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                     discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
                     startActivity(discoverableIntent);
                 }
                 else
                 {
                     Toast myToast = Toast.makeText(getApplicationContext(), "Bluetooth turned off", Toast.LENGTH_SHORT);
                     myToast.show();     
                     mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                     if (mBluetoothAdapter == null) {
                         // Device does not support Bluetooth
                     }
                     if (mBluetoothAdapter.isEnabled()) {
                         if (mBluetoothAdapter != null) {
                                 mBluetoothAdapter.disable();
                         }
                     }
                 }
           }
      }
}
