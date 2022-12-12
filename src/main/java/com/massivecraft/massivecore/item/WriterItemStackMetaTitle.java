package com.massivecraft.massivecore.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackMetaTitle extends WriterAbstractItemStackMetaField<BookMeta, String, String>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaTitle i = new WriterItemStackMetaTitle();
	public static WriterItemStackMetaTitle get() { return i; }
	public WriterItemStackMetaTitle()
	{
		super(BookMeta.class);
		this.setMaterial(Material.WRITTEN_BOOK);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public String getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getTitle();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, String fa, ItemStack d)
	{
		ca.setTitle(fa);
	}

	@Override
	public String getB(@NotNull BookMeta cb, ItemStack d)
	{
		return cb.getTitle();
	}

	@Override
	public void setB(@NotNull BookMeta cb, String fb, ItemStack d)
	{
		cb.setTitle(fb);
	}
	
}
