package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import com.massivecraft.massivecore.util.IdUtil;
import com.massivecraft.massivecore.util.ReflectionUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NmsChat116R1P extends NmsChatAbstract
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static NmsChat116R1P i = new NmsChat116R1P();
	public static NmsChat116R1P get() { return i; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	protected Class<?> classChatMessageType;
	
	protected Enum<?> enumChatMessageTypeGameInfo;
	protected Enum<?> enumChatMessageTypeSystem;
	
	protected UUID systemUUID = new UUID(0,0);
	
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		this.classChatSerializer = PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent$ChatSerializer");
		this.methodChatSerializer = ReflectionUtil.getMethod(this.classChatSerializer, "a", String.class);
		this.classEnumTitleAction = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutTitle$EnumTitleAction");
		
		this.classChatMessageType = PackageType.MINECRAFT_SERVER.getClass("ChatMessageType");
		
		setupCommon();
		
		this.enumChatMessageTypeSystem = (Enum<?>) this.classChatMessageType.getEnumConstants()[1];
		this.enumChatMessageTypeGameInfo = (Enum<?>) this.classChatMessageType.getEnumConstants()[2];
		// CHAT(0),
		// SYSTEM(1),
		// GAME_INFO(2);
	}
	
	// -------------------------------------------- //
	// SETUP COMMON
	// -------------------------------------------- //
	
	@Override
	protected void setupCommon() throws Throwable
	{
		for (Object object : this.classEnumTitleAction.getEnumConstants())
		{
			Enum<?> e = (Enum<?>) object;
			if (e.name().equalsIgnoreCase("TITLE")) this.enumEnumTitleActionMain = e;
			else if (e.name().equalsIgnoreCase("SUBTITLE")) this.enumEnumTitleActionSub = e;
			else if (e.name().equalsIgnoreCase("TIMES")) this.enumEnumTitleActionTimes = e;
		}
		
		this.classIChatBaseComponent = PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent");
		
		// Get title packet and it's constructor
		this.classPacketPlayOutTitle = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutTitle");
		this.constructorPacketPlayOutTitle = ReflectionUtil.getConstructor(this.classPacketPlayOutTitle, this.classEnumTitleAction, this.classIChatBaseComponent);
		
		this.constructorPacketPlayOutTitleTimes = ReflectionUtil.getConstructor(this.classPacketPlayOutTitle, this.classEnumTitleAction, this.classIChatBaseComponent, int.class, int.class, int.class);
		
		// Get Chat packet and it's constructor
		this.classPacketPlayOutChat = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutChat");
		
		// 1.16 Constructor Params changes
		this.constructorPacketPlayOutChat = ReflectionUtil.getConstructor(this.classPacketPlayOutChat, this.classIChatBaseComponent, this.classChatMessageType, UUID.class);
	}
	
	// -------------------------------------------- //
	// CHAT
	// -------------------------------------------- //
	
	@Override
	public void sendChatMson(Object sendeeObject, Mson mson)
	{
		CommandSender sendee = IdUtil.getSender(sendeeObject);
		if (sendee == null) return;
		
		if (sendee instanceof Player)
		{
			Player player = (Player)sendee;
			String raw = mson.toRaw();
			Object component = toComponent(raw);
			Object packet = ReflectionUtil.invokeConstructor(this.constructorPacketPlayOutChat, component, this.enumChatMessageTypeSystem, systemUUID);
			NmsBasics.get().sendPacket(player, packet);
		}
		else
		{
			String plain = mson.toPlain(true);
			this.sendChatPlain(sendee, plain);
		}
	}
	
	// -------------------------------------------- //
	// ACTIONBAR
	// -------------------------------------------- //
	
	@Override
	public <T> T constructActionBarPacket(Object component) {
		return ReflectionUtil.invokeConstructor(this.constructorPacketPlayOutChatType, component, this.enumChatMessageTypeGameInfo, systemUUID);
	}
	
}
