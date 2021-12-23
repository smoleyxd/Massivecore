package com.massivecraft.massivecore.predicate;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record PredicateJPredicate<T>(java.util.function.Predicate<? super T> predicate) implements Predicate<T>
{
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	@Contract(value = "_ -> new", pure = true)
	public static <T> @NotNull PredicateJPredicate<T> get(java.util.function.Predicate<? super T> predicate) {return new PredicateJPredicate<>(predicate);}
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	public java.util.function.Predicate<? super T> getPredicate() {return this.predicate;}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public boolean apply(T type)
	{
		return this.getPredicate().test(type);
	}
	
}
