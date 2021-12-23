package com.massivecraft.massivecore.command.type;

import com.massivecraft.massivecore.item.ContainerGameProfile;

public class TypeContainerGameProfile extends TypeReflection<ContainerGameProfile>
{
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	private static final TypeContainerGameProfile i = new TypeContainerGameProfile();
	public static TypeContainerGameProfile get() { return i; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public TypeContainerGameProfile()
	{
		super(ContainerGameProfile.class);
	}
	
}
