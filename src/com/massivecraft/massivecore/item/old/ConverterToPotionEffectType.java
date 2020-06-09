//package com.massivecraft.massivecore.item.old;
//
//import com.massivecraft.massivecore.item.Converter;
//import org.bukkit.potion.PotionEffectType;
//
//@Deprecated
//public class ConverterToPotionEffectType extends Converter<Integer, PotionEffectType>
//{
//	// -------------------------------------------- //
//	// INSTANCE & CONSTRUCT
//	// -------------------------------------------- //
//
//	private static final ConverterToPotionEffectType i = new ConverterToPotionEffectType();
//	public static ConverterToPotionEffectType get() { return i; }
//
//	// -------------------------------------------- //
//	// OVERRIDE
//	// -------------------------------------------- //
//
//	@Override
//	public PotionEffectType convert(Integer x)
//	{
//		if (x == null) return null;
//		throw new UnsupportedOperationException("Magic number");
//		//return PotionEffectType.getById(x);
//	}
//
//}
