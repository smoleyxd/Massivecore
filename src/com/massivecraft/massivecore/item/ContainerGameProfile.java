package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.command.editor.annotation.EditorMethods;

import java.util.List;
import java.util.Map;

@EditorMethods(true)
public class ContainerGameProfile
{
	public static final transient List<Map.Entry<String, ContainerGameProfileProperty>> GAMEPROFILEPROPERTIESDEFAULT = new MassiveList<>();
	public static final transient String UUID_DEFAULT = null; // TODO should there be a default uuid which isn't null?
	
	public ContainerGameProfile()
	{
		// Construct
	}
	
	public String uuid = null;
	public String getUuid() { return DataItemStack.get(this.uuid, UUID_DEFAULT); }
	public void setUuid(String uuid) { this.uuid = DataItemStack.set(uuid, UUID_DEFAULT); }
	
	private List<Map.Entry<String, ContainerGameProfileProperty>> gameProfileProperties = null;
	
	public void setGameProfileProperties(List<Map.Entry<String, ContainerGameProfileProperty>> properties)
	{
		this.gameProfileProperties = DataItemStack.set(properties, GAMEPROFILEPROPERTIESDEFAULT);
	}
	
	public List<Map.Entry<String, ContainerGameProfileProperty>> getGameProfileProperties()
	{
		return DataItemStack.get(this.gameProfileProperties, GAMEPROFILEPROPERTIESDEFAULT);
	}
	
}
