package com.massivecraft.massivecore.item;

import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.jetbrains.annotations.NotNull;

public class WriterBannerPatternColor extends WriterAbstractBannerPattern<DyeColor, DyeColor>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterBannerPatternColor i = new WriterBannerPatternColor();
	public static WriterBannerPatternColor get() { return i; }
	public WriterBannerPatternColor()
	{
		super("color");
		// FIXME what is needed when a converter isn't used?
		//this.setConverterTo(ConverterToDyeColor.get());
		//this.setConverterFrom(ConverterFromDyeColor.get());
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public DyeColor getA(@NotNull DataBannerPattern ca, Object d)
	{
		return ca.getColor();
	}
	
	@Override
	public void setA(@NotNull DataBannerPattern ca, DyeColor fa, Object d)
	{
		ca.setColor(fa);
	}
	
	@Override
	public DyeColor getB(@NotNull Pattern cb, Object d)
	{
		return cb.getColor();
	}
	
}
