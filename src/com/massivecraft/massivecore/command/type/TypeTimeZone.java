package com.massivecraft.massivecore.command.type;

import com.massivecraft.massivecore.util.MUtil;

import java.util.Collection;
import java.util.TimeZone;

public class TypeTimeZone extends TypeAbstractChoice<String>
{
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	private static final TypeTimeZone i = new TypeTimeZone();
	
	public static TypeTimeZone get() { return i; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public TypeTimeZone()
	{
		super(String.class);
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public Collection<String> getAll()
	{
		return MUtil.list(TimeZone.getAvailableIDs());
	}
}
