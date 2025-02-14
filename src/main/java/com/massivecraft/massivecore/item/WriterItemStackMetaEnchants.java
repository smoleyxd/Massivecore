package com.massivecraft.massivecore.item;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Map.Entry;

public class WriterItemStackMetaEnchants extends WriterAbstractItemStackMetaField<ItemMeta, Map<String, Integer>, Map<Enchantment, Integer>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaEnchants i = new WriterItemStackMetaEnchants();
	public static WriterItemStackMetaEnchants get() { return i; }
	public WriterItemStackMetaEnchants()
	{
		super(ItemMeta.class);
		this.setConverterTo(ConverterToEnchants.get());
		this.setConverterFrom(ConverterFromEnchants.get());
	}

	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public Map<String, Integer> getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getEnchants();
	}
	
	@Override
	public void setA(@NotNull DataItemStack ca, Map<String, Integer> fa, ItemStack d)
	{
		ca.setEnchants(fa);
	}
	
	@Override
	public Map<Enchantment, Integer> getB(@NotNull ItemMeta cb, ItemStack d)
	{
		return cb.getEnchants();
	}
	
	@Override
	public void setB(@NotNull ItemMeta cb, Map<Enchantment, Integer> fb, ItemStack d)
	{
		for (Entry<Enchantment, Integer> entry : fb.entrySet())
		{
			cb.addEnchant(entry.getKey(), entry.getValue(), true);
		}
	}
	
}
