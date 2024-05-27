package com.massivecraft.massivecore.item;

public class WriterArmorTrim extends WriterAbstractArmorTrim<Object, Object>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterArmorTrim i = new WriterArmorTrim();
	public static WriterArmorTrim get() { return i; }
	public WriterArmorTrim()
	{
		this.addWriterClasses(
			WriterArmorTrimMaterial.class,
			WriterArmorTrimPattern.class
		);
	}
	
}
