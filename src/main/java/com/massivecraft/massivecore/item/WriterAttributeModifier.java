package com.massivecraft.massivecore.item;

public class WriterAttributeModifier extends WriterAbstractAttributeModifier<Object, Object>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterAttributeModifier i = new WriterAttributeModifier();
	public static WriterAttributeModifier get() { return i; }
	public WriterAttributeModifier()
	{
		this.addWriterClasses(
			WriterAttributeModifierKey.class,
			WriterAttributeModifierAmount.class,
			WriterAttributeModifierOperation.class,
			WriterAttributeModifierSlot.class
		);
	}
	
}
