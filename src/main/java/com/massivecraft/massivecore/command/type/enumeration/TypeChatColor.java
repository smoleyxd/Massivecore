package com.massivecraft.massivecore.command.type.enumeration;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TypeChatColor extends TypeEnum<ChatColor>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final TypeChatColor i = new TypeChatColor();
	public static TypeChatColor get() { return i; }
	
	private static final TypeChatColor colors = new TypeChatColor();
	public static TypeChatColor getColors() { return colors; }
	
	private static final TypeChatColor format = new TypeChatColor();
	public static TypeChatColor getFormat() { return format; }
	
	public TypeChatColor()
	{
		super(ChatColor.class);
	}
	
	static {
		colors.setAll(
			Arrays.stream(ChatColor.class.getEnumConstants())
				.filter(ChatColor::isColor)
				.collect(Collectors.toSet())
		);
		
		format.setAll(
			Arrays.stream(ChatColor.class.getEnumConstants())
				.filter(ChatColor::isFormat)
				.collect(Collectors.toSet())
		);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public ChatColor getVisualColor(ChatColor value, CommandSender sender)
	{
		return value;
	}
}
