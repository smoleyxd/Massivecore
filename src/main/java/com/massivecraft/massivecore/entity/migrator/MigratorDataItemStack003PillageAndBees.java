package com.massivecraft.massivecore.entity.migrator;

import com.massivecraft.massivecore.item.DataItemStack;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonObject;

public class MigratorDataItemStack003PillageAndBees extends MigratorRoot
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final MigratorDataItemStack003PillageAndBees i = new MigratorDataItemStack003PillageAndBees();
	
	public static MigratorDataItemStack003PillageAndBees get()
	{
		return i;
	}
	
	private MigratorDataItemStack003PillageAndBees()
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
		
		
		switch (legacy)
		{
			case "CACTUS_GREEN" -> entity.addProperty("id", "GREEN_DYE");
			case "DANDELION_YELLOW" -> entity.addProperty("id", "YELLOW_DYE");
			case "ROSE_RED" -> entity.addProperty("id", "RED_DYE");
			case "SIGN" -> entity.addProperty("id", "OAK_SIGN");
			case "WALL_SIGN" -> entity.addProperty("id", "OAK_WALL_SIGN");
		}
	}
	
}
