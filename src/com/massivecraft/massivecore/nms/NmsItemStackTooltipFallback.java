package com.massivecraft.massivecore.nms;

public class NmsItemStackTooltipFallback extends NmsItemStackTooltip
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackTooltipFallback i = new NmsItemStackTooltipFallback();
	public static NmsItemStackTooltipFallback get () { return i; }
	
}
