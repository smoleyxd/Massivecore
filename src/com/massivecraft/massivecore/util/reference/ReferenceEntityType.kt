package com.massivecraft.massivecore.util.reference

import org.bukkit.entity.EntityType

object ReferenceEntityType {

    @JvmStatic
    val typesUndead: Set<EntityType> = ProviderOptimizedCollectionSafe.enumSetOf(
            EntityType::class.java,
            "DROWNED",
            "EVOKER",
            "HUSK",
            "PHANTOM",
            "PIG_ZOMBIE",
            "SKELETON",
            "SKELETON_HORSE",
            "STRAY",
            "VEX",
            "VINDICATOR",
            "WITHER_SKELETON",
            "ZOMBIE",
            "ZOMBIE_HORSE",
            "ZOMBIE_VILLAGER"
    )

}
