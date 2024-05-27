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
		NmsPersistentData120R3.class
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
	
	public String getPersistentData(@NotNull PersistentDataContainer persistentDataContainer) {
		return null;
	}
	
	public void setPersistentData(@NotNull PersistentDataContainer persistentDataContainer, String data) {
	
	}
	
	@Deprecated
	public String mapToString(Map<String, Object> data) {
		// Temporary approach for MigratorDataItemStack007PersistentDataToString
		return null;
	}
	
}
