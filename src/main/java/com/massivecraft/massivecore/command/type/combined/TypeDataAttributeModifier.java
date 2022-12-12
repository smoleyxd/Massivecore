package com.massivecraft.massivecore.command.type.combined;

import com.massivecraft.massivecore.item.DataAttributeModifier;

public class TypeDataAttributeModifier extends TypeCombined<DataAttributeModifier>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final TypeDataAttributeModifier i = new TypeDataAttributeModifier();
	public static TypeDataAttributeModifier get() { return i; }
	
	public TypeDataAttributeModifier()
	{
		super(DataAttributeModifier.class);
	}

}
