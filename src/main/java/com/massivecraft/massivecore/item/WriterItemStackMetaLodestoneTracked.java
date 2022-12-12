package com.massivecraft.massivecore.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackMetaLodestoneTracked extends WriterAbstractItemStackMetaField<CompassMeta, Boolean, Boolean>
{
	
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaLodestoneTracked i = new WriterItemStackMetaLodestoneTracked();
	public static WriterItemStackMetaLodestoneTracked get() { return i; }
	
	// -------------------------------------------- //
	// CONSTRUCTOR
	// -------------------------------------------- //
	
	public WriterItemStackMetaLodestoneTracked()
	{
		super(CompassMeta.class);
		this.setMaterial(Material.COMPASS);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public Boolean getA(@NotNull DataItemStack ca, ItemStack d) { return ca.isLodestoneTracked(); }
	
	@Override
	public void setA(@NotNull DataItemStack ca, Boolean fa, ItemStack d) { ca.setLodestoneTracked(fa); }
	
	@Override
	public Boolean getB(@NotNull CompassMeta cb, ItemStack d) { return cb.isLodestoneTracked(); }
	
	@Override
	public void setB(@NotNull CompassMeta cb, Boolean fb, ItemStack d) { cb.setLodestoneTracked(fb); }
	
}
