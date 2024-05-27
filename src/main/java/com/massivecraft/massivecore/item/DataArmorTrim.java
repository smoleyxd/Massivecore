package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.command.editor.annotation.EditorMethods;
import com.massivecraft.massivecore.comparator.ComparatorSmart;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.massivecraft.massivecore.item.DataItemStack.get;
import static com.massivecraft.massivecore.item.DataItemStack.set;

@EditorMethods(true)
public class DataArmorTrim implements Comparable<DataArmorTrim>
{
	// -------------------------------------------- //
	// DEFAULTS
	// -------------------------------------------- //
	
	public static final transient NamespacedKey DEFAULT_MATERIAL = NamespacedKey.minecraft("iron");
	public static final transient NamespacedKey DEFAULT_PATTERN = NamespacedKey.minecraft("sentry");
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private NamespacedKey material = null;
	public NamespacedKey getMaterial() { return get(this.material, DEFAULT_MATERIAL); }
	public DataArmorTrim setMaterial(NamespacedKey id) { this.material = set(id, DEFAULT_MATERIAL); return this; }

	private NamespacedKey pattern = null;
	public NamespacedKey getPattern() { return get(this.pattern, DEFAULT_PATTERN); }
	public DataArmorTrim setPattern(NamespacedKey color) { this.pattern = set(color, DEFAULT_PATTERN); return this; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public DataArmorTrim()
	{
	
	}
	
	// Minecraft 1.7 Compatibility
	@Contract("null -> fail")
	public DataArmorTrim(Object armorTrim)
	{
		if ( ! (armorTrim instanceof ArmorTrim)) throw new IllegalArgumentException("pattern");
		this.write(armorTrim, false);
	}
	
	// -------------------------------------------- //
	// WRITE
	// -------------------------------------------- //
	
	// Minecraft 1.7 Compatibility
	public void write(Object armorTrim, boolean a2b)
	{
		if ( ! (armorTrim instanceof ArmorTrim)) throw new IllegalArgumentException("armorTrim");
		WriterArmorTrim.get().write(this, (ArmorTrim)armorTrim, a2b);
	}
	
	// -------------------------------------------- //
	// TO BUKKIT
	// -------------------------------------------- //
	
	// Minecraft 1.7 Compatibility
	@SuppressWarnings("unchecked")
	public <T> T toBukkit()
	{
		// Create
		ArmorTrim ret = WriterArmorTrim.get().createOB();
		
		// Fill
		this.write(ret, true);
		
		// Return
		return (T) ret;
	}
	
	// -------------------------------------------- //
	// COMPARE & EQUALS & HASHCODE 
	// -------------------------------------------- //
	
	@Override
	public int compareTo(@NotNull DataArmorTrim that)
	{
		return ComparatorSmart.get().compare(
			this.getMaterial(), that.getMaterial(),
			this.getPattern(), that.getPattern()
		);
	}
	
	// TODO: Use compare instead to avoid bugs?
	@Contract(value = "null -> false", pure = true)
	@Override
	public boolean equals(Object object)
	{
		if ( ! (object instanceof DataArmorTrim that)) return false;
		
		return MUtil.equals(
			this.getMaterial(), that.getMaterial(),
			this.getPattern(), that.getPattern()
		);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(
			this.getMaterial(),
			this.getPattern()
		);
	}
	
}
