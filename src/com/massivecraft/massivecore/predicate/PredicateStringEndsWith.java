package com.massivecraft.massivecore.predicate;

public class PredicateStringEndsWith implements Predicate<String>
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //

	private final String suffix;

	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //

	public static PredicateStringEndsWith get(String suffix) { return new PredicateStringEndsWith(suffix); }
	public PredicateStringEndsWith(String suffix)
	{
		if (suffix == null) throw new NullPointerException("suffix");
		this.suffix = suffix;
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public boolean apply(String str)
	{
		if (str == null) return false;
		return str.endsWith(suffix);
	}
	
}
