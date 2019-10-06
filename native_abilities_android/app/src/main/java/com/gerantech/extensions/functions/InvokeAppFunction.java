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
import com.gerantech.extensions.recievers.InvokeAppReceiver;

public class InvokeAppFunction implements FREFunction 
{


	@Override
	public FREObject call(FREContext context, FREObject[] args)
	{
		Log.w(AndroidExtension.LOG_TAG, "InvokeAppFunction called");
		
		AndroidExtensionContext divExtContext = (AndroidExtensionContext) context;
		Activity activity = divExtContext.getActivity();

		int id = -2;
		double time = 0;
		double interval = 0;
		boolean clearPreviouses = false;
		Bundle bundle = new Bundle();
		try 
		{
			bundle.putString("packageName", args[0].getAsString());
			bundle.putString("scheme", args[1].getAsString());
			time = args[2].getAsDouble();
			interval = args[3].getAsDouble();
			clearPreviouses = args[4].getAsBool();
			id = args[5].getAsInt();

			//Log.i(AndroidExtension.LOG_TAG, args[9].getAsString() + " " + args[10].getAsString());
		}
		catch (Exception e) 
		{
			Log.e(AndroidExtension.LOG_TAG, e.getMessage());
			e.printStackTrace();
		}

		//Cancel specific notifications
		if(id != -2)
		{
			cancel(activity, id);
			return null;
		}
		
		//clear previous all notifications
		if(clearPreviouses)
		{
			cancel(activity, -1);
		}
		
		InvokeAppReceiver.extensionContext = divExtContext;
		if(interval == 0)
			id = AlarmsManager.set(activity, InvokeAppReceiver.class, bundle, (long)time);
		else
			id = AlarmsManager.setRepeating(activity, InvokeAppReceiver.class, bundle, (long)time, (long)interval);
		
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
		AlarmsManager.cancel(context, InvokeAppReceiver.class, id);
	}

}
