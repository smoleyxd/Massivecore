package com.massivecraft.massivecore.predicate;

import com.massivecraft.massivecore.xlib.guava.collect.ImmutableList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class PredicateAnd<T> implements Predicate<T>
{
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	@Contract("_ -> new")
	@SafeVarargs
	public static <T> @NotNull PredicateAnd<T> get(Predicate<? super T>... predicates) { return new PredicateAnd<>(predicates); }
	@Contract("_ -> new")
	public static <T> @NotNull PredicateAnd<T> get(Collection<Predicate<? super T>> predicates) { return new PredicateAnd<>(predicates); }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	@SafeVarargs
	public PredicateAnd(Predicate<? super T>... predicates)
	{
		this(ImmutableList.copyOf(predicates));
	}
	
	public PredicateAnd(Collection<Predicate<? super T>> predicates)
	{
		this.predicates = ImmutableList.copyOf(predicates);
	}
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final List<Predicate<? super T>> predicates;
	public List<Predicate<? super T>> getPredicates() { return this.predicates; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public boolean apply(T type)
	{
		for (Predicate<? super T> predicate : this.getPredicates())
		{
			if ( ! predicate.apply(type)) return false;
		}
		return true;
	}
	
}
