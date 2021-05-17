package com.massivecraft.massivecore.store;

public class DriverReactiveStreamsSrv extends DriverReactiveStreams
{
	
	protected static DriverReactiveStreamsSrv i = new DriverReactiveStreamsSrv();
	public static DriverReactiveStreamsSrv get() { return i; }
	private DriverReactiveStreamsSrv() { super(); }
	
	@Override
	public String getDriverName()
	{
		return "reactivestreams+srv";
	}
	
}
