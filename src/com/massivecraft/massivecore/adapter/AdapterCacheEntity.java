package com.massivecraft.massivecore.adapter;

import com.massivecraft.massivecore.CacheEntity;
import com.massivecraft.massivecore.store.Coll;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializer;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonNull;
import com.massivecraft.massivecore.xlib.gson.JsonParseException;
import com.massivecraft.massivecore.xlib.gson.JsonPrimitive;
import com.massivecraft.massivecore.xlib.gson.JsonSerializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonSerializer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class AdapterCacheEntity implements JsonDeserializer<CacheEntity<?,?>>, JsonSerializer<CacheEntity<?,?>>
{
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	private static final AdapterCacheEntity i = new AdapterCacheEntity();
	@Contract(pure = true)
	public static AdapterCacheEntity get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public CacheEntity<?,?> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
	{
		Type collType = getCollType(type);
		CacheEntity<?,?> ret = new CacheEntity((Class<? extends Coll<?>>) collType);
		
		if (json == null) return ret;
		if (json instanceof JsonNull) return ret;
		
		String usedId = json.getAsString();
		ret.setEntityId(usedId);
		return ret;
	}
	
	@Override
	public JsonElement serialize(CacheEntity<?,?> src, Type type, JsonSerializationContext context)
	{
		// Null
		if (src == null) return JsonNull.INSTANCE;
		if (src.getEntityId() == null) return JsonNull.INSTANCE;
		
		return new JsonPrimitive(src.getEntityId());
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	public static Type getCollType(@NotNull Type type)
	{
		return getType(type, 0);
	}
	
	public static Type getType(@NotNull Type type, int index)
	{
		ParameterizedType ptype = (ParameterizedType)type;
		Type[] types = ptype.getActualTypeArguments();
		return types[index];
	}
	
}
