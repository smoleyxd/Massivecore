package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.mixin.Mixin;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NmsItemStackCreate extends Mixin
{
	// -------------------------------------------- //
	// DEFAULT
	// -------------------------------------------- //
	
	private static NmsItemStackCreate d = new NmsItemStackCreate().setAlternatives(
		NmsItemStackCreate117R1P.class,
		NmsItemStackCreate17R4P.class,
		NmsItemStackCreateFallback.class
	);
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static NmsItemStackCreate i = d;
	public static NmsItemStackCreate get() { return i; }
	
	// -------------------------------------------- //
	// CREATE
	// -------------------------------------------- //
	
	public ItemStack create()
	{
		throw notImplemented();
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	public @NotNull Class<?> getClassCraftItemStack() throws ClassNotFoundException
	{
		throw notImplemented();
	}
	
	public @Nullable Class<?> getClassCraftItemStackCatch()
	{
		try
		{
			return getClassCraftItemStack();
		}
		catch (Throwable t)
		{
			return null;
		}
	}
	
}
