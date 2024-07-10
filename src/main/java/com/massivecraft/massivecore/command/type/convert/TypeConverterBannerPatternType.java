package com.massivecraft.massivecore.command.type.convert;

import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.TypeNamespacedKey;
import com.massivecraft.massivecore.command.type.enumeration.TypePatternType;
import com.massivecraft.massivecore.item.Converter;
import com.massivecraft.massivecore.item.ConverterFromBannerPatternType;
import com.massivecraft.massivecore.item.ConverterToBannerPatternType;
import org.bukkit.NamespacedKey;

// Minecraft 1.21 Compatibility
public class TypeConverterBannerPatternType<A> extends TypeConverter<A, NamespacedKey>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static TypeConverterBannerPatternType<?> i = null;
	@SuppressWarnings("unchecked")
	public static <T> TypeConverterBannerPatternType<T> get()
	{
		if (i == null) i = new TypeConverterBannerPatternType<>(TypePatternType.get(), ConverterFromBannerPatternType.get(), ConverterToBannerPatternType.get());
		return (TypeConverterBannerPatternType<T>) i;
	}
	
	public TypeConverterBannerPatternType(Type<A> typeA, Converter<A, NamespacedKey> a2b, Converter<NamespacedKey, A> b2a)
	{
		super(typeA, TypeNamespacedKey.get(), a2b, b2a);
	}

}
