package com.massivecraft.massivecore.command.type;

import com.massivecraft.massivecore.item.ContainerGameProfileProperty;

public class TypeContainerGameProfileProperty extends TypeReflection<ContainerGameProfileProperty>
{
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	private static final TypeContainerGameProfileProperty i = new TypeContainerGameProfileProperty();
	public static TypeContainerGameProfileProperty get() { return i; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public TypeContainerGameProfileProperty()
	{
		super(ContainerGameProfileProperty.class);
	}
	
}
