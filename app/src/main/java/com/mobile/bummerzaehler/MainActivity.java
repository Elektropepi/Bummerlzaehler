package com.mobile.bummerzaehler;




import com.mobile.bummerzaehler.helper.HelperClass;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ParentActivity {

	@Override
	protected void onResume() {
        HelperClass.removeAllNotifications(this);
		super.onResume();
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(ViewTyp.MAIN); 
        super.onCreate(savedInstanceState);
		createNotificationChannel();
    }

    private void createNotificationChannel() {
		// Source: https://developer.android.com/training/notify-user/build-notification#java
		// In newer SDK versions, notification is not shown when a channel isn't registered
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = getString(R.string.notification_channel_name);
			String description = getString(R.string.notification_channel_description);
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel(HelperClass.NOTIFICATION_CHANNEL_ID, name, importance);
			channel.setDescription(description);
			// Register the channel with the system; you can't change the importance
			// or other notification behaviors after this
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}

	}
    
    public void twoPlayersStart(View v)
    {
    	Intent nextIntent = null;
		nextIntent = new Intent(MainActivity.this, TwoPlayerStartActivity.class);
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(nextIntent);
		overridePendingTransition(R.anim.right_to_left,
				R.anim.noanimation);
    }
    public void threePlayersStart(View v)
    {
    	Intent nextIntent = null;
		nextIntent = new Intent(MainActivity.this, ThreePlayerStartActivity.class);
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(nextIntent);
		overridePendingTransition(R.anim.right_to_left,
				R.anim.noanimation);
    }
    public void fourPlayersStart(View v)
    {
    	Intent nextIntent = null;
		nextIntent = new Intent(MainActivity.this, FourPlayerStartActivity.class);
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(nextIntent);
		overridePendingTransition(R.anim.right_to_left,
				R.anim.noanimation);
    }
}
