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
	
	public static long millis(@NotNull String formatted, long count) throws Exception
	{
		Map<TimeUnit, Long> unitcount = unitcounts(formatted);
		return millis(unitcount, count);
	}
	
	public static long millis(@NotNull String formatted) throws Exception
	{
		return millis(formatted, 1);
	}
	
	// -------------------------------------------- //
	// UNITCOUNT
	// -------------------------------------------- //
	
	@Contract("null -> fail")
	public static @NotNull LinkedHashMap<TimeUnit, Long> unitcounts(String formatted) throws Exception
	{
		if (formatted == null) throw new NullPointerException("The string can't be null.");
		
		Matcher matcherFull = patternFull.matcher(formatted);
		if (!matcherFull.matches()) throw new NullPointerException("Invalid time diff format.");
		
		LinkedHashMap<TimeUnit, Long> ret = new LinkedHashMap<>();
		if (formatted.equals("0")) return ret;
		
		Matcher matcherPart = patternPart.matcher(formatted);
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
	
	public static final String FORMAT_ENTRY_VERBOSE = Txt.parse("<v>%1$d <k>%3$s");
	public static final String FORMAT_COMMA_VERBOSE = "%s, ";
	public static final String FORMAT_AND_VERBOSE = " %sand ";
	
	@Deprecated
	public static final String FORMAT_ENTRY_VERBOOSE = Txt.parse("<v>%1$d <k>%3$s");
	@Deprecated
	public static final String FORMAT_COMMA_VERBOOSE = "%s, ";
	@Deprecated
	public static final String FORMAT_AND_VERBOOSE = " %sand ";
	
	public static final String FORMAT_ENTRY_MINIMAL = Txt.parse("<v>%1$d<k>%2$s");
	public static final String FORMAT_COMMA_MINIMAL = "%s";
	public static final String FORMAT_AND_MINIMAL = "%s";
	
	public static String formatted(@NotNull TimeUnit unit, long count, String formatEntry)
	{
		return String.format(formatEntry, count, unit.getUnitString(count), unit.getNameString(count));
	}
	
	public static String formatted(@NotNull Map<TimeUnit, Long> unitcounts, String entryFormat, String commaFormat, String andFormat, String color)
	{
		String comma = String.format(commaFormat, Txt.parse(color));
		String and = String.format(andFormat, Txt.parse(color));
		
		if (unitcounts.isEmpty())
		{
			return formatted(TimeUnit.SECOND, 0, entryFormat);
		}
		
		List<String> parts = new ArrayList<>();
		for (Entry<TimeUnit, Long> entry : unitcounts.entrySet())
		{
			parts.add(formatted(entry.getKey(), entry.getValue(), entryFormat));
		}
		return Txt.implodeCommaAnd(parts, comma, and);
	}
	
	public static String formattedVerbose(@NotNull TimeUnit unit, long count)
	{
		return formatted(unit, count, FORMAT_ENTRY_VERBOSE);
	}
	
	public static String formattedVerbose(@NotNull Map<TimeUnit, Long> unitcounts, String color)
	{
		return formatted(unitcounts, FORMAT_ENTRY_VERBOSE, FORMAT_COMMA_VERBOSE, FORMAT_AND_VERBOSE, color);
	}
	
	public static String formattedVerbose(@NotNull Map<TimeUnit, Long> unitcounts)
	{
		return formattedVerbose(unitcounts, "<i>");
	}
	
	public static String formattedMinimal(@NotNull TimeUnit unit, long count)
	{
		return formatted(unit, count, FORMAT_ENTRY_MINIMAL);
	}
	
	public static String formattedMinimal(@NotNull Map<TimeUnit, Long> unitcounts, String color)
	{
		return formatted(unitcounts, FORMAT_ENTRY_MINIMAL, FORMAT_COMMA_MINIMAL, FORMAT_AND_MINIMAL, color);
	}
	
	public static String formattedMinimal(@NotNull Map<TimeUnit, Long> unitcounts)
	{
		return formattedMinimal(unitcounts, "");
	}
	
	@Deprecated
	public static String formated(@NotNull TimeUnit unit, long count, String formatEntry)
	{
		return formatted(unit, count, formatEntry);
	}
	
	@Deprecated
	public static String formated(@NotNull Map<TimeUnit, Long> unitcounts, String entryFormat, String commaFormat, String andFormat, String color)
	{
		return formatted(unitcounts, entryFormat, commaFormat, andFormat, color);
	}
	
	@Deprecated
	public static String formatedVerboose(@NotNull TimeUnit unit, long count)
	{
		return formattedVerbose(unit, count);
	}
	
	@Deprecated
	public static String formatedVerboose(@NotNull Map<TimeUnit, Long> unitcounts, String color)
	{
		return formattedVerbose(unitcounts, color);
	}
	
	@Deprecated
	public static String formatedVerboose(@NotNull Map<TimeUnit, Long> unitcounts)
	{
		return formattedVerbose(unitcounts);
	}
	
	@Deprecated
	public static String formatedMinimal(@NotNull TimeUnit unit, long count)
	{
		return formattedMinimal(unit, count);
	}
	
	@Deprecated
	public static String formatedMinimal(@NotNull Map<TimeUnit, Long> unitcounts, String color)
	{
		return formattedMinimal(unitcounts, color);
	}
	
	@Deprecated
	public static String formatedMinimal(@NotNull Map<TimeUnit, Long> unitcounts)
	{
		return formattedMinimal(unitcounts);
	}
}
