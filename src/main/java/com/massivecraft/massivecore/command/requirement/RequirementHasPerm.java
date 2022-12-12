package com.massivecraft.massivecore.command.requirement;

import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.util.PermissionUtil;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;

public class RequirementHasPerm extends RequirementAbstract
{
	@Serial
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@Contract("_ -> new")
	public static @NotNull RequirementHasPerm get(Object permission) { return new RequirementHasPerm(permission); }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public RequirementHasPerm(Object permission)
	{
		this.permissionId = PermissionUtil.asPermissionId(permission);
	}
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final String permissionId;
	public String getPermissionId() { return this.permissionId; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public boolean apply(@NotNull CommandSender sender, MassiveCommand command)
	{
		return sender.hasPermission(this.permissionId);
	}
	
	@Override
	public String createErrorMessage(CommandSender sender, MassiveCommand command)
	{
		return PermissionUtil.getPermissionDeniedMessage(this.permissionId);
	}
	
}
