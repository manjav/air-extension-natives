package com.gerantech.extensions.functions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.AndroidExtension;
import com.gerantech.extensions.AndroidExtensionContext;

public class IntentFunction implements FREFunction 
{

	@Override
	public FREObject call(FREContext context, FREObject[] args) 
	{
		
		Log.w(AndroidExtension.LOG_TAG, "IntentFunction called");
		
		AndroidExtensionContext divExtContext = (AndroidExtensionContext) context;
		Activity _activity = divExtContext.getActivity();
		
		
		try 
		{
			Intent intent = null;
			int _type = args[0].getAsInt();			
			Log.i(AndroidExtension.LOG_TAG, _type+"");
			switch (_type)
			{
			case 0:
			   	intent = new Intent(Intent.ACTION_SEND);
		    	intent.setType("text/plain");  
		    	intent.putExtra(Intent.EXTRA_SUBJECT, args[1].getAsString());
		    	intent.putExtra(Intent.EXTRA_TEXT, args[2].getAsString());
				break;
			case 1:
				intent = _activity.getPackageManager().getLaunchIntentForPackage(args[1].getAsString());
				break;
			case 2:
			   	intent = new Intent(args[1].getAsString());
			   	if(args[2]!=null)
			   		intent.setData(Uri.parse(args[2].getAsString()));
				break;
			}
			
			_activity.startActivity(intent);
			
		} 
		catch (Exception e)
		{
			Log.e(AndroidExtension.LOG_TAG, e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
}
