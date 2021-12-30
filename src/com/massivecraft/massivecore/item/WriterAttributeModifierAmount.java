package com.massivecraft.massivecore.item;

import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.NotNull;

public class WriterAttributeModifierAmount extends WriterAbstractAttributeModifier<Double, Double>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterAttributeModifierAmount i = new WriterAttributeModifierAmount();
	public static WriterAttributeModifierAmount get() { return i; }
	public WriterAttributeModifierAmount()
	{
		super("amount");
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public Double getA(@NotNull DataAttributeModifier ca, Object d)
	{
		return ca.getAmount();
	}
	
	@Override
	public void setA(@NotNull DataAttributeModifier ca, Double fa, Object d)
	{
		ca.setAmount(fa);
	}
	
	@Override
	public Double getB(@NotNull AttributeModifier cb, Object d)
	{
		return cb.getAmount();
	}
	
}
