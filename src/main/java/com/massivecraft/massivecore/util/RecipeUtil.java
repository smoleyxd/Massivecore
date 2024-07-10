package com.massivecraft.massivecore.util;

import com.massivecraft.massivecore.mixin.MixinRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

// NOTE: This utility targets 1.9 and will crash on older servers.
public class RecipeUtil
{
	// ------------------------------------------- //
	// POTION
	// -------------------------------------------- //
	
	public static @NotNull ItemStack createPotionItemStack(PotionType type, Material material, int amount)
	{
		ItemStack ret = new ItemStack(material, amount);
		PotionMeta meta = InventoryUtil.createMeta(ret);
		meta.setBasePotionType(type);
		ret.setItemMeta(meta);
		return ret;
	}
	
	public static ShapelessRecipe createPotion(PotionType type, Material material, Object... objects)
	{
		// When brewing you actually get 3 potions.
		final int amount = 3;
		ItemStack item = createPotionItemStack(type, material, amount);
		return createShapeless(item, objects);
	}
	
	public static ShapelessRecipe addPotion(PotionType type, Material material, Object... objects)
	{
		ShapelessRecipe recipe = createPotion(type, material, objects);
		Bukkit.getServer().addRecipe(recipe);
		return recipe;
	}
	
	// ------------------------------------------- //
	// CIRCULAR
	// -------------------------------------------- //
	
	public static void addCircular(ItemStack @NotNull ... items)
	{
		for (int i = 0; i < items.length; i++)
		{
			int next = (i+1) % items.length;
			ItemStack item = items[i];
			addShapeless(items[next], item.getAmount(), item.getType());
		}
	}
	
	// ------------------------------------------- //
	// SHAPELESS
	// -------------------------------------------- //

	// TODO check this out
	public static ShapelessRecipe createShapeless(ItemStack result, Object @NotNull ... objects)
	{
		ShapelessRecipe recipe = MixinRecipe.get().createShapeless(result);
		
		int quantity = 1;
		Material material;
		
		for (Object object : objects)
		{
            switch (object) {
                case Number number -> {
                    if (number instanceof Integer integer) {
                        quantity = integer;
                    }
                }
                case Material material1 -> {
                    material = material1;

                    recipe.addIngredient(quantity, material);

                    quantity = 1;
                }
                case RecipeChoice recipeChoice -> recipe.addIngredient(recipeChoice);
                case null, default -> throw new IllegalArgumentException(String.valueOf(object));
            }
		}
		
		return recipe;
	}
	
	public static ShapelessRecipe addShapeless(ItemStack result, Object @NotNull ... objects)
	{
		ShapelessRecipe recipe = createShapeless(result, objects);
		Bukkit.getServer().addRecipe(recipe);
		return recipe;
	}
	
}
