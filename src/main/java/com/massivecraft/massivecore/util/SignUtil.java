package com.massivecraft.massivecore.util;

import com.massivecraft.massivecore.util.reference.ReferenceMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class SignUtil
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	public final static String SPECIAL_PREFIX_LENIENT = "[";
	public final static String SPECIAL_PREFIX_STRICT = SPECIAL_PREFIX_LENIENT + ChatColor.DARK_GREEN;
	
	public final static String SPECIAL_SUFFIX_LENIENT = "]";
	public final static String SPECIAL_SUFFIX_STRICT = ChatColor.BLACK + SPECIAL_SUFFIX_LENIENT;
	
	// -------------------------------------------- //
	// SPECIAL LINE, TITLE & PADDING
	// -------------------------------------------- //
	// "special line": The whole first line with title and padding.
	// "special title": The title part without the padding.
	// "special padding": The special prefix and suffix.
	
	@Contract("null -> fail")
	public static @NotNull String getSanitizedSpecialTitle(String title)
	{
		if (title == null) throw new NullPointerException("title");
		
		title = title.trim();
		title = ChatColor.stripColor(title);
		title = Txt.upperCaseFirst(title);
		
		return title;
	}
	
	@Contract("null -> fail")
	public static @NotNull String getSpecialLine(String title)
	{
		if (title == null) throw new NullPointerException("title");
		
		title = getSanitizedSpecialTitle(title);
		
		return SPECIAL_PREFIX_STRICT + title + SPECIAL_SUFFIX_STRICT;
	}
	
	@Contract("null, _ -> fail")
	public static @Nullable String getSpecialTitle(String line, boolean strict)
	{
		if (line == null) throw new NullPointerException("line");
		
		String prefix = SPECIAL_PREFIX_STRICT;
		String suffix = SPECIAL_SUFFIX_STRICT;
		
		if (!strict)
		{
			line = line.trim();
			line = ChatColor.stripColor(line);
			
			prefix = SPECIAL_PREFIX_LENIENT;
			suffix = SPECIAL_SUFFIX_LENIENT;
		}
		
		if (!line.startsWith(prefix)) return null;
		if (!line.endsWith(suffix)) return null;
		
		String title = line.substring(prefix.length(), line.length() - suffix.length());
		
		title = getSanitizedSpecialTitle(title);
		
		return title;
	}
	
	@Contract("null, _ -> fail")
	public static @Nullable String getSpecialTitle(String[] lines, boolean strict)
	{
		if (lines == null) throw new NullPointerException("lines");
		String line = lines[0];
		return getSpecialTitle(line, strict);
	}
	
	@Contract("null, _ -> fail")
	public static @Nullable String getSpecialTitle(Sign sign, boolean strict)
	{
		if (sign == null) throw new NullPointerException("sign");
		String[] lines = sign.getLines();
		return getSpecialTitle(lines, strict);
	}
	
	@Contract("null, _ -> fail")
	public static @Nullable String getSpecialTitle(Block block, boolean strict)
	{
		if (block == null) throw new NullPointerException("block");
		Sign sign = getSign(block);
		if (sign == null) return null;
		return getSpecialTitle(sign, strict);
	}
	
	// -------------------------------------------- //
	// SPECIAL PERMISSION FIX
	// -------------------------------------------- //
	// Returns true if the result is a special sign of with the specified title.
	
	@Contract("null, _, _ -> fail; !null, null, _ -> fail; !null, !null, null -> fail")
	public static boolean handleSpecialPermissionFix(SignChangeEvent event, String title, String permissionId)
	{
		if (event == null) throw new NullPointerException("event");
		if (title == null) throw new NullPointerException("title");
		if (permissionId == null) throw new NullPointerException("permissionId");
		
		// If a player is changing a sign ...
		final Player player = event.getPlayer();
		
		// ... that leniently is a special sign ...
		String lenientTitle = getSpecialTitle(event.getLines(), false);
		if (lenientTitle == null) return false;
		
		// ... of the type we seek ...
		if ( ! title.equalsIgnoreCase(lenientTitle)) return false;
		
		// ... verify that the player has permission to create that type of sign ...
		if ( ! PermissionUtil.hasPermission(player, permissionId, true))
		{
			event.setCancelled(true);
			return false;
		}
		
		// ... and fix the first line.
		event.setLine(0, getSpecialLine(title));
		return true;
	}
	
	// -------------------------------------------- //
	// SPECIAL PILLAR
	// -------------------------------------------- //
	// These methods are used for detecting special sign pillars
	
	public static @Nullable Block getSpecialPillarTop(@NotNull Block block)
	{
		World world = block.getWorld();
		int maxHeight = world.getMaxHeight();
		
		while (isSign(block) && block.getY() < maxHeight)
		{
			if (getSpecialTitle(block, true) != null) return block;
			block = block.getRelative(0, 1, 0);
		}
		
		return null;
	}
	
	@Contract("null -> fail")
	public static @NotNull List<Block> getSpecialPillarFromTop(Block block)
	{
		if (block == null) throw new NullPointerException("block");
		if (getSpecialTitle(block, true) == null) throw new InvalidParameterException("block");
		
		List<Block> ret = new ArrayList<>();
		
		do
		{
			ret.add(block);
			block = block.getRelative(0, -1, 0);
		}
		while (isSign(block) && getSpecialTitle(block, true) == null);
				
		return ret;
	}
	
	@Contract("null -> fail")
	public static @Nullable List<Block> getSpecialPillar(Block block)
	{
		if (block == null) throw new NullPointerException("block");
		
		block = getSpecialPillarTop(block);
		if (block == null) return null;
		
		return getSpecialPillarFromTop(block);
	}
	
	@Contract("null, _ -> fail")
	public static @Nullable List<String> getSpecialPillarLines(Block block, @Nullable String title)
	{
		if (block == null) throw new NullPointerException("block");
		
		List<Block> blocks = getSpecialPillar(block);
		if (blocks == null) return null;
		
		List<String> lines = getLines(blocks);
		
		if (title != null)
		{
			String specialLine = lines.remove(0);
			String specialTitle = getSpecialTitle(specialLine, true);
			if (!title.equalsIgnoreCase(specialTitle)) return null;
		}
		
		return lines;
	}
	
	protected final static Set<Action> VALID_INTERACT_ACTIONS = EnumSet.of(
		// Action.LEFT_CLICK_BLOCK, // We must allow breaking the sign. 
		Action.RIGHT_CLICK_BLOCK
	);
	@Contract("null, _ -> fail; !null, null -> fail")
	public static @Nullable List<String> getSpecialPillarLines(PlayerInteractEvent event, String title)
	{
		// Arguments
		if (event == null) throw new NullPointerException("event");
		if (title == null) throw new NullPointerException("title");
		
		// Player
		final Player player = event.getPlayer();
		if (MUtil.isntPlayer(player)) return null;
		
		// Action
		if ( ! VALID_INTERACT_ACTIONS.contains(event.getAction())) return null;
		
		// Block
		Block block = event.getClickedBlock();
		if (block == null) return null;
		
		// Lines
		List<String> lines = getSpecialPillarLines(block, title);
		if (lines == null) return null;
		
		// Cancel
		event.setCancelled(true);
		
		// Return
		return lines;
	}
	
	// -------------------------------------------- //
	// IS SIGN
	// -------------------------------------------- //

	@Contract("null -> fail")
	public static boolean isSign(Material material)
	{
		if (material == null) throw new NullPointerException("material");

		return ReferenceMaterial.getMaterialsSign().contains(material);
	}
	
	@Contract("null -> fail")
	public static boolean isSign(Block block)
	{
		if (block == null) throw new NullPointerException("block");
		
		return isSign(block.getType());
	}
	
	// -------------------------------------------- //
	// GET SIGN
	// -------------------------------------------- //
	
	public static @Nullable Sign getSign(@NotNull Block block)
	{
		BlockState blockState = block.getState();
		if ( ! (blockState instanceof Sign)) return null;
		return (Sign)blockState;
	}
	
	// -------------------------------------------- //
	// GET LINES
	// -------------------------------------------- //
	
	@Contract("null -> fail")
	public static @NotNull List<String> getLines(List<@NotNull Block> blocks)
	{
		if (blocks == null) throw new NullPointerException("blocks");
		
		List<String> ret = new ArrayList<>();
		
		for (Block block : blocks)
		{
			List<String> lines = getLines(block);
			if (lines == null) continue;
			ret.addAll(lines);
		}
		
		return ret;
	}
	
	@Contract("null -> fail")
	public static @Nullable List<String> getLines(Block block)
	{
		if (block == null) throw new NullPointerException("block");
		
		Sign sign = getSign(block);
		if (sign == null) return null;
		
		return getLines(sign);
	}
	
	@Contract("null -> fail")
	public static @NotNull List<String> getLines(Sign sign)
	{
		if (sign == null) throw new NullPointerException("sign");
		
		// Create
		List<String> ret = new ArrayList<>();
		
		// Fill
		for (String line : sign.getLines())
		{
			if (line == null) continue;
			if (line.trim().isEmpty()) continue;
			ret.add(line);
		}
		
		// Return
		return ret;
	}
	
}
