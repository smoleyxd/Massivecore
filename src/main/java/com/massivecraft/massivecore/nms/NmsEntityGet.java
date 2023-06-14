package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.mixin.Mixin;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class NmsEntityGet extends Mixin
{
	// -------------------------------------------- //
	// DEFAULT
	// -------------------------------------------- //
	
	private static final NmsEntityGet d = new NmsEntityGet().setAlternatives(
		NmsEntityGet120R1.class,
		NmsEntityGetFallback.class
	);
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsEntityGet i = d;
	public static NmsEntityGet get() { return i; }

	// -------------------------------------------- //
	// GET ENTITY
	// -------------------------------------------- //
	
	public Entity getEntity(UUID uuid)
	{
		throw notImplemented();
	}
	
	public Entity getEntity(World world, UUID uuid)
	{
		throw notImplemented();
	}
	
}
