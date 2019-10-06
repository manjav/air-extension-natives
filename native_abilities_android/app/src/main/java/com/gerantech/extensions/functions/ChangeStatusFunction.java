package com.gerantech.extensions.functions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.AndroidExtension;
import com.gerantech.extensions.AndroidExtensionContext;

public class ChangeStatusFunction implements FREFunction 
{
	@SuppressLint("NewApi") @Override
	public FREObject call(FREContext context, FREObject[] args) 
	{
		Log.w(AndroidExtension.LOG_TAG, "ChangeStatusFunction called");

		AndroidExtensionContext divExtContext = (AndroidExtensionContext) context;
		Activity _activity = divExtContext.getActivity();
		int r = 0,g = 0,b = 0,a = 0;
		try {
			r = args[0].getAsInt();
			g = args[1].getAsInt();
			b = args[2].getAsInt();
			a = args[3].getAsInt();
		} catch (Exception e) { e.printStackTrace(); }

		Window window = _activity.getWindow();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP )
		{
//        	window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		    window.setStatusBarColor(Color.argb(a,r,g,b));
		}
        else if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
        {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
		
		return null;
	}
}
