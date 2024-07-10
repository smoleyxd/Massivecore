package com.massivecraft.massivecore.item;

import org.bukkit.inventory.EquipmentSlotGroup;

@SuppressWarnings("UnstableApiUsage")
public class ConverterFromEquipmentSlotGroup extends Converter<EquipmentSlotGroup, String>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterFromEquipmentSlotGroup i = new ConverterFromEquipmentSlotGroup();
	public static ConverterFromEquipmentSlotGroup get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
    @Override
	public String convert(EquipmentSlotGroup x)
	{
		if (x == null) return null;
		return x.toString();
	}

}
