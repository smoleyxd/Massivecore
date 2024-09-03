package com.massivecraft.massivecore.engine;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.command.MassiveCoreBukkitCommand;
import com.massivecraft.massivecore.util.ReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;

import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

public class EngineMassiveCoreCommandRegistration extends Engine
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final EngineMassiveCoreCommandRegistration i = new EngineMassiveCoreCommandRegistration();
	@Contract(pure = true)
	public static EngineMassiveCoreCommandRegistration get() { return i; }
	public EngineMassiveCoreCommandRegistration()
	{
		long interval = Long.getLong("MassiveCoreCommandRegistrationPeriod", 20L * 60L * 60L); // default to every hour
		this.setPeriod(interval);
		
		Bukkit.getScheduler().runTaskLater(
			MassiveCore.get(),
			EngineMassiveCoreCommandRegistration::updateRegistrations,
			100
		);
	}
	
	// -------------------------------------------- //
	// TASK
	// -------------------------------------------- //
	
	@Override
	public void run()
	{
		updateRegistrations();
	}
	
	// -------------------------------------------- //
	// UPDATE REGISTRATIONS
	// -------------------------------------------- //
	
	public static void updateRegistrations()
	{
		// Step #1: Get the Bukkit commandMap, and its knockCommands map.
		CommandMap commandMap = Bukkit.getCommandMap();
		Map<String, Command> knownCommands = commandMap.getKnownCommands();
		
		// Step #2: Create a "name --> target" map that contains the MassiveCommands that /should/ be registered in Bukkit. 
		Map<String, MassiveCommand> nameTargets = new HashMap<>();
		// For each MassiveCommand that is supposed to be registered ...
		for (MassiveCommand massiveCommand : MassiveCommand.getAllInstances())
		{
			// ... and for each of it's aliases ...
			for (String alias : massiveCommand.getAliases())
			{
				// ... that aren't null ...
				if (alias == null) continue;
				
				// ... clean the alias ...
				alias = alias.trim().toLowerCase();
				
				// ... and put it in the map.
				// NOTE: In case the same alias is used by many commands the overwrite occurs here!
				nameTargets.put(alias, massiveCommand);
			}
		}
		
		// Step #3: Ensure the nameTargets created in Step #2 are registered in Bukkit.
		// For each nameTarget entry ...
		for (Entry<String, MassiveCommand> entry : nameTargets.entrySet())
		{
			String name = entry.getKey();
			MassiveCommand target = entry.getValue();
			
			// ... find the current command registered in Bukkit under that name (if any) ...
			Command current = knownCommands.get(name);
			MassiveCommand massiveCurrent = getMassiveCommand(current);
			
			// ... and if the current command is not the target ...
			// NOTE: We do this check since it's important we don't create new MassiveCoreBukkitCommands unless required.
			// NOTE: Before I implemented this check I caused a memory leak in tandem with Spigots timings system.
			if (target == massiveCurrent) continue;
			
			// ... unregister the current command if there is one ...
			if (current != null)
			{
				knownCommands.remove(name);
				current.unregister(commandMap);
			}
			
			// ... create a new MassiveCoreBukkitCommand ...
			MassiveCoreBukkitCommand command = new MassiveCoreBukkitCommand(name, target);
			
			// ... and finally register it.
			Plugin plugin = command.getPlugin();
			String pluginName = plugin.getName();
			commandMap.register(pluginName, command);
		}
		
		// Step #4: Remove/Unregister MassiveCommands from Bukkit that are but should not be that any longer. 
		// For each known command ...
		Set<String> commandsToRemove = new HashSet<>();
        for (Entry<String, Command> entry : knownCommands.entrySet()) {
            String name = entry.getKey();
            Command command = entry.getValue();

            // ... that is a MassiveCoreBukkitCommand ...
            MassiveCommand massiveCommand = getMassiveCommand(command);
            if (massiveCommand == null) continue;

            // ... and not a target ...
            if (nameTargets.containsKey(name)) continue;

            // ... unregister it.
            command.unregister(commandMap);
            commandsToRemove.add(name);
        }
		commandsToRemove.forEach(knownCommands::remove);

		syncCommands();
	}
	
	// -------------------------------------------- //
	// UTIL
	// -------------------------------------------- //
	
	@Contract("null -> null")
	public static MassiveCommand getMassiveCommand(Command command)
	{
		if (command == null) return null;
		if (!(command instanceof MassiveCoreBukkitCommand mcbc)) return null;
		return mcbc.getMassiveCommand();
	}
	
	// -------------------------------------------- //
	// 1.13 SYNC COMMANDS
	// -------------------------------------------- //
	
	private static Method CRAFTSERVER_SYNC_COMMANDS = null;
	private static boolean syncCommandsFailed = false;
	private static Method getSyncCommandMethod() {
		if (CRAFTSERVER_SYNC_COMMANDS != null || syncCommandsFailed) return CRAFTSERVER_SYNC_COMMANDS;
		Class<?> clazz = Bukkit.getServer().getClass();
		try {
			CRAFTSERVER_SYNC_COMMANDS = ReflectionUtil.getMethod(clazz, "syncCommands");
		} catch (Exception ex) {
			syncCommandsFailed = true;
		}
		return CRAFTSERVER_SYNC_COMMANDS;
	}
	private static void syncCommands() {
		Method sync = getSyncCommandMethod();
		if (sync == null) return;
		ReflectionUtil.invokeMethod(sync, Bukkit.getServer());
	}

}
