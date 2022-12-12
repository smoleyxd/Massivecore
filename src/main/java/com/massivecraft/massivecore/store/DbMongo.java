package com.massivecraft.massivecore.store;

import com.massivecraft.massivecore.xlib.mongodb.client.MongoDatabase;

public class DbMongo extends DbAbstract
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //

	public MongoDatabase db;
	
	protected DriverMongo driver;
	@Override public DriverMongo getDriver() { return driver; }
	
	// -------------------------------------------- //
	// CONSTRUCTORS
	// -------------------------------------------- //
	
	public DbMongo(DriverMongo driver, MongoDatabase db)
	{
		this.driver = driver;
		this.db = db;
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public String getDbName()
	{
		return db.getName();
	}
	
	@Override
	public Object createCollDriverObject(Coll<?> coll)
	{
		return db.getCollection(coll.getName());
	}
	
}
