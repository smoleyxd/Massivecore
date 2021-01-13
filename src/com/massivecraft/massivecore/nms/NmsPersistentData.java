package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.mixin.Mixin;
import org.bukkit.persistence.PersistentDataContainer;

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
	
	public Map<String, Object> getPersistentData(PersistentDataContainer persistentDataContainer) {
		return null;
	}
	
	public void setPersistentData(PersistentDataContainer persistentDataContainer, Map<String, Object> data) {
	
	}
	
}
