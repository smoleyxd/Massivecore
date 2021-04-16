package com.massivecraft.massivecore.predicate;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PredicateStringEndsWith implements Predicate<String>
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //

	private final @NotNull String suffix;

	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //

	@Contract("_ -> new")
	public static @NotNull PredicateStringEndsWith get(@NotNull String suffix) { return new PredicateStringEndsWith(suffix); }
	@Contract("null -> fail")
	public PredicateStringEndsWith(String suffix)
	{
		if (suffix == null) throw new NullPointerException("suffix");
		this.suffix = suffix.toLowerCase();
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public boolean apply(String str)
	{
		if (str == null) return false;
		return str.toLowerCase().endsWith(suffix);
	}
	
}
