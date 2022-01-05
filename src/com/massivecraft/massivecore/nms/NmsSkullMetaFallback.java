package com.massivecraft.massivecore.nms;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class NmsSkullMetaFallback extends NmsSkullMeta
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static NmsSkullMetaFallback i = new NmsSkullMetaFallback();
	public static NmsSkullMetaFallback get () { return i; }
	
	// -------------------------------------------- //
	// RAW
	// -------------------------------------------- //
	
	@Override
	public UUID getId(SkullMeta meta)
	{
		OfflinePlayer offlinePlayer = meta.getOwningPlayer();
		if (offlinePlayer == null) return null;
		return offlinePlayer.getUniqueId();
	}
	
	@Override
	public void set(@NotNull SkullMeta meta, String name, UUID id)
	{
		meta.setOwningPlayer(Bukkit.getOfflinePlayer(id));
	}
	
}
