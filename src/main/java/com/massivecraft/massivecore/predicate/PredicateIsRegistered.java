package com.massivecraft.massivecore.predicate;

import com.massivecraft.massivecore.Registerable;
import org.jetbrains.annotations.NotNull;

public class PredicateIsRegistered implements Predicate<Registerable>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final PredicateIsRegistered i = new PredicateIsRegistered();
	public static PredicateIsRegistered get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public boolean apply(@NotNull Registerable registerable)
	{
		return registerable.isRegistered();
	}
	
}
