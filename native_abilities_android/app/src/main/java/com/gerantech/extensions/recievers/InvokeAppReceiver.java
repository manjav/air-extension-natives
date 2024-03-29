/**
 * 
 */
package com.gerantech.extensions.recievers;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.adobe.fre.FREContext;
import com.gerantech.extensions.AndroidExtension;

/**
 * @author Mansour Djawadi
 *
 */

public class InvokeAppReceiver extends BroadcastReceiver 
{
	public static FREContext extensionContext;

	@SuppressLint("WrongConstant")
	public void onReceive(Context context, Intent intent)
    {   
		Bundle bundle = intent.getExtras();
        String packageName = bundle.getString("packageName");
        String scheme = bundle.getString("scheme");
        Log.i(AndroidExtension.LOG_TAG, "packageName "+ packageName + "  scheme: " + scheme);
        if(packageName.length() > 2)
        {
    		try
            {
                Intent i = context.getPackageManager().getLaunchIntentForPackage(packageName);
                if (i == null)
                {
                	Log.e(AndroidExtension.LOG_TAG, "PackageManager.NameNotFoundException");
                	return;
                }
                i.setAction(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_LAUNCHER);
    			context.startActivity(i);
            } 
            catch (Exception e) 
            {
    			Log.e(AndroidExtension.LOG_TAG, e.getMessage());
    			e.printStackTrace();
            }
    	}
        else if( scheme.length() > 2)
        {
			try
			{
				Intent i = Intent.parseUri(scheme, Intent.URI_INTENT_SCHEME);
				i.addCategory(Intent.CATEGORY_BROWSABLE);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
				i.setComponent(null);
				context.startActivity(i);
			}
			catch (Exception e)
			{
				Log.e(AndroidExtension.LOG_TAG, e.getMessage());
				e.printStackTrace();
			}
        }
    }
}