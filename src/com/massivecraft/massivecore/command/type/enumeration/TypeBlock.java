package com.massivecraft.massivecore.command.type.enumeration;

import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TypeBlock extends TypeMaterial
{
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	private static final TypeBlock i = new TypeBlock();
	
	public static TypeBlock get() { return i; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public TypeBlock()
	{
		this.setAll(new MassiveSet<>(Arrays.stream(Material.values()).filter(Material::isBlock).collect(Collectors.toSet())));
		this.setHelp(
			Txt.parse("<aqua>https://hub.spigotmc.org/stash/projects/SPIGOT/repos/bukkit/browse/src/main/java/org/bukkit/Material.java")
		);
	}
}
