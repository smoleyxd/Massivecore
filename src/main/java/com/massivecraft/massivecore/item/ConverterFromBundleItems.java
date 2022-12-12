package com.massivecraft.massivecore.item;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ConverterFromBundleItems extends Converter<List<ItemStack>, List<DataItemStack>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterFromBundleItems i = new ConverterFromBundleItems();
	public static ConverterFromBundleItems get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public List<DataItemStack> convert(List<ItemStack> x)
	{
		if (x == null) return null;
		return DataItemStack.fromBukkit(x);
	}

}
