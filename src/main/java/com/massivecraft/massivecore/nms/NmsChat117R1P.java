package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.util.IdUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NmsChat117R1P extends NmsChat
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final NmsChat117R1P i = new NmsChat117R1P();
	public static NmsChat117R1P get() { return i; }
	
	// -------------------------------------------- //
	// CHAT
	// -------------------------------------------- //
	
	@Override
	public void sendChatMson(Object sendeeObject, @NotNull Mson mson)
	{
		CommandSender sendee = IdUtil.getSender(sendeeObject);
		if (sendee == null) return;
		
		if (sendee instanceof Player player)
		{
			player.spigot().sendMessage(ChatMessageType.CHAT, ComponentSerializer.parse(mson.toRaw()));
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
		
		player.sendTitle(
			BaseComponent.toLegacyText(ComponentSerializer.parse(rawMain)),
			BaseComponent.toLegacyText(ComponentSerializer.parse(rawSub)),
			ticksIn,
			ticksStay,
			ticksOut
		);
	}
	
	// -------------------------------------------- //
	// ACTIONBAR
	// -------------------------------------------- //
	
	@Override
	public void sendActionbarRaw(Object sendeeObject, String raw)
	{
		Player player = IdUtil.getPlayer(sendeeObject);
		if (player == null) return;
		
		player.spigot().sendMessage(
			ChatMessageType.ACTION_BAR,
			ComponentSerializer.parse(raw)
		);
	}
}
