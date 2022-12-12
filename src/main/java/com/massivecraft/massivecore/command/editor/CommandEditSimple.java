package com.massivecraft.massivecore.command.editor;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.Type;
import com.massivecraft.massivecore.command.type.TypeNullable;
import org.jetbrains.annotations.NotNull;

public class CommandEditSimple<O, V> extends CommandEditAbstract<O, V>
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public CommandEditSimple(@NotNull EditSettings<O> settings, @NotNull Property<O, V> property)
	{
		// Super
		super(settings, property, null);
		
		// Parameters
		if (property.isEditable())
		{
			Type<V> type = this.getValueType();
			if (property.isNullable()) type = TypeNullable.get(type);
			this.addParameter(type, "set", "show", true);	
		}
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void perform() throws MassiveException
	{
		// Show
		if ( ! this.argIsSet(0))
		{
			this.show(1);
			return;
		}
		
		// Arguments
		V after = this.readArg();
		
		// Validate
		if (after == null) this.requireNullable();
		
		// Apply
		this.attemptSet(after);
	}
	
}
