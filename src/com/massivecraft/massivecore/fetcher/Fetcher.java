package com.massivecraft.massivecore.fetcher;

import com.massivecraft.massivecore.util.MUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Fetcher implements Callable<Set<IdAndName>>
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	public static final ExecutorService ES = Executors.newFixedThreadPool(100);
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final Collection<Object> objects;
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //

	@Contract(pure = true)
	public Fetcher(Collection<Object> objects)
	{
		this.objects = objects;
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public Set<IdAndName> call() throws Exception
	{
		return fetch(this.objects);
	}
	
	// -------------------------------------------- //
	// STATIC
	// -------------------------------------------- //
	
	public static @NotNull Set<IdAndName> fetch(@NotNull Collection<?> objects) throws Exception
	{
		// Separate names and ids
		final Set<String> names = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		final Set<UUID> ids = new HashSet<>();
		for (Object object : objects)
		{
			if (object instanceof UUID)
			{
				UUID id = (UUID)object;
				ids.add(id);
			}
			else if (object instanceof String)
			{
				String string = (String)object;
				if (MUtil.isValidPlayerName(string))
				{
					names.add(string);
				}
				else if (MUtil.isUuid(string))
				{
					ids.add(UUID.fromString(string));
				}
			}
		}
		
		// Create Tasks
		Callable<Set<IdAndName>> taskName = () -> new HashSet<>(new FetcherByName(names).call().values());
		
		Callable<Set<IdAndName>> taskId = () -> new HashSet<>(new FetcherById(ids).call().values());
		
		final List<Callable<Set<IdAndName>>> tasks = new ArrayList<>();
		tasks.add(taskName);
		tasks.add(taskId);
		
		// Invoke All Tasks
		List<Future<Set<IdAndName>>> futures = ES.invokeAll(tasks);
		
		// Merge Return Value
		Set<IdAndName> ret = new HashSet<>();
		for (Future<Set<IdAndName>> future : futures)
		{
			Set<IdAndName> set = future.get();
			ret.addAll(set);
		}
		
		return ret;
	}
	
}
