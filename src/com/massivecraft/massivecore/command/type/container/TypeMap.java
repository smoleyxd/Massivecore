package com.massivecraft.massivecore.command.type.container;

import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.combined.TypeEntry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Map.Entry;

public class TypeMap<K, V> extends TypeContainer<Map<K, V>, Entry<K, V>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@Contract("_, _ -> new")
	public static <K, V> @NotNull TypeMap<K, V> get(Type<K> keyType, Type<V> valueType)
	{
		return get(TypeEntry.get(keyType, valueType));
	}
	
	@Contract("_ -> new")
	public static <K, V> @NotNull TypeMap<K, V> get(TypeEntry<K, V> entryType)
	{
		return new TypeMap<>(entryType);
	}
	
	public TypeMap(TypeEntry<K, V> entryType)
	{
		super(Map.class, entryType);
	}
	
	// -------------------------------------------- //
	// INNER TYPES
	// -------------------------------------------- //
	
	public TypeEntry<K, V> getEntryType() { return this.getInnerType(); }
	public Type<K> getKeyType() { return this.getEntryType().getKeyType(); }
	public Type<V> getValueType() { return this.getEntryType().getValueType(); }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public Map<K, V> createNewInstance()
	{
		return new MassiveMap<>();
	}

}
