package com.massivecraft.massivecore.nms;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class NmsEntityGet119R1 extends NmsEntityGet
{
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsEntityGet119R1 i = new NmsEntityGet119R1();
	public static NmsEntityGet119R1 get () { return i; }
	
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
