package com.massivecraft.massivecore.mixin;

import com.massivecraft.massivecore.event.EventMassiveCorePlayerLeave;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.Contract;

public class MixinActual extends Mixin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final MixinActual d = new MixinActual();
	@SuppressWarnings("FieldMayBeFinal")
	private static MixinActual i = d;
	@Contract(pure = true)
	public static MixinActual get() { return i; }
	
	// -------------------------------------------- //
	// METHODS
	// -------------------------------------------- //
	
	public boolean isActualJoin(PlayerJoinEvent event)
	{
		return true;
	}
	
	public boolean isActualLeave(EventMassiveCorePlayerLeave event)
	{
		return true;
	}

}
