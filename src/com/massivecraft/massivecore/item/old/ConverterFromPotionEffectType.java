//package com.massivecraft.massivecore.item.old;
//
//import com.massivecraft.massivecore.item.Converter;
//import org.bukkit.potion.PotionEffectType;
//
//@Deprecated
//public class ConverterFromPotionEffectType extends Converter<PotionEffectType, Integer>
//{
//	// -------------------------------------------- //
//	// INSTANCE & CONSTRUCT
//	// -------------------------------------------- //
//
//	private static final ConverterFromPotionEffectType i = new ConverterFromPotionEffectType();
//	public static ConverterFromPotionEffectType get() { return i; }
//
//	// -------------------------------------------- //
//	// OVERRIDE
//	// -------------------------------------------- //
//
//	@Override
//	public Integer convert(PotionEffectType x)
//	{
//		if (x == null) return null;
//		throw new UnsupportedOperationException("Magic number");
//		//return x.getId();
//	}
//
//}
