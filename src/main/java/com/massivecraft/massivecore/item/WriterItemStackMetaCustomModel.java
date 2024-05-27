package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.nms.NmsItemStackMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WriterItemStackMetaCustomModel extends WriterAbstractItemStackMetaField<ItemMeta, Integer, Integer>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaCustomModel i = new WriterItemStackMetaCustomModel();
	public static WriterItemStackMetaCustomModel get() { return i; }
	public WriterItemStackMetaCustomModel()
	{
		super(ItemMeta.class);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public Integer getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getCustomModel();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, Integer fa, ItemStack d)
	{
		ca.setCustomModel(fa);
	}

	@Override
	public Integer getB(@NotNull ItemMeta cb, ItemStack d)
	{
		if (!cb.hasCustomModelData()) return null;
		return cb.getCustomModelData();
	}

	@Override
	public void setB(@NotNull ItemMeta cb, Integer fb, ItemStack d)
	{
		cb.setCustomModelData(fb);
	}
	
}
