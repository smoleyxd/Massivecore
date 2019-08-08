package com.massivecraft.massivecore.item.old;

import com.massivecraft.massivecore.item.ConverterDefault;
import com.massivecraft.massivecore.item.ConverterMap;
import org.bukkit.enchantments.Enchantment;

@Deprecated
public class ConverterFromEnchants extends ConverterMap<Enchantment, Integer, Integer, Integer>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ConverterFromEnchants i = new ConverterFromEnchants();
	public static ConverterFromEnchants get() { return i; }
	public ConverterFromEnchants()
	{
		super(ConverterFromEnchant.get(), ConverterDefault.get(Integer.class, Integer.class));
	}

}
