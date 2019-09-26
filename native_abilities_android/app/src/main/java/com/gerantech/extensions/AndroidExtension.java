package com.gerantech.extensions;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class AndroidExtension implements FREExtension
{
	public static final String LOG_TAG = "android.a.n.e";
    /** Called when the activity is first created. */
	@Override
	public FREContext createContext(String arg0)
	{
		// TODO Auto-generated method stub
		Log.w(AndroidExtension.LOG_TAG, "Inside Create Context");
		return new AndroidExtensionContext();
	}

	@Override
	public void dispose() 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize()
	{
		// TODO Auto-generated method stub
	}
}