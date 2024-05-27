package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.mixin.Mixin;
import org.bukkit.inventory.PlayerInventory;

public class NmsPlayerInventoryCreate extends Mixin
{
	// -------------------------------------------- //
	// DEFAULT
	// -------------------------------------------- //
	
	private static final NmsPlayerInventoryCreate d = new NmsPlayerInventoryCreate().setAlternatives(
		NmsPlayerInventoryCreate120R3.class
	);
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsPlayerInventoryCreate i = d;
	public static NmsPlayerInventoryCreate get() { return i; }
	
	// -------------------------------------------- //
	// CREATE
	// -------------------------------------------- //
	
	public PlayerInventory create()
	{
		throw notImplemented();
	}
	
}
