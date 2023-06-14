package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import com.massivecraft.massivecore.util.ReflectionUtil;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R1.block.CraftSign;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R1.scoreboard.CraftScoreboard;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Field;

public class NmsBasics120R1 extends NmsBasics
{
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	@SuppressWarnings("FieldMayBeFinal")
	private static NmsBasics120R1 i = new NmsBasics120R1();
	public static NmsBasics120R1 get() { return i; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	// org.bukkit.craftbukkit.scoreboard.CraftTeam
	private Class<?> classCraftTeam;
	// org.bukkit.craftbukkit.scoreboard.CraftTeam#team
	private Field fieldCraftTeamHandle;
	
	// org.bukkit.craftbukkit.scoreboard.CraftObjective
	private Class<?> classCraftObjective;
	// org.bukkit.craftbukkit.scoreboard.CraftObjective#objective
	private Field fieldCraftObjectiveHandle;
	
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		// GET HANDLE
		this.classCraftTeam = PackageType.CRAFTBUKKIT_VERSION_SCOREBOARD.getClass("CraftTeam");
		this.fieldCraftTeamHandle = ReflectionUtil.getField(this.classCraftTeam, "team");
		
		this.classCraftObjective = PackageType.CRAFTBUKKIT_VERSION_SCOREBOARD.getClass("CraftObjective");
		this.fieldCraftObjectiveHandle = ReflectionUtil.getField(this.classCraftObjective, "objective");
	}
	
	// -------------------------------------------- //
	// GET HANDLE
	// -------------------------------------------- //
	
	@Override
	@SuppressWarnings("deprecation")
	public net.minecraft.world.entity.Entity getHandle(Entity entity)
	{
		if (entity == null) return null;
		if (!(entity instanceof CraftEntity craftEntity))
			throw new IllegalArgumentException("Entity provided is not a CraftEntity");
		
		return craftEntity.getHandle();
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public net.minecraft.server.level.ServerLevel getHandle(World world)
	{
		if (world == null) return null;
		if (!(world instanceof CraftWorld craftWorld))
			throw new IllegalArgumentException("World provided is not a CraftWorld");
		
		return craftWorld.getHandle();
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public net.minecraft.world.scores.Scoreboard getHandle(Scoreboard scoreboard)
	{
		if (scoreboard == null) return null;
		if (!(scoreboard instanceof CraftScoreboard craftScoreboard))
			throw new IllegalArgumentException("Scoreboard provided is not a CraftScoreboard");
		
		return craftScoreboard.getHandle();
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public <T> T getHandle(Team team)
	{
		// CraftTeam is not public, annoyingly.
		if (team == null) return null;
		return ReflectionUtil.getField(this.fieldCraftTeamHandle, team);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public <T> T getHandle(Objective objective)
	{
		// CraftObjective is not public, annoyingly.
		if (objective == null) return null;
		return ReflectionUtil.getField(this.fieldCraftObjectiveHandle, objective);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public net.minecraft.world.level.block.entity.SignBlockEntity getHandle(Sign sign)
	{
		if (sign == null) return null;
		if (!(sign instanceof CraftSign craftSign))
			throw new IllegalArgumentException("Sign provided is not a CraftSign");
		
		return (SignBlockEntity) craftSign.getTileEntity();
	}
	
	// -------------------------------------------- //
	// GET BUKKIT
	// -------------------------------------------- //
	
	@Override
	public Entity getBukkit(Object handle)
	{
		if (handle == null) return null;
		if (!(handle instanceof net.minecraft.world.entity.Entity entity))
			throw new IllegalArgumentException("handle provided is not an NMS Entity");
		
		return entity.getBukkitEntity();
	}
	
	// -------------------------------------------- //
	// CONNECTION & PACKET
	// -------------------------------------------- //
	
	@Override
	public net.minecraft.server.network.ServerGamePacketListenerImpl getConnection(Player player)
	{
		if (player == null) return null;
		if (!(player instanceof CraftPlayer craftPlayer))
			throw new IllegalArgumentException("player provided is not a CraftPlayer");
		
		return craftPlayer.getHandle().connection;
	}
	
	@Override
	public void sendPacket(Object connection, Object packet)
	{
		if (!(connection instanceof net.minecraft.server.network.ServerGamePacketListenerImpl nmsConnection))
			throw new IllegalArgumentException("connection provided is not an NMS ServerGamePacketListenerImpl");
		
		if (!(packet instanceof net.minecraft.network.protocol.Packet<?> nmsPacket))
			throw new IllegalArgumentException("packet provided is not an NMS Packet");
		
		nmsConnection.send(nmsPacket);
	}
	
}
