package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import com.massivecraft.massivecore.util.ReflectionUtil;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;

public class NmsItemStackCreate117R1P extends NmsItemStackCreate
{
	
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	private static NmsItemStackCreate117R1P i = new NmsItemStackCreate117R1P();
	public static NmsItemStackCreate117R1P get () { return i; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	// net.minecraft.world.item.ItemStack
	private Class<?> classNmsItemStack;
	
	// org.bukkit.craftbukkit.inventory.CraftItemStack
	private Class<?> classCraftItemStack;
	
	// org.bukkit.craftbukkit.inventory.CraftItemStack(net.minecraft.server.ItemStack)
	private Constructor<?> constructorCraftItemStack;
	
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		this.classNmsItemStack = getClassCraftItemStack();
		this.classCraftItemStack = PackageType.CRAFTBUKKIT_VERSION_INVENTORY.getClass("CraftItemStack");
		this.constructorCraftItemStack = ReflectionUtil.getConstructor(this.classCraftItemStack, this.classNmsItemStack);
	}
	
	// -------------------------------------------- //
	// CREATE
	// -------------------------------------------- //
	
	@Override
	public ItemStack create()
	{
		return ReflectionUtil.invokeConstructor(this.constructorCraftItemStack, (Object)null);
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	public static @NotNull Class<?> getClassCraftItemStack() throws ClassNotFoundException
	{
		if (ServerType.get() == ServerType.FORGE)
		{
			return PackageType.MINECRAFT_ITEM.getClass("ItemStack");
		}
		else
		{
			return PackageType.MINECRAFT_WORLD_ITEM.getClass("ItemStack");
		}
	}
	
	public static @Nullable Class<?> getClassCraftItemStackCatch()
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
