package com.massivecraft.massivecore.nms;

public class NmsItemStackMetaFallback extends NmsItemStackMeta
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackMetaFallback i = new NmsItemStackMetaFallback();
	public static NmsItemStackMetaFallback get() { return i; }
}
