package com.massivecraft.massivecore.entity.migrator;

import com.massivecraft.massivecore.item.DataItemStack;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonObject;

public class MigratorDataItemStack005GrassPathNoMore extends MigratorRoot
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final MigratorDataItemStack005GrassPathNoMore i = new MigratorDataItemStack005GrassPathNoMore();
	
	public static MigratorDataItemStack005GrassPathNoMore get()
	{
		return i;
	}
	
	private MigratorDataItemStack005GrassPathNoMore()
	{
		super(DataItemStack.class);
	}
	
	// -------------------------------------------- //
	// CONVERSION
	// -------------------------------------------- //
	
	@Override
	public void migrateInner(JsonObject entity)
	{
		// Get the legacy id
		JsonElement jsonId = entity.get("id");
		if (jsonId == null || jsonId.isJsonNull()) return;
		String legacy = jsonId.getAsString();
		
		if ("GRASS_PATH".equals(legacy)) entity.addProperty("id", "DIRT_PATH");
	}
	
}
