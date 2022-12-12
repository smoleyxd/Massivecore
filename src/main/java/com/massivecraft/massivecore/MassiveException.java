package com.massivecraft.massivecore;

import com.massivecraft.massivecore.mson.Mson;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.util.Arrays;
import java.util.Collection;


public class MassiveException extends Exception
{
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	// -------------------------------------------- //
	// MESSAGES
	// -------------------------------------------- //
	
	protected Mson messages = Mson.mson();
	public boolean hasMessages() { return ! this.messages.isEmpty(); }
	public Mson getMessages() { return this.messages; }
	
	@Override
	public @NotNull String getMessage()
	{
		return this.messages.toPlain(true);
	}
	
	// Set single
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull MassiveException setMessage(@NotNull Object part) { this.messages = Mson.mson(part); return this; }
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull MassiveException setMsg(@NotNull String msg) { this.messages = Mson.parse(msg); return this; }
	@Contract(value = "_, _ -> this", mutates = "this")
	public @NotNull MassiveException setMsg(String msg, Object... objects) { this.messages = Mson.parse(msg, objects); return this; }
	
	// Add single
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull MassiveException addMessage(@NotNull Object part)
	{
		// Only add a newline if not empty.
		Mson mson = this.messages.isEmpty() ? Mson.mson(part) : Mson.mson("\n", part);
		this.messages = this.messages.add(mson);
		return this;
	}
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull MassiveException addMsg(@NotNull String msg) { return this.addMessage(Mson.parse(msg)); }
	@Contract(value = "_, _ -> this", mutates = "this")
	public @NotNull MassiveException addMsg(String msg, Object... args) { return this.addMessage(Mson.parse(msg, args)); }
	
	// Set several
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull MassiveException setMsgs(@NotNull Collection<@NotNull String> msgs) { this.messages = Mson.parse(msgs); return this; }
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull MassiveException setMsgs(String @NotNull ... msgs) { return this.setMsgs(Arrays.asList(msgs)); }

	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull MassiveException addMsgs(@NotNull Collection<@NotNull String> msgs) { return this.addMessage(Mson.parse(msgs)); }
	@Contract(value = "_ -> this", mutates = "this")
	public @NotNull MassiveException addMsgs(@NotNull String @NotNull ... msgs) { return this.addMsgs(Arrays.asList(msgs)); }
	
}
