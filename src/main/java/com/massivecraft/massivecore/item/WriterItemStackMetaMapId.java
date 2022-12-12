package com.massivecraft.massivecore.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class WriterItemStackMetaMapId extends WriterAbstractItemStackMetaField<MapMeta, Integer, Integer>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaMapId i = new WriterItemStackMetaMapId();
	public static WriterItemStackMetaMapId get() { return i; }
	
	public WriterItemStackMetaMapId()
	{
		super(MapMeta.class);
		this.setMaterial(Material.FILLED_MAP);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public Integer getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getMapId();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, Integer fa, ItemStack d)
	{
		ca.setMapId(fa);
	}

	@Override
	public Integer getB(@NotNull MapMeta cb, ItemStack d)
	{
		return cb.hasMapId() ? cb.getMapId() : null;
	}

	@Override
	public void setB(@NotNull MapMeta cb, Integer fb, ItemStack d)
	{
		if (fb != null) cb.setMapId(fb);
	}
	
}
