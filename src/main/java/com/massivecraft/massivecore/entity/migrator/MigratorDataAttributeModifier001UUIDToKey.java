package com.massivecraft.massivecore.entity.migrator;

import com.massivecraft.massivecore.item.ConverterFromNamespacedKey;
import com.massivecraft.massivecore.item.ConverterToNamespacedKey;
import com.massivecraft.massivecore.item.DataBannerPattern;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;
import com.massivecraft.massivecore.xlib.gson.JsonObject;
import com.massivecraft.massivecore.xlib.gson.JsonPrimitive;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;

public class MigratorDataAttributeModifier001UUIDToKey extends MigratorRoot
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static final MigratorDataAttributeModifier001UUIDToKey i = new MigratorDataAttributeModifier001UUIDToKey();
	public static MigratorDataAttributeModifier001UUIDToKey get() { return i; }
	private MigratorDataAttributeModifier001UUIDToKey()
	{
		super(DataBannerPattern.class);
	}
	
	// -------------------------------------------- //
	// CONVERSION
	// -------------------------------------------- //
	
    @Override
    @SuppressWarnings("UnstableApiUsage")
	public void migrateInner(JsonObject json)
	{
		// Remove UUID
		json.remove("uuid");

		// Get "Name"
		String name = json.get("name").getAsString();
		json.remove("name");

		// Move Name to Key
		NamespacedKey key = ConverterToNamespacedKey.get().convert(name);
		String keyString = ConverterFromNamespacedKey.get().convert(key);
		json.add("key", new JsonPrimitive(keyString));

		// Get "Slot"
		String slot = json.get("slot").getAsString();
		json.remove("slot");

		//Change EquipmentSlot to EquipmentSlotGroup
		EquipmentSlot equipmentSlot = EquipmentSlot.valueOf(slot);
		EquipmentSlotGroup equipmentSlotGroup = equipmentSlot.getGroup();
		json.add("slot", new JsonPrimitive(equipmentSlotGroup.toString()));
	}
	
}
