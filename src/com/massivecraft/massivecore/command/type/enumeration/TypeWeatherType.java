package com.massivecraft.massivecore.command.type.enumeration;

import com.massivecraft.massivecore.collections.MassiveSet;
import org.bukkit.ChatColor;
import org.bukkit.WeatherType;
import org.bukkit.command.CommandSender;

import java.util.Set;

public class TypeWeatherType extends TypeEnum<WeatherType>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final TypeWeatherType i = new TypeWeatherType();
	public static TypeWeatherType get() {return i;}
	public TypeWeatherType()
	{
		super(WeatherType.class);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public ChatColor getVisualColor(WeatherType value, CommandSender sender)
	{
		if (value == null) return ChatColor.GRAY;
		if (value == WeatherType.CLEAR) return ChatColor.GREEN;
		return ChatColor.RED;
	}
	
	@Override
	public String getNameInner(WeatherType value)
	{
		return switch (value)
				   {
					   case DOWNFALL -> "Rain";
					   case CLEAR -> "Sun";
				   };
	}
	
	@Override
	public Set<String> getNamesInner(WeatherType value)
	{
		Set<String> ret = new MassiveSet<>();
		
		switch (value)
		{
			case DOWNFALL -> {
				ret.add("Rain");
				ret.add("Storm");
			}
			case CLEAR -> {
				ret.add("Sun");
				ret.add("Sky");
			}
		}
		
		ret.addAll(super.getNamesInner(value));
		
		return ret;
	}
	
}
