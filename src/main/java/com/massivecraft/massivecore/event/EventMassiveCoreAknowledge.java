package com.massivecraft.massivecore.event;

import org.bukkit.event.HandlerList;

// "Acknowledge" is in our mind the opposite of "Ignore".
// The purpose of this event is to decide if a unit of communication should be received or ignored.
// A unit of communication can for example be a chat message or a sound effect.
@Deprecated
public class EventMassiveCoreAknowledge extends EventMassiveCoreAcknowledge
{
	// -------------------------------------------- //
	// REQUIRED EVENT CODE
	// -------------------------------------------- //
	
	@Override public HandlerList getHandlers() { return handlers; }
	public static HandlerList getHandlerList() { return handlers; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public EventMassiveCoreAknowledge(Object sender, Object sendee)
	{
		super(sender, sendee);
	}
	
}
