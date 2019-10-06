package com.gerantech.extensions.recievers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.gerantech.extensions.AndroidExtension;

public class SMSSentReceiver extends BroadcastReceiver
{
	public static FREContext extensionContext;

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		String message = null;
        switch (getResultCode()) {
        case Activity.RESULT_OK:
            message = "Message sent!";
            break;
        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
            message = "Error. Message not sent.";
            break;
        case SmsManager.RESULT_ERROR_NO_SERVICE:
            message = "Error: No service.";
            break;
        case SmsManager.RESULT_ERROR_NULL_PDU:
            message = "Error: Null PDU.";
            break;
        case SmsManager.RESULT_ERROR_RADIO_OFF:
            message = "Error: Radio off.";
            break;
        }
        
        if(context != null)
        	context.unregisterReceiver(this);

        if(extensionContext!=null)
        	extensionContext.dispatchStatusEventAsync("smsEvent", "smsSent");
        Log.i(AndroidExtension.LOG_TAG, message);
    }

}
