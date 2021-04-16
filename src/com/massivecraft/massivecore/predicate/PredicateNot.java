package com.massivecraft.massivecore.predicate;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PredicateNot<T> implements Predicate<T>
{
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	@Contract(value = "_ -> new", pure = true)
	public static <T> @NotNull PredicateNot<T> get(@NotNull Predicate<? super T> predicate) { return new PredicateNot<>(predicate); }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public PredicateNot(@NotNull Predicate<? super T> predicate)
	{
		this.predicate = predicate;
	}
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final @NotNull Predicate<? super T> predicate;
	public @NotNull Predicate<? super T> getPredicate() { return this.predicate; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public boolean apply(T type)
	{
		return !this.getPredicate().apply(type);
	}
	
}
