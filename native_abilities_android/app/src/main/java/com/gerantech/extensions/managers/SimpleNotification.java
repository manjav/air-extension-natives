package com.gerantech.extensions.managers;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.gerantech.extensions.AndroidExtension;

public class SimpleNotification {
	
	@SuppressLint("NewApi") 
	public static void notify(Context context, int id, String ticker, String title, String text, String info, String icon, String sound)
	{
        Intent myintent;
		try {
			myintent = new Intent(context, Class.forName(context.getPackageName() + ".AppEntry"));
			//Log.i(AndroidExtension.LOG_TAG, notiID+" "+notiTicker+" "+notiTitle+" "+notiText+" "+notiInfo+" "+notiData);
			//use the flag FLAG_UPDATE_CURRENT to override any notification already there
			PendingIntent contentIntent = PendingIntent.getActivity(context, 0, myintent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		    Notification.Builder builder = new Notification.Builder(context);

		    Resources res = context.getResources();
		    builder.setContentIntent(contentIntent)
		    			//.setLargeIcon(BitmapFactory.decodeResource(res, res.getIdentifier("icon", "drawable", context.getPackageName())))
		                .setSmallIcon(res.getIdentifier("icon", "drawable", context.getPackageName()))
		                .setWhen(System.currentTimeMillis())
		                .setAutoCancel(true)
		                //.setDefaults(Notification.DEFAULT_ALL)
		                .setTicker(ticker)
		                .setContentTitle(title)
		                .setContentText(text)
		                .setContentInfo(info);
		     
			 
			if(icon != "")
			{
				Bitmap bitmap = BitmapFactory.decodeFile(icon);
				builder.setLargeIcon(bitmap);
			}
			 
			if(sound != "")
				 builder.setSound(Uri.parse(sound));
			 
			 
			Notification notification = builder.build();
			notificationManager.notify(id, notification);
		} 
		catch (Exception e)
		{
			Log.e(AndroidExtension.LOG_TAG, e.toString());
			e.printStackTrace();
		}
	}
}