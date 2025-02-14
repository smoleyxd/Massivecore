package com.massivecraft.massivecore.predicate;

import com.massivecraft.massivecore.store.Entity;
import org.jetbrains.annotations.NotNull;

public class PredicateIsntDefaultEntity implements Predicate<Entity<?>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final PredicateIsntDefaultEntity i = new PredicateIsntDefaultEntity();
	public static PredicateIsntDefaultEntity get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public boolean apply(@NotNull Entity<?> entity)
	{
		return !entity.isDefault();
	}
	
}
