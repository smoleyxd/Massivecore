package com.massivecraft.massivecore.item;

import org.bukkit.FireworkEffect;
import org.jetbrains.annotations.NotNull;

public class WriterFireworkEffectFlicker extends WriterAbstractFireworkEffect<Boolean, Boolean>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterFireworkEffectFlicker i = new WriterFireworkEffectFlicker();
	public static WriterFireworkEffectFlicker get() { return i; }
	public WriterFireworkEffectFlicker()
	{
		super("flicker");
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public Boolean getA(@NotNull DataFireworkEffect ca, Object d)
	{
		return ca.hasFlicker();
	}
	
	@Override
	public void setA(@NotNull DataFireworkEffect ca, Boolean fa, Object d)
	{
		ca.setFlicker(fa);
	}
	
	@Override
	public Boolean getB(@NotNull FireworkEffect cb, Object d)
	{
		return cb.hasFlicker();
	}
	
}
