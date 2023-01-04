package com.massivecraft.massivecore.nms;

import net.minecraft.world.entity.player.Inventory;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftInventoryPlayer;
import org.bukkit.inventory.PlayerInventory;

@SuppressWarnings("FieldCanBeLocal")
public class NmsPlayerInventoryCreate119R1 extends NmsPlayerInventoryCreate
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsPlayerInventoryCreate119R1 i = new NmsPlayerInventoryCreate119R1();
	public static NmsPlayerInventoryCreate119R1 get () { return i; }
	
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
