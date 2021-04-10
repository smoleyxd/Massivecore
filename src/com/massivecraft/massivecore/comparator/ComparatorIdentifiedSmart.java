package com.massivecraft.massivecore.comparator;

import org.jetbrains.annotations.Contract;

public class ComparatorIdentifiedSmart extends ComparatorIdentified
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static ComparatorIdentifiedSmart i = new ComparatorIdentifiedSmart();
	@Contract(pure = true)
	public static ComparatorIdentifiedSmart get() { return i; }
	public ComparatorIdentifiedSmart()
	{
		this.setSmart(true);
	}

}
