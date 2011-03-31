package com.smilemac.Andlingr;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.view.View;

import com.smilemac.Andlingr.LoginAlert;
import com.smilemac.Andlingr.R.id;
import com.smilemac.Lingr.Connection;

public class Andlingr extends Activity {
    /** Called when the activity is first created. */
	ArrayAdapter<String> adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        // アイテムを追加します
        adapter.add("Open Login Window");
        ListView listView = (ListView) findViewById(id.listView1);
        // アダプターを設定します
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            	Log.v("Andlingr", "Andlingr Room List Clicked.");
            	Andlingr.this.showLoginAlert();
            }
        });
        
        this.showLoginAlert();
    }
    
    public void showLoginAlert() {
    	LoginAlert loginAlert = new LoginAlert(this){
        	public void finishLogin() {
        		HashMap<String, String> options = new HashMap<String, String>();
        		try {
        			Connection conn = new Connection("user", "get_rooms", options) {
        				public void onSuccess(JSONObject rootObject) {
                  			try {
                  				JSONArray rooms = rootObject.getJSONArray("rooms");
                  				Andlingr.this.adapter.clear();
                  				for (int i=0; i<rooms.length(); i++){
                  					Andlingr.this.adapter.add(rooms.get(i).toString());
                  				}
                  				Log.v("Andlingr", "SuccessGetRooms");
                      			Context context = Andlingr.this;
                      			CharSequence text = "Room List Refreshed.";
                      			int duration = Toast.LENGTH_SHORT;
                      			Toast toast = Toast.makeText(context, text, duration);
                      			toast.show();
                  			} catch (Exception e) {
								// TODO: handle exception
                  				Log.v("Andlingr", "FailedGetRooms");
                      			Context context = Andlingr.this;
                      			CharSequence text = "Login Failed.";
                      			int duration = Toast.LENGTH_SHORT;
                      			Toast toast = Toast.makeText(context, text, duration);
                      			toast.show();
							}
        				}
        			};
        			conn.start();
        		} catch (Exception e) {
					// TODO: handle exception
        			Log.v("Andlingr", "FailedGetRooms");
          			Context context = Andlingr.this;
          			CharSequence text = "Login Failed.";
          			int duration = Toast.LENGTH_SHORT;
          			Toast toast = Toast.makeText(context, text, duration);
          			toast.show();
				}
        	}
        	
        	public void failedLogin() {
        	}
        };
        loginAlert.show();
    }
    
}