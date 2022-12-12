package com.massivecraft.massivecore.command.editor;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Parameter;
import org.jetbrains.annotations.NotNull;

public class CommandEditShow<O, V> extends CommandEditAbstract<O, V>
{	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CommandEditShow(@NotNull EditSettings<O> settings, @NotNull Property<O, V> property)
	{
		// Super	
		super(settings, property, false);
		
		// Aliases
		String alias = this.createCommandAlias();
		this.setAliases(alias);
		
		// Parameters
		this.addParameter(Parameter.getPage());
		
		// Desc
		this.setDesc(alias + " " + this.getPropertyName());
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{
		int page = this.readArg();
		this.show(page);
	}
	
}
