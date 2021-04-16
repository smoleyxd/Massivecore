package com.massivecraft.massivecore.nms;

import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class NmsBoard19R1P extends NmsBoard
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static NmsBoard19R1P i = new NmsBoard19R1P();
	public static NmsBoard19R1P get() { return i; }
	
	// -------------------------------------------- //
	// PROVOKE
	// -------------------------------------------- //
	
	@Override
	public Option provoke() throws Throwable
	{
		return Option.COLLISION_RULE;
	}
	
	// -------------------------------------------- //
	// OPTIONS
	// -------------------------------------------- //
	
	@Override
	public TeamOptionValue getOption(@NotNull Team team, @NotNull TeamOptionKey key)
	{
		Option bukkitKey = convert(key, Option.values());
		OptionStatus bukkitValue = team.getOption(bukkitKey);
		return convert(bukkitValue, TeamOptionValue.values());
	}
	
	@Override
	public void setOption(@NotNull Team team, @NotNull TeamOptionKey key, @NotNull TeamOptionValue value)
	{
		Option bukkitKey = convert(key, Option.values());
		OptionStatus bukkitValue = convert(value, OptionStatus.values());
		team.setOption(bukkitKey, bukkitValue);
	}
	
	// -------------------------------------------- //
	// MEMBERS
	// -------------------------------------------- //
	
	@Override
	public void addMember(@NotNull Team team, String key)
	{
		team.addEntry(key);
	}
	
	@Override
	public boolean removeMember(@NotNull Team team, String key)
	{
		return team.removeEntry(key);
	}
	
	@Override
	public boolean isMember(@NotNull Team team, String key)
	{
		return team.hasEntry(key);
	}
	
	@Override
	public Set<String> getMembers(@NotNull Team team)
	{
		return team.getEntries();
	}
	
	// -------------------------------------------- //
	// KEY TEAM
	// -------------------------------------------- //
	
	@Override
	public Team getKeyTeam(@NotNull Scoreboard board, @NotNull String key)
	{
		return board.getEntryTeam(key);
	}
	
	// -------------------------------------------- //
	// IS EQUALS IMPLEMENTED
	// -------------------------------------------- //
	
	public boolean isEqualsImplemented()
	{
		return true;
	}
	
}


