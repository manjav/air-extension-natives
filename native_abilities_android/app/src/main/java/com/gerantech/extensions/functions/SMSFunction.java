package com.gerantech.extensions.functions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.AndroidExtension;
import com.gerantech.extensions.AndroidExtensionContext;
import com.gerantech.extensions.recievers.SMSDeliveredReceiver;
import com.gerantech.extensions.recievers.SMSSentReceiver;

import java.util.ArrayList;

public class SMSFunction implements FREFunction 
{
	private static final int MAX_SMS_MESSAGE_LENGTH = 160;
	private static final short SMS_PORT = 0;

	private Activity _activity;
	private SMSSentReceiver sentReceiver;
	private SMSDeliveredReceiver deliveredReceiver;
	private AndroidExtensionContext divExtContext;

	@Override
	public FREObject call(FREContext context, FREObject[] args) 
	{
		Log.w(AndroidExtension.LOG_TAG, "SMSFunction called");

		divExtContext = (AndroidExtensionContext) context;
		_activity = divExtContext.getActivity();

		try
		{

			String phoneNumber = args[0].getAsString();
			String message = args[1].getAsString();
			Boolean isBinary = args[2].getAsBool();

			sendSMS(phoneNumber, message, isBinary);
			registerReceivers();
		}
		catch (Exception e)
		{
			Log.e(AndroidExtension.LOG_TAG,e.getMessage());
		}
		return null;
	}

	private void sendSMS(String phoneNumber, String message, Boolean isBinary) 
	{
		SmsManager manager = SmsManager.getDefault();

		PendingIntent piSend = PendingIntent.getBroadcast(_activity, 0, new Intent("SMS_SENT"), 0);
		PendingIntent piDelivered = PendingIntent.getBroadcast(_activity, 0, new Intent("SMS_DELIVERED"), 0);

		if(isBinary)
		{
			byte[] data = new byte[message.length()];

			for(int index=0; index<message.length() && index < MAX_SMS_MESSAGE_LENGTH; ++index)
				data[index] = (byte)message.charAt(index);

			manager.sendDataMessage(phoneNumber, null, (short) SMS_PORT, data, piSend, piDelivered);
		}
		else
		{
			int length = message.length();

			if(length > MAX_SMS_MESSAGE_LENGTH)
			{
				ArrayList<String> messagelist = manager.divideMessage(message);
				manager.sendMultipartTextMessage(phoneNumber, null, messagelist, null, null);
			}
			else
			{
				manager.sendTextMessage(phoneNumber, null, message, piSend, piDelivered);
			}
		}
	}

	@SuppressLint("ShowToast") 
	private void ShowToast(Context context, String message)
	{
		Toast.makeText(context, message, Toast.LENGTH_LONG);
	}

	private void registerReceivers()
	{

		SMSSentReceiver.extensionContext = divExtContext;
		sentReceiver = new SMSSentReceiver();
		_activity.registerReceiver(sentReceiver, new IntentFilter("SMS_SENT"));// SMS_SENT is a constant

		SMSDeliveredReceiver.extensionContext = divExtContext;
		deliveredReceiver = new SMSDeliveredReceiver();
		_activity.registerReceiver(deliveredReceiver, new IntentFilter("SMS_DELIVERED"));// SMS_DELIVERED is a constant
	}

}