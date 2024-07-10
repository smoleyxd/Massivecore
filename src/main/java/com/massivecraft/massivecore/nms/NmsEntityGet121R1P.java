package com.massivecraft.massivecore.nms;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class NmsEntityGet121R1P extends NmsEntityGet
{
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsEntityGet121R1P i = new NmsEntityGet121R1P();
	public static NmsEntityGet121R1P get () { return i; }
	
	// -------------------------------------------- //
	// GET ENTITY
	// -------------------------------------------- //
	
	@Override
	public Entity getEntity(UUID uuid)
	{
		if (uuid == null) return null;
		
		for (World world : Bukkit.getWorlds())
		{
			Entity ret = this.getEntity(world, uuid);
			if (ret != null) return ret;
		}
		
		return null;
	}
	
	@Override
	public Entity getEntity(World world, UUID uuid)
	{
		if (world == null) throw new NullPointerException("world");
		if (uuid == null) return null;
		
		net.minecraft.world.entity.Entity nmsEntity = ((CraftWorld) world).getHandle().getEntity(uuid);
		if (nmsEntity == null) return null;
		
		return nmsEntity.getBukkitEntity();
	}
	
}
