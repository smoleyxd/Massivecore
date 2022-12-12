package com.massivecraft.massivecore.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WriterItemStackMetaBundleItems extends WriterAbstractItemStackMetaField<BundleMeta, List<DataItemStack>, List<ItemStack>>
{
	
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaBundleItems i = new WriterItemStackMetaBundleItems();
	public static WriterItemStackMetaBundleItems get()
	{
		return i;
	}
	
	// -------------------------------------------- //
	// CONSTRUCTOR
	// -------------------------------------------- //
	
	public WriterItemStackMetaBundleItems()
	{
		super(BundleMeta.class);
		this.setMaterial(Material.BUNDLE);
		this.setConverterTo(ConverterToBundleItems.get());
		this.setConverterFrom(ConverterFromBundleItems.get());
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public List<DataItemStack> getA(@NotNull DataItemStack ca, ItemStack d) {
		return ca.getBundle();
	}
	
	@Override
	public void setA(@NotNull DataItemStack ca, List<DataItemStack> fa, ItemStack d) {
		ca.setBundle(fa);
	}
	
	@Override
	public List<ItemStack> getB(@NotNull BundleMeta cb, ItemStack d) {
		if (!cb.hasItems()) return null;
		return cb.getItems();
	}
	
	@Override
	public void setB(@NotNull BundleMeta cb, List<ItemStack> fb, ItemStack d) {
		cb.setItems(fb);
	}
	
}
