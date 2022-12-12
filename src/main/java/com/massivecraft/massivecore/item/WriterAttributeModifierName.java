package com.massivecraft.massivecore.item;

import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.NotNull;

public class WriterAttributeModifierName extends WriterAbstractAttributeModifier<String, String>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterAttributeModifierName i = new WriterAttributeModifierName();
	public static WriterAttributeModifierName get() { return i; }
	public WriterAttributeModifierName()
	{
		super("name");
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public String getA(@NotNull DataAttributeModifier ca, Object d)
	{
		return ca.getName();
	}
	
	@Override
	public void setA(@NotNull DataAttributeModifier ca, String fa, Object d)
	{
		ca.setName(fa);
	}
	
	@Override
	public String getB(@NotNull AttributeModifier cb, Object d)
	{
		return cb.getName();
	}
	
}
