package com.massivecraft.massivecore.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackMetaFireworkFlight extends WriterAbstractItemStackMetaField<FireworkMeta, Integer, Integer>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaFireworkFlight i = new WriterItemStackMetaFireworkFlight();
	public static WriterItemStackMetaFireworkFlight get() { return i; }
	public WriterItemStackMetaFireworkFlight()
	{
		super(FireworkMeta.class);
		this.setMaterial(Material.FIREWORK_ROCKET);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public Integer getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getFireworkFlight();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, Integer fa, ItemStack d)
	{
		ca.setFireworkFlight(fa);
	}

	@Override
	public Integer getB(@NotNull FireworkMeta cb, ItemStack d)
	{
		return cb.getPower();
	}

	@Override
	public void setB(@NotNull FireworkMeta cb, Integer fb, ItemStack d)
	{
		fb = Math.min(fb, 127);
		fb = Math.max(fb, 0);
		
		cb.setPower(fb);
	}
	
}
