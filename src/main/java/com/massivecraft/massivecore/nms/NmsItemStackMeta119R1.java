package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import com.massivecraft.massivecore.util.ReflectionUtil;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftChatMessage;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NmsItemStackMeta119R1 extends NmsItemStackMeta
{
	
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackMeta119R1 i = new NmsItemStackMeta119R1();
	public static NmsItemStackMeta119R1 get() { return i; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	protected Class<?> classCraftMetaItem;
	
	protected Field fieldCraftMetaItem_displayName;
	protected Field fieldCraftMetaItem_lore;
	
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		this.classCraftMetaItem = PackageType.CRAFTBUKKIT_VERSION_INVENTORY.getClass("CraftMetaItem");
		
		this.fieldCraftMetaItem_displayName = ReflectionUtil.getField(this.classCraftMetaItem, "displayName");
		this.fieldCraftMetaItem_lore = ReflectionUtil.getField(this.classCraftMetaItem, "lore");
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
		if (name.isBlank()) name = null;
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
		String json = CraftChatMessage.fromStringOrNullToJSON(message);
		if (json == null) json = "{\"text\":\"\"}";
		return json;
	}
	
	@Override
	public String fromJSONToLegacy(String jsonMessage)
	{
		return CraftChatMessage.fromJSONComponent(jsonMessage);
	}
	
}
