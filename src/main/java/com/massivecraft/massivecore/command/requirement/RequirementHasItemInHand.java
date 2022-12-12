package com.massivecraft.massivecore.command.requirement;

import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.type.TypeItemStack;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;

public class RequirementHasItemInHand extends RequirementAbstract
{
	@Serial
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final RequirementHasItemInHand i = new RequirementHasItemInHand(TypeItemStack.get());
	public static RequirementHasItemInHand get() { return i; }
	
	@Contract("_ -> new")
	public static @NotNull RequirementHasItemInHand get(TypeItemStack innerType) { return new RequirementHasItemInHand(innerType); }
	@Contract("_ -> new")
	public static @NotNull RequirementHasItemInHand get(Material... materialWhitelist) { return get(TypeItemStack.get(materialWhitelist)); }
	
	public RequirementHasItemInHand(@NotNull TypeItemStack innerType) { this.innerType = innerType; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final TypeItemStack innerType;
	public @NotNull TypeItemStack getInnerType() { return this.innerType; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public boolean apply(CommandSender sender, MassiveCommand command)
	{
		return this.getInnerType().isValid(null, sender);
	}
	
	@Override
	public String createErrorMessage(CommandSender sender, MassiveCommand command)
	{
		try
		{
			this.getInnerType().read(sender);
		}
		catch (MassiveException e)
		{
			return e.getMessages().toPlain(true);
		}
		return null;
	}
	
}
