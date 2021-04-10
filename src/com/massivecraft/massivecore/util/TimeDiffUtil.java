package com.massivecraft.massivecore.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeDiffUtil
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	public final static Pattern patternFull = Pattern.compile("^(?:([^a-zA-Z]+)([a-zA-Z]*))+$");
	public final static Pattern patternPart = Pattern.compile("([^a-zA-Z]+)([a-zA-Z]*)");
	
	// -------------------------------------------- //
	// MILLIS
	// -------------------------------------------- //
	
	@Contract(pure = true)
	public static long millis(@NotNull TimeUnit timeUnit, long count)
	{
		return timeUnit.millis*count;
	}
	
	@Contract(pure = true)
	public static long millis(@NotNull TimeUnit timeUnit)
	{
		return millis(timeUnit, 1);
	}
	
	public static long millis(@NotNull Map<TimeUnit, Long> unitcounts, long count)
	{
		long ret = 0;
		for (Entry<TimeUnit, Long> entry : unitcounts.entrySet())
		{
			ret += millis(entry.getKey(), entry.getValue()*count);
		}
		return ret;
	}
	
	public static long millis(@NotNull Map<TimeUnit, Long> unitcounts)
	{
		return millis(unitcounts, 1);
	}
	
	public static long millis(@NotNull String formated, long count) throws Exception
	{
		Map<TimeUnit, Long> unitcount = unitcounts(formated);
		return millis(unitcount, count);
	}
	
	public static long millis(@NotNull String formated) throws Exception
	{
		return millis(formated, 1);
	}
	
	// -------------------------------------------- //
	// UNITCOUNT
	// -------------------------------------------- //
	
	@Contract("null -> fail")
	public static @NotNull LinkedHashMap<TimeUnit, Long> unitcounts(String formated) throws Exception
	{
		if (formated == null) throw new NullPointerException("The string can't be null.");
		
		Matcher matcherFull = patternFull.matcher(formated);
		if (!matcherFull.matches()) throw new NullPointerException("Invalid time diff format.");
		
		LinkedHashMap<TimeUnit, Long> ret = new LinkedHashMap<>();
		if (formated.equals("0")) return ret;
		
		Matcher matcherPart = patternPart.matcher(formated);
		while (matcherPart.find())
		{
			// Parse the count
			String countString = matcherPart.group(1);
			String countStringFixed = countString.replaceAll("[+\\s]", "");
			long count;
			try
			{
				count = Long.parseLong(countStringFixed);
			}
			catch (Exception e)
			{
				throw new Exception("\""+countString+"\" is not a valid integer.");
			}
			
			// Parse the time unit
			String unitString = matcherPart.group(2);
			TimeUnit unit = TimeUnit.get(unitString);
			if (unit == null)
			{
				throw new Exception("\""+unitString+"\" is not a valid time unit.");
			}
			
			// Add to the return map
			Long old = ret.put(unit, count);
			if (old != null)
			{
				ret.put(unit, count+old);
			}
		}
		
		return ret;
	}
	
	public static @NotNull LinkedHashMap<TimeUnit, Long> unitcounts(long millis, @NotNull TreeSet<TimeUnit> units)
	{
		// Create non-negative millis decumulator
		long millisLeft = Math.abs(millis);
		
		LinkedHashMap<TimeUnit, Long> ret = new LinkedHashMap<>();
		
		for (TimeUnit unit : units)
		{
			long count = millisLeft / unit.millis;
			if (count < 1) continue;
			millisLeft -= unit.millis*count;
			ret.put(unit, count);
		}
		
		return ret;
	}
	
	public static @NotNull LinkedHashMap<TimeUnit, Long> unitcounts(long millis)
	{
		return unitcounts(millis, TimeUnit.getAll());
	}
	
	public static @NotNull LinkedHashMap<TimeUnit, Long> limit(@NotNull LinkedHashMap<TimeUnit, Long> unitcounts, int limit)
	{
		LinkedHashMap<TimeUnit, Long> ret = new LinkedHashMap<>();
		
		Iterator<Entry<TimeUnit, Long>> iter = unitcounts.entrySet().iterator();
		int i = 0;
		while (iter.hasNext() && i < limit)
		{
			Entry<TimeUnit, Long> entry = iter.next();
			ret.put(entry.getKey(), entry.getValue());
			i++;
		}
		
		return ret;
	}
	
	// -------------------------------------------- //
	// FORMAT
	// -------------------------------------------- //
	
	public static final String FORMAT_ENTRY_VERBOOSE = Txt.parse("<v>%1$d <k>%3$s");
	public static final String FORMAT_COMMA_VERBOOSE = "%s, ";
	public static final String FORMAT_AND_VERBOOSE = " %sand ";
	
	public static final String FORMAT_ENTRY_MINIMAL = Txt.parse("<v>%1$d<k>%2$s");
	public static final String FORMAT_COMMA_MINIMAL = "%s";
	public static final String FORMAT_AND_MINIMAL = "%s";
	
	public static String formated(@NotNull TimeUnit unit, long count, String formatEntry)
	{
		return String.format(formatEntry, count, unit.getUnitString(count), unit.getNameString(count));
	}
	
	public static String formated(@NotNull Map<TimeUnit, Long> unitcounts, String entryFormat, String commaFormat, String andFormat, String color)
	{
		String comma = String.format(commaFormat, Txt.parse(color));
		String and = String.format(andFormat, Txt.parse(color));
		
		if (unitcounts.isEmpty())
		{
			return formated(TimeUnit.SECOND, 0, entryFormat);
		}
		
		List<String> parts = new ArrayList<>();
		for (Entry<TimeUnit, Long> entry : unitcounts.entrySet())
		{
			parts.add(formated(entry.getKey(), entry.getValue(), entryFormat));
		}
		return Txt.implodeCommaAnd(parts, comma, and);
	}
	
	public static String formatedVerboose(@NotNull TimeUnit unit, long count)
	{
		return formated(unit, count, FORMAT_ENTRY_VERBOOSE);
	}
	
	public static String formatedVerboose(@NotNull Map<TimeUnit, Long> unitcounts, String color)
	{
		return formated(unitcounts, FORMAT_ENTRY_VERBOOSE, FORMAT_COMMA_VERBOOSE, FORMAT_AND_VERBOOSE, color);
	}
	
	public static String formatedVerboose(@NotNull Map<TimeUnit, Long> unitcounts)
	{
		return formatedVerboose(unitcounts, "<i>");
	}
	
	public static String formatedMinimal(@NotNull TimeUnit unit, long count)
	{
		return formated(unit, count, FORMAT_ENTRY_MINIMAL);
	}
	
	public static String formatedMinimal(@NotNull Map<TimeUnit, Long> unitcounts, String color)
	{
		return formated(unitcounts, FORMAT_ENTRY_MINIMAL, FORMAT_COMMA_MINIMAL, FORMAT_AND_MINIMAL, color);
	}
	
	public static String formatedMinimal(@NotNull Map<TimeUnit, Long> unitcounts)
	{
		return formatedMinimal(unitcounts, "");
	}
	
}
