package com.massivecraft.massivecore.command.requirement;

import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.editor.CommandEditAbstract;
import com.massivecraft.massivecore.command.editor.Property;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;

public class RequirementEditorPropertyCreated extends RequirementAbstract
{
	@Serial
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	protected final boolean createdTarget;
	public boolean isCreatedTarget() { return this.createdTarget; }
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@Contract("_ -> new")
	public static @NotNull RequirementEditorPropertyCreated get(boolean createdTarget) { return new RequirementEditorPropertyCreated(createdTarget); }
	public RequirementEditorPropertyCreated(boolean createdTarget)
	{
		this.createdTarget = createdTarget;
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public boolean apply(CommandSender sender, MassiveCommand command)
	{
		return this.applyInner(sender, command);
	}
	
	public <O, V> boolean applyInner(CommandSender sender, MassiveCommand command)
	{
		if ( ! (command instanceof CommandEditAbstract)) return false;
		
		@SuppressWarnings("unchecked")
		CommandEditAbstract<O, V> commandEditor = (CommandEditAbstract<O, V>)command;
		
		Property<O, V> property = commandEditor.getProperty();
		if (property == null) return false;
		
		O used = commandEditor.getObject(sender);
		if (used == null) return false;
		
		boolean created = (property.getRaw(used) != null);
		
		return created == this.isCreatedTarget();
	}
	
	@Override
	public String createErrorMessage(CommandSender sender, MassiveCommand command)
	{
		if ( ! (command instanceof CommandEditAbstract<?, ?> commandEditor)) return Txt.parse("<b>This is not an editor!");
		
		Property<?, ?> property = commandEditor.getProperty();
		return Txt.parse("<b>You must " + (this.isCreatedTarget() ? "create" : "delete") + " " + (property != null ? property.getName() : "the property")  + " before you " + getDesc(command) + ".");
	}
	
}
