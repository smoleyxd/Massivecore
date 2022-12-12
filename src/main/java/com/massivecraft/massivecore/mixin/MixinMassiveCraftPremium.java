package com.massivecraft.massivecore.mixin;

import org.jetbrains.annotations.Contract;

public class MixinMassiveCraftPremium extends Mixin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final MixinMassiveCraftPremium d = new MixinMassiveCraftPremium();
	@SuppressWarnings("FieldMayBeFinal")
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
