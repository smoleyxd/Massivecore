package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.command.editor.annotation.EditorEditable;
import com.massivecraft.massivecore.command.editor.annotation.EditorMethods;
import com.massivecraft.massivecore.command.editor.annotation.EditorType;
import com.massivecraft.massivecore.command.editor.annotation.EditorVisible;
import com.massivecraft.massivecore.command.type.convert.TypeConverterBannerPatternType;
import com.massivecraft.massivecore.comparator.ComparatorSmart;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.DyeColor;
import org.bukkit.NamespacedKey;
import org.bukkit.block.banner.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.massivecraft.massivecore.item.DataItemStack.get;
import static com.massivecraft.massivecore.item.DataItemStack.set;

@EditorMethods(true)
public class DataBannerPattern implements Comparable<DataBannerPattern>
{
	// -------------------------------------------- //
	// DEFAULTS
	// -------------------------------------------- //
	
	public static final transient NamespacedKey DEFAULT_ID = null;
	public static final transient DyeColor DEFAULT_COLOR = DyeColor.WHITE;

	// -------------------------------------------- //
	// FIELDS > VERSION
	// -------------------------------------------- //

	@EditorEditable(false)
	@EditorVisible(false)
	private int version = 1;
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	@EditorType(TypeConverterBannerPatternType.class)
	private NamespacedKey id = null;
	public NamespacedKey getId() { return get(this.id, DEFAULT_ID); }
	public DataBannerPattern setId(NamespacedKey id) { this.id = set(id, DEFAULT_ID); return this; }

	private DyeColor color = null;
	public DyeColor getColor() { return get(this.color, DEFAULT_COLOR); }
	public DataBannerPattern setColor(DyeColor color) { this.color = set(color, DEFAULT_ID); return this; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public DataBannerPattern()
	{
		
	}
	
	// Minecraft 1.7 Compatibility
	@Contract("null -> fail")
	public DataBannerPattern(Object pattern)
	{
		if ( ! (pattern instanceof Pattern)) throw new IllegalArgumentException("pattern");
		this.write(pattern, false);
	}
	
	// -------------------------------------------- //
	// WRITE
	// -------------------------------------------- //
	
	// Minecraft 1.7 Compatibility
	public void write(Object pattern, boolean a2b)
	{
		if ( ! (pattern instanceof Pattern)) throw new IllegalArgumentException("pattern");
		WriterBannerPattern.get().write(this, (Pattern)pattern, a2b);
	}
	
	// -------------------------------------------- //
	// TO BUKKIT
	// -------------------------------------------- //
	
	// Minecraft 1.7 Compatibility
	@SuppressWarnings("unchecked")
	public <T> T toBukkit()
	{
		// Create
		Pattern ret = WriterBannerPattern.get().createOB();
		
		// Fill
		this.write(ret, true);
		
		// Return
		return (T) ret;
	}
	
	// -------------------------------------------- //
	// COMPARE & EQUALS & HASHCODE 
	// -------------------------------------------- //
	
	@Override
	public int compareTo(@NotNull DataBannerPattern that)
	{
		return ComparatorSmart.get().compare(
			this.getId(), that.getId(),
			this.getColor(), that.getColor()
		);
	}
	
	// TODO: Use compare instead to avoid bugs?
	@Contract(value = "null -> false", pure = true)
	@Override
	public boolean equals(Object object)
	{
		if ( ! (object instanceof DataBannerPattern that)) return false;
		
		return MUtil.equals(
			this.getId(), that.getId(),
			this.getColor(), that.getColor()
		);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(
			this.getId(),
			this.getColor()
		);
	}
	
}
