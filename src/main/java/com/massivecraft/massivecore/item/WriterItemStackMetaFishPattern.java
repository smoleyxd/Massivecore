package com.massivecraft.massivecore.item;

import org.bukkit.Material;
import org.bukkit.entity.TropicalFish.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackMetaFishPattern extends WriterAbstractItemStackMetaField<TropicalFishBucketMeta, Pattern, Pattern>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaFishPattern i = new WriterItemStackMetaFishPattern();
	public static WriterItemStackMetaFishPattern get() { return i; }
	public WriterItemStackMetaFishPattern()
	{
		super(TropicalFishBucketMeta.class);
		this.setMaterial(Material.TROPICAL_FISH_BUCKET);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public Pattern getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getFishPattern();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, Pattern fa, ItemStack d)
	{
		ca.setFishPattern(fa);
	}

	@Override
	public Pattern getB(@NotNull TropicalFishBucketMeta cb, ItemStack d)
	{
		if (cb.hasVariant()) return cb.getPattern();
		return null;
	}

	@Override
	public void setB(@NotNull TropicalFishBucketMeta cb, Pattern fb, ItemStack d)
	{
		if (fb == null)
		{
			if (!cb.hasVariant()) return;
			fb = Pattern.KOB;
		}
		cb.setPattern(fb);
	}
	
}
