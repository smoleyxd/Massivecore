package com.massivecraft.massivecore.item;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ConverterToBundleItems extends Converter<List<DataItemStack>, List<ItemStack>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterToBundleItems i = new ConverterToBundleItems();
	public static ConverterToBundleItems get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public List<ItemStack> convert(List<DataItemStack> x)
	{
		if (x == null) return null;
		return DataItemStack.toBukkit(x);
	}

}
