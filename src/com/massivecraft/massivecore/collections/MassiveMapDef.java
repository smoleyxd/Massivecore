package com.massivecraft.massivecore.collections;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.io.Serial;
import java.util.Map;

/**
 * This subclass does nothing new except implementing the Def interface.
 * Def is short for "Default" and means GSON should handle "null" as "empty".
 */
public class MassiveMapDef<K, V> extends MassiveMap<K, V> implements Def
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------- //
	// CONSTRUCT: SUPER
	// -------------------------------------------- //
	
	public MassiveMapDef(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity, float loadFactor)
	{
		super(initialCapacity, loadFactor);
	}

	public MassiveMapDef(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity)
	{
		super(initialCapacity);
	}

	public MassiveMapDef()
	{
		super();
	}

	public MassiveMapDef(Map<? extends K, ? extends V> m)
	{
		super(m);
	}

	public MassiveMapDef(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity, float loadFactor, boolean accessOrder)
	{
		super(initialCapacity, loadFactor, accessOrder);
	}
	
	public MassiveMapDef(K key1, V value1, Object @NotNull ... objects)
	{
		super(key1, value1, objects);
	}

}
