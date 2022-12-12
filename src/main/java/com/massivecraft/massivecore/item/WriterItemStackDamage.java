package com.massivecraft.massivecore.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackDamage extends WriterAbstractItemStackField<Integer, Integer>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackDamage i = new WriterItemStackDamage();
	public static WriterItemStackDamage get() { return i; }
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public Integer getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getDamage();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, Integer fa, ItemStack d)
	{
		ca.setDamage(fa);
	}

	@Override
	public Integer getB(@NotNull ItemStack cb, ItemStack d)
	{
		ItemMeta meta = cb.getItemMeta();
		return (!(meta instanceof Damageable)) ? 0 : ((Damageable) meta).getDamage();
	}

	@Override
	public void setB(@NotNull ItemStack cb, Integer fb, ItemStack d)
	{
		ItemMeta meta = cb.getItemMeta();
		if (meta instanceof Damageable) {
			((Damageable) meta).setDamage(fb);
			cb.setItemMeta(meta);
		}
	}
	
}
