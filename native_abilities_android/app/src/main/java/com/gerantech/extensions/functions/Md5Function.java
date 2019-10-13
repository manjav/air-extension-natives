package com.gerantech.extensions.functions;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.AndroidExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.content.ContentValues.TAG;

public class Md5Function implements FREFunction
{
	@Override
	public FREObject call(FREContext context, FREObject[] args)
	{
		Log.w(AndroidExtension.LOG_TAG, "Md5Function called");

		FREObject ret = null;
		try {
			long t = System.currentTimeMillis();
			File file = new File(args[0].getAsString());
			if( file.isDirectory() )
			{
				StringBuilder out = new StringBuilder();
				File[] files = file.listFiles();
				int len = files.length;
				for (int i = 0; i < len; i++)
					if( !files[i].isDirectory() )
						out.append(files[i].getName() + ":" + getMd5(files[i]) + (i < len - 1 ? "," : ""));
				ret = FREObject.newObject(out.toString());
				Log.i(TAG, "Md5s are ready in " + (System.currentTimeMillis() - t));
			}
			else
			{
				ret = FREObject.newObject(getMd5(file));
			}
		} catch (Exception e) { e.printStackTrace(); return null; }

		return ret;
	}

	private String getMd5(File file)
	{
		FileInputStream input = null;
		try {
			input = new FileInputStream(file);
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		// get hash of file
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "Exception while getting digest", e);
			return null;
		}

		byte[] buffer = new byte[8192];
		int read;
		String output = null;
		try {
			while ((read = input.read(buffer)) > 0)
				digest.update(buffer, 0, read);

			byte[] md5sum = digest.digest();
			BigInteger bigInt = new BigInteger(1, md5sum);
			output = bigInt.toString(16);
			// Fill to 32 chars
			output = String.format("%32s", output).replace(' ', '0');
		} catch (Exception e) {
			throw new RuntimeException("Unable to process file for MD5", e);
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				Log.e(TAG, "Exception on closing MD5 input stream", e);
			}
		}
		return output;
	}
}
