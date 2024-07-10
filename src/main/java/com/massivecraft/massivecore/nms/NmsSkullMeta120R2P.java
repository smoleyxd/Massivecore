package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.Couple;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.item.ContainerGameProfileProperty;
import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import com.massivecraft.massivecore.util.ReflectionUtil;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class NmsSkullMeta120R2P extends NmsSkullMeta
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsSkullMeta120R2P i = new NmsSkullMeta120R2P();
	public static NmsSkullMeta120R2P get() { return i; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	// GameProfile#properties
	public Field fieldGameProfilePropertyMap;
	
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
		this.fieldGameProfilePropertyMap = ReflectionUtil.getField(GameProfile.class, "properties");
		
		Class<?> classCraftMetaSkull = PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftMetaSkull");
		this.fieldCraftMetaSkullProfile = ReflectionUtil.getField(classCraftMetaSkull, "profile");
		this.methodCraftMetaSkullSetProfile = ReflectionUtil.getMethod(classCraftMetaSkull, "setProfile", GameProfile.class);
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
	public GameProfile createGameProfile(UUID id, String name)
	{
		return new GameProfile(id, name);
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
	public String getGameProfileName(Object profile)
	{
		if (profile instanceof GameProfile gameProfile) return gameProfile.getName();
		return null;
	}
	
	@Override
	public UUID getGameProfileId(Object profile)
	{
		if (profile instanceof GameProfile gameProfile) return gameProfile.getId();
		return null;
	}
	
	// -------------------------------------------- //
	// GAMEPROFILE > PROPERTIES
	// -------------------------------------------- //
	
	@Override
	public PropertyMap getPropertyMap(Object profile)
	{
		if (!(profile instanceof GameProfile gameProfile))
			return new PropertyMap();
		
		return gameProfile.getProperties();
	}
	
	@Override
	public void setPropertyMap(Object profile, Object propertyMap)
	{
		if (!(profile instanceof GameProfile gameProfile))
			throw new IllegalArgumentException("profile provided is not an Authlib GameProfile");
		
		ReflectionUtil.setField(this.fieldGameProfilePropertyMap, gameProfile, propertyMap);
	}
	
	// -------------------------------------------- //
	// PROPERTY > GET
	// -------------------------------------------- //
	
	@Override
	public Collection<Map.Entry<String, ContainerGameProfileProperty>> getGameProfileProperties(Object propertyMap)
	{
		if (!(propertyMap instanceof PropertyMap map))
			throw new IllegalArgumentException("propertyMap provided is not an Authlib PropertyMap");
		
		Collection<Map.Entry<String, ContainerGameProfileProperty>> ret = new MassiveList<>();
		for (Map.Entry<String, Property> entry : map.entries())
		{
			ret.add(Couple.valueOf(
				entry.getKey(),
				unsafePropertyToContainer(entry.getValue())
			));
		}
		
		return ret;
	}
	
	private @NotNull ContainerGameProfileProperty unsafePropertyToContainer(Property property)
	{
		ContainerGameProfileProperty ret = new ContainerGameProfileProperty();
		ret.name = property.name();
		ret.value = property.value();
		ret.signature = property.signature();
		
		return ret;
	}
	
	// -------------------------------------------- //
	// PROPERTYMAP > SET
	// -------------------------------------------- //
	
	@Override
	public Object createPropertyMap()
	{
		return new PropertyMap();
	}
	
	@Override
	public void setGameProfileProperties(@NotNull Object propertyMap, @NotNull Collection<Map.Entry<String, ContainerGameProfileProperty>> properties)
	{
		if (!(propertyMap instanceof PropertyMap map)) return;
		
		map.clear();
		
		for (Map.Entry<String, ContainerGameProfileProperty> entry : properties)
		{
			ContainerGameProfileProperty prop = entry.getValue();
			// Add the property objects to propertyMap
			map.put(
				entry.getKey(),
				new Property(
					prop.name,
					prop.value,
					prop.signature
				)
			);
		}
	}
}
