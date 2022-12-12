package com.massivecraft.massivecore.item;

import com.google.common.collect.ImmutableList;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WriterFireworkEffectFadeColors extends WriterAbstractFireworkEffect<List<Integer>, ImmutableList<Color>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterFireworkEffectFadeColors i = new WriterFireworkEffectFadeColors();
	public static WriterFireworkEffectFadeColors get() { return i; }
	public WriterFireworkEffectFadeColors()
	{
		super("fadeColors");
		this.setConverterTo(ConverterToColors.get());
		this.setConverterFrom(ConverterFromColors.get());
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //
	
	@Override
	public List<Integer> getA(@NotNull DataFireworkEffect ca, Object d)
	{
		return ca.getFadeColors();
	}
	
	@Override
	public void setA(@NotNull DataFireworkEffect ca, List<Integer> fa, Object d)
	{
		ca.setFadeColors(fa);
	}
	
	@Override
	public ImmutableList<Color> getB(@NotNull FireworkEffect cb, Object d)
	{
		return (ImmutableList<Color>) cb.getFadeColors();
	}
	
}
