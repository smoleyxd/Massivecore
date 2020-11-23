package com.massivecraft.massivecore.adapter;

import com.massivecraft.massivecore.item.ConverterFromNamespacedKey;
import com.massivecraft.massivecore.item.ConverterToNamespacedKey;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializer;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonNull;
import com.massivecraft.massivecore.xlib.gson.JsonParseException;
import com.massivecraft.massivecore.xlib.gson.JsonPrimitive;
import com.massivecraft.massivecore.xlib.gson.JsonSerializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonSerializer;
import org.bukkit.NamespacedKey;

import java.lang.reflect.Type;

public class AdapterNamespacedKey implements JsonDeserializer<NamespacedKey>, JsonSerializer<NamespacedKey>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static final AdapterNamespacedKey i = new AdapterNamespacedKey();
	public static AdapterNamespacedKey get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public JsonElement serialize(NamespacedKey src, Type typeOfSrc, JsonSerializationContext context)
	{
		if (src == null) return JsonNull.INSTANCE;
		return new JsonPrimitive(ConverterFromNamespacedKey.get().convert(src));
	}

	@Override
	public NamespacedKey deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		if (json == null) return null;
		if (json.equals(JsonNull.INSTANCE)) return null;
		return ConverterToNamespacedKey.get().convert(json.getAsString());
	}

}
