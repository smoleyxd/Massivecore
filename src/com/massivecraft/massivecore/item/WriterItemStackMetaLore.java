package com.massivecraft.massivecore.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WriterItemStackMetaLore extends WriterAbstractItemStackMetaField<ItemMeta, List<String>, List<String>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaLore i = new WriterItemStackMetaLore();
	public static WriterItemStackMetaLore get() { return i; }
	public WriterItemStackMetaLore()
	{
		super(ItemMeta.class);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public List<String> getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getLore();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, List<String> fa, ItemStack d)
	{
		ca.setLore(fa);
	}

	@Override
	public List<String> getB(@NotNull ItemMeta cb, ItemStack d)
	{
		return cb.getLore();
	}

	@Override
	public void setB(@NotNull ItemMeta cb, List<String> fb, ItemStack d)
	{
		cb.setLore(fb);		
	}
	
}
