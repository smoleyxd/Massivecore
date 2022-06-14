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
	
	private static final NmsPersistentData d = new NmsPersistentData().setAlternatives(
		NmsPersistentData117R1P.class
	);
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
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
