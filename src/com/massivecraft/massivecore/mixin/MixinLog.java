package com.massivecraft.massivecore.mixin;

import com.massivecraft.massivecore.Log;
import org.jetbrains.annotations.Contract;

public class MixinLog extends Mixin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final MixinLog d = new MixinLog();
	@SuppressWarnings("FieldMayBeFinal")
	private static MixinLog i = d;
	@Contract(pure = true)
	public static MixinLog get() { return i; }
	
	// -------------------------------------------- //
	// SEND
	// -------------------------------------------- //
	
	public void send(Log log)
	{
		
	}

}
