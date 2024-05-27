package com.massivecraft.massivecore.item;

import org.bukkit.DyeColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.jetbrains.annotations.NotNull;

public class WriterArmorTrimMaterial extends WriterAbstractArmorTrim<NamespacedKey, TrimMaterial>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterArmorTrimMaterial i = new WriterArmorTrimMaterial();
	public static WriterArmorTrimMaterial get() { return i; }
	public WriterArmorTrimMaterial()
	{
		super("material");
		this.setConverterTo(ConverterToArmorTrimMaterial.get());
		this.setConverterFrom(ConverterFromArmorTrimMaterial.get());
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public NamespacedKey getA(@NotNull DataArmorTrim ca, Object d)
	{
		return ca.getMaterial();
	}
	
	@Override
	public void setA(@NotNull DataArmorTrim ca, NamespacedKey fa, Object d)
	{
		ca.setMaterial(fa);
	}
	
	@Override
	public TrimMaterial getB(@NotNull ArmorTrim cb, Object d)
	{
		return cb.getMaterial();
	}
}
