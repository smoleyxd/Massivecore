package com.massivecraft.massivecore.command.type.enumeration;

import org.bukkit.attribute.AttributeModifier;

public class TypeAttributeModifierOperation extends TypeEnum<AttributeModifier.Operation>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final TypeAttributeModifierOperation i = new TypeAttributeModifierOperation();
	public static TypeAttributeModifierOperation get() { return i; }
	public TypeAttributeModifierOperation()
	{
		super(AttributeModifier.Operation.class);
	}

}
