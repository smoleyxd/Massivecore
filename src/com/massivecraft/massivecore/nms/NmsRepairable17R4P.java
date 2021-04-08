package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

@SuppressWarnings("deprecation")
public class NmsRepairable17R4P extends NmsRepairable
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static NmsRepairable17R4P i = new NmsRepairable17R4P();
	public static NmsRepairable17R4P get() { return i; }
	
	// -------------------------------------------- //
	// METHODS
	// -------------------------------------------- //
	
	@Override
	public boolean isRepairable(@NotNull ItemStack itemStack)
	{
		Material material = itemStack.getType();

		// Blocks are never repairable.
		// Only items take damage in Minecraft.
		if (material.isBlock()) return false;

		// This list was created by checking for the "B" notation on:
		// http://minecraft.gamepedia.com/Data_values
		if (nonRepairables.contains(material.name())) return false;

		// This lines actually catches most of the specific lines above.
		// However we add this in anyways for future compatibility.
		if ( ! material.getData().equals(MaterialData.class)) return false;

		// We may also not repair things that can not take any damage.
		// NOTE: MaxDurability should be renamed to MaxDamage.
		return material.getMaxDurability() != 0;
	}

	private static final Set<String> nonRepairables = Collections.unmodifiableSet(MUtil.set(
			"COAL",
			"GOLDEN_APPLE",
			"RAW_FISH",
			"COOKED_FISH",
			"INK_SACK",
			"MAP",
			"POTION",
			"MONSTER_EGG",
			"SKULL_ITEM"
	));

	@Override
	protected void repairInner(@NotNull ItemStack itemStack)
	{
		itemStack.setDurability((short) 0);
	}

}
