package com.gerantech.extensions.functions;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;
import com.gerantech.extensions.AndroidExtension;
import com.gerantech.extensions.AndroidExtensionContext;
import com.gerantech.extensions.activities.PermissionsActivity;

public class RequestPermissionFunction implements FREFunction
{

	@TargetApi(Build.VERSION_CODES.M)
	@Override
	public FREObject call(FREContext context, FREObject[] args) 
	{
		Log.w(AndroidExtension.LOG_TAG, "RequestPermissionFunction called");
		AndroidExtensionContext divExtContext = (AndroidExtensionContext) context;
		FREObject ret = null;

		try
		{
			PermissionsActivity.extensionContext = divExtContext;
			boolean result = PermissionsActivity.checkPermission(divExtContext.getActivity(), args[0].getAsString(), args[1].getAsInt());
			ret = FREObject.newObject(result);
		}
		catch (FREWrongThreadException e) {
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			e.printStackTrace();
		}

		return ret;
	}
}
