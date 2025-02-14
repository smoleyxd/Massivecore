package com.massivecraft.massivecore.store;

import com.massivecraft.massivecore.ConfServer;
import com.massivecraft.massivecore.MassiveCoreMConf;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MStore
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	// This class also serves the purpose of containing database related constants.
	
	public static final boolean DEBUG_ENABLED = false;
	
	// -------------------------------------------- //
	// DRIVER REGISTRY
	// -------------------------------------------- //
	
	private static final Map<String, Driver> drivers = new HashMap<>();
	public static boolean registerDriver(@NotNull Driver driver)
	{
		if (drivers.containsKey(driver.getDriverName())) return false;
		drivers.put(driver.getDriverName(), driver);
		return true;
	}

	public static Driver getDriver(String id)
	{
		return drivers.get(id);
	}
	
	static
	{
		registerDriver(DriverMongo.get());
		registerDriver(DriverMongoSrv.get());
		registerDriver(DriverFlatfile.get());
	}
	
	// -------------------------------------------- //
	// ID CREATION
	// -------------------------------------------- //
	
	public static String createId()
	{
		return UUID.randomUUID().toString();
	}
	
	// -------------------------------------------- //
	// JSON ELEMENT EQUAL
	// -------------------------------------------- //
	
	public static boolean equal(JsonElement one, JsonElement two)
	{
		if (one == null) return two == null;
		if (two == null) return false;
		
		return GsonEqualsChecker.equals(one, two);
	}
	
	// -------------------------------------------- //
	// FROODLSCHTEIN
	// -------------------------------------------- //
	
	// We cache databases here
	private static final Map<String, Db> uri2db = new HashMap<>();
	
	public static String resolveAlias(String alias)
	{
		String uri = ConfServer.alias2uri.get(alias);
		if (uri == null) return alias;
		return resolveAlias(uri);
	}

	public static @Nullable Db getDb(String alias)
	{
		String uri = resolveAlias(alias);
		Db db = uri2db.get(uri);
		if (db != null) return db;

		try
		{
			db = getDb(new URI(uri));
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
			return null;
		}

		uri2db.put(uri, db);

		return db;
	}

	public static Db getDb()
	{
		return getDb(ConfServer.dburi);
	}

	public static @Nullable Db getDb(@NotNull URI uri)
	{
		String scheme = uri.getScheme();
		Driver driver = getDriver(scheme);
		if (driver == null) return null;
		return driver.getDb(uri.toString());
	}

	// -------------------------------------------- //
	// OTHER
	// -------------------------------------------- //

	public static boolean isLocalPollingDebugEnabled()
	{
		return ConfServer.localPollingEnabled && MassiveCoreMConf.get().warnOnLocalAlter;
	}


}
