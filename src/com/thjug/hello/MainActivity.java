package com.thjug.hello;

import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final int FM_NOTIFICATION_ID = 1;
	private static final String IS_OPEN_ACCEPTDIALOG = "IS_OPEN_ACCEPTDIALOG";

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.btnHello).setOnClickListener(btnHelloClick);
		findViewById(R.id.btnFill).setOnClickListener(btnFillClick);
		
        final SharedPreferences preferences;
        preferences = getSharedPreferences("1", MODE_PRIVATE);
        
        if (preferences.getBoolean(IS_OPEN_ACCEPTDIALOG, true)) {
        	dialogMessage("Welcome", "You wanna use this app.");
        }
	}
	
    private void dialogMessage(final String tile, final String message) {
    	final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(tile);
        alert.setMessage(message);
        alert.setIcon(R.drawable.petdo);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override public void onClick(final DialogInterface dialog, final int id) {
                        final SharedPreferences preferences = getSharedPreferences("1", MODE_PRIVATE);
                        final SharedPreferences.Editor editor = preferences.edit(); 
                        editor.putBoolean(IS_OPEN_ACCEPTDIALOG, false);
                        editor.commit();

                        dialog.dismiss();
                    }
                });
        alert.show();
    }

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	        	startActivity(new Intent(this, SettingsActivity.class));
	            return true;
	        case R.id.action_showsetting:
	        	startActivity(new Intent(this, ShowPreferenceActivity.class));
	            return true;
	        case R.id.action_alert:
	        	notification();
	            return true;
	        case R.id.action_about:
	        	Toast.makeText(getApplicationContext(), "About Clicked !", Toast.LENGTH_SHORT).show();
	            return true;
            default:
            	return super.onOptionsItemSelected(item);
	    }
	}

	private void notification() {
        final NotificationManager mNotificationManager;
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        final CharSequence title =  "Training";
        final CharSequence text = "notification";

        final Intent notificationIntent = new Intent(this, MainActivity.class);
        final PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        
        Notification notification;
        try {
        	notification = getNotificationApi11AndGreater(title, text, contentIntent);
        } catch(final Exception e) {
        	notification = getNotification(getApplicationContext(),title, text, contentIntent);
        }
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(FM_NOTIFICATION_ID, notification);
	}
	
	@SuppressWarnings("deprecation")
	private Notification getNotification(final Context context, 
    		final CharSequence title, final CharSequence text, final PendingIntent contentIntent) {
    	final Notification notification = new Notification(R.drawable.petdo, text, System.currentTimeMillis());
    	notification.setLatestEventInfo(context, title, text, contentIntent);
    	
    	return notification;
    }
	
    @TargetApi(11)
    private Notification getNotificationApi11AndGreater(
    		final CharSequence title, final CharSequence text, final PendingIntent contentIntent) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)  
                .setSmallIcon(R.drawable.ic_launcher)  
                .setContentTitle("Notifications Example")  
                .setContentText("This is a test notification");

        builder.setContentIntent(contentIntent); 
        return builder.build();
    }
	
	final OnClickListener btnHelloClick = new OnClickListener() {
		@Override
		public void onClick(final View v) {
	    	Toast.makeText(getApplicationContext(), "Hello World !", Toast.LENGTH_SHORT).show();
	    	
			final TextView lbl = (TextView) findViewById(R.id.lblHello);
			lbl.setText(R.string.hello_norbor);			
		}
	};
	
	final OnClickListener btnFillClick = new OnClickListener() {
		@Override
		public void onClick(final View v) {
			final EditText txt = (EditText) findViewById(R.id.txtHello);
			txt.setText(R.string.hello_norbor);			
		}
	};
	
	final OnKeyListener txtKey = new OnKeyListener() {
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			//Toast.makeText(getApplicationContext(), ""+keyCode, Toast.LENGTH_SHORT).show();
			return true;
		}
	};

}
