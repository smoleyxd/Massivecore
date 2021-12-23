package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import com.massivecraft.massivecore.util.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.lang.reflect.Method;
import java.util.UUID;

public class NmsEntityGet117R1 extends NmsEntityGet
{
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsEntityGet117R1 i = new NmsEntityGet117R1();
	public static NmsEntityGet117R1 get () { return i; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	// net.minecraft.server.level.WorldServer#getEntity(UUID uuid)
	private Method methodNmsWorldServerGetEntity;
	
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		NmsBasics.get().require();
		
		// net.minecraft.server.level.WorldServer
		Class<?> classNmsWorldServer = PackageType.MINECRAFT_SERVER_LEVEL.getClass("WorldServer");
		this.methodNmsWorldServerGetEntity = ReflectionUtil.getMethod(classNmsWorldServer, "getEntity", UUID.class);
	}
	
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
		
		Object worldHandle = NmsBasics.get().getHandle(world);
		Object nmsEntity = ReflectionUtil.invokeMethod(methodNmsWorldServerGetEntity, worldHandle, uuid);
		if (nmsEntity == null) return null;
		
		return NmsBasics.get().getBukkit(nmsEntity);
	}
	
}
