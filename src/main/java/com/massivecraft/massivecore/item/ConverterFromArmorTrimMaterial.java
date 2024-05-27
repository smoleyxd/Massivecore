package com.massivecraft.massivecore.item;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.meta.trim.TrimMaterial;

public class ConverterFromArmorTrimMaterial extends Converter<TrimMaterial, NamespacedKey>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterFromArmorTrimMaterial i = new ConverterFromArmorTrimMaterial();
	public static ConverterFromArmorTrimMaterial get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public NamespacedKey convert(TrimMaterial trimMaterial)
	{
		return Registry.TRIM_MATERIAL.getKey(trimMaterial);
	}
}
