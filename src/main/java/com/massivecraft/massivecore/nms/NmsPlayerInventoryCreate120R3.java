package com.massivecraft.massivecore.nms;

import net.minecraft.world.entity.player.Inventory;
import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftInventoryPlayer;
import org.bukkit.inventory.PlayerInventory;

@SuppressWarnings("FieldCanBeLocal")
public class NmsPlayerInventoryCreate120R3 extends NmsPlayerInventoryCreate
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsPlayerInventoryCreate120R3 i = new NmsPlayerInventoryCreate120R3();
	public static NmsPlayerInventoryCreate120R3 get () { return i; }
	
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
