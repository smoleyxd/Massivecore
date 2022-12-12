package com.massivecraft.massivecore.command.type.container;

import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.command.type.Type;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class TypeSet<E> extends TypeContainer<Set<E>, E>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	@Contract("_ -> new")
	public static <E> @NotNull TypeSet<E> get(Type<E> innerType)
	{
		return new TypeSet<>(innerType);
	}
	
	public TypeSet(Type<E> innerType)
	{
		super(Set.class, innerType);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public Set<E> createNewInstance()
	{
		return new MassiveSet<>();
	}

}
