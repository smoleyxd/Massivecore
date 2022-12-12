package com.massivecraft.massivecore.chestgui;

import org.bukkit.inventory.ItemStack;

public record ChestButtonSimple(ChestAction action, ItemStack item) implements ChestButton
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	@Override
	public ChestAction getAction() {return this.action;}
	
	@Override
	public ItemStack getItem() {return this.item;}
}
