package com.massivecraft.massivecore.nms;

import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NmsItemStackCreate119R1 extends NmsItemStackCreate
{
	
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackCreate119R1 i = new NmsItemStackCreate119R1();
	public static NmsItemStackCreate119R1 get () { return i; }
	
	// -------------------------------------------- //
	// CREATE
	// -------------------------------------------- //
	
	@Override
	public ItemStack create()
	{
		return CraftItemStack.asCraftMirror(null);
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	@Override
	public @NotNull Class<?> getClassCraftItemStack()
	{
		return net.minecraft.world.item.ItemStack.class;
	}
	
}
