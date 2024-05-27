package com.massivecraft.massivecore.item;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.jetbrains.annotations.NotNull;

public class WriterArmorTrimPattern extends WriterAbstractArmorTrim<NamespacedKey, TrimPattern>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterArmorTrimPattern i = new WriterArmorTrimPattern();
	public static WriterArmorTrimPattern get() { return i; }
	public WriterArmorTrimPattern()
	{
		super("pattern");
		this.setConverterTo(ConverterToArmorTrimPattern.get());
		this.setConverterFrom(ConverterFromArmorTrimPattern.get());
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public NamespacedKey getA(@NotNull DataArmorTrim ca, Object d)
	{
		return ca.getPattern();
	}
	
	@Override
	public void setA(@NotNull DataArmorTrim ca, NamespacedKey fa, Object d)
	{
		ca.setPattern(fa);
	}
	
	@Override
	public TrimPattern getB(@NotNull ArmorTrim cb, Object d)
	{
		return cb.getPattern();
	}
}
