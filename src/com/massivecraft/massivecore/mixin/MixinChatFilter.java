package com.massivecraft.massivecore.mixin;

import org.bukkit.command.CommandSender;

public class MixinChatFilter extends Mixin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MixinChatFilter d = new MixinChatFilter();
	private static MixinChatFilter i = d;
	public static MixinChatFilter get() { return i; }
	
	// -------------------------------------------- //
	// METHODS
	// -------------------------------------------- //
	
	public String modify(String original)
	{
		return modify(null, original);
	}
	
	public String modify(CommandSender sender, String original)
	{
		return original;
	}
	
}
