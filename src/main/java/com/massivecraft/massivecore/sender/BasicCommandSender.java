package com.massivecraft.massivecore.sender;

import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.util.IdUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissibleBase;

public abstract class BasicCommandSender extends PermissibleBase implements CommandSender
{
	public final String name;
	
	public BasicCommandSender(String name, boolean op, boolean opChangeable)
	{
		super(new BasicServerOperator(name, op, opChangeable));
		this.name = name;
	}

	@Override
	public void setOp(boolean value)
	{
		boolean before = this.isOp();
		super.setOp(value);
		boolean after = this.isOp();
		if (before == after) return;
		this.recalculatePermissions();
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public net.kyori.adventure.text.Component name() {
		return net.kyori.adventure.text.Component.text(this.getName());
	}
	
	@Override
	public Server getServer()
	{
		return Bukkit.getServer();
	}

	@Override
	public void sendMessage(String[] messages)
	{
		for (String message : messages)
		{
			this.sendMessage(message);
		}
	}
	
	public void register()
	{
		final BasicCommandSender ME = this;
		
		// Register Now
		registerImmediately();
		
		// Register Later
		Bukkit.getScheduler().scheduleSyncDelayedTask(MassiveCore.get(), ME::registerImmediately);
	}
	
	public void registerImmediately()
	{
		IdUtil.register(this);
	}
	
	public void unregister()
	{
		IdUtil.unregister(this);
	}
	
}
