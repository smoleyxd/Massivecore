package com.massivecraft.massivecore;

import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.command.MassiveCommand;
import com.massivecraft.massivecore.cmd.CmdMassiveCore;
import com.massivecraft.massivecore.command.requirement.Requirement;
import com.massivecraft.massivecore.command.requirement.RequirementAbstract;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.mson.MsonEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.massivecraft.massivecore.mson.Mson.mson;

public class Button
{
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	@Contract(" -> new")
	public static @NotNull Button get() { return new Button(); }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public Button()
	{
		
	}
	
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	// A button is either enabled or disabled. These are the two corresponding colors.
	public static final ChatColor COLOR_ENABLED = ChatColor.AQUA;
	public static final ChatColor COLOR_DISABLED = ChatColor.GRAY;
	
	// -------------------------------------------- //
	// FIELDS > COMMON
	// -------------------------------------------- //
	
	// This is the text inside the button.
	private String name = null;
	public String getName() { return this.name; }
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull Button setName(String name) { this.name = name; return this; }
	
	// Padding right, left or none. 
	public Boolean paddingRight = null;
	public Boolean isPaddingRight() { return this.paddingRight; }
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull Button setPaddingRight(Boolean paddingRight) { this.paddingRight = paddingRight; return this; }
	
	// Verbose visible as grey. Otherwise hidden.
	public boolean verbose = true;
	public boolean isVerbose() { return this.verbose; }
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull Button setVerbose(boolean verbose) { this.verbose = verbose; return this; }
	
	// When you just want to error really hard!
	private String error = null;
	public String getError() { return this.error; }
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull Button setError(String error) { this.error = error; return this; }
	
	// Requirements to always be validated.
	private final List<@NotNull Requirement> requirements = new MassiveList<>();
	public List<@NotNull Requirement> getRequirements() { return this.requirements; }
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull Button addRequirements(@NotNull Collection<@NotNull Requirement> requirements) { this.requirements.addAll(requirements); return this; }
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull Button addRequirements(@NotNull Requirement @NotNull ... requirements) { this.addRequirements(Arrays.asList(requirements)); return this; }
	
	// -------------------------------------------- //
	// FIELDS > COMMAND
	// -------------------------------------------- //
	
	private CommandSender sender = null;
	public CommandSender getSender() { return this.sender; }
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull Button setSender(CommandSender sender) { this.sender = sender; return this; }
	
	private MassiveCommand command = null;
	public MassiveCommand getCommand() { return this.command; }
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull Button setCommand(MassiveCommand command) { this.command = command; return this; }
	
	private List<String> args = new MassiveList<>();
	public List<String> getArgs() { return this.args; }
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull Button setArgs(@Nullable Collection<String> args) { this.args = new MassiveList<>(args); return this; }
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull Button setArgs(String @NotNull ... args) { this.setArgs(Arrays.asList(args)); return this; }
	
	public boolean clicking = true;
	public boolean isClicking() { return this.clicking; }
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull Button setClicking(boolean clicking) { this.clicking = clicking; return this; }
	
	// -------------------------------------------- //
	// FIELDS > LINK
	// -------------------------------------------- //
	
	private String link = null;
	public String getLink() { return this.link; }
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull Button setLink(String link) { this.link = link; return this; }
	
	// -------------------------------------------- //
	// RENDER
	// -------------------------------------------- //
	
	public Mson render()
	{
		// Create
		Mson ret = mson(
			"[",
			Mson.parse(this.getName()),
			"]"
		);
		
		// Error and Enabled
		String error = this.getError();
		if (error == null)
		{
			// Get Requirements
			List<Requirement> requirements = new MassiveList<>();
			requirements.add(RequirementIsPlayer.get());
			requirements.addAll(this.getRequirements());
			if (this.getCommand() != null) requirements.addAll(this.getCommand().getRequirements());
			
			// Check Requirements
			error = RequirementAbstract.getRequirementsError(requirements, this.getSender(), this.getCommand(), true);
		}
		
		boolean enabled = (error == null);
		
		// Check Verbose
		if ( ! enabled && ! this.isVerbose()) return null;
		
		// Colorize
		ChatColor color = (enabled ? COLOR_ENABLED : COLOR_DISABLED);
		ret = ret.color(color);
		
		// Empower
		if (enabled)
		{
			if (this.getCommand() != null)
			{
				// Create the command line
				String commandLine = this.getCommand().getCommandLine(this.getArgs());
				
				// Render the corresponding tooltip
				String tooltip = MsonEvent.command(commandLine).createTooltip();
				
				// Possibly make command line clicking
				if (this.isClicking()) commandLine = CmdMassiveCore.get().cmdMassiveCoreClick.getCommandLine(commandLine);
				
				// Apply command
				ret = ret.command(commandLine);
				
				// Possibly set tooltip to hide the clicking clutter
				if (this.isClicking()) ret = ret.tooltip(tooltip);
			}
			else if (this.getLink() != null)
			{
				ret = ret.link(this.getLink());
			}
			else
			{
				throw new RuntimeException();
			}
		}
		else
		{
			ret = ret.tooltip(error);
		}
		
		// Pad
		if (Boolean.TRUE.equals(this.isPaddingRight())) return mson(ret, " ");
		if (Boolean.FALSE.equals(this.isPaddingRight())) return mson(" ", ret);
		
		// Return
		return ret;
	}
}
