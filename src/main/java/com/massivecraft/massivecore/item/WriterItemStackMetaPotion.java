package com.massivecraft.massivecore.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

public class WriterItemStackMetaPotion extends WriterAbstractItemStackMetaField<PotionMeta, Object, Object>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaPotion i = new WriterItemStackMetaPotion();
	public static WriterItemStackMetaPotion get() { return i; }
	public WriterItemStackMetaPotion()
	{
		super(PotionMeta.class);
		this.setMaterial(Material.POTION);
	}
	
	// -------------------------------------------- //
	// WRITE
	// -------------------------------------------- //
	// Due to the complexity of the upgrade mechanic we override writeInner.
	//
	// There are two interesting data points.
	// 1. The old item damage.
	// 2. The new item potion.
	// These two data points exists in both our implementation and in Bukkit.
	//
	// Serialization is simple.
	// We just assume everything is properly upgraded and save accordingly.
	//
	// The hard part is deserialization (writing to Bukkit).
	//
	// In Minecraft 1.8 the damage value was used to store the potion effects.
	// In Minecraft 1.9 the damage value is no longer used and the potion effect is stored by string instead.
	// 
	// Sticking to the damage value for serialization is not feasible.
	// Minecraft 1.9 adds new potion effects that did not exist in Minecraft 1.8 such as LUCK.
	//
	// Thus we upgrade the database from damage values to the new potion string where possible.
	// Invalid old damage values that does not make any sense are left as is.
	//
	// TODO: As Ulumulu1510 indicated the material must be changed for throwable/splash potions.
	// TODO: That must be implemented for a 100% proper upgrading experience.
	
	@Override
	public void writeInner(DataItemStack oa, ItemMeta ob, DataItemStack ca, PotionMeta cb, ItemStack d, boolean a2b)
	{
		if (a2b)
		{
			// DESERIALIZE
			
			// Create & Fill
			String potionString = ca.getPotion();
			PotionType potionType = PotionUtil.toPotionType(potionString);
			
			// Set
			cb.setBasePotionType(potionType);
		}
		else
		{
			// SERIALIZE
			PotionType potionType = cb.getBasePotionType();
			String potionString = PotionUtil.toPotionString(potionType);
			ca.setPotion(potionString);
		}
	}
	
}
