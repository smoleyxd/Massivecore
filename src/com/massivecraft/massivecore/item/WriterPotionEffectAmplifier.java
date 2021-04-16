package com.massivecraft.massivecore.item;

import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

public class WriterPotionEffectAmplifier extends WriterAbstractPotionEffect<Integer, Integer>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterPotionEffectAmplifier i = new WriterPotionEffectAmplifier();
	public static WriterPotionEffectAmplifier get() { return i; }
	public WriterPotionEffectAmplifier()
	{
		super("amplifier");
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public Integer getA(@NotNull DataPotionEffect ca, Object d)
	{
		return ca.getAmplifier();
	}
	
	@Override
	public void setA(@NotNull DataPotionEffect ca, Integer fa, Object d)
	{
		ca.setAmplifier(fa);
	}
	
	@Override
	public Integer getB(@NotNull PotionEffect cb, Object d)
	{
		return cb.getAmplifier();
	}
	
}
