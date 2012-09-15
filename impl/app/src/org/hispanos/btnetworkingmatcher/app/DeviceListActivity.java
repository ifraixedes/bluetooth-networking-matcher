package org.hispanos.btnetworkingmatcher.app;

import java.util.ArrayList;
import java.util.HashMap;

import org.hispanos.btnetworkingmatcher.app.storage.AppDAOFactory;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DeviceListActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        
        ArrayList<HashMap<String,String>> a = new ArrayList<HashMap<String,String>>();
        
        AppDAOFactory appDAO = new AppDAOFactory(this);
        a = appDAO.getBluetoothDAO().getMatchedDevicesInfo();
        //a = ; 
        ListView listView = (ListView) findViewById(R.id.devices_list);

        ArrayAdapter<HashMap<String,String>> adapter = new ArrayAdapter<HashMap<String,String>>(this,
          android.R.layout.simple_list_item_1, android.R.id.text1, a);

        // Assign adapter to ListView
        listView.setAdapter(adapter); 
    }
}
