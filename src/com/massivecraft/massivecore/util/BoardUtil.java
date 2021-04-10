package com.massivecraft.massivecore.util;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.event.EventMassiveCoreBoardEnsure;
import com.massivecraft.massivecore.nms.NmsBoard;
import com.massivecraft.massivecore.nms.TeamOptionKey;
import com.massivecraft.massivecore.nms.TeamOptionValue;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

// # RESEARCH > CLEANUP
// The main server scoreboard is the only one that is saved to NBT.
// We must make sure to clean up after ourselves if we use that one.
// The other scoreboards are just temporary.
// For this reason there is a value to avoiding using the main scoreboard at all.
// However so long as we clean up after ourselves properly there is a simplicity to using all known scoreboards.
//
// # RESEARCH > DEFAULT TEAM
// Per default players have no team.
// To disable collisions we must set a team flag.
// This means some sort of default team creation can be useful.
// For this we use so called personal teams with only one member.
//
// # TERMIOLOGY
// Board: the "score board"
// Objective: the score board "objective"
// Id: the unchangeable "name"
// Name: the changeable "display name"
// Slot: the "display slot"
// Entries: Map from key to value
// Key: the player name or stringified entity uuid
// Value: the integer objective score value
// Team: the score board team
// Members: the score board team members. These are of Key type.
//
// # DESIGN
// NoChange: Do not trigger network packets in vain through detecting "same setting".
public class BoardUtil extends Engine
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static BoardUtil i = new BoardUtil();
	@Contract(pure = true)
	public static BoardUtil get() { return i; }
	public BoardUtil()
	{
		this.setPeriod(1L);
	}
	
	// -------------------------------------------- //
	// DATA
	// -------------------------------------------- //
	
	// All online players at the beginning of the tick.
	private static Map<String, Player> players = Collections.emptyMap();
	@Contract(pure = true)
	public static Map<String, Player> getPlayers() { return players; }
	
	// The boards based off the players above.
	private static Set<Scoreboard> boards = Collections.emptySet();
	@Contract(pure = true)
	public static Set<Scoreboard> getBoards() { return boards; }
	
	// Temporary Fake Fields
	private static Set<Objective> temporaryObjectives = null;
	public static Set<Objective> getTemporaryObjectives()
	{
		if (temporaryObjectives == null) temporaryObjectives = NmsBoard.get().createObjectiveSet();
		return temporaryObjectives;
	}
	
	private static Set<Team> temporaryTeams = null;
	public static Set<Team> getTemporaryTeams()
	{
		if (temporaryTeams == null) temporaryTeams = NmsBoard.get().createTeamSet();
		return temporaryTeams;
	}
	
	// -------------------------------------------- //
	// UPDATE
	// -------------------------------------------- //
	
	@Override
	public void setActiveInner(boolean active)
	{
		if (active)
		{
			// We do not trigger an update here.
			// We must wait for the first server tick.
			// Otherwise the Scoreboard manager is null.
		}
		else
		{
			// We delete everything marked as temporary on deactivation.
			
			List<Objective> objectives = new MassiveList<>(getTemporaryObjectives());
			for (Objective objective : objectives)
			{
				deleteObjective(objective);
			}
			
			List<Team> teams = new MassiveList<>(getTemporaryTeams());
			for (Team team : teams)
			{
				deleteTeam(team);
			}
		}
	}
	
	@Override
	public void run()
	{
		update();
	}
	
	public static void update()
	{
		updatePlayers();
		updateBoards();
		updateEnsure();
	}
	
	public static void updatePlayers()
	{
		// Create
		Map<String, Player> players = new MassiveMap<>();
		
		// Fill
		for (Player player : MUtil.getOnlinePlayers())
		{
			players.put(player.getName(), player);
		}
		players = Collections.unmodifiableMap(players);
		
		// Set
		BoardUtil.players = players;
	}
	
	public static void updateBoards()
	{
		// Create
		Set<Scoreboard> boards = new MassiveSet<>();
		
		// Fill > Simple
		boards.add(getBoardMain());
		boards.add(getBoardOur());
		
		// Fill > Players
		for (Player player : getPlayers().values())
		{
			Scoreboard board = getBoard(player);
			boards.add(board);
		}
		
		// Set
		boards = Collections.unmodifiableSet(boards);
		BoardUtil.boards = boards;
	}
	
	public static void updateEnsure()
	{
		EventMassiveCoreBoardEnsure event = new EventMassiveCoreBoardEnsure();
		event.run();
		
		for (Player player : getPlayers().values())
		{
			if (event.isEnsureBoardEnabled())
			{
				ensureBoard(player, event.isEnsureBoardStrict());
			}
			
			if (event.isEnsureTeamEnabled())
			{
				for (Scoreboard board : getBoards())
				{
					ensureTeam(board, player, event.isEnsureTeamStrict());
				}
			}
		}
	}
	
	// -------------------------------------------- //
	// ENSURE
	// -------------------------------------------- //
	
	public static Scoreboard ensureBoard(@NotNull Player player, boolean strict)
	{
		Scoreboard board = getBoard(player);
		
		if (isBoardOur(board)) return board;
		if ( ! strict && ! isBoardMain(board)) return board;
		
		board = getBoardOur();
		setBoard(player, board);
		
		return board;
	}
	
	public static Team ensureTeam(@NotNull Scoreboard board, @NotNull Player player, boolean strict)
	{
		Team team = getKeyTeam(board, player);
		
		if (isPersonalTeam(team, player)) return team;
		if ( ! strict && team != null) return team;
		
		team = getPersonalTeam(board, player, true);
		return team;
	}
	
	// -------------------------------------------- //
	// CLEAN
	// -------------------------------------------- //
	
	public static void clean(Player player)
	{
		// Delete scores for temporary objectives.
		for (Objective objective : getTemporaryObjectives())
		{
			setObjectiveValue(objective, player, 0);
		}
		
		// Delete player team if temporary and sole player.
		for (Scoreboard board : getBoards())
		{
			Team team = getKeyTeam(board, player);
			if (isTeamPersistent(team)) continue;
			if (getTeamMembers(team).size() > 1) continue;
			deleteTeam(team);
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void clean(final @NotNull PlayerQuitEvent event)
	{
		Bukkit.getScheduler().runTask(this.getPlugin(), () -> clean(event.getPlayer()));
	}
	
	// -------------------------------------------- //
	// KEY
	// -------------------------------------------- //
	
	@Contract("null -> null")
	public static String getKey(Object key)
	{
		if (key == null) return null;
		
		if (key instanceof String)
		{
			return (String)key;
		}
		
		if (key instanceof Player)
		{
			Player player = (Player)key;
			return player.getName();
		}
		
		if (key instanceof Entity)
		{
			Entity entity = (Entity)key; 
			UUID uuid = entity.getUniqueId();
			String string = uuid.toString();
			string = string.substring(0, 16);
			return string;
		}
		
		throw new IllegalArgumentException(key.toString());
	}
	
	// -------------------------------------------- //
	// BOARD
	// -------------------------------------------- //
	
	public static @NotNull Scoreboard getBoard(@NotNull Player player)
	{
		return player.getScoreboard();
	}
	
	public static void setBoard(@NotNull Player player, @NotNull Scoreboard board)
	{
		player.setScoreboard(board);
	}
	
	// -------------------------------------------- //
	// BOARD > MAIN
	// -------------------------------------------- //
	
	public static @NotNull Scoreboard getBoardMain()
	{
		return Bukkit.getScoreboardManager().getMainScoreboard();
	}
	
	@Contract("null -> false")
	public static boolean isBoardMain(Scoreboard board)
	{
		return getBoardMain().equals(board);
	}
	
	// -------------------------------------------- //
	// BOARD > OUR
	// -------------------------------------------- //
	
	private static Scoreboard BOARD_OUR = null;
	
	public static @NotNull Scoreboard getBoardOur()
	{
		if (BOARD_OUR == null) BOARD_OUR = Bukkit.getScoreboardManager().getNewScoreboard();
		return BOARD_OUR;
	}
	
	@Contract("null -> false")
	public static boolean isBoardOur(Scoreboard board)
	{
		return getBoardOur().equals(board);
	}
	
	// -------------------------------------------- //
	// OBJECTIVE
	// -------------------------------------------- //
	
	// Note that "dummy" actually seems to be the right word with a certain meaning to the vanilla server.
	// http://minecraft.gamepedia.com/Scoreboard
	public static final String OBJECTIVE_CRITERIA_DUMMY = "dummy";
	
	public static @NotNull Objective createObjective(@NotNull Scoreboard board, @NotNull String id)
	{
		return createObjective(board, id, OBJECTIVE_CRITERIA_DUMMY);
	}
	
	public static @NotNull Objective createObjective(@NotNull Scoreboard board, @NotNull String id, @NotNull String criteria)
	{
		return createObjective(board, id, criteria, id);
	}
	
	public static @NotNull Objective createObjective(@NotNull Scoreboard board, @NotNull String id, @NotNull String criteria, @NotNull String displayName)
	{
		return board.registerNewObjective(id, criteria, displayName);
	}
	
	public static Objective getObjective(@NotNull Scoreboard board, @NotNull String id, boolean creative)
	{
		return getObjective(board, id, OBJECTIVE_CRITERIA_DUMMY, creative);
	}
	
	public static Objective getObjective(@NotNull Scoreboard board, @NotNull String id, @NotNull String criteria, boolean creative)
	{
		return getObjective(board, id, criteria, id, creative);
	}
	
	public static Objective getObjective(@NotNull Scoreboard board, @NotNull String id, @NotNull String criteria, @NotNull String displayName, boolean creative)
	{
		Objective objective = board.getObjective(id);
		if (objective == null && creative) objective = createObjective(board, id, criteria, displayName);
		return objective;
	}
	
	public static void deleteObjective(@Nullable Objective objective)
	{
		if (objective == null) return;
		getTemporaryObjectives().remove(objective);
		try
		{
			objective.unregister();
		}
		catch (IllegalStateException e)
		{
			// Already Done
		}
	}
	
	public static void deleteObjective(@NotNull Scoreboard board, @NotNull String id)
	{
		Objective objective = board.getObjective(id);
		deleteObjective(objective);
	}
	
	public static boolean setObjective(Objective objective, Boolean persistent, String name, DisplaySlot slot, Map<String, Integer> entries)
	{
		boolean ret = false;
		ret |= setObjectivePersistent(objective, persistent);
		ret |= setObjectiveName(objective, name);
		ret |= setObjectiveSlot(objective, slot);
		ret |= setObjectiveEntries(objective, entries);
		return ret;
	}
	
	public static boolean setObjective(Objective objective, Objective blueprint)
	{
		return setObjective(objective,
			isObjectivePersistent(blueprint),
			getObjectiveName(blueprint),
			getObjectiveSlot(blueprint),
			getObjectiveEntries(blueprint)
		);
	}
	
	// -------------------------------------------- //
	// OBJECTIVE > ID
	// -------------------------------------------- //
	
	public static @NotNull String getObjectiveId(@NotNull Objective objective)
	{
		return objective.getName();
	}
	
	// -------------------------------------------- //
	// OBJECTIVE > PERSISTENT
	// -------------------------------------------- //
	
	public static boolean isObjectivePersistent(Objective objective)
	{
		return ! getTemporaryObjectives().contains(objective);
	}
	
	@Contract("_, null -> false")
	public static boolean setObjectivePersistent(Objective objective, Boolean persistent)
	{
		if (persistent == null) return false;
		
		if (persistent)
		{
			return getTemporaryObjectives().remove(objective);
		}
		else
		{
			return getTemporaryObjectives().add(objective);
		}
	}
	
	// -------------------------------------------- //
	// OBJECTIVE > NAME
	// -------------------------------------------- //
	
	public static @NotNull String getObjectiveName(@NotNull Objective objective)
	{
		return objective.getDisplayName();
	}
	
	@Contract("_, null -> false")
	public static boolean setObjectiveName(@NotNull Objective objective, String name)
	{
		if (name == null) return false;
		String before = getObjectiveName(objective);
		if (MUtil.equals(before, name)) return false;
		objective.setDisplayName(name);
		return true;
	}
	
	// -------------------------------------------- //
	// OBJECTIVE > SLOT
	// -------------------------------------------- //
	
	public static DisplaySlot getObjectiveSlot(@NotNull Objective objective)
	{
		return objective.getDisplaySlot();
	}
	
	@Contract("_, null -> false")
	public static boolean setObjectiveSlot(Objective objective, DisplaySlot slot)
	{
		if (slot == null) return false;
		DisplaySlot before = getObjectiveSlot(objective);
		if (MUtil.equals(before, slot)) return false;
		objective.setDisplaySlot(slot);
		return true;
	}
	
	// -------------------------------------------- //
	// OBJECTIVE > VALUE
	// -------------------------------------------- //
	
	public static int getObjectiveValue(@NotNull Objective objective, Object key)
	{
		Score score = objective.getScore(getKey(key));
		return getScoreValue(score);
	}
	
	@Contract("_, _, null -> false")
	public static boolean setObjectiveValue(Objective objective, Object key, Integer value)
	{
		if (value == null) return false;
		Score score = objective.getScore(getKey(key));
		return setScoreValue(score, value);
	}
	
	// -------------------------------------------- //
	// OBJECTIVE > ENTRIES
	// -------------------------------------------- //
	
	public static @NotNull Map<String, Integer> getObjectiveEntries(@NotNull Objective objective)
	{
		// Create
		Map<String, Integer> ret = new MassiveMap<>();
		
		// Fill
		for (String key : objective.getScoreboard().getEntries())
		{
			int value = getObjectiveValue(objective, key); 
			if (value == 0) continue;
			ret.put(key, value);
		}
		
		// Return
		return ret;
	}
	
	@Contract("_, null -> false")
	public static boolean setObjectiveEntries(Objective objective, Map<String, Integer> entries)
	{
		if (entries == null) return false;
		boolean ret = false;
		
		// Add or Update
		for (Entry<String, Integer> entry : entries.entrySet())
		{
			String key = entry.getKey();
			Integer value = entry.getValue();
			ret |= setObjectiveValue(objective, key, value);
		}
		
		// Remove
		Scoreboard board = objective.getScoreboard();
		for (String key : board.getEntries())
		{
			if (entries.containsKey(key)) continue;
			if (getObjectiveValue(objective, key) == 0) continue;
			board.resetScores(key);
		}
		
		return ret;
	}
	
	// -------------------------------------------- //
	// SCORE > VALUE
	// -------------------------------------------- //
	
	public static int getScoreValue(@NotNull Score score)
	{
		return score.getScore();
	}
	
	@Contract("_, null -> false")
	public static boolean setScoreValue(Score score, Integer value)
	{
		if (value == null) return false;
		int before = getScoreValue(score);
		if (before == value) return false;
		score.setScore(value);
		return true;
	}
	
	// -------------------------------------------- //
	// TEAM
	// -------------------------------------------- //
	
	public static @NotNull Team createTeam(@NotNull Scoreboard board, String id)
	{
		return board.registerNewTeam(id);
	}
	
	public static Team getTeam(@NotNull Scoreboard board, String id, boolean creative)
	{
		Team team = board.getTeam(id);
		if (team == null && creative) team = createTeam(board, id);
		return team;
	}
	
	public static void deleteTeam(Team team)
	{
		if (team == null) return;
		getTemporaryTeams().remove(team);
		try
		{
			team.unregister();
		}
		catch (IllegalStateException e)
		{
			// Already Done
		}
	}
	
	public static void deleteTeam(@NotNull Scoreboard board, String id)
	{
		Team team = board.getTeam(id);
		deleteTeam(team);
	}
	
	public static boolean setTeam(Team team, Boolean persistent, String name, String prefix, String suffix, Boolean friendlyFireEnabled, Boolean friendlyTruesightEnabled, Map<TeamOptionKey, TeamOptionValue> options, Set<String> members)
	{
		boolean ret = false;
		ret |= setTeamPersistent(team, persistent);
		ret |= setTeamName(team, name);
		ret |= setTeamPrefix(team, prefix);
		ret |= setTeamSuffix(team, suffix);
		ret |= setTeamFriendlyFireEnabled(team, friendlyFireEnabled);
		ret |= setTeamFriendlyTruesightEnabled(team, friendlyTruesightEnabled);
		ret |= setTeamOptions(team, options);
		ret |= setTeamMembers(team, members);
		return ret;
	}
	
	public static boolean setTeam(Team team, Team blueprint)
	{
		return setTeam(team,
			isTeamPersistent(blueprint),
			getTeamName(blueprint),
			getTeamPrefix(blueprint),
			getTeamSuffix(blueprint),
			isTeamFriendlyFireEnabled(blueprint),
			isTeamFriendlyTruesightEnabled(blueprint),
			getTeamOptions(blueprint),
			getTeamMembers(blueprint)
		);
	}
	
	// -------------------------------------------- //
	// TEAM > SEND
	// -------------------------------------------- //
	
	public static void sendTeamUpdate(@NotNull Team team)
	{
		team.setDisplayName(team.getDisplayName());
	}
	
	// -------------------------------------------- //
	// TEAM > ID
	// -------------------------------------------- //
	
	public static @NotNull String getTeamId(@NotNull Team team)
	{
		return team.getName();
	}
	
	// -------------------------------------------- //
	// TEAM > PERSISTENT
	// -------------------------------------------- //
	
	public static boolean isTeamPersistent(Team team)
	{
		return ! getTemporaryTeams().contains(team);
	}
	
	@Contract("_, null -> false")
	public static boolean setTeamPersistent(Team team, Boolean persistent)
	{
		if (persistent == null) return false;
		
		if (persistent)
		{
			return getTemporaryTeams().remove(team);
		}
		else
		{
			return getTemporaryTeams().add(team);
		}
	}
	
	// -------------------------------------------- //
	// TEAM > NAME
	// -------------------------------------------- //
	
	public static @NotNull String getTeamName(@NotNull Team team)
	{
		return team.getDisplayName();
	}
	
	@Contract("_, null -> false")
	public static boolean setTeamName(Team team, String name)
	{
		if (name == null) return false;
		String before = getTeamName(team);
		if (MUtil.equals(before, name)) return false;
		team.setDisplayName(name);
		return true;
	}
	
	// -------------------------------------------- //
	// TEAM > PREFIX
	// -------------------------------------------- //
	
	public static @NotNull String getTeamPrefix(@NotNull Team team)
	{
		return team.getPrefix();
	}
	
	@Contract("_, null -> false")
	public static boolean setTeamPrefix(@NotNull Team team, String prefix)
	{
		if (prefix == null) return false;
		String before = getTeamPrefix(team);
		if (MUtil.equals(before, prefix)) return false;
		team.setPrefix(prefix);
		return true;
	}
	
	// -------------------------------------------- //
	// TEAM > SUFFIX
	// -------------------------------------------- //
	
	public static @NotNull String getTeamSuffix(@NotNull Team team)
	{
		return team.getSuffix();
	}
	
	@Contract("_, null -> false")
	public static boolean setTeamSuffix(@NotNull Team team, String suffix)
	{
		if (suffix == null) return false;
		String before = getTeamSuffix(team);
		if (MUtil.equals(before, suffix)) return false;
		team.setSuffix(suffix);
		return true;
	}
	
	// -------------------------------------------- //
	// TEAM > FRIENDLY FIRE ENABLED
	// -------------------------------------------- //
	
	public static boolean isTeamFriendlyFireEnabled(@NotNull Team team)
	{
		return team.allowFriendlyFire();
	}
	
	@Contract("_, null -> false")
	public static boolean setTeamFriendlyFireEnabled(Team team, Boolean friendlyFireEnabled)
	{
		if (friendlyFireEnabled == null) return false;
		boolean before = isTeamFriendlyFireEnabled(team);
		if (MUtil.equals(before, friendlyFireEnabled)) return false;
		team.setAllowFriendlyFire(friendlyFireEnabled);
		return true;
	}
	
	// -------------------------------------------- //
	// TEAM > FRIENDLY TRUESIGHT ENABLED
	// -------------------------------------------- //
	
	public static boolean isTeamFriendlyTruesightEnabled(@NotNull Team team)
	{
		return team.canSeeFriendlyInvisibles();
	}
	
	@Contract("_, null -> false")
	public static boolean setTeamFriendlyTruesightEnabled(Team team, Boolean friendlyTruesightEnabled)
	{
		if (friendlyTruesightEnabled == null) return false;
		boolean before = isTeamFriendlyTruesightEnabled(team);
		if (MUtil.equals(before, friendlyTruesightEnabled)) return false;
		team.setCanSeeFriendlyInvisibles(friendlyTruesightEnabled);
		return true;
	}
	
	// -------------------------------------------- //
	// TEAM > OPTION
	// -------------------------------------------- //
	
	public static TeamOptionValue getTeamOption(@NotNull Team team, @NotNull TeamOptionKey key)
	{
		return NmsBoard.get().getOption(team, key);
	}
	
	@Contract("_, _, null -> false")
	public static boolean setTeamOption(@NotNull Team team, @NotNull TeamOptionKey key, TeamOptionValue value)
	{
		if (value == null) return false;
		TeamOptionValue before = getTeamOption(team, key);
		if (before == value) return false;
		NmsBoard.get().setOption(team, key, value);
		return true;
	}
	
	// -------------------------------------------- //
	// TEAM > OPTIONS
	// -------------------------------------------- //
	
	public static @NotNull Map<TeamOptionKey, TeamOptionValue> getTeamOptions(@NotNull Team team)
	{
		// Create
		Map<TeamOptionKey, TeamOptionValue> ret = new MassiveMap<>();
		
		// Fill
		for (TeamOptionKey key : TeamOptionKey.values())
		{
			TeamOptionValue value = getTeamOption(team, key);
			if (value == null) continue;
			ret.put(key, value);
		}
		
		// Return
		return ret;
	}
	
	@Contract("_, null -> false")
	public static boolean setTeamOptions(@NotNull Team team, Map<TeamOptionKey, TeamOptionValue> options)
	{
		if (options == null) return false;
		
		boolean ret = false;
		for (Entry<TeamOptionKey, TeamOptionValue> entry : options.entrySet())
		{
			TeamOptionKey option = entry.getKey();
			TeamOptionValue status = entry.getValue();
			ret |= setTeamOption(team, option, status);
		}
		return ret;
	}
	
	// -------------------------------------------- //
	// TEAM > MEMBERS
	// -------------------------------------------- //
	
	public static boolean addTeamMember(@NotNull Team team, Object key)
	{
		if (isTeamMember(team, key)) return false;
		NmsBoard.get().addMember(team, getKey(key));
		return true;
	}
	
	public static boolean removeTeamMember(@NotNull Team team, Object key)
	{
		if ( ! isTeamMember(team, key)) return false;
		NmsBoard.get().removeMember(team, getKey(key));
		return true;
	}
	
	public static boolean isTeamMember(@NotNull Team team, Object key)
	{
		return NmsBoard.get().isMember(team, getKey(key));
	}
	
	public static Set<String> getTeamMembers(@NotNull Team team)
	{
		return NmsBoard.get().getMembers(team);
	}
	
	@Contract("_, null -> false")
	public static boolean setTeamMembers(@NotNull Team team, Set<String> members)
	{
		if (members == null) return false;
		Set<String> befores = getTeamMembers(team);
		boolean ret = false;
		
		// Add
		for (String member : members)
		{
			if (befores.contains(member)) continue;
			NmsBoard.get().addMember(team, member);
			ret = true;
		}
		
		// Remove
		for (String before : befores)
		{
			if (members.contains(before)) continue;
			NmsBoard.get().removeMember(team, before);
			ret = true;
		}
		
		return ret;
	}
	
	// -------------------------------------------- //
	// KEY TEAM
	// -------------------------------------------- //
	// Treating the team like a property of the key.
	// Get and set the team for the key.
	
	public static Team getKeyTeam(@NotNull Scoreboard board, @NotNull Object key)
	{
		return NmsBoard.get().getKeyTeam(board, getKey(key));
	}
	
	public static void setKeyTeam(@NotNull Scoreboard board, @NotNull Object key, Team team)
	{
		Team before = getKeyTeam(board, key);
		if (MUtil.equals(before, team)) return;
		// TODO: Do we really need to remove from the old team first?
		// TODO: Chances are this would be done automatically.
		if (before != null) removeTeamMember(before, key);
		if (team != null) addTeamMember(team, key);
	}
	
	// -------------------------------------------- //
	// PERSONAL TEAM
	// -------------------------------------------- //
	// The id is the player name.
	
	private static final Boolean PERSONAL_DEFAULT_PERSISTENT = false;
	private static final String PERSONAL_DEFAULT_NAME = null;
	private static final String PERSONAL_DEFAULT_PREFIX = "";
	private static final String PERSONAL_DEFAULT_SUFFIX = ChatColor.RESET.toString();
	private static final Boolean PERSONAL_DEFAULT_FRIENDLY_FIRE_ENABLED = true;
	private static final Boolean PERSONAL_DEFAULT_FRIENDLY_TRUESIGHT_ENABLED = false;
	private static final Map<TeamOptionKey, TeamOptionValue> PERSONAL_DEFAULT_OPTIONS = new MassiveMap<>(
		TeamOptionKey.COLLISION_RULE, TeamOptionValue.ALWAYS,
		TeamOptionKey.DEATH_MESSAGE_VISIBILITY, TeamOptionValue.ALWAYS,
		TeamOptionKey.NAME_TAG_VISIBILITY, TeamOptionValue.ALWAYS
	);
	
	public static boolean isPersonalTeam(@NotNull Scoreboard board, @NotNull Object key)
	{
		Team team = getKeyTeam(board, key);
		return isPersonalTeam(team, key);
	}
	
	@Contract("null, _ -> false")
	public static boolean isPersonalTeam(Team team, Object key)
	{
		if (team == null) return false;
		String id = getTeamId(team);
		return id.equals(getKey(key));
	}
	
	public static @NotNull Team createPersonalTeam(@NotNull Scoreboard board, Object key)
	{
		// Create
		String id = getKey(key);
		Team team = createTeam(board, id);
		
		// Fill
		setTeam(
			team,
			PERSONAL_DEFAULT_PERSISTENT,
			PERSONAL_DEFAULT_NAME,
			PERSONAL_DEFAULT_PREFIX,
			PERSONAL_DEFAULT_SUFFIX,
			PERSONAL_DEFAULT_FRIENDLY_FIRE_ENABLED,
			PERSONAL_DEFAULT_FRIENDLY_TRUESIGHT_ENABLED,
			PERSONAL_DEFAULT_OPTIONS,
			Collections.singleton(id)
		);
		
		// Return
		return team;
	}
	
	public static Team getPersonalTeam(@NotNull Scoreboard board, Object key, boolean creative)
	{
		String id = getKey(key);
		Team team = getTeam(board, id, false);
		if (team == null)
		{
			if (creative)
			{
				team = createPersonalTeam(board, key);
			}
		}
		else
		{
			addTeamMember(team, key);			
		}
		
		return team;
	}
	
	public static void deletePersonalTeam(@NotNull Scoreboard board, Object key)
	{
		Team team = getPersonalTeam(board, key, false);
		if ( ! isPersonalTeam(team, key)) return;
		deleteTeam(team);
	}
	
}
