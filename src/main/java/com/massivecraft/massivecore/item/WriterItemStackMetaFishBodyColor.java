package com.massivecraft.massivecore.item;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackMetaFishBodyColor extends WriterAbstractItemStackMetaField<TropicalFishBucketMeta, DyeColor, DyeColor>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaFishBodyColor i = new WriterItemStackMetaFishBodyColor();
	public static WriterItemStackMetaFishBodyColor get() { return i; }
	public WriterItemStackMetaFishBodyColor()
	{
		super(TropicalFishBucketMeta.class);
		this.setMaterial(Material.TROPICAL_FISH_BUCKET);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public DyeColor getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getFishBodyColor();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, DyeColor fa, ItemStack d)
	{
		ca.setFishBodyColor(fa);
	}

	@Override
	public DyeColor getB(@NotNull TropicalFishBucketMeta cb, ItemStack d)
	{
		if (cb.hasVariant()) return cb.getBodyColor();
		return null;
	}

	@Override
	public void setB(@NotNull TropicalFishBucketMeta cb, DyeColor fb, ItemStack d)
	{
		if (fb == null)
		{
			if (!cb.hasVariant()) return;
			fb = DyeColor.WHITE;
		}
		cb.setBodyColor(fb);
	}
	
}
