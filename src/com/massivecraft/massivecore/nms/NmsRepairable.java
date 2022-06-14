package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.mixin.Mixin;
import com.massivecraft.massivecore.util.InventoryUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NmsRepairable extends Mixin
{
	// -------------------------------------------- //
	// DEFAULT
	// -------------------------------------------- //
	
	private static final NmsRepairable d = new NmsRepairable().setAlternatives(
		NmsRepairable113R1P.class
	);
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsRepairable i = d;
	public static NmsRepairable get() { return i; }

	// -------------------------------------------- //
	// REPAIR
	// -------------------------------------------- //

	public boolean isRepairable(@NotNull ItemStack itemStack)
	{
		throw notImplemented();
	}

	public void repair(ItemStack itemStack)
	{
		// Check Null
		if (InventoryUtil.isNothing(itemStack)) return;

		// Check Repairable
		Material material = itemStack.getType();
		if ( ! isRepairable(itemStack)) return;

		// Repair
		repairInner(itemStack);
	}

	protected void repairInner(@NotNull ItemStack itemStack) {
		throw notImplemented();
	}

}
