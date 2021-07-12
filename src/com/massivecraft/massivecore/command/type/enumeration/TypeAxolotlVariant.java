package com.massivecraft.massivecore.command.type.enumeration;

import org.bukkit.entity.Axolotl;

public class TypeAxolotlVariant extends TypeEnum<Axolotl.Variant>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static TypeAxolotlVariant i = new TypeAxolotlVariant();
	public static TypeAxolotlVariant get() { return i; }
	public TypeAxolotlVariant()
	{
		super(Axolotl.Variant.class);
	}
	
}
