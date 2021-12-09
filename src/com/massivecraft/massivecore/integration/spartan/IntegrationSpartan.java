package com.massivecraft.massivecore.integration.spartan;

import com.massivecraft.massivecore.Integration;
import com.massivecraft.massivecore.MassiveCoreMConf;
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
	
	public void disableVelocityProtection(Player p)
	{
		if (!this.isIntegrationActive()) return;
		SpartanAPIShell.disableVelocityProtection(p, MassiveCoreMConf.get().velocityBypassTicks);
	}
	
}
