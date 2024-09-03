package com.massivecraft.massivecore.item;

import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class WriterAttributeModifierSlot extends WriterAbstractAttributeModifier<String, EquipmentSlotGroup>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterAttributeModifierSlot i = new WriterAttributeModifierSlot();
	public static WriterAttributeModifierSlot get() { return i; }
	public WriterAttributeModifierSlot()
	{
		super("slot");
		this.setConverterTo(ConverterToEquipmentSlotGroup.get());
		this.setConverterFrom(ConverterFromEquipmentSlotGroup.get());
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public String getA(@NotNull DataAttributeModifier ca, Object d)
	{
		return ca.getSlot();
	}
	
	@Override
	public void setA(@NotNull DataAttributeModifier ca, String fa, Object d)
	{
		ca.setSlot(fa);
	}
	
	@Override
	public EquipmentSlotGroup getB(@NotNull AttributeModifier cb, Object d)
	{
		return cb.getSlotGroup();
	}
	
}
