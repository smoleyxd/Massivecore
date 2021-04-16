package com.massivecraft.massivecore.item;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackMetaShieldBase extends WriterAbstractItemStackMetaField<BlockStateMeta, DyeColor, DyeColor>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaShieldBase i = new WriterItemStackMetaShieldBase();
	public static WriterItemStackMetaShieldBase get() { return i; }
	public WriterItemStackMetaShieldBase()
	{
		super(BlockStateMeta.class);
		this.setMaterial(Material.SHIELD);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public DyeColor getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getBannerBase();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, DyeColor fa, ItemStack d)
	{
		ca.setBannerBase(fa);
	}

	@Override
	public DyeColor getB(@NotNull BlockStateMeta cb, ItemStack d)
	{
		// Get
		Banner banner = getBanner(cb, false);
		if (banner == null) return null;
		
		// Return
		return banner.getBaseColor();
	}

	@Override
	public void setB(@NotNull BlockStateMeta cb, DyeColor fb, ItemStack d)
	{
		// Get
		boolean creative = (fb != null);
		Banner banner = getBanner(cb, creative);
		if (banner == null || fb == null) return;
		
		// Change
		banner.setBaseColor(fb);
		
		// Set
		setBanner(cb, banner);
	}
	
}
