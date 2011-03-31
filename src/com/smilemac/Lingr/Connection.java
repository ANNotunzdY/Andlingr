package com.smilemac.Lingr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import android.util.Log;

import com.smilemac.Lingr.Manager;

public class Connection {
	static final String LINGRURLBASE = "http://lingr.com/api/";
	protected HttpURLConnection conn;
	public Connection(String category, String action, HashMap<String,String> options) throws IOException, ProtocolException
	{
		
		options.put("app_key", "gP59sR");
		if (Manager.sessionID != null) {
			options.put("session", Manager.sessionID);
		}
		
		String URLString = LINGRURLBASE + category + "/" + action;
		Set<Map.Entry<String, String>> set = options.entrySet();
		Iterator<Map.Entry<String, String>> it = set.iterator();
		boolean isFirstOption = true;
		while(it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			String appendString = isFirstOption ? "?" : "&";
			URLString = URLString + appendString + entry.getKey() + "=" + entry.getValue();
			isFirstOption = false;
		}
		URL connURL = new URL(URLString);
		conn = (HttpURLConnection) connURL.openConnection();
		conn.setRequestMethod("GET");
	}
	
	Connection(String category, String action, Runnable aCallback) throws IOException, ProtocolException
	{
			this(category, action, new HashMap<String, String>(0));
	}
	
	public void start () throws Exception {
		conn.connect();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    	String line;
    	String JSONString = "";
    	while ((line = reader.readLine()) != null) {
    		JSONString = JSONString + line;
    	}
    	Log.v("Lingr", JSONString);
    	this.finishDownload(JSONString);
	}
	
	public void finishDownload (String JSONString) throws Exception {
		JSONObject rootObject = new JSONObject(JSONString);
		this.onSuccess(rootObject);
	}
	
	public void onSuccess (JSONObject rootObject) {
	}
	
}
