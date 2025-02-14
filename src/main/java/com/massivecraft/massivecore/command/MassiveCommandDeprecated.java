package com.massivecraft.massivecore.command;

import org.jetbrains.annotations.NotNull;

public class MassiveCommandDeprecated extends MassiveCommand
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	public MassiveCommand target;
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public MassiveCommandDeprecated(MassiveCommand target, String @NotNull ... aliases)
	{
		// Fields
		this.target = target;
		
		// Aliases
		this.setAliases(aliases);
		
		// Parameters
		this.setOverflowSensitive(false);
		
		// Visibility
		this.setVisibility(Visibility.INVISIBLE);
		
		// Priority
		this.setPriority(-1);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform()
	{	
		msg("<i>Use this new command instead:");
		message(target.getTemplate(true));
	}
	
}
