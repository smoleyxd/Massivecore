package com.massivecraft.massivecore.entity.migrator;

import com.massivecraft.massivecore.item.DataItemStack;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.xlib.gson.JsonArray;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonObject;
import com.massivecraft.massivecore.xlib.gson.JsonPrimitive;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;
import java.util.Map.Entry;

import static org.bukkit.Material.LEGACY_PREFIX;

public class MigratorDataItemStack002TheFlattening extends MigratorRoot
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MigratorDataItemStack002TheFlattening i = new MigratorDataItemStack002TheFlattening();
	
	public static MigratorDataItemStack002TheFlattening get()
	{
		return i;
	}
	
	private MigratorDataItemStack002TheFlattening()
	{
		super(DataItemStack.class);
	}
	
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	public Map<String, String> enchantIdtoKey = MUtil.map(
		"0", "protection",
		"1", "fire_protection",
		"2", "feather_falling",
		"3", "blast_protection",
		"4", "projectile_protection",
		"5", "respiration",
		"6", "aqua_affinity",
		"7", "thorns",
		"8", "depth_strider",
		"9", "frost_walker",
		"10", "binding_curse",
		"16", "sharpness",
		"17", "smite",
		"18", "bane_of_arthropods",
		"19", "knockback",
		"20", "fire_aspect",
		"21", "looting",
		"22", "sweeping",
		"32", "efficiency",
		"33", "silk_touch",
		"34", "unbreaking",
		"35", "fortune",
		"48", "power",
		"49", "punch",
		"50", "flame",
		"51", "infinity",
		"61", "luck_of_the_sea",
		"62", "lure",
		"70", "mending",
		"71", "vanishing_curse"
	);
	
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
		
		if (!legacy.startsWith(LEGACY_PREFIX))
		{
			legacy = LEGACY_PREFIX + legacy;
		}
		
		// Get the damage
		JsonPrimitive jsonDamage = entity.getAsJsonPrimitive("damage");
		int damage = 0;
		if (jsonDamage != null && jsonDamage.isNumber())
		{
			damage = jsonDamage.getAsInt();
		}
		
		Material legacyMaterial = Material.getMaterial(legacy);
		if (legacyMaterial == null) return;
		
		MaterialData legacyData = new MaterialData(legacyMaterial, (byte) damage);
		
		try
		{
			Material newMaterial = Bukkit.getUnsafe().fromLegacy(legacyData);
			
			// White Bed Edgecase
			// Bukkit seems to be converting White beds to Red beds because of the 0 damage value.
			
			if (newMaterial == Material.RED_BED && damage != 14) newMaterial = Material.WHITE_BED;
			
			entity.addProperty("id", newMaterial.getKey().getKey().toUpperCase());
			if (newMaterial.getMaxDurability() <= 0) entity.remove("damage");
		}
		catch (Exception e)
		{
			entity.add("id", new JsonPrimitive(Material.PAPER.getKey().getKey()));
			entity.add("name", new JsonPrimitive("§b☢Error Migrating '" + legacy + "' - Message Birb☢"));
			entity.remove("damage");
			System.out.println("Error migrating '" + legacy + "' - Paper placed.");
		}
		
		for (String key : MUtil.list("enchants", "stored-enchants"))
		{
			JsonObject enchantsObject = entity.getAsJsonObject(key);
			if (enchantsObject != null && !enchantsObject.isJsonNull())
			{
				JsonObject newEnchants = new JsonObject();
				for (Entry<String, JsonElement> entry : enchantsObject.entrySet())
				{
					String enchantKey = enchantIdtoKey.get(entry.getKey());
					if (enchantKey == null) continue;
					newEnchants.add(enchantKey, entry.getValue());
				}
				entity.add(key, newEnchants);
			}
		}
		
		JsonArray bannerArray = entity.getAsJsonArray("banner");
		if (bannerArray != null && !bannerArray.isJsonNull())
		{
			for (JsonElement jsonElement : bannerArray)
			{
				JsonObject bannerObject = jsonElement.getAsJsonObject();
				try
				{
					long bannerColour = bannerObject.get("color").getAsLong();
					bannerObject.addProperty("color", DyeColor.getByDyeData((byte) bannerColour).toString());
				}
				catch (Exception ignored)
				{
					System.out.println("Error converting " + bannerObject.toString() + " to Keyed variant.");
				}
			}
		}
		
		JsonArray potionEffectsArray = entity.getAsJsonArray("potion-effects");
		if (potionEffectsArray != null && !potionEffectsArray.isJsonNull())
		{
			for (JsonElement jsonElement : potionEffectsArray)
			{
				JsonObject potionObject = jsonElement.getAsJsonObject();
				try
				{
					long potionId = potionObject.get("id").getAsLong();
					potionObject.addProperty("id", PotionEffectType.getById((int) potionId).getName().toLowerCase());
				}
				catch (Exception ignored)
				{
					System.out.println("Error converting " + potionObject.toString() + " to Keyed variant.");
				}
			}
		}
	}
	
}
