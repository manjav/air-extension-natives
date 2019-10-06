package com.gerantech.extensions.functions;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.adobe.fre.FREArray;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.adobe.fre.FREWrongThreadException;
import com.gerantech.extensions.AndroidExtension;
import com.gerantech.extensions.AndroidExtensionContext;

import java.util.List;

public class InstallationFunction implements FREFunction
{
	@Override
	public FREObject call(FREContext context, FREObject[] args)
	{
		Log.w(AndroidExtension.LOG_TAG, "InstallationFunction called");
		AndroidExtensionContext divExtContext = (AndroidExtensionContext) context;
		Activity a = divExtContext.getActivity();

		// get list of all installed apps
		if( args.length == 0 )
		{
			List<ApplicationInfo> packages = a.getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
			FREObject ret = null;
			try {
				String ps = "";
				for (int i =0; i < packages.size(); i++ )
					ps += (packages.get(i).packageName + ( i < packages.size() - 1 ? "," : "" ));
				ret = FREObject.newObject(ps);
			} catch (Exception e) { e.printStackTrace(); }
			return ret;
		}

		// get package name
		String packageName = null;
		try {
			packageName = args[0].getAsString();
		} catch (Exception e) { e.printStackTrace(); }	
		
		Log.w(AndroidExtension.LOG_TAG, "InstallationFunction packageName:" + packageName);
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
