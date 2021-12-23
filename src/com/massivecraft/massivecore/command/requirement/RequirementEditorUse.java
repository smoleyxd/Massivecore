package com.massivecraft.massivecore.command.requirement;

import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.editor.CommandEditAbstract;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.command.CommandSender;

import java.io.Serial;

public class RequirementEditorUse extends RequirementAbstract
{
	@Serial
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final RequirementEditorUse i = new RequirementEditorUse();
	public static RequirementEditorUse get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public boolean apply(CommandSender sender, MassiveCommand command)
	{
		if ( ! (command instanceof CommandEditAbstract<?, ?> commandEditor)) return false;
		return commandEditor.getSettings().getUsed(sender) != null;
	}
	
	@Override
	public String createErrorMessage(CommandSender sender, MassiveCommand command)
	{
		if ( ! (command instanceof CommandEditAbstract<?, ?> commandEditor)) return Txt.parse("<b>This is not an editor!");
		
		String noun = commandEditor.getSettings().getObjectType().getName();
		String aan = Txt.aan(noun);
		
		return Txt.parse("<b>You must use %s %s to edit it.", aan, noun);
	}
	
}
