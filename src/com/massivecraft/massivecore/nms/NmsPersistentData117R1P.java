package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.particleeffect.ReflectionUtils;
import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;

public class NmsPersistentData117R1P extends NmsPersistentData
{
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static NmsPersistentData117R1P i = new NmsPersistentData117R1P();
	public static NmsPersistentData117R1P get () { return i; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private Class<?> classNBTTagCompound;
	
	private Class<?> classCraftNBTTagConfigSerializer;
	
	private Class<?> classCraftPersistentDataContainer;
	
	private Method serializePersistentData;
	private Method deserializeNBT;
	
	private Method putAllPersistentData;
	
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		this.classNBTTagCompound = PackageType.MINECRAFT_NBT.getClass("NBTTagCompound");
		
		this.classCraftNBTTagConfigSerializer = PackageType.CRAFTBUKKIT_VERSION_UTIL.getClass("CraftNBTTagConfigSerializer");
		
		this.classCraftPersistentDataContainer = PackageType.CRAFTBUKKIT_VERSION_PERSISTENCE.getClass("CraftPersistentDataContainer");
		
		this.serializePersistentData = ReflectionUtils.getMethod(classCraftPersistentDataContainer, "serialize");
		this.deserializeNBT = ReflectionUtils.getMethod(classCraftNBTTagConfigSerializer, "deserialize", Object.class);
		
		this.putAllPersistentData = ReflectionUtils.getMethod(classCraftPersistentDataContainer, "putAll", classNBTTagCompound);
	}
	
	// -------------------------------------------- //
	// CREATE
	// -------------------------------------------- //
	
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, Object> getPersistentData(@NotNull PersistentDataContainer persistentDataContainer) {
		try
		{
			return (Map<String, Object>) serializePersistentData.invoke(persistentDataContainer);
		}
		catch (Exception ignored) {
			MassiveCore.get().log(Level.WARNING, "Failed to getPersistentData");
			return null;
		}
	}
	
	@Override
	public void setPersistentData(@NotNull PersistentDataContainer persistentDataContainer, Map<String, Object> data) {
		try
		{
			Object deserialized = deserializeNBT.invoke(persistentDataContainer, data);
			putAllPersistentData.invoke(persistentDataContainer, deserialized);
		}
		catch (Exception ignored) {
			MassiveCore.get().log(Level.WARNING, "Failed to setPersistentData");
		}
	}
	
}
