package com.massivecraft.massivecore.util;

import com.massivecraft.massivecore.MassiveCore;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class IntervalUtil
{	
	// -------------------------------------------- //
	// PARSING SIMPLE
	// -------------------------------------------- //
	
	@Contract("null, _ -> param2")
	public static Double parseDouble(String str, Double def)
	{
		if (str == null) return def;
		try
		{
			return Double.valueOf(str);
		}
		catch (Exception e)
		{
			return def;
		}
	}
	
	@Contract("null, _ -> param2")
	public static Integer parseInteger(String str, Integer def)
	{
		if (str == null) return def;
		try
		{
			return Integer.valueOf(str);
		}
		catch (Exception e)
		{
			return def;
		}
	}
	
	@Contract(pure = true)
	public static boolean isValidInterval(@NotNull String interval)
	{
		return interval.matches("^.+to.+$");
	}
	
	// -------------------------------------------- //
	// PARSING ADVANCED
	// -------------------------------------------- //
	
	@Contract("null, _, _ -> new")
	public static @NotNull Entry<Double, Double> parseDoubleInterval(String interval, Double dmin, Double dmax)
	{
		if (interval == null)
		{
			return new SimpleEntry<>(dmin, dmax);
		}
		
		if (interval.contains("to"))
		{
			String[] parts = interval.split("to");
			if (parts.length == 2)
			{
				Double min = parseDouble(parts[0], dmin);
				Double max = parseDouble(parts[1], dmax);
				return new SimpleEntry<>(min, max);
			}
		}
		Double single = parseDouble(interval, dmin);
		return new SimpleEntry<>(single, single);
	}
	
	@Contract("null, _, _ -> new")
	public static @NotNull Entry<Integer, Integer> parseIntegerInterval(String interval, Integer dmin, Integer dmax)
	{
		if (interval == null)
		{
			return new SimpleEntry<>(dmin, dmax);
		}
		
		if (interval.contains("to"))
		{
			String[] parts = interval.split("to");
			if (parts.length == 2)
			{
				Integer min = parseInteger(parts[0], dmin);
				Integer max = parseInteger(parts[1], dmax);
				return new SimpleEntry<>(min, max);
			}
		}
		Integer single = parseInteger(interval, dmin);
		return new SimpleEntry<>(single, single);
	}
	
	// -------------------------------------------- //
	// RANDOM SIMPLE
	// -------------------------------------------- //
	
	public static int random(int min, int max)
	{
		return min + MassiveCore.random.nextInt(max - min + 1);
	}
	
	public static long random(long min, long max)
	{
		return min + (long)(MassiveCore.random.nextDouble() * (max - min));
	}
	
	public static double random(double min, double max)
	{
		return min + MassiveCore.random.nextDouble() * (max - min);
	}
	
	public static int randomIntegerFromInterval(@NotNull Entry<Integer, Integer> interval)
	{
		int min = interval.getKey();
		int max = interval.getValue();
		return random(min, max);
	}
	
	public static double randomDoubleFromInterval(@NotNull Entry<Double, Double> interval)
	{
		double min = interval.getKey();
		double max = interval.getValue();
		return random(min, max);
	}
	
	// -------------------------------------------- //
	// RANDOM COMBINED
	// -------------------------------------------- //
	
	public static Double randomDoubleFromInterval(@NotNull String data, Double def)
	{
		if (isValidInterval(data))
		{
			Entry<Double, Double> interval = parseDoubleInterval(data, def, def);
			return randomDoubleFromInterval(interval);
		}
		else
		{
			return parseDouble(data, def);
		}
	}
	
	public static Integer randomIntegerFromInterval(@NotNull String data, Integer def)
	{
		if (isValidInterval(data))
		{
			Entry<Integer, Integer> interval = parseIntegerInterval(data, def, def);
			return randomIntegerFromInterval(interval);
		}
		else
		{
			return parseInteger(data, def);
		}
	}
	
}
