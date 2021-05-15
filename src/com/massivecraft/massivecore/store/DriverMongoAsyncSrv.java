package com.massivecraft.massivecore.store;

public class DriverMongoAsyncSrv extends DriverMongoAsync
{
	
	protected static DriverMongoAsyncSrv i = new DriverMongoAsyncSrv();
	public static DriverMongoAsyncSrv get() { return i; }
	private DriverMongoAsyncSrv() { super(); }
	
	@Override
	public String getDriverName()
	{
		return "asyncmongodb+srv";
	}
	
}
