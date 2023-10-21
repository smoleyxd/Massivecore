package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.nms.NmsPersistentData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackMetaPersistentData extends WriterAbstractItemStackMetaField<ItemMeta, String, String>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaPersistentData i = new WriterItemStackMetaPersistentData();
	public static WriterItemStackMetaPersistentData get() { return i; }
	
	public WriterItemStackMetaPersistentData()
	{
		super(ItemMeta.class);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public String getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getPersistentData();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, String fa, ItemStack d)
	{
		ca.setPersistentData(fa);
	}

	@Override
	public String getB(@NotNull ItemMeta cb, ItemStack d)
	{
		PersistentDataContainer container = cb.getPersistentDataContainer();
		return NmsPersistentData.get().getPersistentData(container);
	}

	@Override
	public void setB(@NotNull ItemMeta cb, String fb, ItemStack d)
	{
		PersistentDataContainer container = cb.getPersistentDataContainer();
		NmsPersistentData.get().setPersistentData(container, fb);
	}
	
}
