package com.gerantech.extensions.functions;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.AndroidExtension;
import com.gerantech.extensions.AndroidExtensionContext;

public class ToastFunction implements FREFunction 
{

	@Override
	public FREObject call(FREContext context, FREObject[] args) 
	{
		
		Log.w(AndroidExtension.LOG_TAG, "ToastFunction called");
		
		AndroidExtensionContext divExtContext = (AndroidExtensionContext) context;
		Activity _activity = divExtContext.getActivity();
		
		try 
		{
			Toast.makeText(_activity, args[0].getAsString(), args[1].getAsInt()).show();			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}
