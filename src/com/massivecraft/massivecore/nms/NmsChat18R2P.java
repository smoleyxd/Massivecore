package com.massivecraft.massivecore.nms;

import com.massivecraft.massivecore.particleeffect.ReflectionUtils.PackageType;
import com.massivecraft.massivecore.util.ReflectionUtil;

public class NmsChat18R2P extends NmsChatAbstract
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final NmsChat18R2P i = new NmsChat18R2P();
	public static NmsChat18R2P get() { return i; }
	
	// -------------------------------------------- //
	// SETUP
	// -------------------------------------------- //
	
	@Override
	public void setup() throws Throwable
	{
		this.classChatSerializer = PackageType.MINECRAFT_SERVER_VERSION.getClass("IChatBaseComponent$ChatSerializer");
		this.methodChatSerializer = ReflectionUtil.getMethod(this.classChatSerializer, "a", String.class);
		this.classEnumTitleAction = PackageType.MINECRAFT_SERVER_VERSION.getClass("PacketPlayOutTitle$EnumTitleAction");
		
		setupCommon();
		
		this.constructorPacketPlayOutChatType = ReflectionUtil.getConstructor(this.classPacketPlayOutChat, this.classIChatBaseComponent, Byte.TYPE);
	}
}
