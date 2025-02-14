package com.massivecraft.massivecore.comparator;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Map.Entry;

public class ComparatorEntryValue<K, V> extends ComparatorAbstractTransformer<Entry<K, V>, V>
{	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@Contract("_ -> new")
	public static <K, V> @NotNull ComparatorEntryValue<K, V> get(Comparator<V> comparator) { return new ComparatorEntryValue<>(comparator); }
	public ComparatorEntryValue(Comparator<V> comparator)
	{
		super(comparator);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public V transform(@NotNull Entry<K, V> type)
	{
		return type.getValue();
	}
	
}
