package com.massivecraft.massivecore.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneUtil
{
	// -------------------------------------------- //
	// GET REGIONAL TIME
	// -------------------------------------------- //
	
	public static String getAdjustedTime(long millis, String timeZoneString)
	{
		TimeZone timeZone = TimeZone.getTimeZone(timeZoneString);
		DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z", Locale.getDefault());
		dateFormat.setTimeZone(timeZone);
		
		return dateFormat.format(millis);
	}
}
