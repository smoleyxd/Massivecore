package com.massivecraft.massivecore.command.type.convert;

import com.massivecraft.massivecore.command.type.enumeration.TypeDyeColor;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.item.old.ConverterFromDyeColor;
import com.massivecraft.massivecore.item.old.ConverterToDyeColor;
import org.bukkit.DyeColor;

// Depends on magic numbers, should be moved away from in favor of TypeDyeColor
@Deprecated
public class TypeConverterDyeColor extends TypeConverter<DyeColor, Integer> 
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static TypeConverterDyeColor i = new TypeConverterDyeColor();
	public static TypeConverterDyeColor get() { return i; }
	
	public TypeConverterDyeColor()
	{
		super(TypeDyeColor.get(), TypeInteger.get(), ConverterFromDyeColor.get(), ConverterToDyeColor.get());
	}
	
}
