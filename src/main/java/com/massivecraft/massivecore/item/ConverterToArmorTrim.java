package com.massivecraft.massivecore.item;

import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.meta.trim.ArmorTrim;

import javax.xml.crypto.Data;

public class ConverterToArmorTrim extends Converter<DataArmorTrim, ArmorTrim>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterToArmorTrim i = new ConverterToArmorTrim();
	public static ConverterToArmorTrim get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public ArmorTrim convert(DataArmorTrim x)
	{
		if (x == null) return null;
		return x.toBukkit();
	}

}
