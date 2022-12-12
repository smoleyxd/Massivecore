package com.massivecraft.massivecore.item;

import org.bukkit.Material;
import org.bukkit.entity.Axolotl.Variant;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.AxolotlBucketMeta;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackMetaAxolotlVariant extends WriterAbstractItemStackMetaField<AxolotlBucketMeta, Variant, Variant>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaAxolotlVariant i = new WriterItemStackMetaAxolotlVariant();
	public static WriterItemStackMetaAxolotlVariant get() { return i; }
	public WriterItemStackMetaAxolotlVariant()
	{
		super(AxolotlBucketMeta.class);
		this.setMaterial(Material.AXOLOTL_BUCKET);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public Variant getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getAxolotlVariant();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, Variant fa, ItemStack d)
	{
		ca.setAxolotlVariant(fa);
	}

	@Override
	public Variant getB(@NotNull AxolotlBucketMeta cb, ItemStack d)
	{
		if (cb.hasVariant()) return cb.getVariant();
		return null;
	}

	@Override
	public void setB(@NotNull AxolotlBucketMeta cb, Variant fb, ItemStack d)
	{
		if (fb == null)
		{
			if (!cb.hasVariant()) return;
			fb = Variant.WILD;
		}
		cb.setVariant(fb);
	}
	
}
