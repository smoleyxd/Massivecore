package com.massivecraft.massivecore.item;

import org.bukkit.Material;
import org.bukkit.MusicInstrument;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.MusicInstrumentMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackMetaArmorTrim extends WriterAbstractItemStackMetaField<ArmorMeta, DataArmorTrim, ArmorTrim>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaArmorTrim i = new WriterItemStackMetaArmorTrim();
	public static WriterItemStackMetaArmorTrim get() { return i; }
	
	public WriterItemStackMetaArmorTrim()
	{
		super(ArmorMeta.class);
		this.setMaterial(Material.LEATHER_CHESTPLATE);
		this.setConverterTo(ConverterToArmorTrim.get());
		this.setConverterFrom(ConverterFromArmorTrim.get());
		this.addDependencyClasses(
			WriterArmorTrim.class
		);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public DataArmorTrim getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getArmorTrim();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, DataArmorTrim fa, ItemStack d)
	{
		ca.setArmorTrim(fa);
	}

	@Override
	public ArmorTrim getB(@NotNull ArmorMeta cb, ItemStack d)
	{
		return cb.getTrim();
	}

	@Override
	public void setB(@NotNull ArmorMeta cb, ArmorTrim fb, ItemStack d)
	{
		cb.setTrim(fb);
	}
	
}
