package com.massivecraft.massivecore.nms;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

public class NmsRepairable113R1P extends NmsRepairable
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //

	private static NmsRepairable113R1P i = new NmsRepairable113R1P();
	public static NmsRepairable113R1P get() { return i; }

	@Override
	public Object provoke() throws Throwable {
		super.provoke();
		return Repairable.class;
	}

	// -------------------------------------------- //
	// METHODS
	// -------------------------------------------- //

	@Override
	public boolean isRepairable(ItemStack itemStack)
	{
		return itemStack.getItemMeta() instanceof Repairable;
	}

	@Override
    protected void repairInner(ItemStack itemStack) {
	    ItemMeta meta = itemStack.getItemMeta();
        if (meta instanceof Damageable) {
            ((Damageable) meta).setDamage(0);
        }
        itemStack.setItemMeta(meta);
    }

}
