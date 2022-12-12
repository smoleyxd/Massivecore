package com.massivecraft.massivecore.command.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TypeSingleton<T> extends TypeReflection<T>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@Contract("_ -> new")
	public static <T> @NotNull TypeSingleton<T> get(T singleton){ return new TypeSingleton<>(singleton); }
	@SuppressWarnings("unchecked")
	public TypeSingleton(@NotNull T singleton)
	{
		super((Class<T>) singleton.getClass());
		super.setAll(singleton);
	}

}
