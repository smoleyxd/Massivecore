package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import com.massivecraft.massivecore.util.ReflectionUtil;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class NmsItemStackMeta117R1P extends NmsItemStackMeta
{
	
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackMeta117R1P i = new NmsItemStackMeta117R1P();
	public static NmsItemStackMeta117R1P get() { return i; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	protected Class<?> classCraftMetaItem;
	
	protected Field fieldCraftMetaItem_displayName;
	protected Field fieldCraftMetaItem_lore;
	
	
	protected Class<?> classCraftChatMessage;
	
	protected Method methodCraftChatMessage_fromStringOrNullToJSON;
	protected Method methodCraftChatMessage_fromJSONComponent;
	
	
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		this.classCraftMetaItem = PackageType.CRAFTBUKKIT_VERSION_INVENTORY.getClass("CraftMetaItem");
		
		this.fieldCraftMetaItem_displayName = ReflectionUtil.getField(this.classCraftMetaItem, "displayName");
		this.fieldCraftMetaItem_lore = ReflectionUtil.getField(this.classCraftMetaItem, "lore");
		
		this.classCraftChatMessage = PackageType.CRAFTBUKKIT_VERSION_UTIL.getClass("CraftChatMessage");
		
		this.methodCraftChatMessage_fromStringOrNullToJSON = ReflectionUtil.getMethod(this.classCraftChatMessage, "fromStringOrNullToJSON", String.class);
		this.methodCraftChatMessage_fromJSONComponent = ReflectionUtil.getMethod(this.classCraftChatMessage, "fromJSONComponent", String.class);
	}
	
	// -------------------------------------------- //
	// ITEM UTILS
	// -------------------------------------------- //
	
	public String getDisplayName(ItemMeta itemMeta)
	{
		return ReflectionUtil.getField(this.fieldCraftMetaItem_displayName, itemMeta);
	}
	
	public void setDisplayName(ItemMeta itemMeta, String name)
	{
		ReflectionUtil.setField(this.fieldCraftMetaItem_displayName, itemMeta, name);
	}
	
	public List<String> getLore(ItemMeta itemMeta)
	{
		return ReflectionUtil.getField(this.fieldCraftMetaItem_lore, itemMeta);
	}
	
	public void setLore(ItemMeta itemMeta, List<String> lore)
	{
		if (lore == null || lore.isEmpty())
		{
			ReflectionUtil.setField(this.fieldCraftMetaItem_lore, itemMeta, null);
			return;
		}
		
		List<String> itemLore = ReflectionUtil.getField(this.fieldCraftMetaItem_lore, itemMeta);
		if (itemLore == null)
		{
			itemLore = new ArrayList<>(lore.size());
			ReflectionUtil.setField(this.fieldCraftMetaItem_lore, itemMeta, itemLore);
		}
		else
		{
			itemLore.clear();
		}
		
		itemLore.addAll(lore);
	}
	
	// -------------------------------------------- //
	// JSON / LEGACY UTILS
	// -------------------------------------------- //
	
	@Override
	public String fromLegacyToJSON(String message)
	{
		String json = ReflectionUtil.invokeMethod(this.methodCraftChatMessage_fromStringOrNullToJSON, null, message);
		if (json == null) json = "{\"text\":\"\"}";
		return json;
	}
	
	@Override
	public String fromJSONToLegacy(String jsonMessage)
	{
		String legacy = ReflectionUtil.invokeMethod(this.methodCraftChatMessage_fromJSONComponent, null, jsonMessage);
		if (legacy == null) legacy = "";
		return legacy;
	}
	
}
