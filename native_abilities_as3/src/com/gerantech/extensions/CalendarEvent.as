package com.gerantech.extensions
{
	public class CalendarEvent
	{
		public var id:String;
		public var name:String;
		public var description:String;
		public var startTime:Number;
		public var endTime:Number;
		
		public function CalendarEvent(id:String, name:String, description:String, startTime:Number, endTime:Number)
		{
			this.id = id;
			this.name = name;
			this.description = description;
			this.startTime = startTime;
			this.endTime = endTime;
		}
		public function toString():String
		{
			return "id: "+id+", name: "+name+", description: "+description+", startTime: "+startTime+", endTime: "+endTime;
		}
	}
}