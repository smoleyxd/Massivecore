package com.massivecraft.massivecore.entity.migrator;

import com.massivecraft.massivecore.item.DataItemStack;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonObject;

public class MigratorDataItemStack008GrassToShortGrass extends MigratorRoot
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final MigratorDataItemStack008GrassToShortGrass i = new MigratorDataItemStack008GrassToShortGrass();
	
	public static MigratorDataItemStack008GrassToShortGrass get()
	{
		return i;
	}
	
	private MigratorDataItemStack008GrassToShortGrass()
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
		
		if ("GRASS".equals(legacy)) entity.addProperty("id", "SHORT_GRASS");
	}
	
}
