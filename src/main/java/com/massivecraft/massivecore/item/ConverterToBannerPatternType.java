package com.massivecraft.massivecore.item;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.banner.PatternType;

public class ConverterToBannerPatternType extends Converter<NamespacedKey, PatternType>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterToBannerPatternType i = new ConverterToBannerPatternType();
	public static ConverterToBannerPatternType get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
    @Override
	public PatternType convert(NamespacedKey x)
	{
		if (x == null) return null;
		return Registry.BANNER_PATTERN.get(x);
	}

}
