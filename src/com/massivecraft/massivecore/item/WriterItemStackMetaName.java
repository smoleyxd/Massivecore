package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.nms.NmsItemStackMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackMetaName extends WriterAbstractItemStackMetaField<ItemMeta, String, String>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaName i = new WriterItemStackMetaName();
	public static WriterItemStackMetaName get() { return i; }
	public WriterItemStackMetaName()
	{
		super(ItemMeta.class);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public String getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getName();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, String fa, ItemStack d)
	{
		ca.setName(fa);
	}

	@Override
	public String getB(@NotNull ItemMeta cb, ItemStack d)
	{
		return NmsItemStackMeta.get().getDisplayName(cb);
	}

	@Override
	public void setB(@NotNull ItemMeta cb, String fb, ItemStack d)
	{
		NmsItemStackMeta.get().setDisplayName(cb, fb);
	}
	
}
