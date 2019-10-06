package com.gerantech.extensions.functions;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.adobe.fre.FREArray;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.AndroidExtension;
import com.gerantech.extensions.AndroidExtensionContext;

public class CalendarFunction implements FREFunction 
{

	@Override
	public FREObject call(FREContext context, FREObject[] args) 
	{
		
		Log.w(AndroidExtension.LOG_TAG, "CalendarFunction called");
		//FREObject IMEI = null;
		AndroidExtensionContext divExtContext = (AndroidExtensionContext) context;

		Activity _activity = divExtContext.getActivity();
		
		Cursor cursor = _activity.getContentResolver().query(
		                    Uri.parse("content://com.android.calendar/events"),
		                    new String[] { "calendar_id", "title", "description", "dtstart", "dtend" }, null,
		                    null, null);
	    cursor.moveToFirst();

	    int len = cursor.getCount();
	    FREArray vector = null;
		try {
			//Log.i(AndroidExtension.LOG_TAG, "FREArray.newArray");
			vector = FREArray.newArray( "String", len, true);
		    // fetching calendars id
			//Log.i(AndroidExtension.LOG_TAG, "Loop");
		    for (int i = 0; i < len; i++)
		    {
		        //Log.i(AndroidExtension.LOG_TAG, cursor.getString(0)+","+cursor.getString(1)+","+cursor.getString(2)+","+cursor.getString(3)+","+cursor.getString(4));
		        FREObject str = FREObject.newObject(cursor.getString(0)+"||"+cursor.getString(1)+"||"+cursor.getString(2)+"||"+cursor.getString(3)+"||"+cursor.getString(4)); 
		        vector.setObjectAt(i, str);
		        cursor.moveToNext();
		    }
		} 
	    catch (Exception e) {
			Log.i(AndroidExtension.LOG_TAG, e.getMessage());
			e.printStackTrace();
		}
 
		return vector;
	}
}
