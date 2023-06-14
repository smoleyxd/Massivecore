package com.massivecraft.massivecore.nms;

import net.minecraft.world.entity.player.Inventory;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftInventoryPlayer;
import org.bukkit.inventory.PlayerInventory;

@SuppressWarnings("FieldCanBeLocal")
public class NmsPlayerInventoryCreate120R1 extends NmsPlayerInventoryCreate
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsPlayerInventoryCreate120R1 i = new NmsPlayerInventoryCreate120R1();
	public static NmsPlayerInventoryCreate120R1 get () { return i; }
	
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
