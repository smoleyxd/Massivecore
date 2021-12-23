package com.massivecraft.massivecore.integration.spartan;

import me.vagdedes.spartan.api.API;
import me.vagdedes.spartan.system.Enums.HackType;
import org.bukkit.entity.Player;

public class SpartanAPIShell
{
	// -------------------------------------------- //
	// Velocity Handler
	// -------------------------------------------- //
	
	public static void disableVelocityProtection(Player p, int ticks)
	{
		API.cancelCheck(p, HackType.IrregularMovements, ticks);
		API.cancelCheck(p, HackType.Speed, ticks);
		API.cancelCheck(p, HackType.Clip, ticks);
		API.cancelCheck(p, HackType.Fly, ticks);
		API.disableVelocityProtection(p, ticks);
	}
}
