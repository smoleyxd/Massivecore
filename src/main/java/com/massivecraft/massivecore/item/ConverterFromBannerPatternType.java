package com.massivecraft.massivecore.item;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.banner.PatternType;

public class ConverterFromBannerPatternType extends Converter<PatternType, NamespacedKey>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterFromBannerPatternType i = new ConverterFromBannerPatternType();
	public static ConverterFromBannerPatternType get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
    @Override
	public NamespacedKey convert(PatternType x)
	{
		if (x == null) return null;
		return Registry.BANNER_PATTERN.getKey(x);
	}

}
