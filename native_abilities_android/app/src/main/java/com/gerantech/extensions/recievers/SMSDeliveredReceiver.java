package com.gerantech.extensions.recievers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.gerantech.extensions.AndroidExtension;

public class SMSDeliveredReceiver extends BroadcastReceiver
{
	public static FREContext extensionContext;

	@Override
	public void onReceive(Context context, Intent intent) 
	{
        String message = null;
        switch (getResultCode())
        {
            case Activity.RESULT_OK:
            	message = "SMS delivered";
                break;
                
            case Activity.RESULT_CANCELED:
            	message = "SMS not delivered";
                break;                        
        }
        
        if(context != null)
        	context.unregisterReceiver(this);

        if(extensionContext!=null)
        	extensionContext.dispatchStatusEventAsync("smsEvent", "smsDelivered");
        Log.i(AndroidExtension.LOG_TAG, message);
    }

}
