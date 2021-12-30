package com.massivecraft.massivecore.command.type.enumeration;

import org.bukkit.inventory.EquipmentSlot;

public class TypeEquipmentSlot extends TypeEnum<EquipmentSlot>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final TypeEquipmentSlot i = new TypeEquipmentSlot();
	public static TypeEquipmentSlot get() { return i; }
	public TypeEquipmentSlot()
	{
		super(EquipmentSlot.class);
	}

}
