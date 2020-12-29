package com.massivecraft.massivecore.command.type;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import org.bukkit.command.CommandSender;

public class TypeRange extends TypeInteger
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private TypeRange(int MIN_RANGE, int MAX_RANGE)
	{
		this.MIN_RANGE = MIN_RANGE;
		this.MAX_RANGE = MAX_RANGE;
	}
	public static TypeRange get(int min, int max) { return new TypeRange(min, max); }
	public static TypeRange get(int max) { return new TypeRange(0, max); }
	
	// -------------------------------------------- //
	// CONSTANT
	// -------------------------------------------- //
	
	public final int MIN_RANGE;
	public final int MAX_RANGE;
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public Integer read(String arg, CommandSender sender) throws MassiveException
	{
		Integer ret = super.read(arg, sender);

		if (ret <= MIN_RANGE || ret > MAX_RANGE) throw new MassiveException().addMsg("<b>Invalid range <h>%d.<b> Range must be between %d and %d.", ret, MIN_RANGE, MAX_RANGE);

		return ret;
	}

}
