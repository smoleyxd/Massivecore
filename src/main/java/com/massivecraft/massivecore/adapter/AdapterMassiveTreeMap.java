package com.massivecraft.massivecore.adapter;

import com.massivecraft.massivecore.collections.MassiveTreeMap;
import com.massivecraft.massivecore.collections.MassiveTreeMapDef;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Map;

public class AdapterMassiveTreeMap extends AdapterMassiveX<MassiveTreeMap<?, ?, ?>>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final AdapterMassiveTreeMap i = new AdapterMassiveTreeMap();
	@Contract(pure = true)
	public static AdapterMassiveTreeMap get() { return i; }

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public MassiveTreeMap<?, ?, ?> create(Object parent, boolean def, JsonElement json, @NotNull Type typeOfT, JsonDeserializationContext context)
	{
		Object comparator = getComparator(typeOfT);
		if (def)
		{
			return new MassiveTreeMapDef(comparator, (Map)parent);
		}
		else
		{
			return new MassiveTreeMap(comparator, (Map)parent);
		}
	}
	
	// -------------------------------------------- //
	// GET COMPARATOR
	// -------------------------------------------- //
	
	public static @NotNull Object getComparator(@NotNull Type typeOfT)
	{
		return getNewArgumentInstance(typeOfT, 2);
	}
	
}
