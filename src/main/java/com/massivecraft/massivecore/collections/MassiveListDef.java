package com.massivecraft.massivecore.collections;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.io.Serial;
import java.util.Collection;

/**
 * This subclass does nothing new except implementing the Def interface.
 * Def is short for "Default" and means GSON should handle "null" as "empty".
 */
public class MassiveListDef<E> extends MassiveList<E> implements Def
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------- //
	// CONSTRUCT: SUPER
	// -------------------------------------------- //
	
	public MassiveListDef(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity)
	{
		super(initialCapacity);
	}

	public MassiveListDef()
	{
		super();
	}
	
	public MassiveListDef(@Nullable Collection<? extends E> c)
	{
		super(c);
	}
	
	@SafeVarargs
	public MassiveListDef(E @NotNull ... elements)
	{
		super(elements);
	}

}
