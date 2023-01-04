package com.massivecraft.massivecore.nms;

import net.minecraft.nbt.CompoundTag;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NmsItemStackTooltip119R1 extends NmsItemStackTooltip
{
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackTooltip119R1 i = new NmsItemStackTooltip119R1();
	public static NmsItemStackTooltip119R1 get () { return i; }
	
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
