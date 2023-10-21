package com.massivecraft.massivecore.nms;

import net.minecraft.nbt.CompoundTag;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NmsItemStackTooltip120R2 extends NmsItemStackTooltip
{
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackTooltip120R2 i = new NmsItemStackTooltip120R2();
	public static NmsItemStackTooltip120R2 get () { return i; }
	
	// -------------------------------------------- //
	// TOOLTIP
	// -------------------------------------------- //
	
	@Override
	public String getNbtStringTooltip(ItemStack item)
	{
		net.minecraft.world.item.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
		return itemStack.save(new CompoundTag()).toString();
	}
	
}
