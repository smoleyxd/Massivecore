package com.massivecraft.massivecore.command.type.enumeration;

import org.bukkit.entity.TropicalFish;

public class TypeTropicalFishPattern extends TypeEnum<TropicalFish.Pattern>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static TypeTropicalFishPattern i = new TypeTropicalFishPattern();
	public static TypeTropicalFishPattern get() { return i; }
	public TypeTropicalFishPattern()
	{
		super(TropicalFish.Pattern.class);
	}

}
