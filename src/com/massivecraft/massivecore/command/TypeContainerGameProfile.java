package com.massivecraft.massivecore.command;

import com.massivecraft.massivecore.command.type.TypeReflection;
import com.massivecraft.massivecore.item.ContainerGameProfile;

public class TypeContainerGameProfile extends TypeReflection<ContainerGameProfile>
{
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	private static TypeContainerGameProfile i = new TypeContainerGameProfile();
	public static TypeContainerGameProfile get() { return i; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public TypeContainerGameProfile()
	{
		super(ContainerGameProfile.class);
	}
	
}
