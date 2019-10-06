package com.gerantech.extensions.functions;

import android.view.WindowManager;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

public class ShowOnLockedFunction implements FREFunction 
{

	@Override
	public FREObject call(FREContext context, FREObject[] args)
	{
		context.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
	            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
	            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
	            WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		
        return null;
	}
}