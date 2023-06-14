package com.massivecraft.massivecore.item;

import org.bukkit.Material;
import org.bukkit.MusicInstrument;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MusicInstrumentMeta;
import org.jetbrains.annotations.NotNull;

public class WriterItemStackMetaInstrument extends WriterAbstractItemStackMetaField<MusicInstrumentMeta, NamespacedKey, NamespacedKey>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaInstrument i = new WriterItemStackMetaInstrument();
	public static WriterItemStackMetaInstrument get() { return i; }
	
	public WriterItemStackMetaInstrument()
	{
		super(MusicInstrumentMeta.class);
		this.setMaterial(Material.GOAT_HORN);
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public NamespacedKey getA(@NotNull DataItemStack ca, ItemStack d)
	{
		return ca.getMusicInstrument();
	}

	@Override
	public void setA(@NotNull DataItemStack ca, NamespacedKey fa, ItemStack d)
	{
		ca.setMusicInstrument(fa);
	}

	@Override
	public NamespacedKey getB(@NotNull MusicInstrumentMeta cb, ItemStack d)
	{
		MusicInstrument instrument = cb.getInstrument();
		if (instrument == null) return null;
		return instrument.getKey();
	}

	@Override
	public void setB(@NotNull MusicInstrumentMeta cb, NamespacedKey fb, ItemStack d)
	{
		MusicInstrument instrument = MusicInstrument.getByKey(fb);
		if (instrument == null) return;
		cb.setInstrument(instrument);
	}
	
}
