package com.massivecraft.massivecore.entity.migrator;

import com.massivecraft.massivecore.item.DataItemStack;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;
import com.massivecraft.massivecore.xlib.gson.JsonObject;

public class MigratorDataItemStack004SkullCleanup extends MigratorRoot
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final MigratorDataItemStack004SkullCleanup i = new MigratorDataItemStack004SkullCleanup();
	public static MigratorDataItemStack004SkullCleanup get() { return i; }
	private MigratorDataItemStack004SkullCleanup() { super(DataItemStack.class); }
	
	// -------------------------------------------- //
	// CONVERSION
	// -------------------------------------------- //
	
	@Override
	public void migrateInner(JsonObject entity) { entity.remove("skull"); }
	
}
