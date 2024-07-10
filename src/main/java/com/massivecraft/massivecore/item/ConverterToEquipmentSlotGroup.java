package com.massivecraft.massivecore.item;

import org.bukkit.inventory.EquipmentSlotGroup;

@SuppressWarnings("UnstableApiUsage")
public class ConverterToEquipmentSlotGroup extends Converter<String, EquipmentSlotGroup>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterToEquipmentSlotGroup i = new ConverterToEquipmentSlotGroup();
	public static ConverterToEquipmentSlotGroup get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
    @Override
	public EquipmentSlotGroup convert(String x)
	{
		if (x == null) return null;
		return EquipmentSlotGroup.getByName(x);
	}

}
