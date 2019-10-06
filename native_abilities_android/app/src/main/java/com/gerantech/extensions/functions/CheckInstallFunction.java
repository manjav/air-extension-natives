package com.gerantech.extensions.functions;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;
import com.gerantech.extensions.AndroidExtension;
import com.gerantech.extensions.AndroidExtensionContext;

public class CheckInstallFunction implements FREFunction 
{
	@Override
	public FREObject call(FREContext context, FREObject[] args)
	{
		Log.w(AndroidExtension.LOG_TAG, "CheckInstallFunction called");
		AndroidExtensionContext divExtContext = (AndroidExtensionContext) context;
		Activity a = divExtContext.getActivity();
		
		// get package name
		String packageName = null;
		try {
			packageName = args[0].getAsString();
		} catch (Exception e) { e.printStackTrace(); }	
		
		Log.w(AndroidExtension.LOG_TAG, "CheckInstallFunction packageName:" + packageName);
		// check
		FREObject ret = null;
		try {
			ret = FREObject.newObject(true);
	        PackageManager packageManager = a.getPackageManager();
	        try {
	            packageManager.getPackageInfo(packageName, 0);
	        } catch (PackageManager.NameNotFoundException e) {
				ret = FREObject.newObject(false);
	        }
		} catch (FREWrongThreadException e1) { e1.printStackTrace(); }

		return ret;
	}

}
