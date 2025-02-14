package com.massivecraft.massivecore.adapter;

import com.massivecraft.massivecore.store.EntityInternal;
import com.massivecraft.massivecore.store.EntityInternalMap;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializer;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonNull;
import com.massivecraft.massivecore.xlib.gson.JsonObject;
import com.massivecraft.massivecore.xlib.gson.JsonParseException;
import com.massivecraft.massivecore.xlib.gson.JsonSerializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonSerializer;
import org.jetbrains.annotations.Contract;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;

public class AdapterEntityInternalMap implements JsonDeserializer<EntityInternalMap<?>>, JsonSerializer<EntityInternalMap<?>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final AdapterEntityInternalMap i = new AdapterEntityInternalMap();
	@Contract(pure = true)
	public static AdapterEntityInternalMap get() { return i; }

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public JsonElement serialize(EntityInternalMap<?> src, Type type, JsonSerializationContext context)
	{
		// NULL
		if (src == null) return JsonNull.INSTANCE;

		// Get value
		Map<String, ?> contents = src.getIdToEntity();

		// Return Ret
		return context.serialize(contents);
	}
	
	@Override
	public EntityInternalMap<?> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
	{
		// NULL
		if (json == null) return null;
		if (json instanceof JsonNull) return null;

		// Get type
		Class<? extends EntityInternal> entityType = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];

		// Create ret
		EntityInternalMap ret = new EntityInternalMap<>(entityType);

		// Fill ret
		JsonObject jsonObject = (JsonObject) json;
		for (Entry<String, JsonElement> entry : jsonObject.entrySet())
		{
			String id = entry.getKey();
			JsonElement value = entry.getValue();

			EntityInternal obj = context.deserialize(value, entityType);

			ret.getIdToEntityRaw().put(id, obj);
		}

		// Return ret
		return ret;
	}
	
}
