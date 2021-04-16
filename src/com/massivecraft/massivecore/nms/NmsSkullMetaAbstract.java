package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.Couple;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.item.ContainerGameProfileProperty;
import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import com.massivecraft.massivecore.util.ReflectionUtil;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public abstract class NmsSkullMetaAbstract extends NmsSkullMeta
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	// org.bukkit.craftbukkit.inventory.CraftMetaSkull 
	public Class<?> classCraftMetaSkull;
	// org.bukkit.craftbukkit.inventory.CraftMetaSkull#profile
	public Field fieldCraftMetaSkullProfile;
	
	// 17R4: net.minecraft.util.com.mojang.authlib.GameProfile
	// 18R1P: com.mojang.authlib.GameProfile
	public Class<?> classGameProfile;
	// ...#id
	public Field fieldGameProfileId;
	// ...#name
	public Field fieldGameProfileName;
	// #properties
	public Field fieldGameProfilePropertyMap;
	// ...(UUID id, String name);
	public Constructor<?> constructorGameProfile;
	
	public Class<?> classPropertyMap;
	public Method methodPropertyMapEntries;
	public Constructor<?> constructorPropertyMap;
	
	// com.mojang.authlib.properties
	public Class<?> classProperty;
	// name, value, signature
	// (string, string, string)
	public Constructor<?> constructorProperty;
	
	// #name
	public Field fieldPropertyName;
	// #value
	public Field fieldPropertyValue;
	// #signature
	public Field fieldPropertySignature;
	
	public Method methodPropertyMapClear;
	public Method methodPropertyMapPut;
	
	// -------------------------------------------- //
	// GAME PROFILE CLASS NAME
	// -------------------------------------------- //
	
	public abstract String getGameProfileClassName();
	
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		this.classCraftMetaSkull = PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftMetaSkull");
		this.fieldCraftMetaSkullProfile = ReflectionUtil.getField(this.classCraftMetaSkull, "profile");
		
		this.classGameProfile = Class.forName(this.getGameProfileClassName());
		this.fieldGameProfileId = ReflectionUtil.getField(this.classGameProfile, "id");
		this.fieldGameProfileName = ReflectionUtil.getField(this.classGameProfile, "name");
		this.fieldGameProfilePropertyMap = ReflectionUtil.getField(this.classGameProfile, "properties");
		this.classPropertyMap = this.fieldGameProfilePropertyMap.getType();
		this.constructorPropertyMap = ReflectionUtil.getConstructor(this.classPropertyMap);
		
		Class<?> classDeclaringPropertyMapEntries = ReflectionUtil.getSuperclassDeclaringMethod(this.classPropertyMap, true, "entries");
		this.methodPropertyMapEntries = ReflectionUtil.getMethod(classDeclaringPropertyMapEntries,"entries");
		
		this.constructorGameProfile = ReflectionUtil.getConstructor(this.classGameProfile, UUID.class, String.class);
		this.classProperty = Class.forName("com.mojang.authlib.properties.Property");
		this.constructorProperty = ReflectionUtil.getConstructor(this.classProperty, String.class, String.class, String.class);
		this.fieldPropertyName = ReflectionUtil.getField(this.classProperty, "name");
		this.fieldPropertyValue = ReflectionUtil.getField(this.classProperty, "value");
		this.fieldPropertySignature = ReflectionUtil.getField(this.classProperty, "signature");
		
		Class<?> classDeclaringPropertyMapClear = ReflectionUtil.getSuperclassDeclaringMethod(this.classPropertyMap, true, "clear");
		this.methodPropertyMapClear = ReflectionUtil.getMethod(classDeclaringPropertyMapClear, "clear");
		
		Class<?> classDeclaringPropertyMapPut = ReflectionUtil.getSuperclassDeclaringMethod(this.classPropertyMap, true, "put");
		Method[] methodsDeclaredInPut = classDeclaringPropertyMapPut.getDeclaredMethods();
		for (Method method : methodsDeclaredInPut)
		{
			if (!method.getName().equals("put")) continue;
			
			this.methodPropertyMapPut = method;
			break;
		}
	}
	
	// -------------------------------------------- //
	// RAW
	// -------------------------------------------- //
	
	@Override
	public UUID getId(SkullMeta meta)
	{
		Object gameProfile = getGameProfile(meta);
		if (gameProfile == null) return null;
		return getGameProfileId(gameProfile);
	}
	
	@Override
	public void set(@NotNull SkullMeta meta, String name, UUID id)
	{
		final Object gameProfile = createGameProfile(id, name);
		setGameProfile(meta, gameProfile);
	}
	
	// -------------------------------------------- //
	// GAMEPROFILE
	// -------------------------------------------- //
	
	@Override
	public <T> T createGameProfile(UUID id, String name) {
		return ReflectionUtil.invokeConstructor(this.constructorGameProfile, id, name);
	}
	
	@Override
	public <T> T getGameProfile(SkullMeta meta)
	{
		return ReflectionUtil.getField(this.fieldCraftMetaSkullProfile, meta);
	}
	
	@Override
	public void setGameProfile(@NotNull SkullMeta meta, Object gameProfile)
	{
		ReflectionUtil.setField(this.fieldCraftMetaSkullProfile, meta, gameProfile);
	}
	
	// -------------------------------------------- //
	// GAMEPROFILE > GET
	// -------------------------------------------- //
	
	@Override
	public String getGameProfileName(Object gameProfile)
	{
		return ReflectionUtil.getField(this.fieldGameProfileName, gameProfile);
	}
	
	@Override
	public UUID getGameProfileId(Object gameProfile)
	{
		return ReflectionUtil.getField(this.fieldGameProfileId, gameProfile);
	}
	
	// -------------------------------------------- //
	// GAMEPROFILE > SET
	// -------------------------------------------- //
	
	protected void setGameProfileName(Object gameProfile, String name)
	{
		ReflectionUtil.setField(this.fieldGameProfileName, gameProfile, name);
	}
	
	protected void setGameProfileId(Object gameProfile, UUID id)
	{
		ReflectionUtil.setField(this.fieldGameProfileId, gameProfile, id);
	}
	
	// -------------------------------------------- //
	// GAMEPROFILE > PROPERTIES
	// -------------------------------------------- //
	
	@Override
	public <T> T getPropertyMap(Object profile)
	{
		if (profile == null) return (T) this.createPropertyMap();
		return ReflectionUtil.getField(this.fieldGameProfilePropertyMap, profile);
	}
	
	@Override
	public void setPropertyMap(Object profile, Object propertyMap)
	{
		ReflectionUtil.setField(this.fieldGameProfilePropertyMap, profile, propertyMap);
	}
	
	// -------------------------------------------- //
	// PROPERTYMAP >
	// -------------------------------------------- //
	
	private Collection<Map.Entry<String, ?>> getPropertiesFromPropertyMap(Object propertyMap)
	{
		return ReflectionUtil.invokeMethod(this.methodPropertyMapEntries, propertyMap);
	}
	
	// -------------------------------------------- //
	// PROPERTY > GET
	// -------------------------------------------- //
	
	@Override
	public Collection<Map.Entry<String, ContainerGameProfileProperty>> getGameProfileProperties(Object propertyMap)
	{
		Collection<Map.Entry<String, ?>> inner = this.getPropertiesFromPropertyMap(propertyMap);
		if (inner.isEmpty()) return Collections.emptyList();
		
		Collection<Map.Entry<String, ContainerGameProfileProperty>> ret = new MassiveList<>();
		for (Map.Entry<String, ?> entry : inner)
		{
			Object propertyObject = entry.getValue();
			ContainerGameProfileProperty propertyTriple = unsafePropertyToContainer(propertyObject);
			
			ret.add(Couple.valueOf(entry.getKey(), propertyTriple));
		}
		return ret;
	}
	
	private @NotNull ContainerGameProfileProperty unsafePropertyToContainer(Object property)
	{
		String name = ReflectionUtil.getField(this.fieldPropertyName, property);
		String value = ReflectionUtil.getField(this.fieldPropertyValue, property);
		String signature = ReflectionUtil.getField(this.fieldPropertySignature, property);
		ContainerGameProfileProperty ret = new ContainerGameProfileProperty();
		ret.name = name;
		ret.value = value;
		ret.signature = signature;
		return ret;
	}
	
	// -------------------------------------------- //
	// PROPERTYMAP > SET
	// -------------------------------------------- //
	
	@Override
	public Object createPropertyMap()
	{
		return ReflectionUtil.invokeConstructor(this.constructorPropertyMap);
	}
	
	@Override
	public void setGameProfileProperties(@NotNull Object propertyMap, @NotNull Collection<Map.Entry<String, ContainerGameProfileProperty>> properties)
	{
		clearPropertyMap(propertyMap);
		for (Map.Entry<String, ContainerGameProfileProperty> entry : properties)
		{
			// Create the property objects
			Object newPropertyObject = containerToProperty(entry.getValue());
			
			// Add the property objects to propertyMap
			putPropertyInMap(propertyMap, entry.getKey(), newPropertyObject);
		}
	}
	
	public void clearPropertyMap(@NotNull Object propertyMap)
	{
		ReflectionUtil.invokeMethod(this.methodPropertyMapClear, propertyMap);
	}
	
	public void putPropertyInMap(@NotNull Object propertyMap, String key, Object value)
	{
		ReflectionUtil.invokeMethod(this.methodPropertyMapPut, propertyMap, key, value);
	}
	
	private <T> @NotNull T containerToProperty(@NotNull ContainerGameProfileProperty prop)
	{
		T ret = ReflectionUtil.invokeConstructor(
			this.constructorProperty,
			null,
			null,
			null
		);
		ReflectionUtil.setField(this.fieldPropertyName, ret, prop.name);
		ReflectionUtil.setField(this.fieldPropertyValue, ret, prop.value);
		ReflectionUtil.setField(this.fieldPropertySignature, ret, prop.signature);
		return ret;
	}
	
}
