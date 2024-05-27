package com.massivecraft.massivecore.item;

import org.bukkit.inventory.meta.trim.ArmorTrim;

public class ConverterFromArmorTrim extends Converter<ArmorTrim, DataArmorTrim>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterFromArmorTrim i = new ConverterFromArmorTrim();
	public static ConverterFromArmorTrim get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public DataArmorTrim convert(ArmorTrim x)
	{
		if (x == null) return null;
		return new DataArmorTrim(x);
	}

}
