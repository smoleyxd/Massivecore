package com.massivecraft.massivecore.item;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlotGroup;

import java.util.Objects;


public abstract class WriterAbstractAttributeModifier<FA, FB> extends WriterAbstractReflect<DataAttributeModifier, AttributeModifier, DataAttributeModifier, AttributeModifier, FA, FB>
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public WriterAbstractAttributeModifier(String fieldName)
	{
		super(AttributeModifier.class, fieldName);
	}
	
	public WriterAbstractAttributeModifier()
	{
		this(null); 
	}
	
	// -------------------------------------------- //
	// CREATE
	// -------------------------------------------- //
	
	@Override
	public DataAttributeModifier createOA()
	{
		return new DataAttributeModifier();
	}
	
    @Override
	@SuppressWarnings({"UnstableApiUsage"})
	public AttributeModifier createOB()
	{
        return new AttributeModifier(
            Objects.requireNonNull(NamespacedKey.fromString("test")),
			0d,
			Operation.ADD_NUMBER,
			EquipmentSlotGroup.ANY
		);
	}
	
}
