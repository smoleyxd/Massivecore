package com.massivecraft.massivecore.chestgui;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;

public class ChestButtonSimple implements ChestButton
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //

	private final ChestAction action;
	@Override public ChestAction getAction() { return this.action; }

	private final ItemStack item;
	@Override public ItemStack getItem() { return this.item; }

	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //

	@Contract(pure = true)
	public ChestButtonSimple(ChestAction action, ItemStack item)
	{
		this.action = action;
		this.item = item;
	}

}
