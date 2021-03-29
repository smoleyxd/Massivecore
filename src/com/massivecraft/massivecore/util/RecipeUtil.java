package com.massivecraft.massivecore.util;

import com.massivecraft.massivecore.mixin.MixinRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

// NOTE: This utility targets 1.9 and will crash on older servers.
public class RecipeUtil
{
	// ------------------------------------------- //
	// POTION
	// -------------------------------------------- //
	
	public static @NotNull ItemStack createPotionItemStack(PotionType type, Material material, boolean upgraded, boolean extended, int amount)
	{
		ItemStack ret = new ItemStack(material, amount);
		PotionMeta meta = InventoryUtil.createMeta(ret);
		PotionData data = new PotionData(type, extended, upgraded);
		meta.setBasePotionData(data);
		ret.setItemMeta(meta);
		return ret;
	}
	
	public static ShapelessRecipe createPotion(PotionType type, Material material, boolean upgraded, boolean extended, Object... objects)
	{
		// When brewing you actually get 3 potions.
		final int amount = 3;
		ItemStack item = createPotionItemStack(type, material, upgraded, extended, amount);
		return createShapeless(item, objects);
	}
	
	public static ShapelessRecipe addPotion(PotionType type, Material material, boolean upgraded, boolean extended, Object... objects)
	{
		ShapelessRecipe recipe = createPotion(type, material, upgraded, extended, objects);
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
			if (object instanceof Number)
			{
				if (object instanceof Integer)
				{
					quantity = (Integer) object;
				}
			}
			else if (object instanceof Material)
			{
				material = (Material)object;
				
				recipe.addIngredient(quantity, material);
				
				quantity = 1;
			}
			else if (object instanceof RecipeChoice)
			{
				recipe.addIngredient((RecipeChoice) object);
			}
			else
			{
				throw new IllegalArgumentException(String.valueOf(object));
			}
		}
		
		return recipe;
	}
	
	public static ShapelessRecipe addShapeless(ItemStack result, Object... objects)
	{
		ShapelessRecipe recipe = createShapeless(result, objects);
		Bukkit.getServer().addRecipe(recipe);
		return recipe;
	}
	
}
