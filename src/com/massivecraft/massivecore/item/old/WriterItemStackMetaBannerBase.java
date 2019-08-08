package com.massivecraft.massivecore.item.old;

import com.massivecraft.massivecore.item.DataItemStack;
import com.massivecraft.massivecore.item.WriterAbstractItemStackMetaField;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

// This no longer is meta data but different materials
@Deprecated
public class WriterItemStackMetaBannerBase extends WriterAbstractItemStackMetaField<BannerMeta, DyeColor, DyeColor>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaBannerBase i = new WriterItemStackMetaBannerBase();
	public static WriterItemStackMetaBannerBase get() { return i; }
	public WriterItemStackMetaBannerBase()
	{
		super(BannerMeta.class);
		this.setMaterial(Material.LEGACY_BANNER); // FIXME pray this works for now, but change the writers to allow multiple materials
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public DyeColor getA(DataItemStack ca, ItemStack d)
	{
		return ca.getBannerBase();
	}

	@Override
	public void setA(DataItemStack ca, DyeColor fa, ItemStack d)
	{
		ca.setBannerBase(fa);
	}

	@Override
	public DyeColor getB(BannerMeta cb, ItemStack d)
	{
		return cb.getBaseColor();
	}

	@Override
	public void setB(BannerMeta cb, DyeColor fb, ItemStack d)
	{
		cb.setBaseColor(fb);
	}
	
}
