package com.massivecraft.massivecore.item;

import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

public class WriterPotionEffectParticles extends WriterAbstractPotionEffect<Boolean, Boolean>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterPotionEffectParticles i = new WriterPotionEffectParticles();
	public static WriterPotionEffectParticles get() { return i; }
	public WriterPotionEffectParticles()
	{
		super("particles");
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public Boolean getA(@NotNull DataPotionEffect ca, Object d)
	{
		return ca.isParticles();
	}
	
	@Override
	public void setA(@NotNull DataPotionEffect ca, Boolean fa, Object d)
	{
		ca.setParticles(fa);
	}
	
	@Override
	public Boolean getB(@NotNull PotionEffect cb, Object d)
	{
		return cb.hasParticles();
	}
	
}
