package com.massivecraft.massivecore.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackId extends WriterAbstractItemStackField<String, Material>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackId i = new WriterItemStackId();
	public static WriterItemStackId get() { return i; }
	public WriterItemStackId()
	{
		this.setConverterTo(ConverterToMaterial.get());
		this.setConverterFrom(ConverterFromMaterial.get());
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public String getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getId();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, String fa, ItemStack d)
	{
		ca.setId(fa);
	}
	
	@Override
	public Material getB(@NotNull ItemStack cb, ItemStack d)
	{
		return cb.getType();
	}
	
	@Override
	public void setB(@NotNull ItemStack cb, Material fb, ItemStack d)
	{
		cb.setType(fb);
	}
	
}
