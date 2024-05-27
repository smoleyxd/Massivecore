package com.massivecraft.massivecore.integration.mythicmobs;

import com.massivecraft.massivecore.item.DataItemStack;
import com.massivecraft.massivecore.item.WriterAbstractItemStackField;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.utils.jnbt.CompoundTag;
import io.lumine.mythic.core.utils.jnbt.IntTag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackMythicItemVersion extends WriterAbstractItemStackField<Integer, Integer>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMythicItemVersion i = new WriterItemStackMythicItemVersion();
	public static WriterItemStackMythicItemVersion get() { return i; }
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public Integer getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getMythicItemVersion();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, Integer fa, ItemStack d)
	{
		ca.setMythicItemVersion(fa);
	}

	@Override
	public Integer getB(@NotNull ItemStack cb, ItemStack d)
	{
		CompoundTag tagComp = MythicBukkit.inst().getVolatileCodeHandler().getItemHandler().getNBTData(cb);
		if (!tagComp.containsKey("MYTHIC_ITEM_VERSION")) return null;
		return tagComp.getInt("MYTHIC_ITEM_VERSION");
	}

	@Override
	public void setB(@NotNull ItemStack cb, Integer fb, ItemStack d)
	{
		if (fb == null) return; // TODO - Safely remove the Tag if null
		MythicBukkit.inst().getVolatileCodeHandler().getItemHandler().addNBTData(cb, "MYTHIC_ITEM_VERSION", new IntTag(fb));
	}
	
}
