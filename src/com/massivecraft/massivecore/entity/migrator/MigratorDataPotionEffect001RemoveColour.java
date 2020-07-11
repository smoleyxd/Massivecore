package com.massivecraft.massivecore.entity.migrator;

import com.massivecraft.massivecore.item.DataPotionEffect;
import com.massivecraft.massivecore.store.migrator.MigratorRoot;

public class MigratorDataPotionEffect001RemoveColour extends MigratorRoot
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MigratorDataPotionEffect001RemoveColour i = new MigratorDataPotionEffect001RemoveColour();
	public static MigratorDataPotionEffect001RemoveColour get() { return i; }
	private MigratorDataPotionEffect001RemoveColour()
	{
		super(DataPotionEffect.class);
		this.addInnerMigrator(entity -> entity.remove("color"));
	}
}
