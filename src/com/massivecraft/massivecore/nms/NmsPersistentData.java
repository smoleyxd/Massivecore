package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.mixin.Mixin;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class NmsPersistentData extends Mixin
{
	// -------------------------------------------- //
	// DEFAULT
	// -------------------------------------------- //
	
	private static NmsPersistentData d = new NmsPersistentData().setAlternatives(
		NmsPersistentData116R3P.class
	);
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static NmsPersistentData i = d;
	public static NmsPersistentData get() { return i; }
	
	// -------------------------------------------- //
	// CREATE
	// -------------------------------------------- //
	
	public Map<String, Object> getPersistentData(@NotNull PersistentDataContainer persistentDataContainer) {
		return null;
	}
	
	public void setPersistentData(@NotNull PersistentDataContainer persistentDataContainer, Map<String, Object> data) {
	
	}
	
}
