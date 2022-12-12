package com.massivecraft.massivecore.command.type;

import com.massivecraft.massivecore.Aspect;
import com.massivecraft.massivecore.Multiverse;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class TypeUniverse extends TypeAbstractChoice<String>
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	protected Aspect aspect = null;
	protected Multiverse multiverse = null;
	
	public Multiverse getMultiverse()
	{
		if (this.aspect != null) return this.aspect.getMultiverse();
		return this.multiverse;
	}
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@Contract("_ -> new")
	public static @NotNull TypeUniverse get(Aspect aspect) { return new TypeUniverse(aspect); }
	@Contract("_ -> new")
	public static @NotNull TypeUniverse get(Multiverse multiverse) { return new TypeUniverse(multiverse); }
	
	public TypeUniverse(Aspect aspect) { super(String.class); this.aspect = aspect; }
	public TypeUniverse(Multiverse multiverse) { super(String.class); this.multiverse = multiverse; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public Collection<String> getAll()
	{
		return this.getMultiverse().getUniverses();
	}

}
