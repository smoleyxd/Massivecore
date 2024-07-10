package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.command.editor.annotation.EditorEditable;
import com.massivecraft.massivecore.command.editor.annotation.EditorMethods;
import com.massivecraft.massivecore.command.editor.annotation.EditorVisible;
import com.massivecraft.massivecore.comparator.ComparatorSmart;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.massivecraft.massivecore.item.DataItemStack.get;
import static com.massivecraft.massivecore.item.DataItemStack.set;

@SuppressWarnings("UnstableApiUsage")
@EditorMethods(true)
public class DataAttributeModifier implements Comparable<DataAttributeModifier>
{
	// -------------------------------------------- //
	// DEFAULTS
	// -------------------------------------------- //
	
	public static final transient Attribute DEFAULT_ATTRIBUTE = Attribute.GENERIC_MAX_HEALTH;
	public static final transient NamespacedKey DEFAULT_KEY = NamespacedKey.fromString("Modifier");
	public static final transient double DEFAULT_AMOUNT = 0;
	public static final transient Operation DEFAULT_OPERATION = Operation.ADD_NUMBER;
	public static final transient String DEFAULT_SLOT_GROUP = EquipmentSlotGroup.ANY.toString();

	// -------------------------------------------- //
	// FIELDS > VERSION
	// -------------------------------------------- //

	@EditorEditable(false)
	@EditorVisible(false)
	private int version = 1;
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private Attribute attribute;
	public Attribute getAttribute() { return get(this.attribute, DEFAULT_ATTRIBUTE); }
	public DataAttributeModifier setAttribute(@NotNull Attribute attribute) { this.attribute = set(attribute, DEFAULT_ATTRIBUTE); return this; }
	
	private NamespacedKey key;
	public NamespacedKey getKey() { return get(this.key, DEFAULT_KEY); }
	public DataAttributeModifier setKey(@NotNull NamespacedKey key) { this.key = set(key, DEFAULT_KEY); return this; }
	
	private Double amount = null;
	public double getAmount() { return get(this.amount, DEFAULT_AMOUNT); }
	public DataAttributeModifier setAmount(double amount) { this.amount = set(amount, DEFAULT_AMOUNT); return this; }
	
	private Operation operation = null;
	public Operation getOperation() { return get(this.operation, DEFAULT_OPERATION); }
	public DataAttributeModifier setOperation(Operation operation) { this.operation = set(operation, DEFAULT_OPERATION); return this; }
	
	private String slot = null;
	public String getSlot() { return get(this.slot, DEFAULT_SLOT_GROUP); }
	public DataAttributeModifier setSlot(String slot) { this.slot = set(slot, DEFAULT_SLOT_GROUP); return this; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public DataAttributeModifier()
	{
	
	}
	
	public DataAttributeModifier(AttributeModifier attributeModifier)
	{
		this.write(attributeModifier, false);
	}
	
	// -------------------------------------------- //
	// WRITE
	// -------------------------------------------- //
	
	public void write(AttributeModifier attributeModifier, boolean a2b)
	{
		WriterAttributeModifier.get().write(this, attributeModifier, a2b);
	}
	
	// -------------------------------------------- //
	// TO BUKKIT
	// -------------------------------------------- //
	
	public AttributeModifier toBukkit()
	{
		// Create
		AttributeModifier ret = WriterAttributeModifier.get().createOB();
		
		// Fill
		this.write(ret, true);
		
		// Return
		return ret;
	}
	
	// -------------------------------------------- //
	// COMPARE & EQUALS & HASHCODE 
	// -------------------------------------------- //
	
	@Override
	public int compareTo(@NotNull DataAttributeModifier that)
	{
		return ComparatorSmart.get().compare(
			this.getAttribute(), that.getAttribute(),
			this.getKey(), that.getKey(),
			this.getAmount(), that.getAmount(),
			this.getOperation(), that.getOperation(),
			this.getSlot(), that.getSlot()
		);
	}
	
	// TODO: Use compare instead to avoid bugs?
	@Contract(value = "null -> false", pure = true)
	@Override
	public boolean equals(Object object)
	{
		if ( ! (object instanceof DataAttributeModifier that)) return false;
		
		return MUtil.equals(
			this.getAttribute(), that.getAttribute(),
			this.getKey(), that.getKey(),
			this.getAmount(), that.getAmount(),
			this.getOperation(), that.getOperation(),
			this.getSlot(), that.getSlot()
		);
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(
			this.getAttribute(),
			this.getKey(),
			this.getAmount(),
			this.getOperation(),
			this.getSlot()
		);
	}
	
}
