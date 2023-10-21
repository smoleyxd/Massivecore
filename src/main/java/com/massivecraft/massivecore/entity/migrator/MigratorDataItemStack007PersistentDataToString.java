package com.massivecraft.massivecore.entity.migrator;

import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.item.DataItemStack;
import com.massivecraft.massivecore.nms.NmsPersistentData;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonObject;
import com.massivecraft.massivecore.xlib.gson.JsonPrimitive;
import com.massivecraft.massivecore.xlib.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class MigratorDataItemStack007PersistentDataToString extends MigratorRoot
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static MigratorDataItemStack007PersistentDataToString i = new MigratorDataItemStack007PersistentDataToString();
	public static MigratorDataItemStack007PersistentDataToString get() { return i; }
	
	private MigratorDataItemStack007PersistentDataToString() { super(DataItemStack.class); }
	
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	private static final String fieldName = "persistentData";
	private static final Type typeToken = new TypeToken<Map<String, Object>>() { }.getType();
	
	// -------------------------------------------- //
	// CONVERSION
	// -------------------------------------------- //
	
	@Override
	@SuppressWarnings("deprecation")
	public void migrateInner(JsonObject entity) {
		// Get the existing PDC (If there is one)
		JsonElement jsonPDC = entity.get(fieldName);
		if (jsonPDC == null || jsonPDC.isJsonNull()) return;
		
		// Deserialize as Map
		Map<String, Object> persistentData = MassiveCore.gson.fromJson(jsonPDC, typeToken);
		
		// Re-serialize as String
		String persistentDataString = NmsPersistentData.get().mapToString(persistentData);
		
		// Write back to the entity
		entity.add(fieldName, new JsonPrimitive(persistentDataString));
	}
	
}
