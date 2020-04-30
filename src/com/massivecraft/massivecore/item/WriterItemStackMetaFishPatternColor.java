package com.massivecraft.massivecore.item;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

public class WriterItemStackMetaFishPatternColor extends WriterAbstractItemStackMetaField<TropicalFishBucketMeta, DyeColor, DyeColor>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaFishPatternColor i = new WriterItemStackMetaFishPatternColor();
	public static WriterItemStackMetaFishPatternColor get() { return i; }
	public WriterItemStackMetaFishPatternColor()
	{
		super(TropicalFishBucketMeta.class);
		this.setMaterial(Material.TROPICAL_FISH_BUCKET);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public DyeColor getA(DataItemStack ca, ItemStack d)
	{
		return ca.getFishPatternColor();
	}

	@Override
	public void setA(DataItemStack ca, DyeColor fa, ItemStack d)
	{
		ca.setFishPatternColor(fa);
	}

	@Override
	public DyeColor getB(TropicalFishBucketMeta cb, ItemStack d)
	{
		if (cb.hasVariant()) return cb.getPatternColor();
		return null;
	}

	@Override
	public void setB(TropicalFishBucketMeta cb, DyeColor fb, ItemStack d)
	{
		if (fb == null)
		{
			if (!cb.hasVariant()) return;
			fb = DyeColor.WHITE;
		}
		cb.setPatternColor(fb);
	}
	
}
