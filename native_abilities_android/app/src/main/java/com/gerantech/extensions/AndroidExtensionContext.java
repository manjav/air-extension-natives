package com.gerantech.extensions;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.gerantech.extensions.functions.CalendarFunction;
import com.gerantech.extensions.functions.ChangeStatusFunction;
import com.gerantech.extensions.functions.CheckInstallFunction;
import com.gerantech.extensions.functions.FullscreenFunction;
import com.gerantech.extensions.functions.DeviceInfoFunction;
import com.gerantech.extensions.functions.IntentFunction;
import com.gerantech.extensions.functions.InvokeAppFunction;
import com.gerantech.extensions.functions.LocalNotificationFunction;
import com.gerantech.extensions.functions.RequestPermissionFunction;
import com.gerantech.extensions.functions.SMSFunction;
import com.gerantech.extensions.functions.ShowOnLockedFunction;
import com.gerantech.extensions.functions.TelephonyManagerFunction;
import com.gerantech.extensions.functions.ToastFunction;
import com.gerantech.extensions.functions.VibrateFunction;

import java.util.HashMap;
import java.util.Map;

public class AndroidExtensionContext extends FREContext //implements ActivityResultCallback, StateChangeCallback {
{
	
//	private AndroidActivityWrapper aaw;

//	public AndroidExtensionContext() {
//		aaw = AndroidActivityWrapper.GetAndroidActivityWrapper();
//		aaw.addActivityResultListener(this);
//		aaw.addActivityStateChangeListner(this);
//	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//	}
//
//	@Override
//	public void onActivityStateChanged(ActivityState state) {
//		switch (state) {
//		case STARTED:
//		case RESTARTED:
//		case RESUMED:
//		case PAUSED:
//		case STOPPED:
//		case DESTROYED:
//		}
//		Log.w(AndroidExtension.LOG_TAG, "State :  " + state.toString());
//	}
//
//	@Override
//	public void onConfigurationChanged(Configuration paramConfiguration) {
//	}

	@Override
	public void dispose() {
	}

	@Override
	public Map<String, FREFunction> getFunctions() {

		Log.w(AndroidExtension.LOG_TAG, "Map function called");

		Map<String, FREFunction> functionMap = new HashMap<String, FREFunction>();

		return functionMap;
	}
}