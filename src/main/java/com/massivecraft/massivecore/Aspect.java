package com.massivecraft.massivecore;

import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.xlib.gson.annotations.SerializedName;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Aspect extends Entity<Aspect>
{	
	// -------------------------------------------- //
	// META
	// -------------------------------------------- //
	
	public static Aspect get(Object oid)
	{
		return AspectColl.get().get(oid);
	}
	
	// -------------------------------------------- //
	// TRANSIENT FIELDS
	// -------------------------------------------- //
	
	private transient boolean registered = false;
	public boolean isRegistered() { return this.registered; }
	public void register() { this.registered = true; }
	
	private transient Collection<String> desc = new ArrayList<>();
	public Collection<String> getDesc() { return this.desc; }
	@Contract(mutates = "this")
	public void setDesc(Collection<String> val) { this.desc = val; }
	@Contract(mutates = "this")
	public void setDesc(String @NotNull ... val) { this.desc = Arrays.asList(val); }
	
	// -------------------------------------------- //
	// STORED FIELDS
	// -------------------------------------------- //
	
	@SerializedName("mid")
	private String multiverseId;
	public String getMultiverseId() { return this.multiverseId; }
	@Contract(mutates = "this")
	public void setMultiverseId(String multiverseId) { this.multiverseId = multiverseId; }
	public Multiverse getMultiverse()
	{
		Multiverse ret = MultiverseColl.get().get(this.multiverseId);
		if (ret == null) ret = MultiverseColl.get().get(MassiveCore.DEFAULT);
		return ret;
	}
	@Contract(mutates = "this")
	public void setMultiverse(@NotNull Multiverse val) { this.multiverseId = val.getId(); }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public Aspect()
	{
		
	}
	
}
