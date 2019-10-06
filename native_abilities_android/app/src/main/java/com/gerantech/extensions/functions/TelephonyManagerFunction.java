package com.gerantech.extensions.functions;

import android.app.Activity;
import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.AndroidExtensionContext;
import com.gerantech.extensions.listeners.CustomPhoneStateListener;

public class TelephonyManagerFunction implements FREFunction 
{

	@Override
	public FREObject call(FREContext context, FREObject[] args)
	{
		AndroidExtensionContext divExtContext = (AndroidExtensionContext) context;
		Activity a = divExtContext.getActivity();
		TelephonyManager manager = (TelephonyManager) a.getSystemService(Context.TELEPHONY_SERVICE);
        manager.listen(new CustomPhoneStateListener(a.getApplicationContext(), divExtContext), PhoneStateListener.LISTEN_CALL_STATE);

        return null;
	}
}