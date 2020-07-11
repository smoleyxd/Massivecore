package com.massivecraft.massivecore.command.type.enumeration;

import org.bukkit.inventory.meta.BookMeta.Generation;

public class TypeBookGeneration extends TypeEnum<Generation>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static TypeBookGeneration i = new TypeBookGeneration();
	public static TypeBookGeneration get() { return i; }
	public TypeBookGeneration()
	{
		super(Generation.class);
	}
}
