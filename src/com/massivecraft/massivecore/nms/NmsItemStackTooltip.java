package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.mixin.Mixin;
import org.bukkit.inventory.ItemStack;

public class NmsItemStackTooltip extends Mixin
{
	// -------------------------------------------- //
	// DEFAULT
	// -------------------------------------------- //
	
	private static final NmsItemStackTooltip d = new NmsItemStackTooltip().setAlternatives(
		NmsItemStackTooltip118R1P.class,
		NmsItemStackTooltip117R1.class,
		NmsItemStackTooltip18R1P.class,
		NmsItemStackTooltipFallback.class
	);
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsItemStackTooltip i = d;
	public static NmsItemStackTooltip get() { return i; }
	
	// -------------------------------------------- //
	// TOOLTIP
	// -------------------------------------------- //
	
	public String getNbtStringTooltip(ItemStack item)
	{
		return null;
	}
	
}
