package com.gerantech.extensions.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.adobe.fre.FREContext;
import com.gerantech.extensions.AndroidExtension;

public class PermissionsActivity extends Activity
{
    public static  FREContext extensionContext;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        this.requestPermissions(extras.getStringArray("permissionCodes"), extras.getInt("requestCode"));
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    static public boolean checkPermission(Activity activity, String permissionCode, int requestCode)
    {
        Log.i(AndroidExtension.LOG_TAG, "0");
        if( Build.VERSION.SDK_INT < Build.VERSION_CODES.M )
            return true;
        Log.i(AndroidExtension.LOG_TAG, "1");

        if( activity.checkSelfPermission(permissionCode) != PackageManager.PERMISSION_GRANTED )
        {
            Log.i(AndroidExtension.LOG_TAG, "2");
            if( requestCode > 0 )
            {
                Intent intent = new Intent(activity, PermissionsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArray("permissionCodes", new String[]{permissionCode});
                bundle.putInt("requestCode", requestCode);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
            return false;
        }
        Log.w(AndroidExtension.LOG_TAG, "3");
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        Log.i(AndroidExtension.LOG_TAG, "requestCode:" + requestCode + " permissions:" + join(permissions, ",") + " grantResults:" + join(grantResults, ", "));
        extensionContext.dispatchStatusEventAsync("requestPermissionsResult", "{\"succeed\":" + (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) + ", \"permissions\":" + join(permissions) + ", \"requestCode\":" + join(grantResults));
        finish();
    }

    private String join(int[] array) {
        return join(array, ",");
    }
    private String join(int[] array, String delimeter)
    {
        String ret = "";
        for ( int i = 0; i < array.length; i++ )
            ret += array[i] + (i < array.length - 1 ? delimeter : "");
        return  ret;
    }
    private String join(Object[] array) {
        return join(array, ",");
    }
    private String join(Object[] array, String delimeter)
    {
        String ret = "";
        for ( int i = 0; i < array.length; i++ )
            ret += array[i] + (i < array.length - 1 ? delimeter : "");
        return  ret;
    }

}
