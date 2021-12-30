package com.massivecraft.massivecore.item;

import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

public class WriterAttributeModifierSlot extends WriterAbstractAttributeModifier<EquipmentSlot, EquipmentSlot>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterAttributeModifierSlot i = new WriterAttributeModifierSlot();
	public static WriterAttributeModifierSlot get() { return i; }
	public WriterAttributeModifierSlot()
	{
		super("slot");
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public EquipmentSlot getA(@NotNull DataAttributeModifier ca, Object d)
	{
		return ca.getSlot();
	}
	
	@Override
	public void setA(@NotNull DataAttributeModifier ca, EquipmentSlot fa, Object d)
	{
		ca.setSlot(fa);
	}
	
	@Override
	public EquipmentSlot getB(@NotNull AttributeModifier cb, Object d)
	{
		return cb.getSlot();
	}
	
}
