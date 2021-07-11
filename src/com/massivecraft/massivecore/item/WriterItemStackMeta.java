package com.massivecraft.massivecore.item;

public class WriterItemStackMeta extends WriterAbstractItemStackMetaMorph<Object, Object>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMeta i = new WriterItemStackMeta();
	public static WriterItemStackMeta get() { return i; }
	public WriterItemStackMeta()
	{
		this.addWriterClasses(
			// UNSPECIFIC
			WriterItemStackMetaName.class,
			WriterItemStackMetaLore.class,
			WriterItemStackMetaEnchants.class,
			WriterItemStackMetaRepaircost.class,
			
			// PERSISTENT DATA
			WriterItemStackMetaPersistentData.class,
			
			// BOOK
			WriterItemStackMetaTitle.class,
			WriterItemStackMetaAuthor.class,
			WriterItemStackMetaGeneration.class,
			WriterItemStackMetaPages.class,
			
			// LEATHER ARMOR
			WriterItemStackMetaColor.class,

			// COMPASS
			WriterItemStackMetaLodestone.class,
			WriterItemStackMetaLodestoneTracked.class,

			// MAP
			WriterItemStackMetaMapScaling.class,
			WriterItemStackMetaMapColor.class,
			WriterItemStackMetaMapId.class,
			WriterItemStackMetaMapName.class,
			
			// POTION EFFECTS
			WriterItemStackMetaPotionEffects.class,
			
			// SKULL
			WriterItemStackMetaSkullProperties.class,
			
			// FIREWORK EFFECT
			WriterItemStackMetaFireworkEffect.class,
			
			// FIREWORK
			WriterItemStackMetaFireworkEffects.class,
			WriterItemStackMetaFireworkFlight.class,
			
			// STORED ENCHANTS
			WriterItemStackMetaStoredEnchants.class,
			
			// UNBREAKABLE
			WriterItemStackMetaUnbreakable.class,
			
			// FLAGS
			WriterItemStackMetaFlags.class,
			
			// BANNER
			//WriterItemStackMetaBannerBase.class,
			WriterItemStackMetaBannerPatterns.class,
			
			// SHIELD
			WriterItemStackMetaShieldBase.class,
			WriterItemStackMetaShieldPatterns.class,
			
			// POTION
			WriterItemStackMetaPotion.class,
			WriterItemStackMetaPotionColor.class,
			
			// INVENTORY
			WriterItemStackMetaInventory.class,
			
			// TROPICAL FISH BUCKET
			WriterItemStackMetaFishPattern.class,
			WriterItemStackMetaFishPatternColor.class,
			WriterItemStackMetaFishBodyColor.class,
			
			// CROSSBOW
			WriterItemStackMetaChargedProjectiles.class,
			
			// KNOWLEDGE BOOK
			WriterItemStackMetaRecipes.class,
			
			// SUSPICIOUS STEW
			WriterItemStackMetaSuspiciousStewEffects.class,
			
			// BUNDLE (1.17+)
			WriterItemStackMetaBundleItems.class
		);
	}
	
}
