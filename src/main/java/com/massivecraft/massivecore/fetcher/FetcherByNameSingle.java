package com.massivecraft.massivecore.fetcher;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * Many thanks to evilmidget38!
 * This utility class is based on his work.
 * http://forums.bukkit.org/threads/player-name-uuid-fetcher.250926/
 */
public class FetcherByNameSingle implements Callable<Map<String, IdAndName>>
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	public final static int PROFILES_PER_REQUEST = 100;
	public final static String PROFILE_URL = "https://api.mojang.com/profiles/minecraft";
	
	public final static String KEY_ID = "id";
	public final static String KEY_NAME = "name";
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final Collection<String> names;
	private final boolean rateLimiting;
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	@Contract(pure = true)
	public FetcherByNameSingle(Collection<String> names)
	{
		this(names, true);
	}
	
	@Contract(pure = true)
	public FetcherByNameSingle(Collection<String> names, boolean rateLimiting)
	{
		this.names = names;
		this.rateLimiting = rateLimiting;
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public Map<String, IdAndName> call() throws Exception
	{
		return fetch(this.names, this.rateLimiting);
	}
	
	// -------------------------------------------- //
	// STATIC
	// -------------------------------------------- //
	
	public static @NotNull Map<String, IdAndName> fetch(Collection<String> namesCollection) throws Exception
	{
		return fetch(namesCollection, true);
	}
	
	public static @NotNull Map<String, IdAndName> fetch(Collection<String> namesCollection, boolean rateLimiting) throws Exception
	{
		List<String> names = new ArrayList<>(namesCollection);
		Map<String, IdAndName> ret = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		JSONParser jsonParser = new JSONParser();
		
		int requests = (int) Math.ceil(names.size() / (double) PROFILES_PER_REQUEST);
		for (int i = 0; i < requests; i++)
		{
			HttpURLConnection connection = createConnection();
			String body = JSONArray.toJSONString(names.subList(i * 100, Math.min((i + 1) * 100, names.size())));
			writeBody(connection, body);
			InputStream inputStream = connection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			
			JSONArray array = (JSONArray) jsonParser.parse(inputStreamReader);
			
			inputStreamReader.close();
			inputStream.close();
			connection.disconnect();
			
			for (Object profile : array)
			{
				JSONObject jsonProfile = (JSONObject) profile;
				String id = (String) jsonProfile.get(KEY_ID);
				String name = (String) jsonProfile.get(KEY_NAME);
				UUID uuid = getUUID(id);
				ret.put(name, new IdAndName(uuid, name));
			}
			
			if (rateLimiting && i != requests - 1)
			{
				Thread.sleep(100L);
			}
		}
		
		for (String name : names)
		{
			IdAndName idAndName = ret.get(name);
			if (idAndName == null) idAndName = new IdAndName(null, name);
			ret.put(name, idAndName);
		}

		return ret;
	}
	
	private static void writeBody(@NotNull HttpURLConnection connection, @NotNull String body) throws Exception
	{
		OutputStream stream = connection.getOutputStream();
		stream.write(body.getBytes());
		stream.flush();
		stream.close();
	}
	
	private static @NotNull HttpURLConnection createConnection() throws Exception
	{
		URL url = new URL(PROFILE_URL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setConnectTimeout(15000);
		connection.setReadTimeout(15000);
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		return connection;
	}
	
	private static @NotNull UUID getUUID(@NotNull String id)
	{
		return UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" +id.substring(20, 32));
	}
	
	public static byte @NotNull [] toBytes(@NotNull UUID uuid)
	{
		ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
		byteBuffer.putLong(uuid.getMostSignificantBits());
		byteBuffer.putLong(uuid.getLeastSignificantBits());
		return byteBuffer.array();
	}

	@Contract("_ -> new")
	public static @NotNull UUID fromBytes(byte @NotNull [] array)
	{
		if (array.length != 16)
		{
			throw new IllegalArgumentException("Illegal byte array length: " + array.length);
		}
		ByteBuffer byteBuffer = ByteBuffer.wrap(array);
		long mostSignificant = byteBuffer.getLong();
		long leastSignificant = byteBuffer.getLong();
		return new UUID(mostSignificant, leastSignificant);
	}

	public static IdAndName get(String name) throws Exception
	{
		return fetch(Collections.singletonList(name)).get(name);
	}
	
}
