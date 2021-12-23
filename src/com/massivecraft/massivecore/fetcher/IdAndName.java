package com.massivecraft.massivecore.fetcher;

import org.jetbrains.annotations.Contract;

import java.util.UUID;

public class IdAndName
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final UUID id;
	public UUID getId() { return this.id; }
	
	private final String name;
	public String getName() { return this.name; }
	
	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //
	
	@Contract(value = "null, null -> fail", pure = true)
	public IdAndName(UUID id, String name)
	{
		if (id == null && name == null)
		{
			throw new NullPointerException("one of id and name can be null but not both!");
		}
		
		this.id = id;
		this.name = name;
	}

	// -------------------------------------------- //
	// HASH CODE & EQUALS
	// -------------------------------------------- //
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Contract(value = "null -> false", pure = true)
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof IdAndName other)) return false;
		
		if (id == null)
		{
			if (other.id != null) return false;
		}
		else if (!id.equals(other.id)) return false;
		
		if (name == null) return other.name == null;
		else return name.equals(other.name);
	}	
	
}
