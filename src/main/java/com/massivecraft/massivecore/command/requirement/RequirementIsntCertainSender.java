package com.massivecraft.massivecore.command.requirement;

import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.mixin.MixinDisplayName;
import com.massivecraft.massivecore.util.IdUtil;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;

public class RequirementIsntCertainSender extends RequirementAbstract
{
	@Serial
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@Contract("_ -> new")
	public static @NotNull RequirementIsntCertainSender get(@NotNull Object senderObject) { return new RequirementIsntCertainSender(senderObject); }
	public RequirementIsntCertainSender(@NotNull Object senderObject) { this.senderId = IdUtil.getId(senderObject); }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final String senderId;
	public @NotNull String getSenderId() { return this.senderId; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public boolean apply(CommandSender sender, MassiveCommand command)
	{
		return !this.getSenderId().equalsIgnoreCase(IdUtil.getId(sender));
	}
	
	@Override
	public String createErrorMessage(CommandSender sender, MassiveCommand command)
	{
		return Txt.parse("<b>Player can't be <h>%s<b>.", MixinDisplayName.get().getDisplayName(this.getSenderId(), sender));
	}
	
}
