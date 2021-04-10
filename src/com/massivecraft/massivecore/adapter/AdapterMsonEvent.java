package com.massivecraft.massivecore.adapter;

import com.massivecraft.massivecore.mson.MsonEvent;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializer;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonParseException;
import com.massivecraft.massivecore.xlib.gson.JsonSerializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonSerializer;
import org.jetbrains.annotations.Contract;

import java.lang.reflect.Type;

public class AdapterMsonEvent implements JsonDeserializer<MsonEvent>, JsonSerializer<MsonEvent>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static final AdapterMsonEvent i = new AdapterMsonEvent();
	@Contract(pure = true)
	public static AdapterMsonEvent get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public JsonElement serialize(MsonEvent src, Type typeOfSrc, JsonSerializationContext context)
	{
		return MsonEvent.toJson(src);
	}

	@Override
	public MsonEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		return MsonEvent.fromJson(json);
	}
	
}
