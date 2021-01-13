package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class WriterItemStackMetaLodestone extends WriterAbstractItemStackMetaField<CompassMeta, PS, PS>
{
	
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaLodestone i = new WriterItemStackMetaLodestone();
	public static WriterItemStackMetaLodestone get()
	{
		return i;
	}
	
	// -------------------------------------------- //
	// CONSTRUCTOR
	// -------------------------------------------- //
	
	public WriterItemStackMetaLodestone()
	{
		super(CompassMeta.class);
		this.setMaterial(Material.COMPASS);
	}
	
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //

	public static NamespacedKey NSK_LODESTONE_WORLD = new NamespacedKey(MassiveCore.get(), "lodestone_world");
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public PS getA(DataItemStack ca, ItemStack d) {
		return ca.getLodestone();
	}
	
	@Override
	public void setA(DataItemStack ca, PS fa, ItemStack d) {
		ca.setLodestone(fa);
	}
	
	@Override
	public PS getB(CompassMeta cb, ItemStack d) {
		if (!cb.hasLodestone()) return null;
		PS ps = PS.valueOf(cb.getLodestone());
		
		PersistentDataContainer pdc = cb.getPersistentDataContainer();
		return ps.withWorld(
			pdc.getOrDefault(NSK_LODESTONE_WORLD, PersistentDataType.STRING, ps.getWorld())
		);
	}
	
	@Override
	public void setB(CompassMeta cb, PS fb, ItemStack d) {
		
		World world = Bukkit.getWorld(fb.getWorld());
		if (world == null) {
			fb = fb.withWorld(Bukkit.getWorlds().get(0).getName());
			PersistentDataContainer pdc = cb.getPersistentDataContainer();
			pdc.set(NSK_LODESTONE_WORLD, PersistentDataType.STRING, fb.getWorld());
		}
		
		cb.setLodestone(fb.asBukkitLocation());
	}
	
}
