package com.massivecraft.massivecore.command.editor;

import com.massivecraft.massivecore.command.type.RegistryType;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.TypeSingleton;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class CommandEditSingleton<O> extends CommandEditProperties<O, O>
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CommandEditSingleton(@NotNull O object)
	{
		this(object, getType(object));
	}
	
	public CommandEditSingleton(@NotNull O object, @NotNull Type<O> typeObject)
	{
		super(createEditSettings(object, typeObject), new PropertyThis<>(typeObject));
		String name = typeObject.getName(object);
		this.setAliases(name);
		this.setDesc("edit " + name);
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	public static <O> @NotNull EditSettings<O> createEditSettings(O object, Type<O> typeObject)
	{
		EditSettings<O> ret = new EditSettings<>(typeObject);
		
		PropertyUsed<O> usedProperty = new PropertyUsed<>(ret, object);
		ret.setUsedProperty(usedProperty);
		
		return ret;
	}
	
	@Contract("null -> fail")
	@SuppressWarnings("unchecked")
	public static <O> @NotNull Type<O> getType(O object)
	{
		if (object == null) throw new NullPointerException("object");

		// Get the return value
		Type<O> ret = TypeSingleton.get(object);
		
		// If no registered type exists. Use this one.
		if ( ! RegistryType.isRegistered(object.getClass()))
		{
			RegistryType.register((Class<O>) object.getClass(), ret);
		}
		
		return ret;
	}
	
}
