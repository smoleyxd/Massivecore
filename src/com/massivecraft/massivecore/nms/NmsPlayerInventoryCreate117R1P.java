package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.particleeffect.ReflectionUtils;
import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import com.massivecraft.massivecore.util.ReflectionUtil;
import org.bukkit.inventory.PlayerInventory;

import java.lang.reflect.Constructor;

@SuppressWarnings("FieldCanBeLocal")
public class NmsPlayerInventoryCreate117R1P extends NmsPlayerInventoryCreate
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static NmsPlayerInventoryCreate117R1P i = new NmsPlayerInventoryCreate117R1P();
	public static NmsPlayerInventoryCreate117R1P get () { return i; }

	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private Class<?> classNmsPlayerInventory;
	private Class<?> classNmsEntityHuman;
	private Constructor<?> constructorNmsPlayerInventory;
	
	private Class<?> classCraftInventoryPlayer;
	private Constructor<?> constructorCraftInventoryPlayer;
	
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		this.classNmsPlayerInventory = PackageType.MINECRAFT_WORLD_ENTITY_PLAYER.getClass("PlayerInventory");
		this.classNmsEntityHuman = PackageType.MINECRAFT_WORLD_ENTITY_PLAYER.getClass("EntityHuman");
		this.constructorNmsPlayerInventory = ReflectionUtils.getConstructor(this.classNmsPlayerInventory, this.classNmsEntityHuman);
		
		this.classCraftInventoryPlayer = ReflectionUtils.PackageType.CRAFTBUKKIT_VERSION_INVENTORY.getClass("CraftInventoryPlayer");
		this.constructorCraftInventoryPlayer = ReflectionUtils.getConstructor(this.classCraftInventoryPlayer, this.classNmsPlayerInventory);
	}
	
	// -------------------------------------------- //
	// CREATE
	// -------------------------------------------- //
	
	@Override
	public PlayerInventory create()
	{
		Object nmsInventory = ReflectionUtil.invokeConstructor(this.constructorNmsPlayerInventory, (Object)null);
		return ReflectionUtil.invokeConstructor(this.constructorCraftInventoryPlayer, nmsInventory);
	}
	
}
