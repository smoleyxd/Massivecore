package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.MassiveCore;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.bukkit.craftbukkit.v1_19_R1.persistence.CraftPersistentDataContainer;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftNBTTagConfigSerializer;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.logging.Level;

public class NmsPersistentData119R1 extends NmsPersistentData
{
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsPersistentData119R1 i = new NmsPersistentData119R1();
	public static NmsPersistentData119R1 get () { return i; }
	
	// -------------------------------------------- //
	// CREATE
	// -------------------------------------------- //
	
	@Override
	public Map<String, Object> getPersistentData(@NotNull PersistentDataContainer persistentDataContainer) {
		if (!(persistentDataContainer instanceof CraftPersistentDataContainer craftPersistentDataContainer)) {
			MassiveCore.get().log(Level.WARNING, "Failed to getPersistentData - Not CraftPDC");
			return null;
		}
		return craftPersistentDataContainer.serialize();
	}
	
	@Override
	public void setPersistentData(@NotNull PersistentDataContainer persistentDataContainer, Map<String, Object> data) {
		if (!(persistentDataContainer instanceof CraftPersistentDataContainer craftPersistentDataContainer)) {
			MassiveCore.get().log(Level.WARNING, "Failed to setPersistentData - Not CraftPDC");
			return;
		}
		
		Tag deserialized = CraftNBTTagConfigSerializer.deserialize(data);
		if (!(deserialized instanceof CompoundTag compoundTag)) {
			MassiveCore.get().log(Level.WARNING, "Failed to setPersistentData - Deserialized Tag is not CompoundTag");
			return;
		}
		
		craftPersistentDataContainer.putAll(compoundTag);
	}
	
}
