package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.MassiveCore;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.bukkit.craftbukkit.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.util.CraftNBTTagConfigSerializer;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.logging.Level;

public class NmsPersistentData121R1P extends NmsPersistentData
{
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsPersistentData121R1P i = new NmsPersistentData121R1P();
	public static NmsPersistentData121R1P get () { return i; }
	
	// -------------------------------------------- //
	// CREATE
	// -------------------------------------------- //
	
	@Override
	public String getPersistentData(@NotNull PersistentDataContainer persistentDataContainer) {
		if (!(persistentDataContainer instanceof CraftPersistentDataContainer craftPersistentDataContainer)) {
			MassiveCore.get().log(Level.WARNING, "Failed to getPersistentData - Not CraftPDC");
			return null;
		}
		
		if (craftPersistentDataContainer.isEmpty()) return null;
		
		return craftPersistentDataContainer.serialize();
	}
	
	@Override
	public void setPersistentData(@NotNull PersistentDataContainer persistentDataContainer, String data) {
		if (!(persistentDataContainer instanceof CraftPersistentDataContainer craftPersistentDataContainer)) {
			MassiveCore.get().log(Level.WARNING, "Failed to setPersistentData - Not CraftPDC");
			return;
		}
		
		if (data == null) {
			craftPersistentDataContainer.clear();
			return;
		}
		
		Tag deserialized = CraftNBTTagConfigSerializer.deserialize(data);
		if (deserialized instanceof CompoundTag compoundTag) craftPersistentDataContainer.putAll(compoundTag);
	}
	
	@Override
	@Deprecated
	@SuppressWarnings("deprecation")
	public String mapToString(Map<String, Object> data) {
		Tag deserialized = CraftNBTTagConfigSerializer.deserialize(data);
		if (deserialized instanceof CompoundTag compoundTag) return CraftNBTTagConfigSerializer.serialize(compoundTag);
		
		MassiveCore.get().log(Level.WARNING, "Failed to setPersistentData - Deserialized Tag is not CompoundTag");
		return null;
	}
	
}
