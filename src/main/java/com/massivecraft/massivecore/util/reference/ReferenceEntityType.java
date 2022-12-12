package com.massivecraft.massivecore.util.reference;

import org.bukkit.entity.EntityType;

import java.util.Set;

public class ReferenceEntityType {

    private static final Set<EntityType> typesUndead = ProviderOptimizedCollectionSafe.enumSetOf(
            EntityType.class,
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
    );
    public static Set<EntityType> getTypesUndead() { return typesUndead; }
}
