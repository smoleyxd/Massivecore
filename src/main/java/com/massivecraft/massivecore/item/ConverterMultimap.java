package com.massivecraft.massivecore.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Map.Entry;

public class ConverterMultimap<KX, VX, KY, VY> extends Converter<Multimap<KX, VX>, Multimap<KY, VY>>
{
	// -------------------------------------------- //
	// FIELD
	// -------------------------------------------- //
	
	private final Converter<KX, KY> converterKey;
	public Converter<KX, KY> getConverterKey() { return this.converterKey; }
	
	private final Converter<VX, VY> converterValue;
	public Converter<VX, VY> getConverterValue() { return this.converterValue; }
	
	// -------------------------------------------- //
	// CREATE
	// -------------------------------------------- //
	
	public ConverterMultimap(Converter<KX, KY> converterKey, Converter<VX, VY> converterValue)
	{
		this.converterKey = converterKey;
		this.converterValue = converterValue;
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public Multimap<KY, VY> convert(Multimap<KX, VX> multimapx)
	{
		// Null
		if (multimapx == null) return null;
		
		// Create
		Multimap<KY, VY> mapy = HashMultimap.create();
		
		// Fill
		for (Entry<KX, VX> entry : multimapx.entries())
		{
			KY ky;
			KX kx = entry.getKey();
			try
			{
				ky = this.getConverterKey().convert(kx);
			}
			catch (Throwable t)
			{
				t.printStackTrace();
				continue;
			}
			
			VY vy;
			VX vx = entry.getValue();
			try
			{
				vy = this.getConverterValue().convert(vx);
			}
			catch (Throwable t)
			{
				t.printStackTrace();
				continue;
			}
			
			mapy.put(ky, vy);
		}
		
		// Return
		return mapy;
	}

}
