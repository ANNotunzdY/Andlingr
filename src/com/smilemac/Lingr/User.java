package com.smilemac.Lingr;

import org.json.JSONObject;

public class User {
	public String username;
	public String name;
	User (JSONObject json) throws Exception {
		 username = json.getString("username");
		 name = json.getString("name");
	}
}
