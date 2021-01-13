package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.nms.NmsPersistentData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class WriterItemStackMetaPersistentData extends WriterAbstractItemStackMetaField<ItemMeta, Map<String, Object>, Map<String, Object>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final WriterItemStackMetaPersistentData i = new WriterItemStackMetaPersistentData();
	public static WriterItemStackMetaPersistentData get() { return i; }
	
	public WriterItemStackMetaPersistentData()
	{
		super(ItemMeta.class);
	}
	
	// -------------------------------------------- //
	// PERS
	// -------------------------------------------- //
	
	private static class ObjectPersistentDataType implements PersistentDataType<Object, Object> {
		
		private final Class<Object> primitiveType = Object.class;
		
		// -------------------------------------------- //
		// INSTANCE & CONSTRUCT
		// -------------------------------------------- //
		
		private static final PersistentDataType<Object, Object> i = new ObjectPersistentDataType();
		public static PersistentDataType<Object, Object> get() { return i; }
		
		// -------------------------------------------- //
		// UTIL
		// -------------------------------------------- //
		
		public Class<Object> getPrimitiveType() {
			return primitiveType;
		}
		
		public Class<Object> getComplexType() {
			return primitiveType;
		}
		
		public Object toPrimitive(@NotNull Object complex, @NotNull PersistentDataAdapterContext context) {
			return complex;
		}
		
		public Object fromPrimitive(@NotNull Object primitive, @NotNull PersistentDataAdapterContext context) {
			return primitive;
		}
	}
	
	// -------------------------------------------- //
	// ACCESS
	// -------------------------------------------- //

	@Override
	public Map<String, Object> getA(DataItemStack ca, ItemStack d)
	{
		return ca.getPersistentData();
	}

	@Override
	public void setA(DataItemStack ca, Map<String, Object> fa, ItemStack d)
	{
		ca.setPersistentData(fa);
	}

	@Override
	public Map<String, Object> getB(ItemMeta cb, ItemStack d)
	{
		PersistentDataContainer container = cb.getPersistentDataContainer();
		return NmsPersistentData.get().getPersistentData(container);
	}

	@Override
	public void setB(ItemMeta cb, Map<String, Object> fb, ItemStack d)
	{
		PersistentDataContainer container = cb.getPersistentDataContainer();
		NmsPersistentData.get().setPersistentData(container, fb);
	}
	
}
