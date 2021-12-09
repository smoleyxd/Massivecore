package com.massivecraft.massivecore.integration.spartan;

import com.massivecraft.massivecore.Integration;
import org.bukkit.entity.Player;

public class IntegrationSpartan extends Integration
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final IntegrationSpartan i = new IntegrationSpartan();
	public static IntegrationSpartan get() { return i; }
	public IntegrationSpartan()
	{
		this.setPluginName("Spartan");
	}
	
	// -------------------------------------------- //
	// LIABILITY CALCULATION
	// -------------------------------------------- //
	
	public void disableVelocityProtection(Player p, int ticks)
	{
		if (!this.isIntegrationActive()) return;
		SpartanAPIShell.disableVelocityProtection(p, ticks);
	}
	
}
