package com.massivecraft.massivecore.store;

import com.massivecraft.massivecore.xlib.mongodb.reactivestreams.client.MongoDatabase;

public class DbReactiveStreams extends DbAbstract
{
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	public MongoDatabase db;
	
	protected DriverReactiveStreams driver;
	@Override public DriverReactiveStreams getDriver() { return driver; }
	
	// -------------------------------------------- //
	// CONSTRUCTORS
	// -------------------------------------------- //
	
	public DbReactiveStreams(DriverReactiveStreams driver, MongoDatabase db)
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
