package com.massivecraft.massivecore.item;

import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

public class WriterPotionEffectDuraction extends WriterAbstractPotionEffect<Integer, Integer>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterPotionEffectDuraction i = new WriterPotionEffectDuraction();
	public static WriterPotionEffectDuraction get() { return i; }
	public WriterPotionEffectDuraction()
	{
		super("duration");
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public Integer getA(@NotNull DataPotionEffect ca, Object d)
	{
		return ca.getDuration();
	}
	
	@Override
	public void setA(@NotNull DataPotionEffect ca, Integer fa, Object d)
	{
		ca.setDuration(fa);
	}
	
	@Override
	public Integer getB(@NotNull PotionEffect cb, Object d)
	{
		return cb.getDuration();
	}
	
}
