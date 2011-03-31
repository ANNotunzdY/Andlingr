package com.smilemac.Andlingr;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.*;

import com.smilemac.Andlingr.R;

import com.smilemac.Lingr.*;

public class LoginAlert {
	AlertDialog.Builder builder;
	protected AlertDialog alertDialog;
	public Activity activity;
	
	public LoginAlert(Activity aActivity) {
		activity = aActivity;
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.login, (ViewGroup) activity.findViewById(R.id.layout_root));
		
		builder = new AlertDialog.Builder(activity);
        builder.setView(layout);
        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	 EditText idField = (EditText) LoginAlert.this.alertDialog.findViewById(R.id.idField);
          	 EditText passwordField = (EditText) LoginAlert.this.alertDialog.findViewById(R.id.passwordField);
          	 dialog.cancel();

          	 Manager m = new Manager(){
          		 public Timer timer;
          		 public void finishLogin() {
          			Log.v("Andlingr", "FinishLogin");
          			Context context = LoginAlert.this.activity;
          			CharSequence text = "Login Succeed.";
          			int duration = Toast.LENGTH_SHORT;
          			Toast toast = Toast.makeText(context, text, duration);
          			toast.show();
          			LoginAlert.this.finishLogin();
          		 }	 
          		 public void failedLogin(Exception e){
          			Log.v("Andlingr", "FailedLogin");
          			Context context = LoginAlert.this.activity;
          			CharSequence text = "Login Failed.";
          			int duration = Toast.LENGTH_SHORT;
          			Toast toast = Toast.makeText(context, text, duration);
          			toast.show();
//						TODO: How to re-show the login alert?
//          			TimerTask task = new TimerTask(){
//          				public void run() {
//          					LoginAlert.this.show();
//          				}
//          			};
//          			Timer timer = new Timer("RetryTimer");
//          			timer.schedule(task, TimeUnit.SECONDS.toMillis(3));
          			LoginAlert.this.failedLogin();
          		 }
          	 };
          	 m.login(idField.getText().toString(), passwordField.getText().toString());
           }
       });
	}
	
	public void show() {
		
		Log.v("Andlingr", "Show Alert");
		
		alertDialog = builder.create();
		alertDialog.show();
	}
	
	public void finishLogin() {
		
	}
	
	public void failedLogin() {
		
	}
}
