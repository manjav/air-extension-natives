package
{
	import com.gerantech.extensions.CalendarEvent;
	import com.gerantech.extensions.NativeAbilities;
	import com.gerantech.extensions.events.AndroidEvent;

	import flash.desktop.NativeApplication;
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.events.GeolocationEvent;
	import flash.events.InvokeEvent;
	import flash.events.MouseEvent;
	import flash.sensors.Geolocation;
	

	public class Main extends Sprite
	{
		private var na:NativeAbilities;
		private var time:int = 1;
		private var fullState:int;
		
		public function Main()
		{
			stage.align = StageAlign.TOP_LEFT;
			stage.scaleMode = StageScaleMode.NO_SCALE;
			//stage.displayState = StageDisplayState.FULL_SCREEN;
			
			NativeApplication.nativeApplication.addEventListener(InvokeEvent.INVOKE, nativeApp_invokeHandler);
			
			var initBtn:CustomButton = new CustomButton("Telephony Initialize");
			initBtn.y = 0;
			initBtn.addEventListener(MouseEvent.CLICK, initBtn_clickHandler);

			addChild(initBtn);
			
			var imeiBtn:CustomButton = new CustomButton("Device Info");
			imeiBtn.y = 100;
			imeiBtn.addEventListener(MouseEvent.CLICK, imeiBtn_clickHandler);
			addChild(imeiBtn);
			
			var fullscreenBtn:CustomButton = new CustomButton("Fullscreen");
			fullscreenBtn.y = 200;
			fullscreenBtn.addEventListener(MouseEvent.CLICK, fullscreenBtn_clickHandler);
			addChild(fullscreenBtn);
			
			var smsBtn:CustomButton = new CustomButton("Send SMS");
			smsBtn.y = 300;
			smsBtn.addEventListener(MouseEvent.CLICK, smsButton_clickHandler);
			addChild(smsBtn);
			
			var intentBtn:CustomButton = new CustomButton("Intent");
			intentBtn.y = 400;
			intentBtn.addEventListener(MouseEvent.CLICK, intentBtn_clickHandler);
			addChild(intentBtn);
			
			/*var toastBtn:CustomButton = new CustomButton("Toast");
			toastBtn.y = 1000;
			toastBtn.addEventListener(MouseEvent.CLICK, toastBtn_clickHandler);
			addChild(toastBtn);*/
			
			var vibrateBtn:CustomButton = new CustomButton("Vibrate");
			vibrateBtn.y = 500;
			vibrateBtn.addEventListener(MouseEvent.CLICK, vibrateBtn_clickHandler);
			addChild(vibrateBtn);
			
			var notifyBtn:CustomButton = new CustomButton("Schedule Notification");
			notifyBtn.y = 600;
			notifyBtn.addEventListener(MouseEvent.CLICK, notifyBtn_clickHandler);
			addChild(notifyBtn);
			
			var cnotifyBtn:CustomButton = new CustomButton("Cancel Notification");
			cnotifyBtn.y = 700;
			cnotifyBtn.addEventListener(MouseEvent.CLICK, cnotifyBtn_clickHandler);
			addChild(cnotifyBtn);
			
			var calBtn:CustomButton = new CustomButton("Calendar");
			calBtn.y = 800;
			calBtn.addEventListener(MouseEvent.CLICK, calBtn_clickHandler);
			addChild(calBtn);

			var statusBtn:CustomButton = new CustomButton("Change Status Color");
			statusBtn.y = 900;
			statusBtn.addEventListener(MouseEvent.CLICK, statusBtn_clickHandler);
			addChild(statusBtn);
			
			var checkInstallBtn:CustomButton = new CustomButton("Check Installation");
			checkInstallBtn.y = 1000;
			checkInstallBtn.addEventListener(MouseEvent.CLICK, checkInstallBtn_clickHandler);
			addChild(checkInstallBtn);
			
			var requestPermissionBtn:CustomButton = new CustomButton("Request PermissionBtn");
			requestPermissionBtn.y = 1100;
			requestPermissionBtn.addEventListener(MouseEvent.CLICK, requestPermissionBtn_clickHandler);
			addChild(requestPermissionBtn);
			
			shareBtn = new CustomButton("Share");
			shareBtn.addEventListener(MouseEvent.CLICK, shareBtn_clickHandler);
			addChild(shareBtn);
			
			na = NativeAbilities.instance;
			na.showOnLockScreen();
			na.addEventListener(AndroidEvent.SMS_SENT, na_smsHandler)
			na.addEventListener(AndroidEvent.SMS_DELIVERED, na_smsHandler)
			
			var g:Geolocation = new Geolocation();
			g.addEventListener(GeolocationEvent.UPDATE, geolocation_updateHandler);
		}
		
		
		protected function nativeApp_invokeHandler(event:InvokeEvent):void
		{
			for(var k:String in event.arguments)
			{
				/*if(String(event.arguments[k]).search("testapp")>-1)
				{
					var urlVar:URLVariables =  new URLVariables(event.arguments[k]);
					na.showToast(unescape(urlVar.toString()), 2);
				}*/
			}
		}
		
		protected function calBtn_clickHandler(event:MouseEvent):void
		{
			if( na.requestPermission("android.permission.READ_CALENDAR", 1) )
				requestCalendar();
			else
				na.addEventListener(AndroidEvent.PERMISSION_REQUEST , na_requestPermissionHandler);
		}
		
		protected function geolocation_updateHandler(event:GeolocationEvent):void
		{
			event.currentTarget.removeEventListener(GeolocationEvent.UPDATE, geolocation_updateHandler);
			trace(event)
			//extension.runIntent("android.intent.action.VIEW", "http://www.google.com/maps/search/mosque/@"+event.latitude+","+event.longitude+",18z");
		}
		
		protected function cnotifyBtn_clickHandler(event:MouseEvent):void
		{
			na.cancelLocalNotifications();
		}
		
		protected function notifyBtn_clickHandler(event:MouseEvent):void
		{
			var t:Number = new Date().time + 10000;
			/*var icon:File = File.documentsDirectory.resolvePath("islamic/sounds/shahriar_parhizgar/shahriar_parhizgar.pbqr");
			var sound:File = File.documentsDirectory.resolvePath("islamic/sounds/shahriar_parhizgar/001/001001.dat");*/
			trace(na.scheduleLocalNotification("تیکر","عنوان","پیام", t, 60000, "اطلاعات", '{"foo":"bar"}'))//, icon.nativePath, sound.nativePath));

			// na.invokeAppScheme("myapp://test?a=1&b=2", t, 0);
		}
		
		protected function extension_LocalNotificationReceivedHandler(event:AndroidEvent):void
		{
			var o:Object = JSON.parse(event.data);
			na.showToast(o + " " + event.data, 2);
		}
		
		protected function vibrateBtn_clickHandler(event:MouseEvent):void
		{
			shareBtn.y = stage.stageHeight - 100;
			time += 20;
			na.vibrate(time);
		}
		
		protected function toastBtn_clickHandler(event:MouseEvent):void
		{
			na.showToast("salam", 0);
		}
		
		protected function smsButton_clickHandler(event:MouseEvent):void
		{
			if( na.requestPermission("android.permission.SEND_SMS", 1) )
				na.sendSMS("09121778856", "قربانت");
			else
				na.addEventListener(AndroidEvent.PERMISSION_REQUEST , na_requestPermissionHandler);
		}
		
		protected function na_smsHandler(event:AndroidEvent):void
		{
			na.showToast(event.type, 0);
		}
		
		protected function intentBtn_clickHandler(event:MouseEvent):void
		{
			//extension.runIntent("android.intent.action.EDIT", "bazaar://details?id=com.supercell.boombeach");
			//extension.runIntent("android.intent.action.VIEW", "http://maps.google.com/CURRENTLOCATION");
			na.runIntent("android.settings.LOCATION_SOURCE_SETTINGS", null);
		}
		
		protected function shareBtn_clickHandler(event:MouseEvent):void
		{
			na.shareText("sub", "text");
		}
		
		protected function imeiBtn_clickHandler(event:MouseEvent):void
		{
			na.showToast(na.deviceInfo.toString(), 1);
		}
		
		protected function initBtn_clickHandler(event:MouseEvent):void
		{
			na.listenTelephonyStates();
			na.addEventListener(AndroidEvent.CALL_STATE_CHANGED , manager_callStateChangedHandler);
		}
		protected function manager_callStateChangedHandler(event:AndroidEvent):void
		{
			na.showToast(event.type + " " + event.data, 2);
		}

		protected function statusBtn_clickHandler(event:MouseEvent):void
		{
			na.changeStatusColor(Math.random() * 255, Math.random() * 255, Math.random() * 255, Math.random() * 255);
		}

		protected function checkInstallBtn_clickHandler(event:MouseEvent):void
		{
			var apps:Vector.<String> = new <String>["com.farsitel.bazaar", "com.android.vending", "ir.mservices.market"];
			var app:String = apps[Math.floor(Math.random() * apps.length)];
			na.showToast(app + "=>" + na.checkInstalled(app), 0);
		}

		protected function requestPermissionBtn_clickHandler(event:MouseEvent):void
		{
			na.addEventListener(AndroidEvent.PERMISSION_REQUEST , na_requestPermissionHandler);
			na.requestPermission("android.permission.READ_PHONE_STATE", 1);
		}

		private function na_requestPermissionHandler(event:AndroidEvent):void
		{
			na.removeEventListener(AndroidEvent.PERMISSION_REQUEST , na_requestPermissionHandler);
			na.showToast(event.data, 2);
			if( String(event.data).search("SEND_SMS") > -1 )
				na.sendSMS("09121778856", "قربانت");
			else if( String(event.data).search("READ_CALENDAR") > -1 )
				requestCalendar();
		}

		private function requestCalendar():void
		{
			var cas:Vector.<CalendarEvent> = na.getCalendarEvents();
			var msg:String = "";
			for each(var e:CalendarEvent in cas)
				msg += " " + e.toString();
			na.showToast(msg, 2);
		}

		private function fullscreenBtn_clickHandler(event:MouseEvent):void
		{
			// stage.displayState = StageDisplayState.NORMAL;
			na.fullscreen();
			// stage.displayState = StageDisplayState.FULL_SCREEN;
			// stage.width = Capabilities.screenResolutionX;

		}

		public var shareBtn:CustomButton;
	}
}