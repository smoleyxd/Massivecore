package com.massivecraft.massivecore.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;

public class WriterItemStackMetaChargedProjectiles extends WriterAbstractItemStackMetaField<CrossbowMeta, Map<Integer, DataItemStack>, ItemStack[]>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaChargedProjectiles i = new WriterItemStackMetaChargedProjectiles();
	public static WriterItemStackMetaChargedProjectiles get() { return i; }
	public WriterItemStackMetaChargedProjectiles()
	{
		super(CrossbowMeta.class);
		this.setMaterial(Material.CROSSBOW);
		this.setConverterTo(ConverterToInventoryContents.get());
		this.setConverterFrom(ConverterFromInventoryContents.get());
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public Map<Integer, DataItemStack> getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getChargedProjectiles();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, Map<Integer, DataItemStack> fa, ItemStack d)
	{
		ca.setChargedProjectiles(fa);
	}

	@Override
	public ItemStack[] getB(@NotNull CrossbowMeta cb, ItemStack d)
	{
		// Null
		if (cb == null) return null;
		if (!cb.hasChargedProjectiles()) return null;
		return cb.getChargedProjectiles().toArray(new ItemStack[0]);
	}

	@Override
	public void setB(@NotNull CrossbowMeta crossbowMeta, ItemStack[] chargedProjectiles, ItemStack d)
	{
		// Null
		if (crossbowMeta == null) return;
		if (chargedProjectiles == null || chargedProjectiles.length == 0) return;
		
		// Set
		crossbowMeta.setChargedProjectiles(Arrays.asList(chargedProjectiles));
	}
	
}
