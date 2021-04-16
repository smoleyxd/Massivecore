package com.massivecraft.massivecore.adapter;

import com.massivecraft.massivecore.xlib.gson.JsonDeserializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializer;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonParseException;
import com.massivecraft.massivecore.xlib.gson.JsonPrimitive;
import com.massivecraft.massivecore.xlib.gson.JsonSerializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonSerializer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.UUID;

public class AdapterUUID implements JsonDeserializer<UUID>, JsonSerializer<UUID>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final AdapterUUID i = new AdapterUUID();
	@Contract(pure = true)
	public static AdapterUUID get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public JsonElement serialize(@NotNull UUID src, Type typeOfSrc, JsonSerializationContext context)
	{
		return convertUUIDToJsonPrimitive(src);
	}
	
	@Override
	public UUID deserialize(@NotNull JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
	{
		return convertJsonElementToUUID(json);
	}
	
	// -------------------------------------------- //
	// STATIC LOGIC
	// -------------------------------------------- //
	
	@Contract(pure = true)
	public static String convertUUIDToString(@NotNull UUID uuid)
	{
		return uuid.toString();
	}
	
	@Contract("_ -> new")
	public static @NotNull JsonPrimitive convertUUIDToJsonPrimitive(@NotNull UUID uuid)
	{
		return new JsonPrimitive(convertUUIDToString(uuid));
	}
	
	public static @NotNull UUID convertStringToUUID(@NotNull String string)
	{
		return UUID.fromString(string);
	}
	
	public static @NotNull UUID convertJsonElementToUUID(@NotNull JsonElement jsonElement)
	{
		return convertStringToUUID(jsonElement.getAsString());
	}
	
}
