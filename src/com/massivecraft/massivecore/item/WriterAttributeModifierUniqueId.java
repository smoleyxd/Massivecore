package com.massivecraft.massivecore.item;

import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class WriterAttributeModifierUniqueId extends WriterAbstractAttributeModifier<UUID, UUID>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterAttributeModifierUniqueId i = new WriterAttributeModifierUniqueId();
	public static WriterAttributeModifierUniqueId get() { return i; }
	public WriterAttributeModifierUniqueId()
	{
		super("uuid");
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public UUID getA(@NotNull DataAttributeModifier ca, Object d)
	{
		return ca.getUniqueId();
	}
	
	@Override
	public void setA(@NotNull DataAttributeModifier ca, UUID fa, Object d)
	{
		ca.setUniqueId(fa);
	}
	
	@Override
	public UUID getB(@NotNull AttributeModifier cb, Object d)
	{
		return cb.getUniqueId();
	}
	
}
