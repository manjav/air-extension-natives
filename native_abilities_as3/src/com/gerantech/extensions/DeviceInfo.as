package com.gerantech.extensions
{
	public class DeviceInfo
	{
		public var model:String;
		public var manufacturer:String;
		public var device:String;
		public var product:String;
		public var brand:String;
		public var sdkVersion:String;
		public var id:String;
		public var imei:String;
		public function DeviceInfo() { super(); }
		public function toString() : String
		{
			return "[ DeviceInfo => model:" + this.model +
			" manufacturer:" + manufacturer +
			" device:" + device +
			" product:" + product +
			" brand:" + brand +
			" sdkVersion:" + sdkVersion +
			" id:" + id +
			" imei:" + imei +
			" ]";
		}
	}
}