package com.massivecraft.massivecore.command.requirement;

import com.massivecraft.massivecore.Lang;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.Serial;

public class RequirementIsntPlayer extends RequirementAbstract
{
	@Serial
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final RequirementIsntPlayer i = new RequirementIsntPlayer();
	public static RequirementIsntPlayer get() { return i; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public boolean apply(CommandSender sender, MassiveCommand command)
	{
		return ! (sender instanceof Player);
	}
	
	@Override
	public String createErrorMessage(CommandSender sender, MassiveCommand command)
	{
		return Txt.parse(Lang.COMMAND_SENDER_MUSNT_BE_PLAYER);
	}
	
}
