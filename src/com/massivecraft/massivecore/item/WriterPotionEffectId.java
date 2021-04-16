package com.massivecraft.massivecore.item;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class WriterPotionEffectId extends WriterAbstractPotionEffect<PotionEffectType, PotionEffectType>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterPotionEffectId i = new WriterPotionEffectId();
	public static WriterPotionEffectId get() { return i; }
	public WriterPotionEffectId()
	{
		super("type");
		// when we don't have explicit converters, it uses a "return self casted" policy
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public PotionEffectType getA(@NotNull DataPotionEffect ca, Object d)
	{
		return ca.getId();
	}
	
	@Override
	public void setA(@NotNull DataPotionEffect ca, PotionEffectType fa, Object d)
	{
		ca.setId(fa);
	}
	
	@Override
	public PotionEffectType getB(@NotNull PotionEffect cb, Object d)
	{
		return cb.getType();
	}
	
}
