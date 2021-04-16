package com.massivecraft.massivecore.item;

import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

public class WriterPotionEffectAmbient extends WriterAbstractPotionEffect<Boolean, Boolean>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterPotionEffectAmbient i = new WriterPotionEffectAmbient();
	public static WriterPotionEffectAmbient get() { return i; }
	public WriterPotionEffectAmbient()
	{
		super("ambient");
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public Boolean getA(@NotNull DataPotionEffect ca, Object d)
	{
		return ca.isAmbient();
	}
	
	@Override
	public void setA(@NotNull DataPotionEffect ca, Boolean fa, Object d)
	{
		ca.setAmbient(fa);
	}
	
	@Override
	public Boolean getB(@NotNull PotionEffect cb, Object d)
	{
		return cb.isAmbient();
	}
	
}
