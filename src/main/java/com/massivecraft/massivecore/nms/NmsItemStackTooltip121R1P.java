package com.massivecraft.massivecore.nms;

import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NmsItemStackTooltip121R1P extends NmsItemStackTooltip
{
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackTooltip121R1P i = new NmsItemStackTooltip121R1P();
	public static NmsItemStackTooltip121R1P get () { return i; }

	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //

	RegistryAccess registryAccess;

	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //

	@Override
	public void setup() throws Throwable
	{
		registryAccess = MinecraftServer.getServer().registryAccess();
	}

	// -------------------------------------------- //
	// TOOLTIP
	// -------------------------------------------- //
	
	@Override
	public String getNbtStringTooltip(ItemStack item)
	{
		net.minecraft.world.item.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
		return itemStack.saveOptional(registryAccess).toString();
	}
	
}
