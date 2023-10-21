package com.massivecraft.massivecore.nms;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class NmsEntityGet120R2 extends NmsEntityGet
{
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsEntityGet120R2 i = new NmsEntityGet120R2();
	public static NmsEntityGet120R2 get () { return i; }
	
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
