package com.massivecraft.massivecore.chestgui;

import com.massivecraft.massivecore.mixin.MixinCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ChestActionCommand extends ChestActionAbstract
{
	// -------------------------------------------- //
	// FIELD
	// -------------------------------------------- //
	
	protected String command = null;
	
	public void setCommand(String command)
	{
		this.command = command;
	}
	
	public String getCommand()
	{
		return this.command;
	}
	
	// TODO consider removing this or getCommand since they are now identical
	public String getCommandLine()
	{
		return this.command;
	}
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public ChestActionCommand()
	{
		
	}
	
	public ChestActionCommand(String command)
	{
		this.setCommand(command);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public boolean onClick(InventoryClickEvent event, Player player)
	{
		String commandLine = this.getCommandLine();
		if (commandLine == null) return false;
		
		return MixinCommand.get().dispatchCommand(player, commandLine);
	}
	
}
