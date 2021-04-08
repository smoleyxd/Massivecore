package com.massivecraft.massivecore.item;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackCount extends WriterAbstractItemStackField<Integer, Integer>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackCount i = new WriterItemStackCount();
	public static WriterItemStackCount get() { return i; }
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public Integer getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getCount();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, Integer fa, ItemStack d)
	{
		ca.setCount(fa);
	}

	@Override
	public Integer getB(@NotNull ItemStack cb, ItemStack d)
	{
		return cb.getAmount();
	}

	@Override
	public void setB(@NotNull ItemStack cb, Integer fb, ItemStack d)
	{
		cb.setAmount(fb);
	}
	
}
