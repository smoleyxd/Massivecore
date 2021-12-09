package com.massivecraft.massivecore.integration.spartan;

import me.vagdedes.spartan.api.API;
import org.bukkit.entity.Player;

public class SpartanAPIShell
{
	// -------------------------------------------- //
	// Velocity Handler
	// -------------------------------------------- //
	
	public static void disableVelocityProtection(Player p, int ticks)
	{
		API.disableVelocityProtection(p, ticks);
	}
}
