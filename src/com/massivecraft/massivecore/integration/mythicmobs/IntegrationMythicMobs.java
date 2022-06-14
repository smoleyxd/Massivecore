package com.massivecraft.massivecore.integration.mythicmobs;

import com.massivecraft.massivecore.Integration;
import com.massivecraft.massivecore.item.WriterItemStack;

public class IntegrationMythicMobs extends Integration
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final IntegrationMythicMobs i = new IntegrationMythicMobs();
	public static IntegrationMythicMobs get() { return i; }
	private IntegrationMythicMobs() { this.setPluginName("MythicMobs"); }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void setIntegrationActiveInner(boolean active)
	{
		if (active)
		{
			WriterItemStack.get().setupWriter(WriterItemStackMythicType.class, true);
		}
	}
}
