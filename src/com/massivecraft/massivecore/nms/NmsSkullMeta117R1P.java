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

public class NmsSkullMeta117R1P extends NmsSkullMeta
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsSkullMeta117R1P i = new NmsSkullMeta117R1P();
	public static NmsSkullMeta117R1P get () { return i; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	// GameProfile(UUID id, String name);
	public Constructor<?> constructorGameProfile;
	// GameProfile#id
	public Field fieldGameProfileId;
	// GameProfile#name
	public Field fieldGameProfileName;
	// GameProfile#properties
	public Field fieldGameProfilePropertyMap;
	
	// PropertyMap()
	public Constructor<?> constructorPropertyMap;
	// PropertyMap::entries()
	public Method methodPropertyMapEntries;
	// PropertyMap::clear()
	public Method methodPropertyMapClear;
	// PropertyMap::put(K key, V value)
	public Method methodPropertyMapPut;
	
	// Property(String name, String value, String signature)
	public Constructor<?> constructorProperty;
	// Property#name
	public Field fieldPropertyName;
	// Property#value
	public Field fieldPropertyValue;
	// Property#signature
	public Field fieldPropertySignature;
	
	// CraftMetaSkull#profile
	public Field fieldCraftMetaSkullProfile;
	// CraftMetaSkull::setProfile
	public Method methodCraftMetaSkullSetProfile;
	
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		Class<?> classGameProfile = Class.forName("com.mojang.authlib.GameProfile");
		this.constructorGameProfile = ReflectionUtil.getConstructor(classGameProfile, UUID.class, String.class);
		this.fieldGameProfileId = ReflectionUtil.getField(classGameProfile, "id");
		this.fieldGameProfileName = ReflectionUtil.getField(classGameProfile, "name");
		this.fieldGameProfilePropertyMap = ReflectionUtil.getField(classGameProfile, "properties");
		
		Class<?> classPropertyMap = Class.forName("com.mojang.authlib.properties.PropertyMap");
		this.constructorPropertyMap = ReflectionUtil.getConstructor(classPropertyMap);
		this.methodPropertyMapEntries = ReflectionUtil.getMethod(
			ReflectionUtil.getSuperclassDeclaringMethod(classPropertyMap, true, "entries"),
			"entries"
		);
		this.methodPropertyMapClear = ReflectionUtil.getMethod(
			ReflectionUtil.getSuperclassDeclaringMethod(classPropertyMap, true, "clear"),
			"clear"
		);
		Method[] methodsDeclaredInPut = ReflectionUtil.getSuperclassDeclaringMethod(classPropertyMap, true, "put").getDeclaredMethods();
		for (Method method : methodsDeclaredInPut)
		{
			if (!method.getName().equals("put")) continue;
			
			this.methodPropertyMapPut = method;
			break;
		}
		
		Class<?> classProperty = Class.forName("com.mojang.authlib.properties.Property");
		this.constructorProperty = ReflectionUtil.getConstructor(classProperty, String.class, String.class, String.class);
		this.fieldPropertyName = ReflectionUtil.getField(classProperty, "name");
		this.fieldPropertyValue = ReflectionUtil.getField(classProperty, "value");
		this.fieldPropertySignature = ReflectionUtil.getField(classProperty, "signature");
		
		Class<?> classCraftMetaSkull = PackageType.CRAFTBUKKIT_VERSION_INVENTORY.getClass("CraftMetaSkull");
		this.fieldCraftMetaSkullProfile = ReflectionUtil.getField(classCraftMetaSkull,"profile");
		this.methodCraftMetaSkullSetProfile = ReflectionUtil.getMethod(classCraftMetaSkull,"setProfile", classGameProfile);
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
		ReflectionUtil.invokeMethod(this.methodCraftMetaSkullSetProfile, meta, gameProfile);
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
