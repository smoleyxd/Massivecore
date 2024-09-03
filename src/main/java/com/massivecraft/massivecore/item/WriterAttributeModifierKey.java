package com.massivecraft.massivecore.item;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.NotNull;

public class WriterAttributeModifierKey extends WriterAbstractAttributeModifier<NamespacedKey, NamespacedKey>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterAttributeModifierKey i = new WriterAttributeModifierKey();
	public static WriterAttributeModifierKey get() { return i; }
	public WriterAttributeModifierKey()
	{
		super("key");
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public NamespacedKey getA(@NotNull DataAttributeModifier ca, Object d)
	{
		return ca.getKey();
	}
	
	@Override
	public void setA(@NotNull DataAttributeModifier ca, NamespacedKey fa, Object d)
	{
		ca.setKey(fa);
	}
	
	@Override
	public NamespacedKey getB(@NotNull AttributeModifier cb, Object d)
	{
		return cb.getKey();
	}
	
}
