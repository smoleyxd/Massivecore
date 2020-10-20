package com.massivecraft.massivecore.mixin;

public class MixinCombatFlag extends Mixin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MixinCombatFlag d = new MixinCombatFlag();
	private static MixinCombatFlag i = d;
	public static MixinCombatFlag get() { return i; }
	
	// -------------------------------------------- //
	// METHODS
	// -------------------------------------------- //
	
	public boolean isFlagged(Object senderObject)
	{
		return isFlagged(senderObject, false);
	}
	
	public boolean isFlagged(Object senderObject, boolean tick)
	{
		long now = System.currentTimeMillis();
		return isFlagged(senderObject, tick, now);
	}
	
	public boolean isFlagged(Object senderObject, boolean tick, long now)
	{
		return false;
	}

}
