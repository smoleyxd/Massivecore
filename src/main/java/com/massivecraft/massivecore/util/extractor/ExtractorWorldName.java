package com.massivecraft.massivecore.util.extractor;

public class ExtractorWorldName implements Extractor
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final ExtractorWorldName i = new ExtractorWorldName();
	public static ExtractorWorldName get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE: EXTRACTOR
	// -------------------------------------------- //
	
	@Override
	public Object extract(Object o)
	{
		return ExtractorLogic.worldNameFromObject(o);
	}
	
}
