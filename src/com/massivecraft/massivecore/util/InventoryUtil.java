package com.massivecraft.massivecore.util;

import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.adapter.AdapterInventory;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.comparator.ComparatorComparable;
import com.massivecraft.massivecore.comparator.ComparatorEntryValue;
import com.massivecraft.massivecore.event.EventMassiveCoreLorePriority;
import com.massivecraft.massivecore.item.DataItemStack;
import com.massivecraft.massivecore.mixin.MixinInventory;
import com.massivecraft.massivecore.predicate.Predicate;
import com.massivecraft.massivecore.predicate.PredicateNot;
import com.massivecraft.massivecore.predicate.PredicateStringEndsWith;
import com.massivecraft.massivecore.predicate.PredicateStringStartsWith;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class InventoryUtil
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	public static final int SIZE_PLAYER_STORAGE = 36;
	public static final int SIZE_PLAYER_ARMOR = 4;
	public static final int SIZE_PLAYER_EXTRA = 1;
	public static final int SIZE_PLAYER_ALL = SIZE_PLAYER_STORAGE + SIZE_PLAYER_ARMOR + SIZE_PLAYER_EXTRA;
	
	// 0 --> 36 (35 exclusive)
	public static final int INDEX_PLAYER_STORAGE_FROM = 0;
	public static final int INDEX_PLAYER_STORAGE_TO = INDEX_PLAYER_STORAGE_FROM + SIZE_PLAYER_STORAGE;
	
	// 36 --> 40 (39 exclusive)
	public static final int INDEX_PLAYER_ARMOR_FROM = INDEX_PLAYER_STORAGE_TO;
	public static final int INDEX_PLAYER_ARMOR_TO = INDEX_PLAYER_ARMOR_FROM + SIZE_PLAYER_ARMOR;
	
	// 40 --> 41 (40 exclusive)
	public static final int INDEX_PLAYER_EXTRA_FROM = INDEX_PLAYER_ARMOR_TO;
	public static final int INDEX_PLAYER_EXTRA_TO = INDEX_PLAYER_EXTRA_FROM + SIZE_PLAYER_EXTRA;
	
	// 40
	public static final int INDEX_PLAYER_SHIELD = INDEX_PLAYER_EXTRA_FROM;
	
	// -------------------------------------------- //
	// UTILS
	// -------------------------------------------- //
	
	@Contract(value = "null -> null", pure = true)
	public static PlayerInventory asPlayerInventory(Inventory inventory)
	{
		return (inventory instanceof PlayerInventory) ? (PlayerInventory) inventory : null;
	}
	
	// This is a modified copyOfRange implementation.
	// Boundary from is inclusive. Boundary to is exclusive. Just like in copyOfRange.
	// It does however return the original when possible.
	@Contract(pure = true)
	public static <T> T[] range(T @NotNull [] original, @Range(from = 0, to = Integer.MAX_VALUE) int fromInclusive, @Range(from = 0, to = Integer.MAX_VALUE) int toExclusive)
	{
		if (fromInclusive == 0 && toExclusive == original.length) return original;
		return Arrays.copyOfRange(original, fromInclusive, toExclusive);
	}
	
	@SafeVarargs
	public static <T> T @NotNull [] concat(T @NotNull [] first, T @NotNull [] @NotNull ... rest)
	{
		int totalLength = first.length;
		for (T[] array : rest)
		{
			totalLength += array.length;
		}
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : rest)
		{
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}
	
	// This method is used to clean out inconsistent air entries.
	@Contract("null -> null")
	public static ItemStack clean(ItemStack item)
	{
		if (item == null) return null;
		if (item.getType() == Material.AIR) return null;
		// NOTE: In 1.9 zero quantity is a thing.
		return item;
	}
	
	@Contract(mutates = "param1")
	public static void clean(@Nullable ItemStack @NotNull [] items)
	{
		for (int i = 0; i < items.length; i++)
		{
			items[i] = clean(items[i]);
		}
	}
	
	// -------------------------------------------- //
	// SLOTS
	// -------------------------------------------- //
	
	// HELMET
	
	@Contract("null -> null")
	public static ItemStack getHelmet(Inventory inventory)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return null;
		ItemStack ret = playerInventory.getHelmet();
		ret = clean(ret);
		return ret;
	}
	@Contract(mutates = "param1")
	public static void setHelmet(@Nullable Inventory inventory, ItemStack helmet)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return;
		playerInventory.setHelmet(helmet);
	}
	@Contract("null -> null")
	public static ItemStack getHelmet(HumanEntity human)
	{
		if (human == null) return null;
		return getHelmet(human.getInventory());
	}
	@Contract(mutates = "param1")
	public static void setHelmet(@Nullable HumanEntity human, ItemStack helmet)
	{
		if (human == null) return;
		setHelmet(human.getInventory(), helmet);
	}
	
	// CHESTPLATE
	
	@Contract("null -> null")
	public static ItemStack getChestplate(Inventory inventory)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return null;
		ItemStack ret = playerInventory.getChestplate();
		ret = clean(ret);
		return ret;
	}
	@Contract(mutates = "param1")
	public static void setChestplate(@Nullable Inventory inventory, ItemStack chestplate)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return;
		playerInventory.setChestplate(chestplate);
	}
	@Contract("null -> null")
	public static ItemStack getChestplate(HumanEntity human)
	{
		if (human == null) return null;
		return getChestplate(human.getInventory());
	}
	@Contract(mutates = "param1")
	public static void setChestplate(@Nullable HumanEntity human, ItemStack chestplate)
	{
		if (human == null) return;
		setChestplate(human.getInventory(), chestplate);
	}
	
	// LEGGINGS
	
	@Contract("null -> null")
	public static ItemStack getLeggings(Inventory inventory)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return null;
		ItemStack ret = playerInventory.getLeggings();
		ret = clean(ret);
		return ret;
	}
	@Contract(mutates = "param1")
	public static void setLeggings(@Nullable Inventory inventory, ItemStack leggings)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return;
		playerInventory.setLeggings(leggings);
	}
	@Contract("null -> null")
	public static ItemStack getLeggings(HumanEntity human)
	{
		if (human == null) return null;
		return getLeggings(human.getInventory());
	}
	@Contract(mutates = "param1")
	public static void setLeggings(@Nullable HumanEntity human, ItemStack leggings)
	{
		if (human == null) return;
		setLeggings(human.getInventory(), leggings);
	}
	
	// BOOTS
	
	@Contract("null -> null")
	public static ItemStack getBoots(Inventory inventory)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return null;
		ItemStack ret = playerInventory.getBoots();
		ret = clean(ret);
		return ret;
	}
	@Contract(mutates = "param1")
	public static void setBoots(@Nullable Inventory inventory, ItemStack boots)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return;
		playerInventory.setBoots(boots);
	}
	@Contract("null -> null")
	public static ItemStack getBoots(HumanEntity human)
	{
		if (human == null) return null;
		return getBoots(human.getInventory());
	}
	@Contract(mutates = "param1")
	public static void setBoots(@Nullable HumanEntity human, ItemStack boots)
	{
		if (human == null) return;
		setBoots(human.getInventory(), boots);
	}
	
	// WEAPON
	
	// DEPRECATED IN FAVOUR OF getMainHand
	// NOTE: We make sure to convert AIR into null due to a Bukkit API inconsistency.
	@Deprecated
	public static @Nullable ItemStack getWeapon(Inventory inventory)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return null;
		ItemStack ret = playerInventory.getItemInHand();
		ret = clean(ret);
		return ret;
		
	}
	@Deprecated
	public static void setWeapon(Inventory inventory, ItemStack weapon)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return;
		playerInventory.setItemInHand(weapon);
	}
	@Contract("null -> null")
	@Deprecated
	public static ItemStack getWeapon(HumanEntity human)
	{
		if (human == null) return null;
		ItemStack ret = human.getItemInHand();
		ret = clean(ret);
		return ret;
	}
	@Deprecated
	public static void setWeapon(HumanEntity human, ItemStack weapon)
	{
		if (human == null) return;
		human.setItemInHand(weapon);
	}
	
	// SHIELD
	// DEPRECATED IN FAVOUR OF getMainHand
	
	@Deprecated
	public static @Nullable ItemStack getShield(Inventory inventory)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return null;
		ItemStack[] contents = playerInventory.getContents();
		
		if (contents.length <= INDEX_PLAYER_SHIELD) return null;
		
		ItemStack ret = contents[INDEX_PLAYER_SHIELD];
		ret = clean(ret);
		return ret;
	}
	@Deprecated
	public static void setShield(Inventory inventory, ItemStack shield)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return;
		ItemStack[] contents = playerInventory.getContents();
		
		if (contents.length <= INDEX_PLAYER_SHIELD) return;
		
		inventory.setItem(INDEX_PLAYER_SHIELD, shield);
	}
	@Contract("null -> null")
	@Deprecated
	public static ItemStack getShield(HumanEntity human)
	{
		if (human == null) return null;
		return getShield(human.getInventory());
	}
	@Deprecated
	public static void setShield(HumanEntity human, ItemStack shield)
	{
		if (human == null) return;
		setShield(human.getInventory(), shield);
	}
	
	// MAIN HAND
	
	// NOTE: We make sure to convert AIR into null due to a Bukkit API inconsistency.
	@Contract("null -> null")
	public static ItemStack getMainHand(Inventory inventory)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return null;
		ItemStack ret = playerInventory.getItemInMainHand();
		ret = clean(ret);
		return ret;
		
	}
	@Contract(mutates = "param1")
	public static void setMainHand(@Nullable Inventory inventory, ItemStack weapon)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return;
		playerInventory.setItemInMainHand(weapon);
	}
	@Contract("null -> null")
	public static ItemStack getMainHand(HumanEntity human)
	{
		if (human == null) return null;
		return getMainHand(human.getInventory());
	}
	@Contract(mutates = "param1")
	public static void setMainHand(@Nullable HumanEntity human, ItemStack weapon)
	{
		if (human == null) return;
		setMainHand(human.getInventory(), weapon);
	}
	
	// OFF HAND
	
	// NOTE: We make sure to convert AIR into null due to a Bukkit API inconsistency.
	@Contract("null -> null")
	public static ItemStack getOffHand(Inventory inventory)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return null;
		ItemStack ret = playerInventory.getItemInOffHand();
		ret = clean(ret);
		return ret;
		
	}
	@Contract(mutates = "param1")
	public static void setOffHand(@Nullable Inventory inventory, ItemStack weapon)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return;
		playerInventory.setItemInOffHand(weapon);
	}
	@Contract("null -> null")
	public static ItemStack getOffHand(HumanEntity human)
	{
		if (human == null) return null;
		return getOffHand(human.getInventory());
	}
	@Contract(mutates = "param1")
	public static void setOffHand(@Nullable HumanEntity human, ItemStack weapon)
	{
		if (human == null) return;
		setOffHand(human.getInventory(), weapon);
	}
	
	// EQUIPMENT SLOTS
	
	public static @Nullable ItemStack getSlot(@Nullable Inventory inventory, @NotNull EquipmentSlot slot)
	{
		return switch (slot)
				   {
					   case HEAD -> getHelmet(inventory);
					   case CHEST -> getChestplate(inventory);
					   case LEGS -> getLeggings(inventory);
					   case FEET -> getBoots(inventory);
					   case HAND -> getMainHand(inventory);
					   case OFF_HAND -> getOffHand(inventory);
				   };
	}
	@Contract(mutates = "param1")
	public static void setSlot(@Nullable Inventory inventory, ItemStack item, @NotNull EquipmentSlot slot)
	{
		switch (slot)
		{
			case HEAD -> {
				setHelmet(inventory, item);
				return;
			}
			case CHEST -> {
				setChestplate(inventory, item);
				return;
			}
			case LEGS -> {
				setLeggings(inventory, item);
				return;
			}
			case FEET -> {
				setBoots(inventory, item);
				return;
			}
			case HAND -> {
				setMainHand(inventory, item);
				return;
			}
			case OFF_HAND -> {
				setOffHand(inventory, item);
				return;
			}
		}
		
		throw new RuntimeException("Unsupported EquipmentSlot: " + slot);
	}
	@Contract("null, _ -> null")
	public static ItemStack getSlot(HumanEntity human, @NotNull EquipmentSlot slot)
	{
		if (human == null) return null;
		return getSlot(human.getInventory(), slot);
	}
	public static void setSlot(@Nullable HumanEntity human, ItemStack item, @NotNull EquipmentSlot slot)
	{
		if (human == null) return;
		setSlot(human.getInventory(), item, slot);
	}
	
	// -------------------------------------------- //
	// CONTENTS SECTIONS
	// -------------------------------------------- //
	// When Reading:
	// The content sections NPE evade and aim to behave as the latest Minecraft version.
	// So rather than returning null for getContentsExtra() we create and return a new array.
	//
	// When Writing:
	// ...
	
	// All content varies over versions.
	// Before 1.9 it was getContents() + getArmorContents() + new ItemStack[1].
	// From and including 1.9 it's just getContents().
	@Contract("null -> null")
	public static ItemStack[] getContentsAll(Inventory inventory)
	{
		if (inventory == null) return null;
		ItemStack[] contents = inventory.getContents();
		ItemStack[] ret = contents;
		
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory != null && contents.length == SIZE_PLAYER_STORAGE)
		{
			ret = concat(contents, playerInventory.getArmorContents(), new ItemStack[SIZE_PLAYER_EXTRA]);
		}
		
		clean(ret);
		return ret;
	}
	@Contract(mutates = "param1")
	public static void setContentsAll(@Nullable Inventory inventory, ItemStack @NotNull [] all)
	{
		if (inventory == null) return;
		ItemStack[] contents = inventory.getContents();
		
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null)
		{
			inventory.setContents(range(all, 0, contents.length));
			return;
		}
		
		if (all.length < INDEX_PLAYER_STORAGE_TO) return;
		ItemStack[] storage = range(all, INDEX_PLAYER_STORAGE_FROM, INDEX_PLAYER_STORAGE_TO);
		setContentsStorage(playerInventory, storage);
		
		if (all.length < INDEX_PLAYER_ARMOR_TO) return;
		ItemStack[] armor = range(all, INDEX_PLAYER_ARMOR_FROM, INDEX_PLAYER_ARMOR_TO);
		setContentsArmor(playerInventory, armor);
		
		if (all.length < INDEX_PLAYER_EXTRA_TO) return;
		ItemStack[] extra = range(all, INDEX_PLAYER_EXTRA_FROM, INDEX_PLAYER_EXTRA_TO);
		setContentsExtra(playerInventory, extra);
	}
	
	// Storage contents implementation has varied.
	// Before 1.9 it was the same as getContents().
	// From and including 1.9 it became the 36 first of those slots.
	@Contract("null -> null")
	public static ItemStack[] getContentsStorage(Inventory inventory)
	{
		if (inventory == null) return null;
		ItemStack[] contents = inventory.getContents();
		ItemStack[] ret = contents;
		
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory != null)
		{
			ret = range(contents, INDEX_PLAYER_STORAGE_FROM, INDEX_PLAYER_STORAGE_TO);
		}
		
		clean(ret);
		return ret;
	}
	@Contract(mutates = "param1")
	public static void setContentsStorage(@Nullable Inventory inventory, ItemStack @NotNull [] storage)
	{
		if (inventory == null) return;
		ItemStack[] contents = inventory.getContents();
		
		// Calculate exclusive maximum
		int max = Math.min(storage.length, contents.length);
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory != null) max = Math.min(max, INDEX_PLAYER_STORAGE_TO);
		
		// Set as much as possible
		for (int i = 0; i < max; i++)
		{
			inventory.setItem(i, storage[i]);
		}
	}
	
	// Armor contents has always been implemented the same way and can be used directly.
	public static ItemStack @Nullable [] getContentsArmor(@Nullable Inventory inventory)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return null;
		
		ItemStack[] ret = playerInventory.getArmorContents();
		
		clean(ret);
		return ret;
	}
	@Contract(mutates = "param1")
	public static void setContentsArmor(@Nullable Inventory inventory, ItemStack[] armor)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return;
		
		playerInventory.setArmorContents(armor);
	}
	
	// The extra contents was added in 1.9.
	// It is then at the very end of all of the contents.
	// Slot 40 and forward even though it currently is just a single slot.
	public static ItemStack @Nullable [] getContentsExtra(@Nullable Inventory inventory)
	{
		PlayerInventory playerInventory = asPlayerInventory(inventory);
		if (playerInventory == null) return null;
		ItemStack[] contents = playerInventory.getContents();
		ItemStack[] ret = new ItemStack[SIZE_PLAYER_EXTRA];
		
		int max = SIZE_PLAYER_EXTRA;
		max = Math.min(max, contents.length - INDEX_PLAYER_EXTRA_FROM);
		
		if (max >= 0) System.arraycopy(contents, INDEX_PLAYER_EXTRA_FROM, ret, 0, max);
		
		clean(ret);
		return ret;
	}
	@Contract(mutates = "param1")
	public static void setContentsExtra(@Nullable Inventory intentory, ItemStack @NotNull [] extra)
	{
		PlayerInventory playerInventory = asPlayerInventory(intentory);
		if (playerInventory == null) return;
		ItemStack[] contents = playerInventory.getContents();
		
		int max = SIZE_PLAYER_EXTRA;
		max = Math.min(max, contents.length - INDEX_PLAYER_EXTRA_FROM);
		max = Math.min(max, extra.length);
		
		for (int i = 0; i < max; i++)
		{
			playerInventory.setItem(INDEX_PLAYER_EXTRA_FROM + i, extra[i]);
		}
	}
	
	// -------------------------------------------- //
	// UPDATES
	// -------------------------------------------- //
	
	public static void update(@Nullable HumanEntity human)
	{
		if (MUtil.isntPlayer(human)) return;
		Player player = (Player) human;
		player.updateInventory();
	}
	
	public static void updateSoon(final HumanEntity human)
	{
		Bukkit.getScheduler().scheduleSyncDelayedTask(MassiveCore.get(), () -> update(human));
	}
	
	public static void updateLater(final HumanEntity human)
	{
		Bukkit.getScheduler().scheduleSyncDelayedTask(MassiveCore.get(), () -> update(human), 1);
	}
	
	// -------------------------------------------- //
	// EVENT INTERPRETATION > WHERE
	// -------------------------------------------- //
	
	@Contract(pure = true)
	public static boolean isOutside(int rawSlot)
	{
		return rawSlot < 0;
	}
	
	@Contract(pure = true)
	public static boolean isTopInventory(int rawSlot, @NotNull Inventory inventory)
	{
		if (isOutside(rawSlot)) return false;
		return rawSlot < inventory.getSize();
	}
	
	@Contract(pure = true)
	public static boolean isBottomInventory(int rawSlot, @NotNull Inventory inventory)
	{
		if (isOutside(rawSlot)) return false;
		return rawSlot >= inventory.getSize();
	}
	
	@Contract(pure = true)
	public static boolean isOutside(@NotNull InventoryClickEvent event)
	{
		return isOutside(event.getRawSlot());
	}
	
	public static boolean isTopInventory(@NotNull InventoryClickEvent event)
	{
		return isTopInventory(event.getRawSlot(), event.getInventory());
	}
	
	public static boolean isBottomInventory(@NotNull InventoryClickEvent event)
	{
		return isBottomInventory(event.getRawSlot(), event.getInventory());
	}
	
	// -------------------------------------------- //
	// EVENT INTERPRETATION > ALTER
	// -------------------------------------------- //
	
	public static boolean isAltering(@NotNull InventoryInteractEvent event)
	{
		return getAlter(event).isAltering();
	}
	
	public static InventoryAlter getAlter(@NotNull InventoryInteractEvent event)
	{
		if (event instanceof InventoryClickEvent clickEvent)
		{
			return getAlter(clickEvent);
		}
		
		if (event instanceof InventoryDragEvent dragEvent)
		{
			return getAlter(dragEvent);
		}
		
		throw new IllegalArgumentException("Neither InventoryClickEvent nor InventoryDragEvent: " + event);
	}
	
	private static InventoryAlter getAlter(@NotNull InventoryClickEvent event)
	{
		if (isOutside(event)) return InventoryAlter.NONE;
		boolean topClicked = isTopInventory(event);
		InventoryAction action = event.getAction();
		
		if (topClicked)
		{
			switch (action)
			{
				// What is the best thing to do?
				case UNKNOWN:
				case SWAP_WITH_CURSOR:
					return InventoryAlter.BOTH;
				
				// Possibly both
				case HOTBAR_SWAP:
					int slotNum;
					if (event.getClick() == ClickType.SWAP_OFFHAND) slotNum = AdapterInventory.INDEX_PLAYER_SHIELD;
					else slotNum = event.getHotbarButton();
					
					ItemStack hotbar = event.getView().getBottomInventory().getItem(slotNum);
					ItemStack current = event.getCurrentItem();
					boolean give = isSomething(hotbar);
					boolean take = isSomething(current);
					
					return InventoryAlter.get(give, take);
				
				// Neither give nor take
				// FIXME merge these cases
				case NOTHING:
				case CLONE_STACK:
				case DROP_ONE_CURSOR:
				case DROP_ALL_CURSOR:
					return InventoryAlter.NONE;
				
				// Take
				// FIXME merge these cases
				case PICKUP_ALL:
				case DROP_ALL_SLOT:
				case DROP_ONE_SLOT:
				case HOTBAR_MOVE_AND_READD:
				case COLLECT_TO_CURSOR:
				case MOVE_TO_OTHER_INVENTORY:
				case PICKUP_SOME:
				case PICKUP_ONE:
				case PICKUP_HALF:
					return InventoryAlter.TAKE;
				
				// Give
				case PLACE_ALL:
				case PLACE_SOME:
				case PLACE_ONE:
					return InventoryAlter.GIVE;
				
			}
			throw new RuntimeException("Unsupported action: " + action);
		}
		else
		{
			// What is the best thing to do?
			if (action == InventoryAction.UNKNOWN) return InventoryAlter.BOTH;
			
			if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY) return InventoryAlter.GIVE;
			
			// This one will possibly take, but we cannot be 100% sure.
			// We will return TAKE for security reasons.
			if (action == InventoryAction.COLLECT_TO_CURSOR) return InventoryAlter.TAKE;
			
			return InventoryAlter.NONE;
		}
	}
	
	// Drag events by nature only matters when they affect the top inventory.
	// What you are holding in the cursor is already yours.
	// If you drag it into your own inventory you are not really taking anything.
	// If you drag into the top inventory however, you may both give and take.
	// You "take" by dragging over an existing item.
	private static @NotNull InventoryAlter getAlter(@NotNull InventoryDragEvent event)
	{
		// Create
		boolean giving = false;
		boolean taking = false;
		
		// Fill
		final Inventory inventory = event.getInventory();
		for (Entry<Integer, ItemStack> entry : event.getNewItems().entrySet())
		{
			int rawSlot = entry.getKey();
			if (InventoryUtil.isBottomInventory(rawSlot, inventory)) continue;
			
			ItemStack take = inventory.getItem(rawSlot);
			if (isSomething(take)) taking = true;
			
			ItemStack give = entry.getValue();
			if (isSomething(give)) giving = true;
		}
		
		// Return
		return InventoryAlter.get(giving, taking);
	}
	
	public enum InventoryAlter
	{
		GIVE,
		TAKE,
		NONE,
		BOTH,
		;
		
		@Contract(pure = true)
		public boolean isAltering()
		{
			return this != NONE;
		}
		
		@Contract(pure = true)
		public boolean isGiving()
		{
			return this == GIVE || this == BOTH;
		}
		
		@Contract(pure = true)
		public boolean isTaking()
		{
			return this == TAKE || this == BOTH;
		}
		
		@Contract(pure = true)
		public boolean isNothing()
		{
			return this == NONE;
		}
		
		@Contract(pure = true)
		public static @NotNull InventoryAlter get(boolean giving, boolean taking)
		{
			if (giving && taking) return BOTH;
			if (giving) return GIVE;
			if (taking) return TAKE;
			return NONE;
		}
		
	}
	
	// -------------------------------------------- //
	// EVENT INTERPRETATION > EQUIPPING
	// -------------------------------------------- //
	
	/**
	 * This method will return the ItemStack the player is trying to equip.
	 * If the click event would not result in equipping something null will be returned.
	 * Note that this algorithm is not perfect. It's an adequate guess.
	 *
	 * @param event The InventoryClickEvent to analyze.
	 * @return The ItemStack the player is trying to equip.
	 */
	public static @Nullable ItemStack isEquipping(@NotNull InventoryClickEvent event)
	{
		boolean isShiftClick = event.isShiftClick();
		InventoryType inventoryType = event.getInventory().getType();
		SlotType slotType = event.getSlotType();
		ItemStack cursor = event.getCursor();
		ItemStack currentItem = event.getCurrentItem();
		
		if (isShiftClick)
		{
			if (inventoryType != InventoryType.CRAFTING) return null;
			if (slotType == SlotType.CRAFTING) return null;
			if (slotType == SlotType.ARMOR) return null;
			if (slotType == SlotType.RESULT) return null;
			if (currentItem == null || currentItem.getType() == Material.AIR) return null;
			return currentItem;
		}
		else
		{
			if (slotType == SlotType.ARMOR)
			{
				return cursor;
			}
			return null;
		}
	}
	
	// -------------------------------------------- //
	// GET CHANGES
	// -------------------------------------------- //
	// In this section we interpret the changes made by inventory interact events.
	// The very same event may cause both giving and taking of multiple different items.
	// We return a list of entries:
	// > KEY: The raw and unmodified ItemStack.
	// > VALUE: The change in amount where positive means take. (count change measured in the "players inventory")
	// By choosing this return value we can provide the rawest data possible.
	// We never ever clone or modify the ItemStacks in any way. 
	// This means that the amount within the ItemStack key is irrelevant.
	// We can also avoid all kinds of oddities related to ItemStack equals and compare in the Bukkit API.
	
	public static @NotNull List<Entry<ItemStack, Integer>> getChanges(@Nullable InventoryInteractEvent event)
	{
		if (event instanceof InventoryClickEvent clickEvent)
		{
			return getChangesClick(clickEvent);
		}
		
		if (event instanceof InventoryDragEvent dragEvent)
		{
			return getChangesDrag(dragEvent);
		}
		
		return Collections.emptyList();
	}
	
	protected static @NotNull List<Entry<ItemStack, Integer>> getChangesClick(@NotNull InventoryClickEvent event)
	{
		// Create
		List<Entry<ItemStack, Integer>> ret = new MassiveList<>();
		
		// Fill
		final InventoryAlter alter = InventoryUtil.getAlter(event);
		final InventoryAction action = event.getAction();
		ItemStack item;
		int amount;
		
		// Give
		if (alter.isGiving())
		{
			// Special > MOVE_TO_OTHER_INVENTORY
			if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY)
			{
				item = event.getCurrentItem();
				
				ItemStack compare = item.clone();
				compare.setAmount(1);
				amount = InventoryUtil.roomLeft(event.getInventory(), compare, item.getAmount());
			}
			// Special > HOTBAR_SWAP
			else if (action == InventoryAction.HOTBAR_SWAP)
			{
				item = event.getView().getBottomInventory().getItem(event.getHotbarButton());
				
				amount = item.getAmount();
			}
			// Normal
			else
			{
				item = event.getCursor();
				
				amount = item.getAmount();
				if (action == InventoryAction.PLACE_ONE)
				{
					amount = 1;
				}
				else if (action == InventoryAction.PLACE_SOME)
				{
					int max = event.getCurrentItem().getType().getMaxStackSize();
					amount = max - event.getCurrentItem().getAmount();
				}
			}
			
			amount *= -1;
			ret.add(new SimpleEntry<>(item, amount));
		}
		
		// Take
		if (alter.isTaking())
		{
			item = event.getCurrentItem();
			
			amount = item.getAmount();
			if (action == InventoryAction.PICKUP_ONE) amount = 1;
			if (action == InventoryAction.PICKUP_HALF) amount = (int) Math.ceil(amount / 2.0);
			
			ret.add(new SimpleEntry<>(item, amount));
		}
		
		// Return
		return ret;
	}
	
	// Drag events by nature only matters when they affect the top inventory.
	// What you are holding in the cursor is already yours.
	// If you drag it into your own inventory you are not really taking anything.
	// If you drag into the top inventory however, you may both give and take.
	// You "take" by dragging over an existing item (since we don't do any math).
	protected static @NotNull List<Entry<ItemStack, Integer>> getChangesDrag(@NotNull InventoryDragEvent event)
	{
		// Create
		List<Entry<ItemStack, Integer>> ret = new MassiveList<>();
		
		// Fill
		final Inventory inventory = event.getInventory();
		for (Entry<Integer, ItemStack> entry : event.getNewItems().entrySet())
		{
			int rawSlot = entry.getKey();
			if (InventoryUtil.isBottomInventory(rawSlot, inventory)) continue;
			
			ItemStack take = inventory.getItem(rawSlot);
			if (isSomething(take)) ret.add(new SimpleEntry<>(take, +take.getAmount()));
			
			ItemStack give = entry.getValue();
			if (isSomething(give)) ret.add(new SimpleEntry<>(give, -give.getAmount()));
		}
		
		// Return
		return ret;
	}
	
	// -------------------------------------------- //
	// DEBUG
	// -------------------------------------------- //
	
	public static void debug(@NotNull InventoryClickEvent event)
	{
		System.out.println("===== DEBUG START =====");
		System.out.println("event.getAction() " + event.getAction());
		System.out.println("event.isLeftClick() " + event.isLeftClick());
		System.out.println("event.isRightClick() " + event.isRightClick());
		System.out.println("event.isShiftClick() " + event.isShiftClick());
		System.out.println("event.getClick() " + event.getClick());
		System.out.println("event.getCurrentItem() " + event.getCurrentItem());
		System.out.println("event.getCursor() " + event.getCursor());
		System.out.println("event.getHotbarButton() " + event.getHotbarButton());
		System.out.println("getInventory().getType() " + event.getInventory().getType());
		System.out.println("event.getRawSlot() " + event.getRawSlot());
		System.out.println("event.getResult() " + event.getResult());
		System.out.println("event.getSlot() " + event.getSlot());
		System.out.println("event.getSlotType() " + event.getSlotType());
		System.out.println("getView().getTopInventory().getType() " + event.getView().getTopInventory().getType());
		System.out.println("getView().getType() " + event.getView().getType());
		System.out.println("getView().getBottomInventory().getType() " + event.getView().getBottomInventory().getType());
		System.out.println("event.getWhoClicked() " + event.getWhoClicked());
		System.out.println("-----");
		System.out.println("isOutside(event) " + isOutside(event));
		System.out.println("isTopInventory(event) " + isTopInventory(event));
		System.out.println("isBottomInventory(event) " + isBottomInventory(event));
		System.out.println("getAlter(event) " + getAlter(event));
		System.out.println("isAltering(event) " + isAltering(event));
		System.out.println("isEquipping(event) " + isEquipping(event));
		System.out.println("===== DEBUG END =====");
	}
	
	// -------------------------------------------- //
	// IS EMPTY?
	// -------------------------------------------- //
	
	@Contract("null -> true")
	public static boolean isEmpty(Inventory inv)
	{
		if (inv == null) return true;
		
		for (ItemStack itemStack : inv.getContents())
		{
			if (isSomething(itemStack)) return false;
		}
		
		if (inv instanceof PlayerInventory pinv)
		{
			
			if (isSomething(pinv.getHelmet())) return false;
			if (isSomething(pinv.getChestplate())) return false;
			if (isSomething(pinv.getLeggings())) return false;
			if (isSomething(pinv.getBoots())) return false;
		}
		
		return true;
	}
	
	// -------------------------------------------- //
	// TYPE CHECKING
	// -------------------------------------------- //
	
	@Contract("null -> true")
	public static boolean isNothing(ItemStack itemStack)
	{
		if (itemStack == null) return true;
		if (itemStack.getAmount() == 0) return true;
		if (itemStack.getType() == Material.AIR) return true;
		return false;
	}
	
	@Contract("null -> false")
	public static boolean isSomething(ItemStack itemStack)
	{
		return !isNothing(itemStack);
	}
	
	// FIXME use modern logic
	public static void repair(@Nullable ItemStack itemStack)
	{
		// Check Null
		if (isNothing(itemStack)) return;
		
		ItemMeta meta = getMeta(itemStack);
		if (meta == null) return;
		
		if (!(meta instanceof Damageable)) return;
		
		((Damageable) meta).setDamage(0);
		itemStack.setItemMeta(meta);
	}
	
	public static boolean isRepairable(@NotNull Material material)
	{
		ItemMeta meta = createMeta(new ItemStack(material));
		if (meta == null) return false;
		
		return (meta instanceof Damageable);
	}
	
	@Contract("null -> false")
	public static boolean isPotion(ItemStack itemStack)
	{
		if (isNothing(itemStack)) return false;
		Material material = itemStack.getType();
		return isPotion(material);
	}
	
	@Contract("null -> false")
	public static boolean isPotion(Material material)
	{
		if (material == null) return false;
		return material.name().contains("POTION");
	}
	
	// -------------------------------------------- //
	// CLONE ITEMSTACKS/INVENTORY
	// -------------------------------------------- //
	
	@Contract("null -> null; !null -> new")
	public static ItemStack clone(ItemStack itemStack)
	{
		if (itemStack == null) return null;
		return new ItemStack(itemStack);
	}
	
	public static ItemStack @NotNull [] clone(@Nullable ItemStack @NotNull [] itemStacks)
	{
		ItemStack[] ret = new ItemStack[itemStacks.length];
		for (int i = 0; i < itemStacks.length; i++)
		{
			ItemStack stack = itemStacks[i];
			if (stack == null) continue;
			ret[i] = clone(itemStacks[i]);
		}
		return ret;
	}
	
	@Contract("null, _ -> null")
	public static Inventory clone(Inventory inventory, boolean playerSupport)
	{
		// Evade
		if (inventory == null) return null;
		
		// Create
		Inventory ret;
		if (inventory instanceof PlayerInventory && playerSupport)
		{
			ret = MixinInventory.get().createPlayerInventory();
		}
		else
		{
			InventoryHolder holder = inventory.getHolder();
			int size = inventory.getSize();
			if (inventory instanceof PlayerInventory) size = SIZE_PLAYER_STORAGE;
			String title = inventory.getType().getDefaultTitle();
			ret = MixinInventory.get().createInventory(holder, size, title);
		}
		
		// Fill
		ItemStack[] all = getContentsAll(inventory);
		all = clone(all);
		setContentsAll(ret, all);
		
		// Return
		return ret;
	}
	
	// -------------------------------------------- //
	// EQUALS
	// -------------------------------------------- //
	
	public static boolean equals(@Nullable ItemStack one, @Nullable ItemStack two)
	{
		if (isNothing(one)) return isNothing(two);
		if (isNothing(two)) return false;
		
		DataItemStack dataOne = DataItemStack.fromBukkit(one);
		DataItemStack dataTwo = DataItemStack.fromBukkit(two);
		return dataOne.equals(dataTwo);
	}
	
	@Contract("null, null -> true; null, !null -> false; !null, null -> false")
	public static boolean equals(ItemStack[] one, ItemStack[] two)
	{
		if (one == null) return two == null;
		if (two == null) return false;
		if (one.length != two.length) return false;
		for (int i = 0; i < one.length; i++)
		{
			if (!equals(one[i], two[i])) return false;
		}
		return true;
	}
	
	@Contract("null, null -> true; null, !null -> false; !null, null -> false")
	public static boolean equals(Inventory one, Inventory two)
	{
		if (one == null) return two == null;
		if (two == null) return false;
		if (!equals(one.getContents(), two.getContents())) return false;
		if (one instanceof PlayerInventory pone)
		{
			if (two instanceof PlayerInventory ptwo)
			{
				return equals(pone.getArmorContents(), ptwo.getArmorContents());
			}
			else
			{
				return false;
			}
		}
		return true;
	}
	
	// -------------------------------------------- //
	// SET CONTENT
	// -------------------------------------------- //
	// This one simply moves the content pointers from on inventory to another.
	// You may want to clone the from inventory first.
	
	public static void setAllContents(@NotNull Inventory from, @NotNull Inventory to)
	{
		to.setContents(from.getContents());
		if (from instanceof PlayerInventory pfrom)
		{
			if (to instanceof PlayerInventory pto)
			{
				pto.setHelmet(pfrom.getHelmet());
				pto.setChestplate(pfrom.getChestplate());
				pto.setLeggings(pfrom.getLeggings());
				pto.setBoots(pfrom.getBoots());
			}
		}
	}
	
	// -------------------------------------------- //
	// CAN I ADD MANY?
	// -------------------------------------------- //
	
	// Calculate how many times you could add this item to the inventory.
	// NOTE: This method does not alter the inventory.
	public static int roomLeft(@NotNull Inventory inventory, @NotNull ItemStack item, int limit)
	{
		// NOTE: We can not afford to clone player inventories here.
		inventory = clone(inventory, false);
		int ret = 0;
		while (limit <= 0 || ret < limit)
		{
			HashMap<Integer, ItemStack> result = inventory.addItem(item.clone());
			if (result.size() != 0) return ret;
			ret++;
		}
		return ret;
	}
	
	// NOTE: Use the roomLeft method first to ensure this method would succeed
	public static void addItemTimes(@NotNull Inventory inventory, @NotNull ItemStack item, int times)
	{
		for (int i = 0; i < times; i++)
		{
			inventory.addItem(item.clone());
		}
	}
	
	// -------------------------------------------- //
	// COUNT
	// -------------------------------------------- //
	
	public static int countSimilar(@NotNull Inventory inventory, ItemStack itemStack)
	{
		int ret = 0;
		for (ItemStack item : inventory.getContents())
		{
			if (item == null) continue;
			if (!item.isSimilar(itemStack)) continue;
			ret += item.getAmount();
		}
		return ret;
	}
	
	// -------------------------------------------- //
	// GETTERS AND SETTERS
	// -------------------------------------------- //
	
	// META
	
	@Contract("null -> null")
	@SuppressWarnings("unchecked")
	public static <T extends ItemMeta> T getMeta(ItemStack item)
	{
		if (item == null) return null;
		if (!item.hasItemMeta()) return null;
		return (T) item.getItemMeta();
	}
	
	@Contract("null -> null")
	@SuppressWarnings("unchecked")
	public static <T extends ItemMeta> T createMeta(ItemStack item)
	{
		if (item == null) return null;
		return (T) item.getItemMeta();
	}
	
	// DISPLAY NAME
	
	@Contract("null -> null")
	public static String getDisplayName(ItemStack item)
	{
		ItemMeta meta = getMeta(item);
		if (meta == null) return null;
		
		if (!meta.hasDisplayName()) return null;
		return meta.getDisplayName();
	}
	
	public static void setDisplayName(@Nullable ItemStack item, String displayName)
	{
		ItemMeta meta = createMeta(item);
		if (meta == null) return;
		
		meta.setDisplayName(displayName);
		item.setItemMeta(meta);
	}
	
	@Contract("null, null -> true")
	public static boolean isDisplayName(ItemStack item, String displayName)
	{
		String value = getDisplayName(item);
		return MUtil.equals(value, displayName);
	}
	
	// LORE
	
	public static @Nullable List<String> getLore(@Nullable ItemStack item)
	{
		ItemMeta meta = getMeta(item);
		if (meta == null) return null;
		
		if (!meta.hasLore()) return null;
		return meta.getLore();
	}
	
	@Contract(mutates = "param1")
	public static void setLore(@Nullable ItemStack item, @Nullable Collection<String> lore)
	{
		ItemMeta meta = createMeta(item);
		if (meta == null) return;
		
		meta.setLore(lore == null ? null : new MassiveList<>(lore));
		item.setItemMeta(meta);
	}
	
	@Contract(mutates = "param1")
	public static void setLore(@Nullable ItemStack item, String @NotNull ... lore)
	{
		setLore(item, Arrays.asList(lore));
	}
	
	@Contract(mutates = "param1")
	public static void addLore(@Nullable ItemStack item, Collection<String> lore)
	{
		List<String> lines = getLore(item);
		if (lines == null) lines = new MassiveList<>();
		lines.addAll(lore);
		InventoryUtil.setLore(item, lines);
	}
	
	@Contract(mutates = "param1")
	public static void addLore(@Nullable ItemStack item, String @NotNull ... lore)
	{
		addLore(item, Arrays.asList(lore));
	}
	
	// -------------------------------------------- //
	// SORT LORE
	// -------------------------------------------- //
	
	public static @NotNull List<String> getSortedLore(@NotNull ItemStack item)
	{
		if (!createMeta(item).hasLore()) return Collections.emptyList();
		
		EventMassiveCoreLorePriority event = new EventMassiveCoreLorePriority(item);
		event.run();
		
		List<Entry<String, Integer>> entries = event.getLore();
		// Note: Comparator cast is necessary for Maven to compile, even if the IDE doesn't complain.
		Comparator<Entry<? super String, ? super Integer>> comparator = (Comparator) ComparatorEntryValue.get(ComparatorComparable.get());
		entries.sort(comparator);
		
		List<String> ret = new MassiveList<>();
		for (Entry<String, Integer> entry : entries)
		{
			ret.add(entry.getKey());
		}
		return ret;
	}
	
	@Contract(mutates = "param")
	public static void sortLore(@Nullable ItemStack item)
	{
		if (item == null) return;
		if (!createMeta(item).hasLore()) return;
		
		List<String> lore = getSortedLore(item);
		InventoryUtil.setLore(item, lore);
	}
	public static void sortLore(@Nullable Iterable<ItemStack> items)
	{
		if (items == null) return;
		for (ItemStack item : items)
		{
			sortLore(item);
		}
	}
	
	// -------------------------------------------- //
	// LORE PREFIX
	// -------------------------------------------- //
	
	// Return true on change
	@Contract(value = "_, null -> fail", mutates = "param1")
	public static boolean removeLoreMatching(@Nullable ItemStack item, Predicate<String> predicate)
	{
		if (predicate == null) throw new NullPointerException("predicate");
		
		List<String> lore = getLore(item);
		if (lore == null) return false;
		
		List<String> newLore = lore.stream().filter(PredicateNot.get(predicate)).collect(Collectors.toList());
		if (lore.size() != newLore.size())
		{
			setLore(item, newLore);
			return true;
		}
		
		return false;
	}
	
	@Contract(mutates = "param1")
	public static boolean removeLoreWithPrefix(@Nullable ItemStack item, @NotNull String prefix)
	{
		return removeLoreMatching(item, PredicateStringStartsWith.get(prefix));
	}
	
	@Contract(mutates = "param1")
	public static boolean removeLoreWithSuffix(@Nullable ItemStack item, @NotNull String suffix)
	{
		return removeLoreMatching(item, PredicateStringEndsWith.get(suffix));
	}
	
	@Contract("_, null -> fail; null, !null -> null")
	public static List<String> getLoreMatching(ItemStack item, Predicate<String> predicate)
	{
		if (predicate == null) throw new NullPointerException("predicate");
		
		List<String> lore = getLore(item);
		if (lore == null) return null;
		
		return lore.stream().filter(predicate).collect(Collectors.toList());
	}
	
	public static List<String> getLoreWithPrefix(ItemStack item, @NotNull String prefix)
	{
		return getLoreMatching(item, PredicateStringStartsWith.get(prefix));
	}
	
	public static List<String> getLoreWithSuffix(ItemStack item, @NotNull String suffix)
	{
		return getLoreMatching(item, PredicateStringEndsWith.get(suffix));
	}
}
