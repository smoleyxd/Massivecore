package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.mixin.Mixin;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class NmsItemStackMeta extends Mixin
{
	// -------------------------------------------- //
	// DEFAULT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackMeta d = new NmsItemStackMeta().setAlternatives(
		NmsItemStackMeta119R1.class,
		NmsItemStackMetaFallback.class
	);
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackMeta i = d;
	public static NmsItemStackMeta get() { return i; }
	
	// -------------------------------------------- //
	// ITEM UTILS
	// -------------------------------------------- //
	
	public String getDisplayName(ItemMeta itemMeta)
	{
		return itemMeta.getDisplayName();
	}
	
	public void setDisplayName(ItemMeta itemMeta, String name)
	{
		itemMeta.setDisplayName(name);
	}
	
	public List<String> getLore(ItemMeta itemMeta)
	{
		return itemMeta.getLore();
	}
	
	public void setLore(ItemMeta itemMeta, List<String> lore)
	{
		itemMeta.setLore(lore);
	}
	
	// -------------------------------------------- //
	// JSON / LEGACY UTILS
	// -------------------------------------------- //
	
	public String fromLegacyToJSON(String message)
	{
		return message;
	}
	
	public String fromJSONToLegacy(String jsonMessage)
	{
		return jsonMessage;
	}
	
}
