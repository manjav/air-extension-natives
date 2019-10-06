package com.gerantech.extensions.managers;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.util.Log;

import com.gerantech.extensions.AndroidExtension;

public class AthanNotification 
{

	
	@SuppressLint("NewApi") 
	public static void notify(Context context, int id, String ticker, String title, String text, String info)
	{
        Intent myintent;
		try {
			myintent = new Intent(context, Class.forName(context.getPackageName() + ".AppEntry"));
			//Log.i(AndroidExtension.LOG_TAG, id+" "+ticker+" "+title+" "+text+" "+info+" "+notiData);
			//use the flag FLAG_UPDATE_CURRENT to override any notification already there
			PendingIntent contentIntent = PendingIntent.getActivity(context, 0, myintent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		    Notification.Builder builder = new Notification.Builder(context);

		    Resources res = context.getResources();
		    int icon = res.getIdentifier("icon", "drawable", context.getPackageName());
		    
		    // build notification
		    // the addAction re-use the same intent to keep the example short
		    builder.setContentIntent(contentIntent)
		    			//.setLargeIcon(BitmapFactory.decodeResource(res, res.getIdentifier("icon", "drawable", context.getPackageName())))
		                .setSmallIcon(icon)
		                .setWhen(System.currentTimeMillis())
		                .setAutoCancel(true)
		                //.setDefaults(Notification.DEFAULT_ALL)
		                .setTicker(ticker)
		                .setContentTitle(title)
		                .setContentText(text)
		                .setContentInfo(info)
						.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
						.addAction(icon, "یادآوری مجدد", contentIntent)
						.addAction(icon, "امروز پخش نکن", contentIntent)
		                .setContentIntent(contentIntent).build();
			
			notificationManager.notify(id, builder.build());
		}
		catch (Exception e)
		{
			Log.e(AndroidExtension.LOG_TAG, e.toString());
			e.printStackTrace();
		}
	}
}
