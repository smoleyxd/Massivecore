package com.massivecraft.massivecore.item;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

public class ConverterFromArmorTrimPattern extends Converter<TrimPattern, NamespacedKey>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterFromArmorTrimPattern i = new ConverterFromArmorTrimPattern();
	public static ConverterFromArmorTrimPattern get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public NamespacedKey convert(TrimPattern trimPattern)
	{
		return Registry.TRIM_PATTERN.getKey(trimPattern);
	}
}
