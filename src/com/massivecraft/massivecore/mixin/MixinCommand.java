package com.massivecraft.massivecore.mixin;

import com.massivecraft.massivecore.util.IdUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;

public class MixinCommand extends Mixin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MixinCommand d = new MixinCommand();
	private static MixinCommand i = d;
	@Contract(pure = true)
	public static MixinCommand get() { return i; }
	
	// -------------------------------------------- //
	// METHODS
	// -------------------------------------------- //
	
	public boolean dispatchCommand(Object senderObject, String commandLine)
	{
		return this.dispatchCommand(senderObject, senderObject, commandLine);
	}
	
	public boolean dispatchCommand(Object presentObject, Object senderObject, String commandLine)
	{
		// Additional enforced presence
		CommandSender present = IdUtil.getSender(presentObject);
		if (present == null) return false;
		
		// We must then of course have the presence of the sender
		CommandSender sender = IdUtil.getSender(senderObject);
		if (sender == null) return false;
		
		// Discard the unwanted /
		commandLine = commandLine.startsWith("/") ? commandLine.substring(1) : commandLine;
		
		// Great! Let's do it!
		Bukkit.getServer().dispatchCommand(sender, commandLine);
		return true;
	}

}
