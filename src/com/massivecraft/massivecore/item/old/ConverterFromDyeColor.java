package com.massivecraft.massivecore.item.old;

import com.massivecraft.massivecore.item.Converter;
import org.bukkit.DyeColor;

@Deprecated
public class ConverterFromDyeColor extends Converter<DyeColor, Integer>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterFromDyeColor i = new ConverterFromDyeColor();
	public static ConverterFromDyeColor get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public Integer convert(DyeColor x)
	{
		if (x == null) return null;
		//return Integer.valueOf(x.getDyeData());
		throw new UnsupportedOperationException("Magic number");
	}

}
