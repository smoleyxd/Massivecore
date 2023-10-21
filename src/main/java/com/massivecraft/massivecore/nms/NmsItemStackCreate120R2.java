package com.massivecraft.massivecore.nms;

import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class NmsItemStackCreate120R2 extends NmsItemStackCreate
{
	
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackCreate120R2 i = new NmsItemStackCreate120R2();
	public static NmsItemStackCreate120R2 get () { return i; }
	
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
