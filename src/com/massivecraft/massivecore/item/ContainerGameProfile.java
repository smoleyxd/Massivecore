package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.command.editor.annotation.EditorMethods;
import com.massivecraft.massivecore.util.MUtil;
import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.Map;

@EditorMethods(true)
public class ContainerGameProfile
{
	public static final transient List<Map.Entry<String, ContainerGameProfileProperty>> GAMEPROFILEPROPERTIESDEFAULT = new MassiveList<>();
	public static final transient String UUID_DEFAULT = null; // TODO should there be a default uuid which isn't null?
	public static final transient String NAME_DEFAULT = null;
	
	
	public ContainerGameProfile()
	{
		// Construct
	}
	
	private String uuid = null;
	public String getUuid() { return DataItemStack.get(this.uuid, UUID_DEFAULT); }
	public void setUuid(String uuid) { this.uuid = DataItemStack.set(uuid, UUID_DEFAULT); }
	
	private String name = null;
	public String getName() { return DataItemStack.get(this.name, NAME_DEFAULT); }
	public void setName(String name) { this.name = DataItemStack.set(name, NAME_DEFAULT); }
	
	private List<Map.Entry<String, ContainerGameProfileProperty>> gameProfileProperties = null;
	
	public void setGameProfileProperties(List<Map.Entry<String, ContainerGameProfileProperty>> properties)
	{
		this.gameProfileProperties = DataItemStack.set(properties, GAMEPROFILEPROPERTIESDEFAULT);
	}
	
	public List<Map.Entry<String, ContainerGameProfileProperty>> getGameProfileProperties()
	{
		return DataItemStack.get(this.gameProfileProperties, GAMEPROFILEPROPERTIESDEFAULT);
	}
	
	@Contract(value = "null -> false", pure = true)
	@Override
	public boolean equals(Object object)
	{
		if ( ! (object instanceof ContainerGameProfile)) return false;
		ContainerGameProfile that = (ContainerGameProfile)object;
		
		return MUtil.equals(
			this.getUuid(), that.getUuid(),
			this.getName(), that.getName(),
			this.getGameProfileProperties(), that.getGameProfileProperties()
		);
	}
	
}
