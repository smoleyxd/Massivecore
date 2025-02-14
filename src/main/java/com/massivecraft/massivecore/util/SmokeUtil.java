package com.massivecraft.massivecore.util;

import org.apache.commons.lang.mutable.MutableBoolean;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Random;

// http://mc.kev009.com/Protocol
// -----------------------------
// Smoke Directions 
// -----------------------------
// Direction 	ID	Direction
//				0	South - East
//				1	South
//				2	South - West
//				3	East
//				4	(Up or middle ?)
//				5	West
//				6	North - East
//				7	North
//				8	North - West
//-----------------------------

public class SmokeUtil
{
	public static Random random = new Random();
	
	// -------------------------------------------- //
	// Spawn once
	// -------------------------------------------- //
	
	// Single ========
	public static void spawnSingle(@Nullable Location location, int direction)
	{
		if (location == null) return;
		location.getWorld().playEffect(location, Effect.SMOKE, direction);
	}
	
	public static void spawnSingle(Location location)
	{
		spawnSingle(location, 4);
	}
	
	public static void spawnSingleRandom(Location location)
	{
		spawnSingle(location, random.nextInt(9));
	}
	
	// Simple Cloud ========
	public static void spawnCloudSimple(Location location)
	{
		for (int i = 0; i <= 8; i++)
		{
			spawnSingle(location, i);
		}
	}
	
	public static void spawnCloudSimple(@NotNull Collection<Location> locations)
	{
		for (Location location : locations)
		{
			spawnCloudSimple(location);
		}
	}
	
	// Random Cloud ========
	public static void spawnCloudRandom(Location location, float thickness)
	{
		int singles = (int) Math.floor(thickness*9);
		for (int i = 0; i < singles; i++)
		{
			spawnSingleRandom(location);
		}
	}
	
	public static void spawnCloudRandom(@NotNull Collection<Location> locations, float thickness)
	{
		for (Location location : locations)
		{
			spawnCloudRandom(location, thickness);
		}
	}
	
	// Fake Explosion ========
	
	public static void fakeExplosion(Location location)
	{
		fakeExplosion(location, 4F);
	}
	
	public static final MutableBoolean fakeExplosion = new MutableBoolean(false);
	public static void fakeExplosion(@NotNull Location location, float power)
	{
		synchronized (fakeExplosion)
		{
			fakeExplosion.setValue(true);
			location.getWorld().createExplosion(location.getX(), location.getY(), location.getZ(), power, false, false);
			fakeExplosion.setValue(false);
		}
	}
	
	@Deprecated
	public static void fakeExplosion(Location location, int viewDistance)
	{
		fakeExplosion(location);
	}
	
	// -------------------------------------------- //
	// Attach continuous effects to or locations
	// -------------------------------------------- //
	
	// TODO
	
}
