package com.massivecraft.massivecore.item;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.meta.trim.TrimPattern;

public class ConverterToArmorTrimPattern extends Converter<NamespacedKey, TrimPattern>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterToArmorTrimPattern i = new ConverterToArmorTrimPattern();
	public static ConverterToArmorTrimPattern get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public TrimPattern convert(NamespacedKey namespacedKey)
	{
		return Registry.TRIM_PATTERN.get(namespacedKey);
	}
}
