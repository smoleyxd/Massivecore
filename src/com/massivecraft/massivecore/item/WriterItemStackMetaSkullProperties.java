package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.nms.NmsSkullMeta;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// TODO convert fa to ContainerGameProfile
// TODO convert fb to GameProfile as object

// converterto returns fb
// converterfrom returns fa
// Converters go between fa and fb
// fa is List<Map.Entry<String, ContainerGameProfileProperty>>
// fb is Object, representing the propertymap
// TODO get converters for between the object types
// field writing from should be List<Map.Entry<String, ContainerGameProfileProperty>>
// field writing to should be Object, representing the propertymap
// dataitemstack, itemmeta, dataitemstack, skullmeta, field writing from, field writing to, itemstack
public class WriterItemStackMetaSkullProperties extends WriterAbstractItemStackMetaField<SkullMeta, ContainerGameProfile, Object>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaSkullProperties i = new WriterItemStackMetaSkullProperties();
	public static WriterItemStackMetaSkullProperties get() { return i; }
	public WriterItemStackMetaSkullProperties()
	{
		super(SkullMeta.class);
		this.setMaterial(Material.SKULL_ITEM);
		this.setConverterFrom(new Converter<Object, ContainerGameProfile>()
		{
			@Override
			public ContainerGameProfile convert(Object fb)
			{
				return gameProfileToContainer(fb);
			}
		});
		this.setConverterTo(new Converter<ContainerGameProfile, Object>()
		{
			@Override
			public Object convert(ContainerGameProfile fa)
			{
				return dataToGameProfile(fa);
			}
		});
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	// this should get the List<Map.Entry<String, ContainerGameProfileProperty>> from ca
	@Override
	public ContainerGameProfile getA(DataItemStack ca, ItemStack d)
	{
		return ca.getContainerGameProfile();
	}

	// fa should be the List<Map.Entry<String, ContainerGameProfileProperty>>
	@Override
	public void setA(DataItemStack ca, ContainerGameProfile fa, ItemStack d)
	{
		ca.setContainerGameProfile(fa);
	}

	// This should be getting the GameProfile skull meta
	@Override
	public Object getB(SkullMeta cb, ItemStack d)
	{
		NmsSkullMeta nms = NmsSkullMeta.get();
		return nms.getGameProfile(cb);
	}

	// This should set the game profile for the skull meta
	// fb is the game profile object
	@Override
	public void setB(SkullMeta cb, Object fb, ItemStack d)
	{
		if (cb == null) throw new NullPointerException("cb");
		if (fb == null) throw new NullPointerException("fb");
		
		MassiveCore.get().log("setB: " + fb.getClass().getCanonicalName());
		NmsSkullMeta nms = NmsSkullMeta.get();
		
		nms.setGameProfile(cb, fb);
	}
	
	private Object propertyMapObjectFromDataItemStackField(List<Map.Entry<String, ContainerGameProfileProperty>> fa)
	{
		NmsSkullMeta nms = NmsSkullMeta.get();
		Object propertyMap = nms.createPropertyMap();
		if (propertyMap == null) throw new NullPointerException("propertyMap");
		nms.setGameProfileProperties(propertyMap, fa);
		return propertyMap;
	}
	
	private List<Map.Entry<String, ContainerGameProfileProperty>> dataItemStackFieldToGameProfilePropertyMap(Object gameprofilePropertyMap)
	{
		//MassiveCore.get().log("Converting propertyMap");
		if (gameprofilePropertyMap == null)
		{
			MassiveCore.get().log("Warning, propertymap to convert is null, returning empty list");
			return Collections.emptyList();
		}
		NmsSkullMeta nms = NmsSkullMeta.get();
		Collection<Map.Entry<String, ContainerGameProfileProperty>> collection = nms.getGameProfileProperties(gameprofilePropertyMap);
		
		return new MassiveList<>(collection);
	}
	
	// Returns a gameprofile object
	private Object dataToGameProfile(ContainerGameProfile container)
	{
		NmsSkullMeta nms = NmsSkullMeta.get();
		
		if (container == null) return nms.createGameProfile(null, "testing");
		String uuidRaw = container.getUuid();
		UUID uuid = uuidRaw != null ? UUID.fromString(uuidRaw) : UUID.randomUUID();
		Object gameprofile = nms.createGameProfile(uuid, null);
		Object propertyMap = nms.createPropertyMap();
		nms.setGameProfileProperties(propertyMap, container.getGameProfileProperties());
		nms.setPropertyMap(gameprofile, propertyMap);
		return gameprofile;
	}
	
	private ContainerGameProfile gameProfileToContainer(Object gameProfile)
	{
		NmsSkullMeta nms = NmsSkullMeta.get();
		
		ContainerGameProfile ret = new ContainerGameProfile();
		UUID uuid = nms.getGameProfileId(gameProfile);
		
		ret.setUuid(uuid != null ? uuid.toString() : null);
		
		Object propertyMap = nms.getPropertyMap(gameProfile);
		ret.setGameProfileProperties(new MassiveList<>(nms.getGameProfileProperties(propertyMap)));
		
		return ret;
	}
	
}
