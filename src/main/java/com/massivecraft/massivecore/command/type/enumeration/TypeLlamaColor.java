package com.massivecraft.massivecore.command.type.enumeration;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Llama.Color;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TypeLlamaColor extends TypeEnum<Color>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final TypeLlamaColor i = new TypeLlamaColor();
	public static TypeLlamaColor get() { return i; }
	public TypeLlamaColor()
	{
		super(Color.class);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public ChatColor getVisualColor(Color value, CommandSender sender)
	{
		return getChatColor(value);
	}
	
	@Contract(pure = true)
	private @NotNull ChatColor getChatColor(@NotNull Color color)
	{
		return switch (color)
				   {
					   case CREAMY -> ChatColor.GRAY;
					   case BROWN -> ChatColor.GOLD;
					   case GRAY -> ChatColor.DARK_GRAY;
					   default -> ChatColor.WHITE;
				   };
	}
	
}
