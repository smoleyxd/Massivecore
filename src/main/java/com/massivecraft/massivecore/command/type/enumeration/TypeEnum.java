package com.massivecraft.massivecore.command.type.enumeration;

import com.massivecraft.massivecore.command.type.TypeAbstractChoice;
import com.massivecraft.massivecore.util.Txt;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TypeEnum<T extends Enum<T>> extends TypeAbstractChoice<T>
{
	// -------------------------------------------- //
	// FIELD
	// -------------------------------------------- //
	
	protected final Class<T> clazz;
	public Class<T> getClazz() { return this.clazz; }

	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public TypeEnum(@NotNull Class<T> clazz)
	{
		super(clazz);
		if ( ! clazz.isEnum()) throw new IllegalArgumentException("clazz must be enum");
		this.clazz = clazz;
		
		this.setAll(getEnumValues(this.getClazz()));
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public String getName()
	{
		return Txt.getNicedEnumString(this.getClazz().getSimpleName());
	}
	
	@Override
	public String getNameInner(T value)
	{
		return Txt.getNicedEnum(value);
	}

	@Override
	public String getIdInner(T value)
	{
		return value.name();
	}
	
	// -------------------------------------------- //
	// ENUM
	// -------------------------------------------- //
	
	@Contract("null -> fail")
	public static <T extends Enum<T>> T @NotNull [] getEnumValues(Class<T> clazz)
	{
		if (clazz == null) throw new IllegalArgumentException("clazz is null");
		if ( ! clazz.isEnum()) throw new IllegalArgumentException("clazz must be enum");
		
		T[] ret = clazz.getEnumConstants();
		if (ret == null) throw new RuntimeException("failed to retrieve enum constants");
		
		return ret;
	}
	
}
