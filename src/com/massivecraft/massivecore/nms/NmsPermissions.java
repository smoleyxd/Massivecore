package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.mixin.Mixin;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class NmsPermissions extends Mixin
{
	// -------------------------------------------- //
	// DEFAULT
	// -------------------------------------------- //
	
	private static NmsPermissions d = new NmsPermissions().setAlternatives(
		NmsPermissions17R4P.class
	);
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static NmsPermissions i = d;
	public static NmsPermissions get() { return i; }
	
	// -------------------------------------------- //
	// BASE
	// -------------------------------------------- //
	
	public List<PermissionAttachment> getAttachments(@NotNull PermissibleBase base)
	{
		throw this.notImplemented();
	}
	
	// -------------------------------------------- //
	// PERMISSIBLE
	// -------------------------------------------- //
	
	public PermissibleBase getBase(Permissible permissible)
	{
		throw this.notImplemented();
	}
	
	// -------------------------------------------- //
	// ATTACHMENT
	// -------------------------------------------- //
	
	public Map<String, Boolean> getAttachmentPermissions(@NotNull PermissionAttachment permissionAttachment)
	{
		throw this.notImplemented();
	}
	
	public void setAttachmentPermissions(@NotNull PermissionAttachment permissionAttachment, Map<String, Boolean> permissions)
	{
		throw this.notImplemented();
	}
	
}
