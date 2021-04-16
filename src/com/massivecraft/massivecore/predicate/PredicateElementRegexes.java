package com.massivecraft.massivecore.predicate;

import com.massivecraft.massivecore.collections.MassiveList;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class PredicateElementRegexes implements Predicate<StackTraceElement>
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private List<Pattern> patterns = Collections.emptyList();
	public List<Pattern> getPatterns() { return this.patterns; }
	@Contract(mutates = "this")
	public void setPatterns(Collection<Pattern> patterns) { this.patterns = new MassiveList<>(patterns); }
	@Contract(mutates = "this")
	public void setPatterns(@NotNull Iterable<String> regexes) { this.setPatterns(asPatterns(regexes));}
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public PredicateElementRegexes(String @NotNull ... regexes)
	{
		this.setPatterns(Arrays.asList(regexes));
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public boolean apply(StackTraceElement element)
	{
		if (element == null) return false;
		String string = element.toString();
		for (Pattern pattern : this.getPatterns())
		{
			if (pattern.matcher(string).matches()) return true;
		}
		return false;
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	protected @NotNull List<Pattern> asPatterns(@NotNull Iterable<String> regexes)
	{
		// Create
		List<Pattern> ret = new MassiveList<>();
		
		// Fill
		for (String regex : regexes)
		{
			Pattern pattern = Pattern.compile(regex);
			ret.add(pattern);
		}
		
		// Return
		return ret;
	}
	
}
