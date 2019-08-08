package com.massivecraft.massivecore.item.old;

import com.massivecraft.massivecore.item.Converter;
import org.bukkit.DyeColor;

@Deprecated
public class ConverterToDyeColor extends Converter<Integer, DyeColor>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterToDyeColor i = new ConverterToDyeColor();
	public static ConverterToDyeColor get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public DyeColor convert(Integer x)
	{
		if (x == null) return null;
		throw new UnsupportedOperationException("Magic number");
		//return DyeColor.getByDyeData(x.byteValue());
	}

}
