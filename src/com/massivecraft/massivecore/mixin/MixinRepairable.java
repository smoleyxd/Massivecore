package com.massivecraft.massivecore.mixin;

import com.massivecraft.massivecore.nms.NmsRepairable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;

public class MixinRepairable extends Mixin
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MixinRepairable d = new MixinRepairable();
	private static MixinRepairable i = d;
	@Contract(pure = true)
	public static MixinRepairable get() { return i; }
	
	// -------------------------------------------- //
	// AVAILABLE
	// -------------------------------------------- //
	
	@Override
	public boolean isAvailable()
	{
		return NmsRepairable.get().isAvailable();
	}
	
	// -------------------------------------------- //
	// REPAIRABLE
	// -------------------------------------------- //

	public boolean isRepairable(ItemStack itemStack)
	{
		return NmsRepairable.get().isRepairable(itemStack);
	}

	public void repair(ItemStack itemStack)
	{
		NmsRepairable.get().repair(itemStack);
	}

}
