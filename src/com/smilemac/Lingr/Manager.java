package com.smilemac.Lingr;

import java.util.HashMap;
import org.json.JSONObject;
import android.util.Log;

public class Manager {
	public static String sessionID = null;
	public static String publicID = null;
	public static User currentUser = null;
	
	public void login (String userName, String password) {
		try {
     		 HashMap<String, String> options = new HashMap<String, String>();
     		 options.put("user", userName);
     		 options.put("password", password);
     		 com.smilemac.Lingr.Connection conn = new com.smilemac.Lingr.Connection("session", "create", options){
     			 public void onSuccess (JSONObject rootObject) {
     				 Log.v("Andlingr", rootObject.toString());
     				 try {
     					 sessionID = rootObject.getString("session");
     					 publicID = rootObject.getString("public_id");
     					 Manager.this.finishLogin();
     				 } catch (Exception e) {
     					 // TODO: handle exception
     					 Log.v("Lingr", e.getMessage());
     					 Manager.this.failedLogin(e);
     				 }
     			 }
     		 };
     		 conn.start();

     	 } catch (Exception e) {
     		 // TODO: handle exception
     		 Log.v("Lingr", e.getMessage());
     		 this.failedLogin(e);
     	 }
	}
	
	public void finishLogin() {
		Log.v("Lingr", "FinishLogin");
	}
	
	public void failedLogin(Exception e) {
		Log.v("Lingr", "FailedLogin");
	}
	
}
