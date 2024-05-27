package com.massivecraft.massivecore.command.type.combined;

import com.massivecraft.massivecore.item.DataArmorTrim;

public class TypeDataArmorTrim extends TypeCombined<DataArmorTrim>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final TypeDataArmorTrim i = new TypeDataArmorTrim();
	public static TypeDataArmorTrim get() { return i; }
	
	public TypeDataArmorTrim()
	{
		super(DataArmorTrim.class);
	}

}
