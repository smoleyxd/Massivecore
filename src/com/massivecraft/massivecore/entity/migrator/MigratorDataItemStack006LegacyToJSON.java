package com.massivecraft.massivecore.entity.migrator;

import com.massivecraft.massivecore.item.DataItemStack;
import com.massivecraft.massivecore.nms.NmsItemStackMeta;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;
import com.massivecraft.massivecore.xlib.gson.JsonArray;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonObject;
import com.massivecraft.massivecore.xlib.gson.JsonPrimitive;

public class MigratorDataItemStack006LegacyToJSON extends MigratorRoot
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static MigratorDataItemStack006LegacyToJSON i = new MigratorDataItemStack006LegacyToJSON();
	public static MigratorDataItemStack006LegacyToJSON get() { return i; }
	
	private MigratorDataItemStack006LegacyToJSON() { super(DataItemStack.class); }
	
	// -------------------------------------------- //
	// CONVERSION
	// -------------------------------------------- //
	
	@Override
	public void migrateInner(JsonObject entity) {
		// Get the legacy displayName
		JsonElement jsonName = entity.get("name");
		if (jsonName != null && !jsonName.isJsonNull()) {
			String displayName = jsonName.getAsString();
			entity.add("name", new JsonPrimitive(NmsItemStackMeta.get().fromLegacyToJSON(displayName)));
		}
		
		// Get the legacy lore lines
		JsonElement jsonLore = entity.get("lore");
		if (jsonLore != null && !jsonLore.isJsonNull()) {
			JsonArray legacyLore = jsonLore.getAsJsonArray();
			
			JsonArray newLore = new JsonArray();
			
			for (JsonElement jsonElement : legacyLore)
			{
				String loreLine = jsonElement.getAsString();
				loreLine = NmsItemStackMeta.get().fromLegacyToJSON(loreLine);
				if (loreLine == null || loreLine.isEmpty()) loreLine = "{\"text\":\"\"}";
				newLore.add(loreLine);
			}
			
			entity.add("lore", newLore);
		}
	}
	
}
