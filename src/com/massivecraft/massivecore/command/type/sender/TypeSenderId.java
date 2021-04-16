package com.massivecraft.massivecore.command.type.sender;

import com.massivecraft.massivecore.SenderPresence;
import com.massivecraft.massivecore.SenderType;
import com.massivecraft.massivecore.store.SenderIdSource;
import com.massivecraft.massivecore.store.SenderIdSourceMixinAllSenderIds;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TypeSenderId extends TypeSenderIdAbstract<String>
{
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	private TypeSenderId(@NotNull SenderIdSource source, @NotNull SenderPresence presence, @NotNull SenderType type)
	{
		super(String.class, source, presence, type);
	}
	
	private TypeSenderId(@NotNull SenderIdSource source, @NotNull SenderPresence presence)
	{
		super(String.class, source, presence);
	}
	
	private TypeSenderId(@NotNull SenderIdSource source, @NotNull SenderType type)
	{
		super(String.class, source, type);
	}
	
	private TypeSenderId(@NotNull SenderIdSource source)
	{
		super(String.class, source);
	}
	
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //
	
	private static final TypeSenderId i = new TypeSenderId(SenderIdSourceMixinAllSenderIds.get());
	public static TypeSenderId get() { return i; }
	
	// -------------------------------------------- //
	// GET
	// -------------------------------------------- //
	
	@Contract("_, _, _ -> new")
	public static @NotNull TypeSenderId get(@NotNull SenderIdSource source, @NotNull SenderPresence presence, @NotNull SenderType type) { return new TypeSenderId(source, presence, type); }
	@Contract("_, _ -> new")
	public static @NotNull TypeSenderId get(@NotNull SenderIdSource source, @NotNull SenderPresence presence) { return new TypeSenderId(source, presence); }
	@Contract("_, _ -> new")
	public static @NotNull TypeSenderId get(@NotNull SenderIdSource source, @NotNull SenderType type) { return new TypeSenderId(source, type); }
	@Contract("_ -> new")
	public static @NotNull TypeSenderId get(@NotNull SenderIdSource source) { return new TypeSenderId(source); }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public String getResultForSenderId(String senderId)
	{
		return senderId;
	}

}
