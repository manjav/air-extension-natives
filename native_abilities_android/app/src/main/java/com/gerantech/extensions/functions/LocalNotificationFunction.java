package com.gerantech.extensions.functions;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.AndroidExtension;
import com.gerantech.extensions.AndroidExtensionContext;
import com.gerantech.extensions.managers.AlarmsManager;
import com.gerantech.extensions.recievers.LocalNotificationReceiver;

public class LocalNotificationFunction implements FREFunction 
{


	@Override
	public FREObject call(FREContext context, FREObject[] args)
	{
		Log.w(AndroidExtension.LOG_TAG, "LocalNotificationFunction called");
		
		AndroidExtensionContext divExtContext = (AndroidExtensionContext) context;
		Activity activity = divExtContext.getActivity();

		int id = -2;
		double time = 0;
		double interval = 0;
		boolean clearPreviouses = false;
		Bundle bundle = new Bundle();
		try 
		{
			id = args[8].getAsInt();
			time = args[3].getAsDouble();
			interval = args[4].getAsDouble();
			clearPreviouses = args[7].getAsBool();
			bundle.putString("ticker", args[0].getAsString());
			bundle.putString("title", args[1].getAsString());
			bundle.putString("text", args[2].getAsString());
			bundle.putString("info", args[5].getAsString());
			bundle.putString("data", args[6].getAsString());
			bundle.putString("icon", args[9].getAsString());
			bundle.putString("sound", args[10].getAsString());
			//Log.i(AndroidExtension.LOG_TAG, args[9].getAsString() + " " + args[10].getAsString());
		}
		catch (Exception e) 
		{
			Log.e(AndroidExtension.LOG_TAG, e.getMessage());
			e.printStackTrace();
		}

		//Cancel previouse notifications
		if(id != -2)
		{
			cancel(activity, id);
			AlarmsManager.cancel(activity, LocalNotificationReceiver.class, id);
			return null;
		}
		
		//clear previouse notifications
		if(clearPreviouses)
		{
			cancel(activity, -1);
			AlarmsManager.cancel(activity, LocalNotificationReceiver.class, -1);
		}
		
		LocalNotificationReceiver.extensionContext = divExtContext;
		if(interval == 0)
			id = AlarmsManager.set(activity, LocalNotificationReceiver.class, bundle, (long)time);
		else
			id = AlarmsManager.setRepeating(activity, LocalNotificationReceiver.class, bundle, (long)time, (long)interval);
		
		FREObject ret = null;
		try{
			ret = FREObject.newObject(id);
		}
		catch (Exception e){
			Log.e(AndroidExtension.LOG_TAG, e.getMessage());
		}
		return ret;
	}
	
	public void cancel(Context context, int id)
	{
	    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	    if(id == -1)
		    notificationManager.cancelAll();
	    else
	    	notificationManager.cancel(id);
		//Log.i(AndroidExtension.LOG_TAG, "Notification canceled by "+id);
	}

}
