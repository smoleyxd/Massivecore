package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import com.massivecraft.massivecore.util.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class NmsEntityGet117R1P extends NmsEntityGet
{
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static NmsEntityGet117R1P i = new NmsEntityGet117R1P();
	public static NmsEntityGet117R1P get () { return i; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	// net.minecraft.server.level.WorldServer
	private Class<?> classNmsWorldServer;
	
	// net.minecraft.server.level.WorldServer#getEntities()
	private Method methodNmsWorldServerGetEntities;
	
	// net.minecraft.world.level.entity.LevelEntityGetter
	private Class<?> classNmsLevelEntityGetter;
	
	// net.minecraft.world.level.entity.LevelEntityGetter#a(UUID)
	private Method methodNmsLevelEntityGetterGet;
	
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		NmsBasics.get().require();
		
		this.classNmsWorldServer = PackageType.MINECRAFT_SERVER_LEVEL.getClass("WorldServer");
		this.methodNmsWorldServerGetEntities = ReflectionUtil.getMethod(this.classNmsWorldServer, "getEntities");
		
		this.classNmsLevelEntityGetter = PackageType.MINECRAFT_WORLD_LEVEL_ENTITY.getClass("LevelEntityGetter");
		this.methodNmsLevelEntityGetterGet = ReflectionUtil.getMethod(this.classNmsLevelEntityGetter, "a", UUID.class);
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
		
		Object nmsLevelEntityGetter = ReflectionUtil.invokeMethod(methodNmsWorldServerGetEntities, world);
		Object nmsEntity = ReflectionUtil.invokeMethod(methodNmsLevelEntityGetterGet, nmsLevelEntityGetter, uuid);
		if (nmsEntity == null) return null;
		
		return NmsBasics.get().getBukkit(nmsEntity);
	}
	
}
