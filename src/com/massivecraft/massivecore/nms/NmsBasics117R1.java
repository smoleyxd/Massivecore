package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import com.massivecraft.massivecore.util.ReflectionUtil;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NmsBasics117R1 extends NmsBasics
{
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsBasics117R1 i = new NmsBasics117R1();
	public static NmsBasics117R1 get() { return i; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	// GET HANDLE
	
	// org.bukkit.craftbukkit.entity.CraftEntity
	private Class<?> classCraftEntity;
	// org.bukkit.craftbukkit.entity.Entity#getHandle()
	private Method methodCraftEntityGetHandle;
	
	// org.bukkit.craftbukkit.CraftWorld
	public Class<?> classCraftWorld;
	// org.bukkit.craftbukkit.CraftWorld#world
	public Field fieldCraftWorldWorld;
	
	// org.bukkit.craftbukkit.scoreboard.CraftScoreboard
	private Class<?> classCraftScoreboard;
	// org.bukkit.craftbukkit.scoreboard.CraftScoreboard#board
	private Field fieldCraftScoreboardHandle;
	
	// org.bukkit.craftbukkit.scoreboard.CraftTeam
	private Class<?> classCraftTeam;
	// org.bukkit.craftbukkit.scoreboard.CraftTeam#team
	private Field fieldCraftTeamHandle;
	
	// org.bukkit.craftbukkit.scoreboard.CraftObjective
	private Class<?> classCraftObjective;
	// org.bukkit.craftbukkit.scoreboard.CraftObjective#objective
	private Field fieldCraftObjectiveHandle;
	
	// SIGN for 1.12.0 and below
	// org.bukkit.craftbukkit.block.CraftSign
	private Class<?> classCraftSign;
	// org.bukkit.craftbukkit.block.CraftSign#sign
	private Field fieldCraftSignHandle;
	
	// SIGN for 1.12.1 and above
	// org.bukkit.craftbukkit.block.CraftBlockEntityState
	private Class<?> classCraftBlockEntityState;
	// org.bukkit.craftbukkit.block.CraftBlockEntityState#tileEntity
	private Field fieldCraftBlockEntityStateHandle;
	
	// GET BUKKIT
	// net.minecraft.world.entity.Entity
	private Class<?> classNmsEntity;
	// net.minecraft.world.entity.Entity#getBukkitEntity()
	private Method methodNmsEntityGetBukkitEntity;
	
	// CONNECTION & PACKET
	
	// net.minecraft.server.level.EntityPlayer
	private Class<?> classNmsPlayer;
	// net.minecraft.server.level.EntityPlayer#playerConnection
	private Field fieldNmsPlayerPlayerConnection;
	// net.minecraft.network.protocol.Packet
	private Class<?> classNmsPacket;
	// net.minecraft.server.network.PlayerConnection
	private Class<?> classNmsPlayerConnection;
	// net.minecraft.server.network.PlayerConnection#sendPacket(Packet)
	private Method methodPlayerConnectionsendPacket;
	
	// PING
	// net.minecraft.server.EntityPlayer#ping
	private Field fieldNmsPlayerPing;
	
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		// GET HANDLE
		this.classCraftEntity = PackageType.CRAFTBUKKIT_VERSION_ENTITY.getClass("CraftEntity");
		this.methodCraftEntityGetHandle = ReflectionUtil.getMethod(this.classCraftEntity, "getHandle");
		
		this.classCraftWorld = PackageType.CRAFTBUKKIT_VERSION.getClass("CraftWorld");
		this.fieldCraftWorldWorld = ReflectionUtil.getField(this.classCraftWorld, "world");
		
		this.classCraftScoreboard = PackageType.CRAFTBUKKIT_VERSION_SCOREBOARD.getClass("CraftScoreboard");
		this.fieldCraftScoreboardHandle = ReflectionUtil.getField(this.classCraftScoreboard, "board");
		
		this.classCraftTeam = PackageType.CRAFTBUKKIT_VERSION_SCOREBOARD.getClass("CraftTeam");
		this.fieldCraftTeamHandle = ReflectionUtil.getField(this.classCraftTeam, "team");
		
		this.classCraftObjective = PackageType.CRAFTBUKKIT_VERSION_SCOREBOARD.getClass("CraftObjective");
		this.fieldCraftObjectiveHandle = ReflectionUtil.getField(this.classCraftObjective, "objective");
		
		try
		{
			// SIGN for 1.12.0 and below
			this.classCraftSign = PackageType.CRAFTBUKKIT_VERSION_BLOCK.getClass("CraftSign");
			this.fieldCraftSignHandle = ReflectionUtil.getField(this.classCraftSign, "sign");
		}
		catch (Throwable t)
		{
			// SIGN for 1.12.1 and above
			this.classCraftBlockEntityState = PackageType.CRAFTBUKKIT_VERSION_BLOCK.getClass("CraftBlockEntityState");
			this.fieldCraftBlockEntityStateHandle = ReflectionUtil.getField(this.classCraftBlockEntityState, "tileEntity");
		}
		
		// GET BUKKIT
		this.classNmsEntity = PackageType.MINECRAFT_WORLD_ENTITY.getClass("Entity");
		this.methodNmsEntityGetBukkitEntity = ReflectionUtil.getMethod(this.classNmsEntity, "getBukkitEntity");
		
		// CONNECTION & PACKET
		
		this.classNmsPlayer = PackageType.MINECRAFT_SERVER_LEVEL.getClass("EntityPlayer");
		this.fieldNmsPlayerPlayerConnection = ReflectionUtil.getField(this.classNmsPlayer, "b");
		this.classNmsPacket = PackageType.MINECRAFT_NETWORK_PROTOCOL.getClass("Packet");
		this.classNmsPlayerConnection = PackageType.MINECRAFT_SERVER_NETWORK.getClass("PlayerConnection");
		this.methodPlayerConnectionsendPacket = ReflectionUtil.getMethod(this.classNmsPlayerConnection, "sendPacket", this.classNmsPacket);
		
		// PING
		this.fieldNmsPlayerPing = ReflectionUtil.getField(this.classNmsPlayer, "e");
	}
	
	// -------------------------------------------- //
	// GET HANDLE
	// -------------------------------------------- //
	
	@Override
	public <T> T getHandle(Entity entity)
	{
		if (entity == null) return null;
		return ReflectionUtil.invokeMethod(this.methodCraftEntityGetHandle, entity);
	}
	
	@Override
	public <T> T getHandle(World world)
	{
		if (world == null) return null;
		return ReflectionUtil.getField(this.fieldCraftWorldWorld, world);
	}
	
	@Override
	public <T> T getHandle(Scoreboard scoreboard)
	{
		if (scoreboard == null) return null;
		return ReflectionUtil.getField(this.fieldCraftScoreboardHandle, scoreboard);
	}
	
	@Override
	public <T> T getHandle(Team team)
	{
		if (team == null) return null;
		return ReflectionUtil.getField(this.fieldCraftTeamHandle, team);
	}
	
	@Override
	public <T> T getHandle(Objective objective)
	{
		if (objective == null) return null;
		return ReflectionUtil.getField(this.fieldCraftObjectiveHandle, objective);
	}
	
	@Override
	public <T> T getHandle(Sign sign)
	{
		if (sign == null) return null;
		
		Field field;
		
		if (this.fieldCraftSignHandle != null)
		{
			// SIGN for 1.12.0 and below
			field = this.fieldCraftSignHandle;
		}
		else
		{
			// SIGN for 1.12.1 and above
			field = this.fieldCraftBlockEntityStateHandle;
		}
		
		return ReflectionUtil.getField(field, sign);
	}
	
	// -------------------------------------------- //
	// GET BUKKIT
	// -------------------------------------------- //
	
	@Override
	public <T extends Entity> T getBukkit(Object handle)
	{
		if (handle == null) return null;
		return ReflectionUtil.invokeMethod(this.methodNmsEntityGetBukkitEntity, handle);
	}
	
	// -------------------------------------------- //
	// CONNECTION & PACKET
	// -------------------------------------------- //
	
	@Override
	public <T> T getConnection(Player player)
	{
		Object handle = this.getHandle(player);
		return ReflectionUtil.getField(this.fieldNmsPlayerPlayerConnection, handle);
	}
	
	@Override
	public void sendPacket(Object connection, Object packet)
	{
		ReflectionUtil.invokeMethod(this.methodPlayerConnectionsendPacket, connection, packet);
	}
	
	// -------------------------------------------- //
	// PING
	// -------------------------------------------- //
	
	@Override
	public int getPing(Player player)
	{
		Object handle = this.getHandle(player);
		return ReflectionUtil.getField(this.fieldNmsPlayerPing, handle);
	}
	
}
