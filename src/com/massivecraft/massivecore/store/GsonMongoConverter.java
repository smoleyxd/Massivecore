package com.massivecraft.massivecore.store;

import com.massivecraft.massivecore.xlib.bson.Document;
import com.massivecraft.massivecore.xlib.bson.types.ObjectId;
import com.massivecraft.massivecore.xlib.gson.JsonArray;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonNull;
import com.massivecraft.massivecore.xlib.gson.JsonObject;
import com.massivecraft.massivecore.xlib.gson.JsonPrimitive;
import com.massivecraft.massivecore.xlib.gson.internal.LazilyParsedNumber;
import com.massivecraft.massivecore.xlib.mongodb.BasicDBList;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Map.Entry;

public final class GsonMongoConverter
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	public static final String DOT_NORMAL = ".";
	public static final String DOT_MONGO = "<dot>";
	
	public static final String DOLLAR_NORMAL = "$";
	public static final String DOLLAR_MONGO = "<dollar>";
	
	// -------------------------------------------- //
	// GSON 2 MONGO
	// -------------------------------------------- //
	
	public static String gson2MongoKey(String key)
	{
		key = StringUtils.replace(key, DOT_NORMAL, DOT_MONGO);
		key = StringUtils.replace(key, DOLLAR_NORMAL, DOLLAR_MONGO);
		return key;
	}
	
	@Contract("_, _ -> param2")
	public static Document gson2MongoObject(@NotNull JsonElement inElement, Document out)
	{
		JsonObject in = inElement.getAsJsonObject();
		for (Entry<String, JsonElement> entry : in.entrySet())
		{
			String key = gson2MongoKey(entry.getKey());
			JsonElement val = entry.getValue();
			if (val.isJsonArray())
			{
				out.put(key, gson2MongoArray(val));
			}
			else if (val.isJsonObject())
			{
				out.put(key, gson2MongoObject(val));
			}
			else
			{
				out.put(key, gson2MongoPrimitive(val));
			}
		}
		return out;
	}
	
	@Contract("_ -> new")
	public static Document gson2MongoObject(@NotNull JsonElement inElement)
	{
		return gson2MongoObject(inElement, new Document());
	}
	
	public static @NotNull BasicDBList gson2MongoArray(@NotNull JsonElement inElement)
	{
		JsonArray in = inElement.getAsJsonArray();
		BasicDBList out = new BasicDBList();
		for (int i = 0; i < in.size(); i++)
		{
			JsonElement element = in.get(i);
			if (element.isJsonArray())
			{
				out.add(gson2MongoArray(element));
			}
			else if (element.isJsonObject())
			{
				out.add(gson2MongoObject(element));
			}
			else
			{ 
				out.add(gson2MongoPrimitive(element));
			}
		}
		return out;
	}
	
	public static @Nullable Object gson2MongoPrimitive(@NotNull JsonElement inElement)
	{
		if (inElement.isJsonNull()) return null;
		JsonPrimitive in = inElement.getAsJsonPrimitive();
		
		if (in.isBoolean())
		{
			return in.getAsBoolean();
		}
		
		if (in.isNumber())
		{
			Number number = in.getAsNumber();
			boolean floating;
			
			if (number instanceof LazilyParsedNumber)
			{
				floating = StringUtils.contains(number.toString(), '.');
			}
			else
			{
				floating = (number instanceof Double || number instanceof Float);
			}
			
			if (floating)
			{
				return number.doubleValue();
			}
			else
			{
				return number.longValue();
			}
			
		}
		
		if (in.isString())
		{
			return in.getAsString();
		}
		
		throw new IllegalArgumentException("Unsupported value type for: " + in);
	}
	
	// -------------------------------------------- //
	// MONGO 2 GSON
	// -------------------------------------------- //
	
	public static String mongo2GsonKey(String key)
	{
		key = StringUtils.replace(key, DOT_MONGO, DOT_NORMAL);
		key = StringUtils.replace(key, DOLLAR_MONGO, DOLLAR_NORMAL);
		return key;
	}
	
	public static @NotNull JsonObject mongo2GsonObject(@NotNull Document in)
	{
		//if (!(inObject instanceof BasicDBObject)) throw new IllegalArgumentException("Expected BasicDBObject as argument type!");
		//BasicDBObject in = (BasicDBObject)inObject;
		
		JsonObject jsonObject = new JsonObject();
		for (Entry<String, Object> entry : in.entrySet())
		{
			String key = mongo2GsonKey(entry.getKey());
			Object val = entry.getValue();
			if (val instanceof ArrayList)
			{
				jsonObject.add(key, mongo2GsonArray((ArrayList)val));
			}
			else if (val instanceof Document)
			{
				jsonObject.add(key, mongo2GsonObject((Document) val));
			}
			else
			{
				jsonObject.add(key, mongo2GsonPrimitive(val));
			}
		}
		return jsonObject;
	}
	
	public static @NotNull JsonArray mongo2GsonArray(@NotNull ArrayList in)
	{
		
		JsonArray jsonArray = new JsonArray();
		for (Object object : in)
		{
			if (object instanceof ArrayList)
			{
				jsonArray.add(mongo2GsonArray((ArrayList) object));
			}
			else if (object instanceof Document)
			{
				jsonArray.add(mongo2GsonObject((Document) object));
			}
			else
			{ 
				jsonArray.add(mongo2GsonPrimitive(object));
			}
		}
		return jsonArray;
	}

	public static JsonElement mongo2GsonPrimitive(Object inObject)
	{
		if (inObject == null) return JsonNull.INSTANCE;
		if (inObject instanceof Boolean) return new JsonPrimitive((Boolean) inObject);
		if (inObject instanceof Number) return new JsonPrimitive((Number) inObject);
		if (inObject instanceof String) return new JsonPrimitive((String) inObject);
		if (inObject instanceof Character) return new JsonPrimitive((Character) inObject);
		if (inObject instanceof ObjectId) return new JsonPrimitive(inObject.toString());
		throw new IllegalArgumentException("Unsupported value type for: " + inObject);
	}
}
