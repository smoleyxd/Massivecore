package com.massivecraft.massivecore.collections;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * This subclass adds better constructors.
 * It also includes the comparator as a Generic for automatic use with GSON. 
 */
public class MassiveTreeSet<E, C extends Comparator<? super E>> extends TreeSet<E>
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------- //
	// CONSTRUCT: BASE
	// -------------------------------------------- //
	
	@SuppressWarnings("unchecked")
	public MassiveTreeSet(Object comparator)
	{
		super((comparator instanceof Comparator) ? (C)comparator : null);
	}
	
	public MassiveTreeSet(Object comparator, Collection<? extends E> c)
	{
		// Support Null & this(comparator)
		this(comparator);
		if (c != null) addAll(c);
	}
	
	// -------------------------------------------- //
	// CONSTRUCT: EXTRA
	// -------------------------------------------- //
	
	@SafeVarargs
	public MassiveTreeSet(Object comparator, E @NotNull ... elements)
	{
		this(comparator, Arrays.asList(elements));
	}

}
