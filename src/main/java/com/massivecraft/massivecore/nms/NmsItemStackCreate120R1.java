package com.massivecraft.massivecore.nms;

import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NmsItemStackCreate120R1 extends NmsItemStackCreate
{
	
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackCreate120R1 i = new NmsItemStackCreate120R1();
	public static NmsItemStackCreate120R1 get () { return i; }
	
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
