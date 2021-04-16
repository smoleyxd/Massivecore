package com.massivecraft.massivecore.item;

import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.jetbrains.annotations.NotNull;

public class WriterFireworkEffectType extends WriterAbstractFireworkEffect<String, Type>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterFireworkEffectType i = new WriterFireworkEffectType();
	public static WriterFireworkEffectType get() { return i; }
	public WriterFireworkEffectType()
	{
		super("type");
		this.setConverterTo(ConverterToFireworkEffectType.get());
		this.setConverterFrom(ConverterFromFireworkEffectType.get());
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public String getA(@NotNull DataFireworkEffect ca, Object d)
	{
		return ca.getType();
	}
	
	@Override
	public void setA(@NotNull DataFireworkEffect ca, String fa, Object d)
	{
		ca.setType(fa);
	}
	
	@Override
	public Type getB(@NotNull FireworkEffect cb, Object d)
	{
		return cb.getType();
	}
	
}
