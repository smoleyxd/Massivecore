package com.massivecraft.massivecore.collections;

import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class WorldExceptionSet extends ExceptionSet
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public WorldExceptionSet()
	{
		
	}
	
	public WorldExceptionSet(boolean standard)
	{
		super(standard);
	}
	
	@SafeVarargs
	public <O> WorldExceptionSet(boolean standard, O @NotNull ... exceptions)
	{
		super(standard, exceptions);
	}
	
	// -------------------------------------------- //
	// CONTAINS
	// -------------------------------------------- //
	
	public boolean contains(@NotNull PS ps)
	{
		return this.contains(ps.getWorld());
	}
	
	public boolean contains(@NotNull Location loc)
	{
		return this.contains(loc.getWorld());
	}
	
	public boolean contains(@NotNull Entity entity)
	{
		return this.contains(entity.getWorld());
	}
	
	// -------------------------------------------- //
	// STRINGIFY
	// -------------------------------------------- //
	
	@Override
	public String stringifyInner(Object object)
	{
		if ( ! (object instanceof World world)) return null;
		return world.getName();
	}

}
