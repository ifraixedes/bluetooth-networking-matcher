package org.hispanos.btnetworkingmatcher.app.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDAOFactory {
	
	
	protected Context context;
	protected BtNwMatcherOpenHelper btNwMatcherOpenHelper;
	
	public AppDAOFactory(Context ctx) {
		context = ctx;
		btNwMatcherOpenHelper = new BtNwMatcherOpenHelper(context);
	}
	
	public BluetoothDAO getBluetoothDAO() {
		return new BluetoothDAO(btNwMatcherOpenHelper.getWritableDatabase());
	}
	

	private final class  BtNwMatcherOpenHelper extends SQLiteOpenHelper {

	    private static final int DATABASE_VERSION = 1;
	    private static final String DATABASE_NAME = "BTNetworkingMatcher.db";
	    private static final String CREATE_TABLES =
	                "CREATE TABLE IF NOT EXIST BluetoothDevices (" +
	                "macAddr TEXT PRIMARY KEY ON CONFLICT ABORT, " +
	                "pubName TEXT DEFAULT NULL, " +
	                "matches INTEGER DEFAULT 1" +
	                ");";

	    public BtNwMatcherOpenHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }

	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(CREATE_TABLES);
	    }


		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
	}; 
	
}
