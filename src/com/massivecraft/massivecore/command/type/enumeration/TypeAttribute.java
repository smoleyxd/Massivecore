package com.massivecraft.massivecore.command.type.enumeration;

import org.bukkit.attribute.Attribute;

public class TypeAttribute extends TypeEnum<Attribute>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final TypeAttribute i = new TypeAttribute();
	public static TypeAttribute get() { return i; }
	public TypeAttribute()
	{
		super(Attribute.class);
	}

}
