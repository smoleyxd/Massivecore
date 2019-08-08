package com.massivecraft.massivecore.item.old;

import com.massivecraft.massivecore.item.Converter;
import org.bukkit.enchantments.Enchantment;

@Deprecated
public class ConverterFromEnchant extends Converter<Enchantment, Integer>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterFromEnchant i = new ConverterFromEnchant();
	public static ConverterFromEnchant get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public Integer convert(Enchantment x)
	{
		if (x == null) return null;
		throw new UnsupportedOperationException("Magic number");
		//return x.getId();
	}

}
