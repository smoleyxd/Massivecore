package com.massivecraft.massivecore.store;

public class DriverMongoSrv extends DriverMongo
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	protected static DriverMongoSrv i = new DriverMongoSrv();
	public static DriverMongoSrv get() { return i; }
	private DriverMongoSrv() { super(); }
	
	@Override
	public String getDriverName()
	{
		return "mongodb+srv";
	}
}
