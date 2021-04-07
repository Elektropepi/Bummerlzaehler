package com.mobile.bummerzaehler.helper;

import com.mobile.bummerzaehler.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;

import android.widget.Toast;

public class HelperClass {
	private static int UNIQUE_NOTIFICATION_ID = 0;
	public static final String NOTIFICATION_CHANNEL_ID = "Notification";
	public static int getUniqueNotificationId()
	{
		return ++UNIQUE_NOTIFICATION_ID;
	}
	public static void showShortToast(String text, Context c)
	{
		Toast.makeText(c,text,Toast.LENGTH_SHORT).show();
	}
	public static void showLongToast(String text, Context c)
	{
		Toast.makeText(c,text,Toast.LENGTH_LONG).show();
	}
	public static String getShortName(String name)
	{
		if(name.length() < 3) {
			return name;
		}
		return name.substring(0, 3);
	}
	public static void showYesNoCloseGame(final Activity a)
	{
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        switch (which){
		        case DialogInterface.BUTTON_POSITIVE:
		            a.finish();
		            break;

		        case DialogInterface.BUTTON_NEGATIVE:
		            //No button clicked
		            break;
		        }
		    }
		};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(a);
		builder.setMessage("Wollen Sie das Spiel wirklich beenden?").setPositiveButton("Sia", dialogClickListener)
		    .setNegativeButton("Na", dialogClickListener).show();
		
	}
	
	public static boolean isBooleanPrefKeyActivated(SharedPreferences preferences, String prefKey, boolean emptyDefault)
	{
		String isActivated = preferences.getString(prefKey, "");
		return isActivated.isEmpty() ? emptyDefault : Boolean.parseBoolean(isActivated);
	}
	
	public static NotificationCompat.Builder createNotificationManager(Context c, Intent resultIntent)
	{
		NotificationCompat.Builder builder =
		        new NotificationCompat.Builder(c, NOTIFICATION_CHANNEL_ID)
		        .setSmallIcon(R.drawable.notify_icon)
				.setOngoing(true)
		        .setContentTitle("Bummerlzähler");
		
		resultIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		resultIntent.setAction(Long.toString(System.currentTimeMillis()));
		PendingIntent pi =  PendingIntent.getActivity(c, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pi);

		return builder;
	}
	
	public static void notifyUser(NotificationCompat.Builder builder, int notificationId, Context c, String text)
	{
		NotificationManager mNotificationManager =
			    (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
		builder.setContentText(text);
			mNotificationManager.notify(notificationId, builder.build());
	}
	
	public static void removeNotification(int id, Context c)
	{
		NotificationManager mNotificationManager =
			    (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
mNotificationManager.cancel(id);

	}
	public static void removeAllNotifications(Context c)
	{
		NotificationManager mNotificationManager =
			    (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
mNotificationManager.cancelAll();
	}
	
}
