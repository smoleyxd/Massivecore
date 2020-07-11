package com.massivecraft.massivecore.util.reference

import org.bukkit.Material

object ReferenceMaterial {

    @JvmStatic
    val pickaxeMaterials: Set<Material> = ProviderOptimizedCollectionSafe.enumSetOf(
            Material::class.java,
            "DIAMOND_PICKAXE",
            "GOLDEN_PICKAXE",
            "GOLD_PICKAXE",
            "IRON_PICKAXE",
            "STONE_PICKAXE",
            "WOODEN_PICKAXE",
            "WOOD_PICKAXE"
    )

    @JvmStatic
    val swordMaterials: Set<Material> = ProviderOptimizedCollectionSafe.enumSetOf(
            Material::class.java,
            "DIAMOND_SWORD",
            "GOLDEN_SWORD",
            "GOLD_SWORD",
            "IRON_SWORD",
            "STONE_SWORD",
            "WOODEN_SWORD",
            "WOOD_SWORD"
    )

    @JvmStatic
    val axeMaterials: Set<Material> = ProviderOptimizedCollectionSafe.enumSetOf(
            Material::class.java,
            "DIAMOND_AXE",
            "GOLDEN_AXE",
            "GOLD_AXE",
            "IRON_AXE",
            "STONE_AXE",
            "WOODEN_AXE",
            "WOOD_AXE"
    )

    @JvmStatic
    val spadeMaterials: Set<Material> = ProviderOptimizedCollectionSafe.enumSetOf(
            Material::class.java,
            "DIAMOND_SHOVEL",
            "DIAMOND_SPADE",
            "GOLDEN_SHOVEL",
            "GOLD_SPADE",
            "IRON_SHOVEL",
            "IRON_SPADE",
            "STONE_SHOVEL",
            "STONE_SPADE",
            "WOODEN_SHOVEL",
            "WOOD_SPADE"
    )

    @JvmStatic
    val hoeMaterials: Set<Material> = ProviderOptimizedCollectionSafe.enumSetOf(
            Material::class.java,
            "DIAMOND_HOE",
            "GOLDEN_HOE",
            "GOLD_HOE",
            "IRON_HOE",
            "STONE_HOE",
            "WOODEN_HOE"
    )

    @JvmStatic
    val helmetMaterials: Set<Material> = ProviderOptimizedCollectionSafe.enumSetOf(
            Material::class.java,
            "DIAMOND_HELMET",
            "GOLDEN_HELMET",
            "GOLD_HELMET",
            "IRON_HELMET",
            "LEATHER_HELMET",
            "CHAINMAIL_HELMET",
            "TURTLE_HELMET"
    )

    @JvmStatic
    val chestplateMaterials: Set<Material> = ProviderOptimizedCollectionSafe.enumSetOf(
            Material::class.java,
            "DIAMOND_CHESTPLATE",
            "GOLDEN_CHESTPLATE",
            "GOLD_CHESTPLATE",
            "IRON_CHESTPLATE",
            "LEATHER_CHESTPLATE",
            "CHAINMAIL_CHESTPLATE"
    )

    @JvmStatic
    val leggingsMaterials: Set<Material> = ProviderOptimizedCollectionSafe.enumSetOf(
            Material::class.java,
            "DIAMOND_LEGGINGS",
            "GOLDEN_LEGGINGS",
            "GOLD_LEGGINGS",
            "IRON_LEGGINGS",
            "LEATHER_LEGGINGS",
            "CHAINMAIL_LEGGINGS"
    )

    @JvmStatic
    val bootsMaterials: Set<Material> = ProviderOptimizedCollectionSafe.enumSetOf(
            Material::class.java,
            "DIAMOND_BOOTS",
            "GOLDEN_BOOTS",
            "GOLD_BOOTS",
            "IRON_BOOTS",
            "LEATHER_BOOTS",
            "CHAINMAIL_BOOTS"
    )

    @JvmStatic
    val foodMaterials: Set<Material> = ProviderOptimizedCollectionSafe.enumSetOf(
            Material::class.java,
            "APPLE",
            "BAKED_POTATO",
            "BEETROOT_SOUP",
            "BREAD",
            "CARROT",
            "CHICKEN",
            "CHORUS_FRUIT",
            "COD",
            "COOKED_BEEF",
            "COOKED_CHICKEN",
            "COOKED_COD",
            "COOKED_FISH",
            "COOKED_MUTTON",
            "COOKED_PORKCHOP",
            "COOKED_RABBIT",
            "COOKED_SALMON",
            "COOKIE",
            "CORNFLOWER",
            "ENCHANTED_GOLDEN_APPLE",
            "GOLDEN_APPLE",
            "GOLDEN_CARROT",
            "GRILLED_PORK",
            "MELON",
            "MELON_SLICE",
            "MUSHROOM_SOUP",
            "MUSHROOM_STEW",
            "MUTTON",
            "PORK",
            "POTATO",
            "PUFFERFISH",
            "PUMPKIN_PIE",
            "RABBIT",
            "RABBIT_STEW",
            "RAW_BEEF",
            "RAW_CHICKEN",
            "RAW_FISH",
            "ROTTEN_FLESH",
            "SALMON",
            "SPIDER_EYE",
            "SUSPICIOUS_STEW",
            "SWEET_BERRIES",
            "TROPICAL_FISH"
    )

    @JvmStatic
    val materialsVegetation: Set<Material> = ProviderOptimizedCollectionSafe.enumSetOf(
            Material::class.java,
            "ACACIA_LEAVES",
            "ACACIA_SAPLING",
            "ALLIUM",
            "ATTACHED_MELON_STEM",
            "ATTACHED_PUMPKIN_STEM",
            "AZURE_BLUET",
            "BAMBOO",
            "BAMBOO_SAPLING",
            "BEETROOT",
            "BEETROOTS",
            "BEETROOT_SEEDS",
            "BIRCH_LEAVES",
            "BIRCH_SAPLING",
            "BLUE_ORCHID",
            "BRAIN_CORAL",
            "BRAIN_CORAL_FAN",
            "BROWN_MUSHROOM",
            "BROWN_MUSHROOM_BLOCK",
            "BUBBLE_CORAL",
            "BUBBLE_CORAL_FAN",
            "CACTUS",
            "CARROTS",
            "CHORUS_FLOWER",
            "CHORUS_PLANT",
            "COCOA",
            "COCOA_BEANS",
            "CORNFLOWER",
            "CROPS",
            "DANDELION",
            "DARK_OAK_LEAVES",
            "DARK_OAK_SAPLING",
            "DEAD_BRAIN_CORAL",
            "DEAD_BRAIN_CORAL_FAN",
            "DEAD_BUBBLE_CORAL",
            "DEAD_BUBBLE_CORAL_FAN",
            "DEAD_BUSH",
            "DEAD_FIRE_CORAL",
            "DEAD_FIRE_CORAL_FAN",
            "DEAD_HORN_CORAL",
            "DEAD_HORN_CORAL_FAN",
            "DEAD_TUBE_CORAL",
            "DEAD_TUBE_CORAL_FAN",
            "DOUBLE_PLANT",
            "FERN",
            "FIRE_CORAL",
            "FIRE_CORAL_FAN",
            "GRASS",
            "HAY_BLOCK",
            "HORN_CORAL",
            "HORN_CORAL_FAN",
            "JUNGLE_LEAVES",
            "JUNGLE_SAPLING",
            "LARGE_FERN",
            "LEAVES",
            "LEAVES_2",
            "LILAC",
            "LILY_OF_THE_VALLEY",
            "LILY_PAD",
            "LONG_GRASS",
            "MELON",
            "MELON_BLOCK",
            "MELON_STEM",
            "OAK_LEAVES",
            "OAK_SAPLING",
            "ORANGE_TULIP",
            "OXEYE_DAISY",
            "PEONY",
            "PINK_TULIP",
            "POPPY",
            "POTATOES",
            "PUMPKIN",
            "PUMPKIN_STEM",
            "RED_MUSHROOM",
            "RED_MUSHROOM_BLOCK",
            "RED_ROSE",
            "RED_TULIP",
            "ROSE_BUSH",
            "SAPLING",
            "SEAGRASS",
            "SEA_PICKLE",
            "SPRUCE_LEAVES",
            "SPRUCE_SAPLING",
            "SUGAR_CANE",
            "SUGAR_CANE_BLOCK",
            "SUNFLOWER",
            "SWEET_BERRY_BUSH",
            "TALL_GRASS",
            "TALL_SEAGRASS",
            "TUBE_CORAL",
            "TUBE_CORAL_FAN",
            "VINE",
            "WATER_LILY",
            "WHEAT",
            "WHEAT_SEEDS",
            "WHITE_TULIP",
            "WITHER_ROSE",
            "YELLOW_FLOWER"
    )

    @JvmStatic
    val materialsSign: Set<Material> = ProviderOptimizedCollectionSafe.enumSetOf(
            Material::class.java,
            "SIGN", // Minecraft 1.?
            "SIGN_POST", // Minecraft 1.?
            "WALL_SIGN", // Minecraft 1.?
            "ACACIA_SIGN", // Minecraft 1.14
            "ACACIA_WALL_SIGN", // Minecraft 1.14
            "BIRCH_SIGN", // Minecraft 1.14
            "BIRCH_WALL_SIGN", // Minecraft 1.14
            "DARK_OAK_SIGN", // Minecraft 1.14
            "DARK_OAK_WALL_SIGN", // Minecraft 1.14
            "JUNGLE_SIGN", // Minecraft 1.14
            "JUNGLE_WALL_SIGN", // Minecraft 1.14
            "OAK_SIGN", // Minecraft 1.14
            "OAK_WALL_SIGN", // Minecraft 1.14
            "SPRUCE_SIGN", // Minecraft 1.14
            "SPRUCE_WALL_SIGN" // Minecraft 1.14
    )

}
