package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.nms.NmsSkullMeta;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.Map;

// converterto returns fb
// converterfrom returns fa
// Converters go between fa and fb
// fa is List<Map.Entry<String, ContainerGameProfileProperty>>
// fb is Object, representing the propertymap
// TODO get converters for between the object types
// field writing from should be List<Map.Entry<String, ContainerGameProfileProperty>>
// field writing to should be Object, representing the propertymap
// dataitemstack, itemmeta, dataitemstack, skullmeta, field writing from, field writing to, itemstack
public class WriterItemStackMetaSkullProperties extends WriterAbstractItemStackMetaField<SkullMeta, List<Map.Entry<String, ContainerGameProfileProperty>>, Object>
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
		this.setConverterFrom(new Converter<Object, List<Map.Entry<String, ContainerGameProfileProperty>>>()
		{
			@Override
			public List<Map.Entry<String, ContainerGameProfileProperty>> convert(Object fb)
			{
				return dataItemStackFieldToGameProfilePropertyMap(fb);
			}
		});
		this.setConverterTo(new Converter<List<Map.Entry<String, ContainerGameProfileProperty>>, Object>()
		{
			@Override
			public Object convert(List<Map.Entry<String, ContainerGameProfileProperty>> fa)
			{
				return propertyMapObjectFromDataItemStackField(fa);
			}
		});
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	// this should get the List<Map.Entry<String, ContainerGameProfileProperty>> from ca
	@Override
	public List<Map.Entry<String, ContainerGameProfileProperty>> getA(DataItemStack ca, ItemStack d)
	{
		return ca.getGameProfileProperties();
	}

	// fa should be the List<Map.Entry<String, ContainerGameProfileProperty>>
	@Override
	public void setA(DataItemStack ca, List<Map.Entry<String, ContainerGameProfileProperty>> fa, ItemStack d)
	{
		ca.setGameProfileProperties(fa);
	}

	// This should be getting the propertymap from the game profile
	@Override
	public Object getB(SkullMeta cb, ItemStack d)
	{
		NmsSkullMeta nms = NmsSkullMeta.get();
		Object gameProfile = nms.getGameProfile(cb);
		return nms.getPropertyMap(gameProfile);
	}

	// This should set the game propertymap for the game profile
	// fb is the propertymap object
	@Override
	public void setB(SkullMeta cb, Object fb, ItemStack d)
	{
		NmsSkullMeta nms = NmsSkullMeta.get();
		
		Object objectGameProfile = nms.getGameProfile(cb);
		nms.setPropertyMap(objectGameProfile, fb);
	}
	
	private Object propertyMapObjectFromDataItemStackField(List<Map.Entry<String, ContainerGameProfileProperty>> fa)
	{
		NmsSkullMeta nms = NmsSkullMeta.get();
		Object propertyMap = nms.createPropertyMap();
		nms.setGameProfileProperties(propertyMap, fa);
		return propertyMap;
	}
	
	private List<Map.Entry<String, ContainerGameProfileProperty>> dataItemStackFieldToGameProfilePropertyMap(Object gameprofilePropertyMap)
	{
		NmsSkullMeta nms = NmsSkullMeta.get();
		return new MassiveList<>(nms.getGameProfileProperties(gameprofilePropertyMap));
	}
	
}
