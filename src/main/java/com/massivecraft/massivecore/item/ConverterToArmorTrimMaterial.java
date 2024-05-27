package com.massivecraft.massivecore.item;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.meta.trim.TrimMaterial;

public class ConverterToArmorTrimMaterial extends Converter<NamespacedKey, TrimMaterial>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterToArmorTrimMaterial i = new ConverterToArmorTrimMaterial();
	public static ConverterToArmorTrimMaterial get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public TrimMaterial convert(NamespacedKey namespacedKey)
	{
		return Registry.TRIM_MATERIAL.get(namespacedKey);
	}
}
