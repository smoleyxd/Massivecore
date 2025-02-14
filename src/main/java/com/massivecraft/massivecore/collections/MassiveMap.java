package com.massivecraft.massivecore.collections;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.io.Serial;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This subclass adds better constructors. 
 */
public class MassiveMap<K, V> extends LinkedHashMap<K, V>
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------- //
	// CONSTRUCT: BASE
	// -------------------------------------------- //
	
	public MassiveMap(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity, float loadFactor)
	{
		super(initialCapacity, loadFactor);
	}

	public MassiveMap(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity)
	{
		super(initialCapacity);
	}

	public MassiveMap()
	{
		super();
	}

	@SuppressWarnings("unchecked")
	public MassiveMap(Map<? extends K, ? extends V> m)
	{
		// Support Null
		super(m == null ? Collections.EMPTY_MAP : m);
	}

	public MassiveMap(@Range(from = 0, to = Integer.MAX_VALUE) int initialCapacity, float loadFactor, boolean accessOrder)
	{
		super(initialCapacity, loadFactor, accessOrder);
	}
	
	// -------------------------------------------- //
	// CONSTRUCT: EXTRA
	// -------------------------------------------- //
	
	public MassiveMap(K key1, V value1, Object @NotNull ... objects)
	{
		this(varargCreate(key1, value1, objects));
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	@SuppressWarnings("unchecked")
	public static <K, V> @NotNull MassiveMap<K, V> varargCreate(K key1, V value1, Object @NotNull ... objects)
	{
		MassiveMap<K, V> ret = new MassiveMap<>();
		
		ret.put(key1, value1);
		
		Iterator<Object> iter = Arrays.asList(objects).iterator();
		while (iter.hasNext())
		{
			K key = (K) iter.next();
			V value = (V) iter.next();
			ret.put(key, value);
		}
		
		return ret;
	}
	
	// -------------------------------------------- //
	// METHODS
	// -------------------------------------------- //
	
	public V set(K key, V value)
	{
		if (value == null)
		{
			return this.remove(key);
		}
		else
		{
			return this.put(key, value);
		}
	}

}
