package com.massivecraft.massivecore.store;

import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.util.DiscUtil;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonObject;
import com.massivecraft.massivecore.xlib.gson.JsonParser;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DriverFlatfile extends DriverAbstract
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	private static final String DOTJSON = ".json";
	public static final String NAME = "flatfile";
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final DriverFlatfile i = new DriverFlatfile();
	public static DriverFlatfile get() { return i; }
	private DriverFlatfile() { super(NAME); }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
		
	@Override
	public Db getDb(String uri)
	{
		// "flatfile://" is 8+3=11 chars
		File directory = new File(uri.substring(NAME.length() + 3));
		directory.mkdirs();
		return new DbFlatfile(this, directory);
	}
	
	@Override
	public boolean dropDb(Db db)
	{
		if ( ! (db instanceof DbFlatfile dbFlatfile)) throw new IllegalArgumentException("db");
		
		try
		{
			return DiscUtil.deleteRecursive(dbFlatfile.directory);
		}
		catch (Exception e)
		{
			return false;
		}
	}
	
	@Override
	public Set<String> getCollnames(Db db)
	{
		Set<String> ret = new MassiveSet<>();
		
		for (File file : ((DbFlatfile)db).directory.listFiles())
		{
			if ( ! file.isDirectory()) continue;
			ret.add(file.getName());
		}
	
		return ret;
	}
	
	@Override
	public boolean renameColl(Db db, String from, String to)
	{
		File dir = ((DbFlatfile)db).directory;
		File fileFrom = new File(dir, from);
		File fileTo = new File(dir, to);
		return fileFrom.renameTo(fileTo);
	}
	
	@Override
	public boolean containsId(Coll<?> coll, String id)
	{
		return fileFromId(coll, id).isFile();
	}
	
	@Override
	public long getMtime(Coll<?> coll, String id)
	{
		File file = fileFromId(coll, id);
		if ( ! file.isFile()) return 0;
		return file.lastModified();
	}
	
	@Override
	public Collection<String> getIds(Coll<?> coll)
	{
		List<String> ret = new ArrayList<>();
		
		// Scan the collection folder for .json files
		File collDir = getDirectory(coll);
		if ( ! collDir.isDirectory()) return ret;
		for (File file : collDir.listFiles(JsonFileFilter.get()))
		{
			ret.add(idFromFile(file));
		}
		
		return ret;
	}
	
	@Override
	public Map<String, Long> getId2mtime(Coll<?> coll)
	{
		// Create Ret
		Map<String, Long> ret = new HashMap<>();
		
		// Get Directory
		File directory = getDirectory(coll);
		if ( ! directory.isDirectory()) return ret; // TODO: Throw exception instead?
		
		// For each .json file
		for (File file : directory.listFiles(JsonFileFilter.get()))
		{
			String id = idFromFile(file);
			long mtime = file.lastModified();
			// TODO: Check is 0 here?
			ret.put(id, mtime);
		}
		
		// Return Ret
		return ret;
	}
	
	@Override
	public Entry<JsonObject, Long> load(Coll<?> coll, String id)
	{
		File file = fileFromId(coll, id);
		return loadFile(file);
	}
	
	public static @NotNull Entry<JsonObject, Long> loadFile(@NotNull File file)
	{
		long mtime = file.lastModified();
		JsonObject raw = loadFileJsonObject(file);
		
		return new SimpleEntry<>(raw, mtime);
	}
	
	public static @Nullable JsonObject loadFileJsonObject(File file)
	{
		JsonElement ret = loadFileJson(file);
		if (ret == null) return null;
		return ret.getAsJsonObject();
	}

	public static @Nullable JsonElement loadFileJson(File file)
	{
		String content = DiscUtil.readCatch(file);
		if (content == null) return null;

		content = content.trim();
		if (content.length() == 0) return null;
		
		return JsonParser.parseString(content).getAsJsonObject();
	}
	
	@Override
	public Map<String, Entry<JsonObject, Long>> loadAll(Coll<?> coll)
	{
		// Get Directory
		File directory = getDirectory(coll);
		if ( ! directory.isDirectory()) return null;
		
		// Find All
		File[] files = directory.listFiles(JsonFileFilter.get());
		
		// Create Ret
		Map<String, Entry<JsonObject, Long>> ret = new MassiveMap<>(files.length);
		
		// For Each Found
		for (File file : files)
		{
			// Get ID
			String id = idFromFile(file);
			
			// Get Entry
			Entry<JsonObject, Long> entry = loadFile(file);
			// NOTE: The entry can be a failed one with null and 0.
			// NOTE: We add it anyways since it's an informative failure.
			// NOTE: This is supported by our defined specification.
			
			// Add
			ret.put(id, entry);
		}
		
		// Return Ret
		return ret;
	}
	
	@Override
	public long save(Coll<?> coll, String id, JsonObject data)
	{
		File file = fileFromId(coll, id);
		String content = coll.getGson().toJson(data);
		if (!DiscUtil.writeCatch(file, content)) return 0;
		return file.lastModified();
	}
	
	@Override
	public void delete(Coll<?> coll, String id)
	{
		File file = fileFromId(coll, id);
		file.delete();
	}
	
	private final boolean supportsPusher = this.supportsPusherCalc();
	private boolean supportsPusherCalc()
	{
		boolean ret = false;
		WatchService watchService = null;
		try
		{
			watchService = FileSystems.getDefault().newWatchService();
			ret = ! watchService.getClass().getName().equals("sun.nio.fs.PollingWatchService");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (watchService != null)
				{
					watchService.close();
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	@Override
	public boolean supportsPusher()
	{
		return this.supportsPusher;
	}
	
	@Override
	public PusherColl getPusher(Coll<?> coll)
	{
		try
		{
			return new PusherCollFlatfile(coll);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Failed to create a flatfile system pusher.", e);
		}
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	public static File getDirectory(@NotNull Coll<?> coll)
	{
		return (File) coll.getCollDriverObject();
	}
	
	@Contract("null -> null")
	public static String idFromFile(File file)
	{
		if (file == null) return null;
		String name = file.getName();
		return name.substring(0, name.length() - 5);
	}
	
	public static @NotNull File fileFromId(Coll<?> coll, String id)
	{
		File collDir = getDirectory(coll);
		return new File(collDir, id + DOTJSON);
	}

}
