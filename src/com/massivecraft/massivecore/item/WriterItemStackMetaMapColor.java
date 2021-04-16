package com.massivecraft.massivecore.item;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackMetaMapColor extends WriterAbstractItemStackMetaField<MapMeta, Integer, Color>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaMapColor i = new WriterItemStackMetaMapColor();
	public static WriterItemStackMetaMapColor get() { return i; }
	
	public WriterItemStackMetaMapColor()
	{
		super(MapMeta.class);
		this.setMaterial(Material.FILLED_MAP);
		this.setConverterTo(ConverterToColor.get());
		this.setConverterFrom(ConverterFromColor.get());
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public Integer getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getMapColor();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, Integer fa, ItemStack d)
	{
		ca.setMapColor(fa);
	}

	@Override
	public Color getB(@NotNull MapMeta cb, ItemStack d)
	{
		return cb.hasColor() ? cb.getColor() : null;
	}

	@Override
	public void setB(@NotNull MapMeta cb, Color fb, ItemStack d)
	{
		cb.setColor(fb);
	}
	
}
