package com.gerantech.extensions.functions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.AndroidExtension;
import com.gerantech.extensions.AndroidExtensionContext;
import com.gerantech.extensions.activities.PermissionsActivity;

public class DeviceInfoFunction implements FREFunction {

	@Override
	@RequiresApi(api = Build.VERSION_CODES.M)
	public FREObject call(FREContext context, FREObject[] args)
	{
		Log.w(AndroidExtension.LOG_TAG, "DeviceInfo called");
		AndroidExtensionContext divExtContext = (AndroidExtensionContext) context;
		Activity _activity = divExtContext.getActivity();
		String imei = "";
		try {
			PermissionsActivity.extensionContext = divExtContext;
			if( PermissionsActivity.checkPermission(_activity, Manifest.permission.READ_PHONE_STATE, 0) )
				imei = ((TelephonyManager)_activity.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	    }
	    catch (Exception e) { e.printStackTrace(); }
		
		try
	    {
			FREObject deviceInfo = FREObject.newObject("com.gerantech.extensions.DeviceInfo", null);
			deviceInfo.setProperty("model", FREObject.newObject(Build.MODEL));
			deviceInfo.setProperty("manufacturer", FREObject.newObject(Build.MANUFACTURER));
			deviceInfo.setProperty("device", FREObject.newObject(Build.DEVICE));
			deviceInfo.setProperty("product", FREObject.newObject(Build.PRODUCT));
			deviceInfo.setProperty("brand", FREObject.newObject(Build.BRAND));
			deviceInfo.setProperty("sdkVersion", FREObject.newObject(Build.VERSION.SDK_INT));
			deviceInfo.setProperty("id", FREObject.newObject(Settings.Secure.getString(context.getActivity().getContentResolver(), "android_id")));
			deviceInfo.setProperty("imei", FREObject.newObject(imei));

			Log.e(AndroidExtension.LOG_TAG, "DeviceInfo ended.");
			return deviceInfo;
	    }
	    catch (Exception e) { Log.e(AndroidExtension.LOG_TAG, e.getMessage()); }
				
		return null;
	}
}
