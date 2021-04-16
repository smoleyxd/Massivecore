package com.massivecraft.massivecore.mixin;

import org.jetbrains.annotations.Contract;

public class MixinMassiveCraftPremium extends Mixin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MixinMassiveCraftPremium d = new MixinMassiveCraftPremium();
	private static MixinMassiveCraftPremium i = d;
	@Contract(pure = true)
	public static MixinMassiveCraftPremium get() { return i; }
	
	// -------------------------------------------- //
	// METHODS
	// -------------------------------------------- //
	
	public boolean isPremium(Object senderObject)
	{
		return false;
	}

}
