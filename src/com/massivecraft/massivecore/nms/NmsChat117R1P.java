package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import com.massivecraft.massivecore.util.IdUtil;
import com.massivecraft.massivecore.util.ReflectionUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.UUID;

public class NmsChat117R1P extends NmsChat
{
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static NmsChat117R1P i = new NmsChat117R1P();
	public static NmsChat117R1P get() { return i; }
	
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	// ChatSerializer
	protected Class<?> classChatSerializer;
	protected Method methodChatSerializer;
	
	// IChatBaseComponent
	protected Class<?> classIChatBaseComponent;
	
	// PacketPlayOutChat
	protected Class<?> classPacketPlayOutChat;
	protected Constructor<?> constructorPacketPlayOutChat;
	protected Constructor<?> constructorPacketPlayOutChatType;
	
	// PacketPlayOutTitle
	protected Class<?> classTitleAnimationPacket;
	protected Class<?> classTitleTextPacket;
	protected Class<?> classTitleSubtitleTextPacket;
	protected Constructor<?> constructorTitleAnimationPacket;
	protected Constructor<?> constructorTitleTextPacket;
	protected Constructor<?> constructorTitleSubtitleTextPacket;
	
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
		this.classChatSerializer = PackageType.MINECRAFT_NETWORK_CHAT.getClass("IChatBaseComponent$ChatSerializer");
		this.methodChatSerializer = ReflectionUtil.getMethod(this.classChatSerializer, "a", String.class);
		this.classTitleTextPacket = PackageType.MINECRAFT_NETWORK_PROTOCOL_GAME.getClass("ClientboundSetTitleTextPacket");
		this.classTitleSubtitleTextPacket = PackageType.MINECRAFT_NETWORK_PROTOCOL_GAME.getClass("ClientboundSetSubtitleTextPacket");
		this.classTitleAnimationPacket = PackageType.MINECRAFT_NETWORK_PROTOCOL_GAME.getClass("ClientboundSetTitlesAnimationPacket");
		
		this.classChatMessageType = PackageType.MINECRAFT_NETWORK_CHAT.getClass("ChatMessageType");
		
		setupCommon();
		
		this.enumChatMessageTypeSystem = (Enum<?>) this.classChatMessageType.getEnumConstants()[1];
		this.enumChatMessageTypeGameInfo = (Enum<?>) this.classChatMessageType.getEnumConstants()[2];
		// CHAT(0),
		// SYSTEM(1),
		// GAME_INFO(2);
		this.constructorPacketPlayOutChatType = ReflectionUtil.getConstructor(this.classPacketPlayOutChat, this.classIChatBaseComponent, this.classChatMessageType, UUID.class);
	}
	
	// -------------------------------------------- //
	// SETUP COMMON
	// -------------------------------------------- //
	
	protected void setupCommon() throws Throwable
	{
		
		this.classIChatBaseComponent = PackageType.MINECRAFT_NETWORK_CHAT.getClass("IChatBaseComponent");
		
		// Title times
		this.classTitleAnimationPacket = PackageType.MINECRAFT_NETWORK_PROTOCOL_GAME.getClass("ClientboundSetTitlesAnimationPacket");
		this.constructorTitleAnimationPacket = ReflectionUtil.getConstructor(classTitleAnimationPacket, int.class, int.class, int.class);
		
		// Title main text
		this.classTitleTextPacket = PackageType.MINECRAFT_NETWORK_PROTOCOL_GAME.getClass("ClientboundSetTitleTextPacket"); //
		this.constructorTitleTextPacket = ReflectionUtil.getConstructor(classTitleTextPacket, this.classIChatBaseComponent);
		
		// Title subtitle text
		this.classTitleSubtitleTextPacket = PackageType.MINECRAFT_NETWORK_PROTOCOL_GAME.getClass("ClientboundSetSubtitleTextPacket");
		this.constructorTitleSubtitleTextPacket = ReflectionUtil.getConstructor(classTitleSubtitleTextPacket, this.classIChatBaseComponent);
		
		// Get Chat packet and it's constructor
		this.classPacketPlayOutChat = PackageType.MINECRAFT_NETWORK_PROTOCOL_GAME.getClass("PacketPlayOutChat");
		
		// 1.16 Constructor Params changes
		this.constructorPacketPlayOutChat = ReflectionUtil.getConstructor(this.classPacketPlayOutChat, this.classIChatBaseComponent, this.classChatMessageType, UUID.class);
	}
	
	// -------------------------------------------- //
	// TO COMPONENT
	// -------------------------------------------- //
	
	protected Object toComponent(String raw)
	{
		return ReflectionUtil.invokeMethod(this.methodChatSerializer, null, raw);
	}
	
	// -------------------------------------------- //
	// CHAT
	// -------------------------------------------- //
	
	@Override
	public void sendChatMson(Object sendeeObject, @NotNull Mson mson)
	{
		CommandSender sendee = IdUtil.getSender(sendeeObject);
		if (sendee == null) return;
		
		if (sendee instanceof Player)
		{
			Player player = (Player)sendee;
			String raw = mson.toRaw();
			Object component = toComponent(raw);
			Object packet = ReflectionUtil.invokeConstructor(this.constructorPacketPlayOutChat, component);
			NmsBasics.get().sendPacket(player, packet);
		}
		else
		{
			String plain = mson.toPlain(true);
			this.sendChatPlain(sendee, plain);
		}
	}
	
	// -------------------------------------------- //
	// TITLE
	// -------------------------------------------- //
	
	@Override
	public void sendTitleRaw(Object sendeeObject, int ticksIn, int ticksStay, int ticksOut, String rawMain, String rawSub)
	{
		Player player = IdUtil.getPlayer(sendeeObject);
		if (player == null) return;
		
		Object component;
		Object packet;
		
		// in, stay, out
		packet = ReflectionUtil.invokeConstructor(this.constructorTitleAnimationPacket, ticksIn, ticksStay, ticksOut);
		NmsBasics.get().sendPacket(player, packet);
		
		// main
		if (rawMain != null)
		{
			component = toComponent(rawMain);
			packet = ReflectionUtil.invokeConstructor(this.constructorTitleTextPacket, component);
			NmsBasics.get().sendPacket(player, packet);
		}
		
		// sub
		if (rawSub != null)
		{
			component = toComponent(rawSub);
			packet = ReflectionUtil.invokeConstructor(this.constructorTitleSubtitleTextPacket, component);
			NmsBasics.get().sendPacket(player, packet);
		}
	}
	
	// -------------------------------------------- //
	// ACTIONBAR
	// -------------------------------------------- //
	
	@Override
	public void sendActionbarRaw(Object sendeeObject, String raw)
	{
		Player player = IdUtil.getPlayer(sendeeObject);
		if (player == null) return;
		
		Object component = toComponent(raw);
		Object packet = this.constructActionBarPacket(component);
		NmsBasics.get().sendPacket(player, packet);
	}
	
	public <T> T constructActionBarPacket(Object component) {
		return ReflectionUtil.invokeConstructor(this.constructorPacketPlayOutChatType, component, (byte)2);
	}
	
	
}
