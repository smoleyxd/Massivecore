package com.massivecraft.massivecore.cooldown;

import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.util.TimeDiffUtil;
import com.massivecraft.massivecore.util.TimeUnit;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedHashMap;
import java.util.WeakHashMap;

public class Cooldown
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final WeakHashMap<CommandSender, Long> idToLastMillis = new WeakHashMap<>();
	
	private long cooldownMillis;
	public long getCooldownMillis() { return this.cooldownMillis; }
	public void setCooldownMillis(long cooldownMillis) { this.cooldownMillis = cooldownMillis; }
	
	private String name;
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public Cooldown()
	{
		this(TimeUnit.MILLIS_PER_SECOND);
	}
	
	public Cooldown(long cooldownMillis)
	{
		this(cooldownMillis, null);
	}
	
	public Cooldown(long cooldownMillis, String name)
	{
		this.cooldownMillis = cooldownMillis;
		this.name = name;
	}
	
	// -------------------------------------------- //
	// CORE
	// -------------------------------------------- //
	
	public void setLastMillis(CommandSender commandSender, long millis)
	{
		if (commandSender == null) throw new NullPointerException("commandSender");
		this.idToLastMillis.put(commandSender, millis);
	}
	
	public void setLastMillis(CommandSender commandSender)
	{
		if (commandSender == null) throw new NullPointerException("commandSender");
		this.setLastMillis(commandSender, System.currentTimeMillis());
	}
	
	public Long getLastMillis(CommandSender commandSender)
	{
		if (commandSender == null) throw new NullPointerException("commandSender");
		return this.idToLastMillis.get(commandSender);
	}
	
	public long getCooldownMillis(CommandSender commandSender)
	{
		if (commandSender == null) throw new NullPointerException("commandSender");
		
		Long lastMillis = this.getLastMillis(commandSender);
		if (lastMillis == null) return 0;
		
		return this.getCooldownMillis() + lastMillis - System.currentTimeMillis();
	}
	
	// -------------------------------------------- //
	// HAS
	// -------------------------------------------- //
	
	public boolean has(Player commandSender, boolean verbose)
	{
		long millis = this.getCooldownMillis(commandSender);
		if (millis <= 0) return false;
		if (!verbose) return true;
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(Txt.parse("<b>Cooldown"));
		
		if (this.getName() != null)
		{
			sb.append(' ');
			sb.append(this.getName());
		}
		
		sb.append(": ");
		
		LinkedHashMap<TimeUnit, Long> unitcounts = TimeDiffUtil.unitcounts(millis);
		unitcounts = TimeDiffUtil.limit(unitcounts, 1);
		String formatted = TimeDiffUtil.formattedVerbose(unitcounts, "<b>");
		
		sb.append(formatted);
		
		String message = sb.toString();
		MixinMessage.get().messageOne(commandSender, message);
		
		return true;
	}
	
	public boolean has(Player commandSender)
	{
		return this.has(commandSender, true);
	}
	
}

