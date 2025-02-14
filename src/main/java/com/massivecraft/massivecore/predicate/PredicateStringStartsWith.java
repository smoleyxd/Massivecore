package com.massivecraft.massivecore.predicate;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record PredicateStringStartsWith(@NotNull String prefix) implements Predicate<String>
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	@Contract("_ -> new")
	public static @NotNull
	PredicateStringStartsWith get(@NotNull String prefix)
	{
		return new PredicateStringStartsWith(prefix);
	}
	
	@Contract("null -> fail")
	public PredicateStringStartsWith(String prefix)
	{
		if (prefix == null) throw new NullPointerException("prefix");
		this.prefix = prefix.toLowerCase();
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public boolean apply(String str)
	{
		if (str == null) return false;
		return str.toLowerCase().startsWith(prefix);
	}
	
}
