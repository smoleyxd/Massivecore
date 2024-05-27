package com.massivecraft.massivecore.item;

import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;


public abstract class WriterAbstractArmorTrim<FA, FB> extends WriterAbstractReflect<DataArmorTrim, ArmorTrim, DataArmorTrim, ArmorTrim, FA, FB>
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public WriterAbstractArmorTrim(String fieldName)
	{
		super(ArmorTrim.class, fieldName);
	}
	
	public WriterAbstractArmorTrim()
	{
		this(null); 
	}
	
	// -------------------------------------------- //
	// CREATE
	// -------------------------------------------- //
	
	@Override
	public DataArmorTrim createOA()
	{
		return new DataArmorTrim();
	}
	
	@Override
	public ArmorTrim createOB()
	{
		return new ArmorTrim(TrimMaterial.IRON, TrimPattern.SENTRY);
	}
	
}
