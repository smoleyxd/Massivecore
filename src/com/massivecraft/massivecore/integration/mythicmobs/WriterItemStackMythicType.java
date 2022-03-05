package com.massivecraft.massivecore.integration.mythicmobs;

import com.massivecraft.massivecore.item.DataItemStack;
import com.massivecraft.massivecore.item.WriterAbstractItemStackField;
import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.util.jnbt.CompoundTag;
import io.lumine.xikage.mythicmobs.util.jnbt.CompoundTagBuilder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackMythicType extends WriterAbstractItemStackField<String, String>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMythicType i = new WriterItemStackMythicType();
	public static WriterItemStackMythicType get() { return i; }
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public String getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getMythicType();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, String fa, ItemStack d)
	{
		ca.setMythicType(fa);
	}

	@Override
	public String getB(@NotNull ItemStack cb, ItemStack d)
	{
		CompoundTag tagComp = MythicMobs.inst().getVolatileCodeHandler().getItemHandler().getNBTData(cb);
		if (!tagComp.containsKey("MYTHIC_TYPE")) return null;
		return tagComp.getString("MYTHIC_TYPE");
	}

	@Override
	public void setB(@NotNull ItemStack cb, String fb, ItemStack d)
	{
//		if (fb == null) return; // TODO - Safely remove the Tag if null
//		MythicMobs.inst().getVolatileCodeHandler().getItemHandler().addNBTData(cb, "MYTHIC_TYPE", new StringTag(fb));
		
		CompoundTagBuilder tagCompBuilder = MythicMobs.inst().getVolatileCodeHandler().getItemHandler().getNBTData(cb).createBuilder();
		if (fb == null) tagCompBuilder.remove("MYTHIC_TYPE");
		else tagCompBuilder.putString("MYTHIC_TYPE", fb);
		
		MythicMobs.inst().getVolatileCodeHandler().getItemHandler().setNBTData(cb, tagCompBuilder.build());
	}
	
}
