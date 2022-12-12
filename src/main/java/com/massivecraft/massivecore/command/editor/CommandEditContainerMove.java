package com.massivecraft.massivecore.command.editor;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandEditContainerMove<O, V> extends CommandEditContainerAbstract<O, V>
{	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CommandEditContainerMove(@NotNull EditSettings<O> settings, @NotNull Property<O, V> property)
	{
		// Super	
		super(settings, property);

		// Parameters
		this.addParameter(TypeInteger.get(), "indexFrom");
		this.addParameter(TypeInteger.get(), "indexTo");
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void alterElements(@NotNull List<Object> elements) throws MassiveException
	{
		// Args
		int indexFrom = this.readArg();
		int indexTo = this.readArg();
		
		// Alter
		Object element = elements.remove(indexFrom);
		elements.add(indexTo, element);
	}
	
}
