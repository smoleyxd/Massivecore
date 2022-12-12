package com.massivecraft.massivecore.item;

import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.jetbrains.annotations.NotNull;

public class WriterAttributeModifierOperation extends WriterAbstractAttributeModifier<Operation, Operation>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterAttributeModifierOperation i = new WriterAttributeModifierOperation();
	public static WriterAttributeModifierOperation get() { return i; }
	public WriterAttributeModifierOperation()
	{
		super("operation");
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public Operation getA(@NotNull DataAttributeModifier ca, Object d)
	{
		return ca.getOperation();
	}
	
	@Override
	public void setA(@NotNull DataAttributeModifier ca, Operation fa, Object d)
	{
		ca.setOperation(fa);
	}
	
	@Override
	public Operation getB(@NotNull AttributeModifier cb, Object d)
	{
		return cb.getOperation();
	}
	
}
