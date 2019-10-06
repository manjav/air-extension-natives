package com.gerantech.extensions.functions;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.AndroidExtensionContext;

public class VibrateFunction implements FREFunction 
{

	@Override
	public FREObject call(FREContext context, FREObject[] args)
	{
		AndroidExtensionContext divExtContext = (AndroidExtensionContext) context;
		Activity a = divExtContext.getActivity();
		Vibrator vibrator = (Vibrator) a.getSystemService(Context.VIBRATOR_SERVICE);
		try {
			vibrator.vibrate(args[0].getAsInt());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
