package com.massivecraft.massivecore.store;

import com.massivecraft.massivecore.xlib.mongodb.reactivestreams.client.MongoDatabase;

public class DbMongoAsync extends DbAbstract
{
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	public MongoDatabase db;
	
	protected DriverMongoAsync driver;
	@Override public DriverMongoAsync getDriver() { return driver; }
	
	// -------------------------------------------- //
	// CONSTRUCTORS
	// -------------------------------------------- //
	
	public DbMongoAsync(DriverMongoAsync driver, MongoDatabase db)
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
