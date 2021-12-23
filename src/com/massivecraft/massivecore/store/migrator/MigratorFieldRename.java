package com.massivecraft.massivecore.store.migrator;

import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonObject;

public record MigratorFieldRename(String from, String to) implements Migrator
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	public String getFrom() {return from;}
	
	public String getTo() {return to;}
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	public static MigratorFieldRename get(String from, String to) {return new MigratorFieldRename(from, to);}
	public MigratorFieldRename
	{
		if (from == null) throw new NullPointerException("from");
		if (to == null) throw new NullPointerException("to");
		
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void migrate(JsonObject entity)
	{
		String from = this.getFrom();
		String to = this.getTo();
		
		JsonElement element = entity.remove(from);
		if (element != null) entity.add(to, element);
	}
	
}
