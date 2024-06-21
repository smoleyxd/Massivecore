package com.massivecraft.massivecore.command.type.enumeration;

import org.bukkit.block.DecoratedPot;

public class TypeDecoratedPotSide extends TypeEnum<DecoratedPot.Side>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final TypeDecoratedPotSide i = new TypeDecoratedPotSide();
	public static TypeDecoratedPotSide get() { return i; }
	public TypeDecoratedPotSide()
	{
		super(DecoratedPot.Side.class);
	}

}
