package com.gerantech.extensions.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;

import com.gerantech.extensions.AndroidExtension;

import java.util.Map;
import java.util.Random;

public class AlarmsManager 
{
	private static SharedPreferences sharedPreferences;
	private static Editor editor;
	private static String messagesSharedPreferences = "messagesSharedPreferences";

	public static int set(Context context, Class<?> cls, Bundle bundle, long time)
	{
		Intent intent = new Intent(context, cls);
		int id = getRandomID(context);
		bundle.putInt("id", id);
		intent.putExtras(bundle);
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		//alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
		alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        //Log.i(AndroidExtension.LOG_TAG, cls.getSimpleName()+" "+ time+" "+ id);
        return id;
	}

	public static int setRepeating(Context context, Class<?> cls, Bundle bundle, long time, long interval)
	{
		Intent intent = new Intent(context, cls);
	    
		int id = getRandomID(context);
		bundle.putInt("id", id);
		intent.putExtras(bundle);
		
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, interval, pendingIntent); // Millisec * Second * Minute
        Log.i(AndroidExtension.LOG_TAG, "setRepeating "+cls.getSimpleName()+" "+ time+" "+ interval+" "+ id);
        return id;
	}

	@SuppressWarnings("unchecked")
	public static void cancel(Context context, Class<?> cls, int id)
	{
	    Intent intent = new Intent(context, cls);
		PendingIntent pendingIntent;
		pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);	    
		sharedPreferences = context.getSharedPreferences(messagesSharedPreferences , 0);
		editor = sharedPreferences.edit();
	    
	    if(id == -1)
	    {
	    	Map<String, Boolean> messagesMap = (Map<String, Boolean>) sharedPreferences.getAll();
	    	for (Map.Entry<String, Boolean> entry : messagesMap.entrySet()) 
	    	{
				//Log.w(AndroidExtension.LOG_TAG, entry.getKey()+" -> "+entry.getValue());
				id = Integer.parseInt(entry.getKey());
				pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
		    	alarmManager.cancel(pendingIntent);
			}
	    	editor.clear();
	    }
	    else
	    {
	    	alarmManager.cancel(pendingIntent);
			editor.remove(id+"");
	    }
	    editor.commit();
		//Toast.makeText(context, notiID+" canceled", Toast.LENGTH_LONG).show();
	}
	
	
	private static int getRandomID(Context context) 
	{
		sharedPreferences = context.getSharedPreferences(messagesSharedPreferences, 0);
		editor = sharedPreferences.edit();
		int ret = getRandomInt();
		editor.putBoolean(ret+"", true);
		editor.commit();
		//Log.w("SALAAAAAM", ret+"");
		return ret;
	}
	
	private static int getRandomInt()
	{
		Random r = new Random();
		int ret = r.nextInt(100);	
		if(sharedPreferences.contains(""+ret))
		{
			if(sharedPreferences.getBoolean(""+ret, false))
				editor.remove(""+ret);
			else
				ret = getRandomInt();
		}
		return ret;
	}		
}
