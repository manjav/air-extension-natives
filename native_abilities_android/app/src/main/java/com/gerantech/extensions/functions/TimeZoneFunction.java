package com.gerantech.extensions;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import java.util.TimeZone;

public class TimeZoneFunction implements FREFunction
{
	@Override
	public FREObject call(FREContext context, FREObject[] args)
	{
		if(context==null)
		{
			return null;
		}

		try {
			String command = args[0].getAsString();

			if ( command.equalsIgnoreCase("getDefault") )
			{
				TimeZone timezone = TimeZone.getDefault();
				return FREObject.newObject(timezone.getID());
			}
		} catch (Exception error) {
			error.printStackTrace();
		}
		return null;
	}
}