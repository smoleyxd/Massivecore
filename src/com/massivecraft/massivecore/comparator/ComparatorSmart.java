package com.massivecraft.massivecore.comparator;

import org.jetbrains.annotations.Contract;

public class ComparatorSmart extends ComparatorAbstract<Object>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static ComparatorSmart i = new ComparatorSmart();
	@Contract(pure = true)
	public static ComparatorSmart get() { return i; }
	public ComparatorSmart()
	{
		this.setSmart(true);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public int compareInner(Object type1, Object type2)
	{
		return 0;
	}
	
}
