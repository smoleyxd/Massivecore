package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

public class WriterItemStackMetaLodestone extends WriterAbstractItemStackMetaField<CompassMeta, PS, Location>
{
	
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaLodestone i = new WriterItemStackMetaLodestone();
	public static WriterItemStackMetaLodestone get()
	{
		return i;
	}
	
	// -------------------------------------------- //
	// CONSTRUCTOR
	// -------------------------------------------- //
	
	public WriterItemStackMetaLodestone()
	{
		super(CompassMeta.class);
		this.setMaterial(Material.COMPASS);
		this.setConverterTo(ConverterToLocation.get());
		this.setConverterFrom(ConverterFromLocation.get());
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public PS getA(DataItemStack ca, ItemStack d) {
		return ca.getLodestone();
	}
	
	@Override
	public void setA(DataItemStack ca, PS fa, ItemStack d) {
		ca.setLodestone(fa);
	}
	
	@Override
	public Location getB(CompassMeta cb, ItemStack d) {
		if (!cb.hasLodestone()) return null;
		return cb.getLodestone();
	}
	
	@Override
	public void setB(CompassMeta cb, Location fb, ItemStack d) {
		cb.setLodestone(fb);
	}
	
}
