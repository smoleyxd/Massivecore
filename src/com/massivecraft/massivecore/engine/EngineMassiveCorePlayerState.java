package com.massivecraft.massivecore.engine;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.PlayerState;
import com.massivecraft.massivecore.event.EventMassiveCorePlayerLeave;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class EngineMassiveCorePlayerState extends Engine
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final EngineMassiveCorePlayerState i = new EngineMassiveCorePlayerState();
	@Contract(pure = true)
	public static EngineMassiveCorePlayerState get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void setActiveInner(boolean active)
	{
		if ( ! active) return;
		
		this.idToState.clear();
		for (Player player : MUtil.getOnlinePlayers())
		{
			this.idToState.put(player.getUniqueId(), PlayerState.JOINED);
		}
	}
	
	// -------------------------------------------- //
	// STATE STORAGE
	// -------------------------------------------- //
	
	private final Map<UUID, PlayerState> idToState = new ConcurrentHashMap<>();

	@Contract("null -> fail")
	public PlayerState getState(Player player)
	{
		if (player == null) throw new NullPointerException("player");
		if (MUtil.isntPlayer(player)) return PlayerState.JOINED;
		UUID id = player.getUniqueId();
		return this.getState(id);
	}

	@Contract("null -> fail")
	public PlayerState getState(UUID id)
	{
		if (id == null) throw new NullPointerException("id");
		PlayerState ret = this.idToState.get(id);
		if (ret == null) ret = PlayerState.LEFT;
		return ret;
	}
	
	public void setState(Player player, PlayerState state, boolean delayed, PlayerState replaceable)
	{
		if (player == null) throw new NullPointerException("player");
		if (MUtil.isntPlayer(player)) return;
		UUID id = player.getUniqueId();
		this.setState(id, state, delayed, replaceable);
	}
	
	public void setState(final UUID id, final PlayerState state, boolean delayed, final PlayerState replaceable)
	{
		// Delayed!
		if (delayed)
		{
			Bukkit.getScheduler().runTask(this.getPlugin(), () -> EngineMassiveCorePlayerState.this.setState(id, state, false, replaceable));
			return;
		}
		
		// Immediately!
		
		// Before
		PlayerState before = this.idToState.get(id);
		if (before == null) before = PlayerState.LEFT;
		
		// After
		PlayerState after = state;
		if (after == null) after = PlayerState.LEFT;
		
		// NoChange
		if (before == after) return;
		
		// Not Replaceable
		if (replaceable != null && replaceable != before) return;
		
		// Perform
		if (after != PlayerState.LEFT)
		{
			this.idToState.put(id, after);
		}
		else
		{
			this.idToState.remove(id);
		}
	}
	
	// -------------------------------------------- //
	// LOGASYNC
	// -------------------------------------------- //
	// AsyncPlayerPreLoginEvent: LOWEST and MONITOR
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void logasync(@NotNull AsyncPlayerPreLoginEvent event)
	{
		UUID id = event.getUniqueId();
		PlayerState state = PlayerState.LOGASYNC;
		PlayerState replaceable = PlayerState.LEFT;
		this.setState(id, state, false, replaceable);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void logasyncMonitor(@NotNull AsyncPlayerPreLoginEvent event)
	{
		// If the player was denied entrance they are now offline.
		if (event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) return;
		
		UUID id = event.getUniqueId();
		PlayerState state = PlayerState.LEFT;
		PlayerState replaceable = PlayerState.LOGASYNC;
		this.setState(id, state, false, replaceable);
	}
	
	// -------------------------------------------- //
	// LOGSYNC
	// -------------------------------------------- //
	// PlayerLoginEvent: LOWEST and MONITOR DELAYED
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void logsync(@NotNull PlayerLoginEvent event)
	{
		Player player = event.getPlayer();
		PlayerState state = PlayerState.LOGSYNC;
		this.setState(player, state, false, null);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void logsyncMonitor(@NotNull PlayerLoginEvent event)
	{
		// If the player was denied entrance they are now offline.
		if (event.getResult() == PlayerLoginEvent.Result.ALLOWED) return;
		
		Player player = event.getPlayer();
		PlayerState state = PlayerState.LEFT;
		PlayerState replaceable = PlayerState.LOGSYNC;
		this.setState(player, state, true, replaceable);
	}
	
	// -------------------------------------------- //
	// JOINING
	// -------------------------------------------- //
	// PlayerJoinEvent: LOWEST
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void joining(@NotNull PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		PlayerState state = PlayerState.JOINING;
		this.setState(player, state, false, null);
	}
	
	// -------------------------------------------- //
	// JOINED
	// -------------------------------------------- //
	// PlayerJoinEvent: MONITOR DELAYED
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void joined(@NotNull PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		PlayerState state = PlayerState.JOINED;
		PlayerState replaceable = PlayerState.JOINING;
		this.setState(player, state, true, replaceable);
	}
	
	// -------------------------------------------- //
	// LEAVING
	// -------------------------------------------- //
	// EventMassiveCorePlayerLeave: LOWEST
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void leaving(@NotNull EventMassiveCorePlayerLeave event)
	{
		Player player = event.getPlayer();
		PlayerState state = PlayerState.LEAVING;
		this.setState(player, state, false, null);
	}
	
	// -------------------------------------------- //
	// LEFT
	// -------------------------------------------- //
	// EventMassiveCorePlayerLeave: MONITOR DELAYED
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void left(@NotNull EventMassiveCorePlayerLeave event)
	{
		Player player = event.getPlayer();
		PlayerState state = PlayerState.LEFT;
		PlayerState replaceable = PlayerState.LEAVING;
		this.setState(player, state, true, replaceable);
	}
	
}
