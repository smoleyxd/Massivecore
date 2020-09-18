package com.massivecraft.massivecore.command.type.primitive;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.command.CommandSender;

public class TypeStringParsed extends TypeString
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static TypeStringParsed i = new TypeStringParsed();
	public static TypeStringParsed get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public String getName()
	{
		return "colored text";
	}
	
	@Override
	public String read(String arg, CommandSender sender) throws MassiveException
	{
		return Txt.parse(super.read(arg, sender));
	}

}
