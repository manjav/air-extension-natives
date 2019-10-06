/**
 * Copyright (C) <year> <copyright holders>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 **/
package com.gerantech.extensions
{
	import com.gerantech.extensions.events.AndroidEvent;
	
	import flash.events.EventDispatcher;
	import flash.events.StatusEvent;
	import flash.external.ExtensionContext;
	import flash.system.Capabilities;
	
	/**
	 *  
	 * @author Mansour Djawadi
	 */	
		
	public class NativeAbilities extends EventDispatcher
	{
		//----------------------------------------
		//
		// Constants
		//
		//----------------------------------------
		public static const CALL_STATE_IDLE:int = 0;
		public static const CALL_STATE_RINGING:int = 1;
		public static const CALL_STATE_OFFHOOK:int = 2;
		
		public static const ERROR:int = 10;


		//----------------------------------------
		//
		// Variables
		//
		//----------------------------------------
		private static var _instance:NativeAbilities;
		private var extContext:ExtensionContext;
		
		//----------------------------------------
		//
		// Properties
		//
		//----------------------------------------
		
		
		
		//----------------------------------------
		//
		// Public Methods
		//
		//----------------------------------------
		
		public static function get instance():NativeAbilities 
		{
			if ( !_instance )
				_instance = new NativeAbilities( new SingletonEnforcer() );
			
			return _instance;
		}
		
		public function showOnLockScreen():void 
		{
			if(!isAndroid)
				return;
			extContext.call("showOnLockScreen");
		}
		
		public function listenTelephonyStates():void 
		{
			if(!isAndroid)
				return;
			extContext.call("listenTelephonyStates");
		}
		
      public function get deviceInfo() : DeviceInfo
      {
				if(!isAndroid)
					return null;
				return extContext.call("deviceInfo") as DeviceInfo;
      }
      		
		public function showToast(message:String, duration:int):void
		{
			if(!isAndroid)
				return;
			extContext.call("showToast", message, duration);
		}
		
		public function vibrate(miliSecond:int):void
		{
			if(!isAndroid)
				return;
			extContext.call("vibrate", miliSecond);
		}
		
		public function shareText(subject:String, text:String):void
		{
			if(!isAndroid)
				return;
			extContext.call("runIntent", 0, subject, text);
		}
		
		public function launchAPK(packageName:String):void
		{
			if(!isAndroid)
				return;
			extContext.call("runIntent", 1, packageName);
		}
		
		public function runIntent(action:String, url:String=null):void
		{
			if(!isAndroid)
				return;
			extContext.call("runIntent", 2, action, url);
		}
		
		
		public function scheduleLocalNotification(ticker:String, title:String, message:String, time:Number, interval:Number=0, info:String="", data:String="", iconURL:String="", soundURL:String="", clearPreviouses:Boolean=false):int
		{
			if(!isAndroid)
				return -1;
			return extContext.call("localNotification", ticker, title, message, time, interval, info, data, clearPreviouses, -2, iconURL, soundURL) as int;
		}
		
		/**
		 * Cancel previous notification by id<br>if id equals -1 all notifications have been canceled.
		 */
		public function cancelLocalNotifications(notificationID:int = -1):void
		{
			if(!isAndroid)
				return ;
			extContext.call("localNotification", "", "", "", 0, 0, "", "", false, notificationID);
		}		
		
		public function invokeAppScheme(scheme:String, time:Number, interval:Number=0, clearPreviouses:Boolean=false):int
		{
			if(!isAndroid)
				return -1;
			return extContext.call("invokeApp", "", scheme, time, interval, clearPreviouses, -2) as int;
		}
		public function invokeApp(packageName:String, time:Number, interval:Number=0, clearPreviouses:Boolean=false):int
		{
			if(!isAndroid)
				return -1;
			return extContext.call("invokeApp", packageName, "", time, interval, clearPreviouses, -2) as int;
		}
		public function cancelInvokeApp(scheduleID:int = -1):void
		{
			if(!isAndroid)
				return ;
			extContext.call("invokeApp", "", "", 0, 0, false, scheduleID);
		}
				
		public function changeStatusColor(r:int, g:int, b:int, a:int):void
		{
			if(!isAndroid)
				return ;
			extContext.call("changeStatusColor", r, g, b, a);
		}
		
		public function sendSMS(phoneNumber:String, message:String, isBinary:Boolean=false):void
		{
			if(!isAndroid)
				return ;
			extContext.call("sendSMS", phoneNumber, message, isBinary);
		}
		
		public function checkInstalled(packageName:String):Boolean
		{
			if(!isAndroid)
				return false;
			return extContext.call("installation", packageName);
		}
		
		public function getInstalled():Array
		{
			if(!isAndroid)
				return null;
			return String(extContext.call("installation")).split(",");
		}
		
		public function requestPermission(permission:String, code:int):Boolean
		{
			if(!isAndroid)
				return false;
			return extContext.call("requestPermission", permission, code);
		}
		
		public function fullscreen():void
		{
			if(!isAndroid)
				return;
			extContext.call("fullscreen");
		}
		
			
		/**
		 * Get google calendar events.
		 */
		public function getCalendarEvents():Vector.<CalendarEvent>
		{
			if(!isAndroid)
				return null;
			
			var evs:Vector.<String> = extContext.call("getCalendarEvents") as Vector.<String>;
			if(evs==null)
				return null;
			
			var ret:Vector.<CalendarEvent> = new Vector.<CalendarEvent>(evs.length, true);
			var ea:Array;
			for(var e:uint=0; e<evs.length; e++)
			{
				ea = evs[e].split("||");
				ret[e] = new CalendarEvent(ea[0], ea[1], ea[2], ea[3], ea[4]);
			}
			return ret;
		}
				
		/**
		 * Cleans up the instance of the native extension. 
		 */		
		public function dispose():void
		{ 
			if(!isAndroid)
				return;
			extContext.dispose(); 
		}

		
		//----------------------------------------
		//
		// Handlers
		//
		//----------------------------------------
		
		private function onStatus( event:StatusEvent ):void 
		{
			trace(event.code, event.level);
			switch(event.code)
			{
				case "callStateChanged":
					dispatchEvent( new AndroidEvent(AndroidEvent.CALL_STATE_CHANGED, event.level, false, false ) );
					break;
				case "LocalNotificationReceived":
					dispatchEvent( new AndroidEvent(AndroidEvent.LOCAL_NOTIFICATION_RECEIVED, event.level, false, false ) );
					break;
				case "requestPermissionsResult":
					dispatchEvent( new AndroidEvent(AndroidEvent.PERMISSION_REQUEST, event.level, false, false ) );
					break;
				case "smsEvent":
					dispatchEvent( new AndroidEvent(event.level, null, false, false ) );
					break;
			}
		}
		
		//----------------------------------------
		//
		// Constructor
		//
		//----------------------------------------
		
		/**
		 * Constructor. 
		 */		
		public function NativeAbilities( enforcer:SingletonEnforcer )
		{
			super();
			extContext = ExtensionContext.createExtensionContext( "com.gerantech.extensions.nativeabilities", "" );
			
			if ( !extContext )
				throw new Error( "Android native extension is not supported on this platform." );
			extContext.addEventListener( StatusEvent.STATUS, onStatus );
		}
		
		
		private function get isAndroid():Boolean
		{
			var ret:Boolean = Capabilities.os.substr(0,5)=="Linux";
			if(!ret)
				this.dispatchEvent(new AndroidEvent(AndroidEvent.ERROR, ERROR));
			return ret;
		}
	}
}

class SingletonEnforcer
{
}