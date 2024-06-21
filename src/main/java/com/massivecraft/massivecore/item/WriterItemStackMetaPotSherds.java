package com.massivecraft.massivecore.item;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.BlockState;
import org.bukkit.block.DecoratedPot;
import org.bukkit.block.DecoratedPot.Side;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Map.Entry;

public class WriterItemStackMetaPotSherds extends WriterAbstractItemStackMetaField<BlockStateMeta, Map<Side, Material>, Map<DecoratedPot.Side, Material>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaPotSherds i = new WriterItemStackMetaPotSherds();
	public static WriterItemStackMetaPotSherds get() { return i; }
	public WriterItemStackMetaPotSherds()
	{
		super(BlockStateMeta.class);
		this.setMaterial(Material.DECORATED_POT);
	}
	
	// -------------------------------------------- //
	// DECORATED POT
	// -------------------------------------------- //
	
	@Contract("null, _ -> null")
	public static DecoratedPot getDecoratedPot(BlockStateMeta meta, boolean creative)
	{
		if (meta == null) return null;
		
		// Creative
		if (!meta.hasBlockState() && !creative) return null;
		
		// Try
		try
		{
			BlockState ret = meta.getBlockState();
			if (!(ret instanceof DecoratedPot)) return null;
			return (DecoratedPot)ret;
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public static void setDecoratedPot(BlockStateMeta meta, DecoratedPot decoratedPot)
	{
		if (meta == null || decoratedPot == null) return;
		
		// Update with Force without Physics
		decoratedPot.update(true, false);
		
		// Apply
		meta.setBlockState(decoratedPot);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public Map<DecoratedPot.Side, Material> getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getSherds();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, Map<DecoratedPot.Side, Material> fa, ItemStack d)
	{
		ca.setSherds(fa);
	}

	@Override
	public Map<DecoratedPot.Side, Material> getB(@NotNull BlockStateMeta cb, ItemStack d)
	{
		// Get
		DecoratedPot decoratedPot = getDecoratedPot(cb, false);
		if (decoratedPot == null) return null;
		
		// Return
		return decoratedPot.getSherds();
	}

	@Override
	public void setB(@NotNull BlockStateMeta cb, Map<DecoratedPot.Side, Material> fb, ItemStack d)
	{
		// Get
		boolean creative = (fb != null);
		DecoratedPot decoratedPot = getDecoratedPot(cb, creative);
		if (decoratedPot == null || fb == null) return;
		
		// Change
		for (Entry<Side, Material> sideMaterialEntry : fb.entrySet())
		{
			decoratedPot.setSherd(
				sideMaterialEntry.getKey(),
				sideMaterialEntry.getValue()
			);
		}
		
		// Set
		setDecoratedPot(cb, decoratedPot);
	}
	
}
