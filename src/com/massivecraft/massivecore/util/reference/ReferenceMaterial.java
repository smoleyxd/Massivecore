package com.massivecraft.massivecore.util.reference;

import org.bukkit.Material;

import java.util.Set;

public class ReferenceMaterial {

    private static Set<Material> pickaxeMaterials = ProviderOptimizedCollectionSafe.enumSetOf(
            Material.class,
            "NETHERITE_PICKAXE",
            "DIAMOND_PICKAXE",
            "GOLDEN_PICKAXE",
            "GOLD_PICKAXE",
            "IRON_PICKAXE",
            "STONE_PICKAXE",
            "WOODEN_PICKAXE",
            "WOOD_PICKAXE"
    );
    public static Set<Material> getPickaxeMaterials() { return pickaxeMaterials; }

    private static Set<Material> swordMaterials = ProviderOptimizedCollectionSafe.enumSetOf(
            Material.class,
            "NETHERITE_SWORD",
            "DIAMOND_SWORD",
            "GOLDEN_SWORD",
            "GOLD_SWORD",
            "IRON_SWORD",
            "STONE_SWORD",
            "WOODEN_SWORD",
            "WOOD_SWORD"
    );
    public static Set<Material> getSwordMaterials() { return swordMaterials; }

    private static Set<Material> axeMaterials = ProviderOptimizedCollectionSafe.enumSetOf(
            Material.class,
            "NETHERITE_AXE",
            "DIAMOND_AXE",
            "GOLDEN_AXE",
            "GOLD_AXE",
            "IRON_AXE",
            "STONE_AXE",
            "WOODEN_AXE",
            "WOOD_AXE"
    );
    public static Set<Material> getAxeMaterials() { return axeMaterials; }

    private static Set<Material> spadeMaterials = ProviderOptimizedCollectionSafe.enumSetOf(
            Material.class,
            "NETHERITE_SHOVEL",
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
    );
    public static Set<Material> getSpadeMaterials() { return spadeMaterials; }

    private static Set<Material> hoeMaterials = ProviderOptimizedCollectionSafe.enumSetOf(
            Material.class,
            "NETHERITE_HOE",
            "DIAMOND_HOE",
            "GOLDEN_HOE",
            "GOLD_HOE",
            "IRON_HOE",
            "STONE_HOE",
            "WOODEN_HOE"
    );
    public static Set<Material> getHoeMaterials() { return hoeMaterials; }

    private static Set<Material> helmetMaterials = ProviderOptimizedCollectionSafe.enumSetOf(
            Material.class,
            "NETHERITE_HELMET",
            "DIAMOND_HELMET",
            "GOLDEN_HELMET",
            "GOLD_HELMET",
            "IRON_HELMET",
            "LEATHER_HELMET",
            "CHAINMAIL_HELMET",
            "TURTLE_HELMET"
    );
    public static Set<Material> getHelmetMaterials() { return helmetMaterials; }

    private static Set<Material> chestplateMaterials = ProviderOptimizedCollectionSafe.enumSetOf(
            Material.class,
            "NETHERITE_CHESTPLATE",
            "DIAMOND_CHESTPLATE",
            "GOLDEN_CHESTPLATE",
            "GOLD_CHESTPLATE",
            "IRON_CHESTPLATE",
            "LEATHER_CHESTPLATE",
            "CHAINMAIL_CHESTPLATE"
    );
    public static Set<Material> getChestplateMaterials() { return chestplateMaterials; }

    private static Set<Material> leggingsMaterials = ProviderOptimizedCollectionSafe.enumSetOf(
            Material.class,
            "NETHERITE_LEGGINGS",
            "DIAMOND_LEGGINGS",
            "GOLDEN_LEGGINGS",
            "GOLD_LEGGINGS",
            "IRON_LEGGINGS",
            "LEATHER_LEGGINGS",
            "CHAINMAIL_LEGGINGS"
    );
    public static Set<Material> getLeggingsMaterials() { return leggingsMaterials; }

    private static Set<Material> bootsMaterials = ProviderOptimizedCollectionSafe.enumSetOf(
            Material.class,
            "NETHERITE_BOOTS",
            "DIAMOND_BOOTS",
            "GOLDEN_BOOTS",
            "GOLD_BOOTS",
            "IRON_BOOTS",
            "LEATHER_BOOTS",
            "CHAINMAIL_BOOTS"
    );
    public static Set<Material> getBootsMaterials() { return bootsMaterials; }

    private static Set<Material> foodMaterials = ProviderOptimizedCollectionSafe.enumSetOf(
            Material.class,
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
            "DRIED_KELP",
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
    );
    public static Set<Material> getFoodMaterials() { return foodMaterials; }

    private static Set<Material> materialsVegetation = ProviderOptimizedCollectionSafe.enumSetOf(
            Material.class,
            "ACACIA_LEAVES",
            "ACACIA_SAPLING",
            "ALLIUM",
            "ATTACHED_MELON_STEM",
            "ATTACHED_PUMPKIN_STEM",
            "AZURE_BLUET",
            "BAMBOO",
            "BAMBOO_SAPLING",
            "BEETROOTS",
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
            "KELP",
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
            "NETHER_WART",
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
            "TWISTING_VINES",
            "TWISTING_VINES_PLANT",
            "VINE",
            "WATER_LILY",
            "WEEPING_VINES",
            "WEEPING_VINES_PLANT",
            "WHEAT",
            "WHITE_TULIP",
            "WITHER_ROSE",
            "YELLOW_FLOWER"
    );
    public static Set<Material> getMaterialsVegetation() { return materialsVegetation; }

    private static Set<Material> materialsSign = ProviderOptimizedCollectionSafe.enumSetOf(
            Material.class,
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
            "SPRUCE_WALL_SIGN", // Minecraft 1.14
            "CRIMSON_SIGN", // Minecraft 1.16
            "CRIMSON_WALL_SIGN", // Minecraft 1.16
            "WARPED_SIGN", // Minecraft 1.16
            "WARPED_WALL_SIGN" // Minecraft 1.16
    );
    public static Set<Material> getMaterialsSign() { return materialsSign; }
}
