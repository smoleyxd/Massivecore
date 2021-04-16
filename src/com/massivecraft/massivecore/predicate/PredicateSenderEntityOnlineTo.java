package com.massivecraft.massivecore.predicate;

import com.massivecraft.massivecore.store.SenderEntity;
import com.massivecraft.massivecore.util.IdUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PredicateSenderEntityOnlineTo implements Predicate<SenderEntity>
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final String watcherId;
	public String getWatcherId() { return this.watcherId; }
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@Contract("_ -> new")
	public static @NotNull PredicateSenderEntityOnlineTo get(Object watcherObject) { return new PredicateSenderEntityOnlineTo(watcherObject); }
	public PredicateSenderEntityOnlineTo(Object watcherObject)
	{
		this.watcherId = IdUtil.getId(watcherObject);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public boolean apply(@NotNull SenderEntity watchee)
	{
		return watchee.isOnline(this.getWatcherId());
	}

}
