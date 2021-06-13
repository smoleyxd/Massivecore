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
	
	// net.minecraft.server.level.WorldServer#entitiesByUUID
	private Field fieldNmsWorldServerG;
	
	private Class<?> classNmsPersistentEntitySectionManager;
	
	private Method classNmsPersistentEntitySectionManagerD;
	
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		NmsBasics.get().require();
		
		this.classNmsWorldServer = PackageType.MINECRAFT_SERVER_LEVEL.getClass("WorldServer");
		this.fieldNmsWorldServerG = ReflectionUtil.getField(this.classNmsWorldServer, "G");
		this.classNmsPersistentEntitySectionManager = ReflectionUtil.getField(fieldNmsWorldServerG, null).getClass();
		this.classNmsPersistentEntitySectionManagerD = ReflectionUtil.getMethod(classNmsPersistentEntitySectionManager, "d");
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
		
		Map<UUID, Object> worldMap = this.getWorldMap(world);
		
		Object nmsEntity = worldMap.get(uuid);
		if (nmsEntity == null) return null;
		
		return NmsBasics.get().getBukkit(nmsEntity);
	}
	
	// -------------------------------------------- //
	// INTERNAL
	// -------------------------------------------- //
	
	private Map<World, Map<UUID, Object>> worldMaps = new WeakHashMap<>();
	
	@Contract("null -> fail")
	private Map<UUID, Object> getWorldMap(Object handle)
	{
		if (handle == null) throw new NullPointerException("handle");
		
		return ReflectionUtil.getField(this.fieldNmsWorldServerG, handle);
	}
	
	@Contract("null -> fail")
	private Map<UUID, Object> getWorldMap(World world)
	{
		if (world == null) throw new NullPointerException("world");
		
		Map<UUID, Object> ret = this.worldMaps.get(world);
		if (ret == null)
		{
			Object handle = NmsBasics.get().getHandle(world);
			ret = this.getWorldMap(handle);
			this.worldMaps.put(world, ret);
		}
		return ret;
	}
	
}
