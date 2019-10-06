package com.gerantech.extensions.functions;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.AndroidExtension;

public class FullscreenFunction implements FREFunction
{
	@Override
	public FREObject call(FREContext context, FREObject[] args)
	{
		Log.i(AndroidExtension.LOG_TAG, "FullscreenFunction called");
		final Activity activity = context.getActivity();
		final int flags =
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//				| WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN;
//				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

		// This work only for android 4.4+
		if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
		{
			activity.getWindow().getDecorView().setSystemUiVisibility(flags);
			if( activity.getActionBar() != null )
				activity.getActionBar().hide();

			// Code below is to handle presses of Volume up or Volume down.
			// Without this, after pressing volume buttons, the navigation bar will
			// show up and won't hide
			/*activity.getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
				@Override
				public void onSystemUiVisibilityChange(int visibility) {
					if( (visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0 )
						activity.getWindow().getDecorView().setSystemUiVisibility(flags);
				}
			});*/
		}
		return null;
	}
}
