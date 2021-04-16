package com.massivecraft.massivecore.command.type.sender;

import com.massivecraft.massivecore.SenderPresence;
import com.massivecraft.massivecore.SenderType;
import com.massivecraft.massivecore.store.SenderColl;
import com.massivecraft.massivecore.store.SenderEntity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TypeSenderEntity<T extends SenderEntity<T>> extends TypeSenderIdAbstract<T>
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	protected final SenderColl<T> coll;
	public SenderColl<T> getColl() { return this.coll; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	private TypeSenderEntity(@NotNull SenderColl<T> coll, @NotNull SenderPresence presence, @NotNull SenderType type)
	{
		super(SenderEntity.class, coll, presence, type);
		this.coll = coll;
	}
	
	private TypeSenderEntity(@NotNull SenderColl<T> coll, @NotNull SenderPresence presence)
	{
		super(SenderEntity.class, coll, presence);
		this.coll = coll;
	}
	
	private TypeSenderEntity(@NotNull SenderColl<T> coll, @NotNull SenderType type)
	{
		super(SenderEntity.class, coll, type);
		this.coll = coll;
	}
	
	private TypeSenderEntity(@NotNull SenderColl<T> coll)
	{
		super(SenderEntity.class, coll);
		this.coll = coll;
	}
	
	// -------------------------------------------- //
	// GET
	// -------------------------------------------- //
	
	@Contract("_, _, _ -> new")
	public static <T extends SenderEntity<T>> @NotNull TypeSenderEntity<T> get(@NotNull SenderColl<T> coll, @NotNull SenderPresence presence, @NotNull SenderType type) { return new TypeSenderEntity<>(coll, presence, type); }
	@Contract("_, _ -> new")
	public static <T extends SenderEntity<T>> @NotNull TypeSenderEntity<T> get(@NotNull SenderColl<T> coll, @NotNull SenderPresence presence) { return new TypeSenderEntity<>(coll, presence); }
	@Contract("_, _ -> new")
	public static <T extends SenderEntity<T>> @NotNull TypeSenderEntity<T> get(@NotNull SenderColl<T> coll, @NotNull SenderType type) { return new TypeSenderEntity<>(coll, type); }
	@Contract("_ -> new")
	public static <T extends SenderEntity<T>> @NotNull TypeSenderEntity<T> get(@NotNull SenderColl<T> coll) { return new TypeSenderEntity<>(coll); }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public T getResultForSenderId(String senderId)
	{
		// Null check is done in SenderColl & IdUtil :)
		return this.coll.get(senderId);
	}

}
