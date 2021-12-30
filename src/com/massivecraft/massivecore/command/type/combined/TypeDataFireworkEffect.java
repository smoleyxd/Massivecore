package com.massivecraft.massivecore.command.type.combined;

import com.massivecraft.massivecore.item.DataFireworkEffect;

public class TypeDataFireworkEffect extends TypeCombined<DataFireworkEffect>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static TypeDataFireworkEffect i = new TypeDataFireworkEffect();
	public static TypeDataFireworkEffect get() { return i; }
	public TypeDataFireworkEffect()
	{
		super(DataFireworkEffect.class);
	}

}
