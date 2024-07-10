package com.massivecraft.massivecore.nms;

import net.minecraft.world.entity.player.Inventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryPlayer;
import org.bukkit.inventory.PlayerInventory;

@SuppressWarnings("FieldCanBeLocal")
public class NmsPlayerInventoryCreate121R1P extends NmsPlayerInventoryCreate
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsPlayerInventoryCreate121R1P i = new NmsPlayerInventoryCreate121R1P();
	public static NmsPlayerInventoryCreate121R1P get () { return i; }
	
	// -------------------------------------------- //
	// CREATE
	// -------------------------------------------- //
	
	@Override
	public PlayerInventory create()
	{
		Inventory inventory = new Inventory(null);
		return new CraftInventoryPlayer(inventory);
	}
	
}
