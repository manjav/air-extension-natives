package com.gerantech.extensions.listeners;

import android.content.Context;
import android.telephony.PhoneStateListener;

import com.adobe.fre.FREContext;

public class CustomPhoneStateListener extends PhoneStateListener 
{

	//private static final String TAG = "PhoneStateChanged";
    Context context; //Context to make Toast if required 
	private FREContext extensionContext;
	
    public CustomPhoneStateListener(Context context, FREContext extensionContext)
    {
        super();
        this.context = context;
        this.extensionContext = extensionContext;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) 
    {
        super.onCallStateChanged(state, incomingNumber);

        /*switch (state) {
        case TelephonyManager.CALL_STATE_IDLE:
            //when Idle i.e no call
            Toast.makeText(context, "Phone state Idle", Toast.LENGTH_LONG).show();
            break;
        case TelephonyManager.CALL_STATE_RINGING:
            //when Ringing
            //Toast.makeText(context, "Phone state Ringing", Toast.LENGTH_LONG).show();
            break;
        case TelephonyManager.CALL_STATE_OFFHOOK:
            //when Off hook i.e in call
            //Make intent and start your service here
            Toast.makeText(context, "Phone state Off hook", Toast.LENGTH_LONG).show();
            break;
        default:
            break;
        }*/
		extensionContext.dispatchStatusEventAsync("callStateChanged", String.valueOf( state ));
    }
}