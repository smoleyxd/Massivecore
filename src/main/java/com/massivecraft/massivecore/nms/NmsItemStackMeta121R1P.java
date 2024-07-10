package com.massivecraft.massivecore.nms;

import com.google.common.collect.Lists;
import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import com.massivecraft.massivecore.util.ReflectionUtil;
import net.minecraft.network.chat.Component;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NmsItemStackMeta121R1P extends NmsItemStackMeta
{
	
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackMeta121R1P i = new NmsItemStackMeta121R1P();
	public static NmsItemStackMeta121R1P get() { return i; }
	
	// -------------------------------------------- //
	// JSON / LEGACY UTILS
	// -------------------------------------------- //
	
	@Override
	public String fromLegacyToJSON(String message)
	{
		Component component = CraftChatMessage.fromStringOrNull(message);
		String json = CraftChatMessage.toJSONOrNull(component);
		if (json == null) json = "{\"text\":\"\"}";
		return json;
	}
	
	@Override
	public String fromJSONToLegacy(String jsonMessage)
	{
		Component component = CraftChatMessage.fromJSONOrNull(jsonMessage);
		return CraftChatMessage.fromComponent(component);
	}
	
}
