package com.massivecraft.massivecore.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WriterItemStackMetaSuspiciousStewEffects extends WriterAbstractItemStackMetaField<SuspiciousStewMeta, List<DataPotionEffect>, List<PotionEffect>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaSuspiciousStewEffects i = new WriterItemStackMetaSuspiciousStewEffects();
	public static WriterItemStackMetaSuspiciousStewEffects get() { return i; }
	
	public WriterItemStackMetaSuspiciousStewEffects()
	{
		super(SuspiciousStewMeta.class);
		this.setMaterial(Material.SUSPICIOUS_STEW);
		this.setConverterTo(ConverterToPotionEffects.get());
		this.setConverterFrom(ConverterFromPotionEffects.get());
		this.addDependencyClasses(
			WriterPotionEffect.class
		);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public List<DataPotionEffect> getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getPotionEffects();
	}
	
	@Override
	public void setA(@NotNull DataItemStack ca, List<DataPotionEffect> fa, ItemStack d)
	{
		ca.setPotionEffects(fa);
	}
	
	@Override
	public List<PotionEffect> getB(@NotNull SuspiciousStewMeta cb, ItemStack d)
	{
		return cb.getCustomEffects();
	}
	
	@Override
	public void setB(@NotNull SuspiciousStewMeta cb, List<PotionEffect> fb, ItemStack d)
	{
		for (PotionEffect potionEffect : fb)
		{
			cb.addCustomEffect(potionEffect, false);
		}
	}
	
}
