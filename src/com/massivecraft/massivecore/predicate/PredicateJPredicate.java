package com.massivecraft.massivecore.predicate;

public class PredicateJPredicate<T> implements Predicate<T>
{
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	public static <T> PredicateJPredicate<T> get(java.util.function.Predicate<? super T> predicate) { return new PredicateJPredicate<>(predicate); }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public PredicateJPredicate(java.util.function.Predicate<? super T> predicate)
	{
		this.predicate = predicate;
	}
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final java.util.function.Predicate<? super T> predicate;
	public java.util.function.Predicate<? super T> getPredicate() { return this.predicate; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public boolean apply(T type)
	{
		return this.getPredicate().test(type);
	}
	
}
