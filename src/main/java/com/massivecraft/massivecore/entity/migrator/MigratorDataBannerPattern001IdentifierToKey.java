package com.massivecraft.massivecore.entity.migrator;

import com.massivecraft.massivecore.item.DataBannerPattern;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonObject;
import com.massivecraft.massivecore.xlib.gson.JsonPrimitive;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.banner.PatternType;

public class MigratorDataBannerPattern001IdentifierToKey extends MigratorRoot
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static final MigratorDataBannerPattern001IdentifierToKey i = new MigratorDataBannerPattern001IdentifierToKey();
	public static MigratorDataBannerPattern001IdentifierToKey get() { return i; }
	private MigratorDataBannerPattern001IdentifierToKey()
	{
		super(DataBannerPattern.class);
	}
	
	// -------------------------------------------- //
	// CONVERSION
	// -------------------------------------------- //
	
    @Override
	@SuppressWarnings("removal")
	public void migrateInner(JsonObject json)
	{
		// Get the id
		JsonElement id = json.get("id");
		if (id == null || id.isJsonNull()) return;
	
		JsonPrimitive primitive = id.getAsJsonPrimitive();
		
		// Only convert if string
		if (!primitive.isString()) return;
		String identifier = primitive.getAsString();

		// Get the existing PatternType
		PatternType patternType = PatternType.getByIdentifier(identifier);
		if (patternType == null) throw new RuntimeException(identifier + " is not a valid pattern type identifier");

		// Get the key from the registry
		NamespacedKey namespacedKey = Registry.BANNER_PATTERN.getKey(patternType);
		if (namespacedKey == null) throw new RuntimeException(identifier + " is not a valid namespaced key identifier");

		// Store the new key
		JsonElement newValue = new JsonPrimitive(namespacedKey.toString());
		json.add("id", newValue);
	}
	
}
