package com.massivecraft.massivecore.collections;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * This subclass adds better constructors. 
 */
public class MassiveList<E> extends ArrayList<E>
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------- //
	// CONSTRUCT: BASE
	// -------------------------------------------- //
	
	public MassiveList(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity)
	{
		super(initialCapacity);
	}

	public MassiveList()
	{
		super();
	}
	
	@SuppressWarnings("unchecked")
	public MassiveList(@Nullable Collection<? extends E> c)
	{
		// Support Null
		super(c == null ? Collections.EMPTY_LIST : c);
	}
	
	// -------------------------------------------- //
	// CONSTRUCT: EXTRA
	// -------------------------------------------- //
	
	@SafeVarargs
	public MassiveList(E @NotNull ... elements)
	{
		this(Arrays.asList(elements));
	}
	
	// -------------------------------------------- //
	// OPTIMIZE: REMOVE ALL & RETAIN ALL
	// -------------------------------------------- //
	// This will greatly reduce the complexity in cases with big sizes.
	
	@Override
	public boolean removeAll(@NotNull Collection<?> c)
	{
		if (c instanceof List) c = new HashSet<Object>(c);
		return super.removeAll(c);
	}
	
	@Override
	public boolean retainAll(@NotNull Collection<?> c)
	{
		if (c instanceof List) c = new HashSet<Object>(c);
		return super.retainAll(c);
	}
	

}
