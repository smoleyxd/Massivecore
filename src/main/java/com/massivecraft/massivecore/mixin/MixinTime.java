package com.massivecraft.massivecore.mixin;

import com.massivecraft.massivecore.MassiveCoreMConf;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.TimeZoneUtil;
import org.jetbrains.annotations.Contract;

public class MixinTime extends Mixin
{
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	private static MixinTime d = new MixinTime();
	private static MixinTime i = d;
	@Contract(pure = true)
	public static MixinTime get() { return i; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public MixinTime()
	{
	}

	// -------------------------------------------- //
	// METHODS
	// -------------------------------------------- //
	
	public String getAdjustedTime(long millis, Object senderObject) // TODO Should I have the TZ in the params?
	{
		// If they're a player
		if (MUtil.isntPlayer(senderObject)) return null;
	
		return TimeZoneUtil.getAdjustedTime(millis, MassiveCoreMConf.get().defaultTimeZone);
	}
}
