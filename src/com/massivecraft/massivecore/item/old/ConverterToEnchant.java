package com.massivecraft.massivecore.item.old;

import com.massivecraft.massivecore.item.Converter;
import org.bukkit.enchantments.Enchantment;

@Deprecated
public class ConverterToEnchant extends Converter<Integer, Enchantment>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterToEnchant i = new ConverterToEnchant();
	public static ConverterToEnchant get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public Enchantment convert(Integer x)
	{
		if (x == null) return null;
		throw new UnsupportedOperationException("Magic numbers are no longer supported");
		//return Enchantment.getById(x);
	}

}
