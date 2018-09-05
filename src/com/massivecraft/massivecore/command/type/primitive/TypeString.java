package com.massivecraft.massivecore.command.type.primitive;

import com.massivecraft.massivecore.command.type.TypeAbstract;
import com.massivecraft.massivecore.mixin.MixinChatFilter;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.Collections;

public class TypeString extends TypeAbstract<String>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static TypeString i = new TypeString();
	public static TypeString get() { return i; }
	private static TypeString filteredInstance = new TypeString() {
		@Override
		public boolean isFiltered()
		{
			return true;
		}
	};
	public static TypeString getFiltered() { return filteredInstance;}
	public TypeString() { super(String.class); }
	
	// -------------------------------------------- //
	// FILTER
	// -------------------------------------------- //
	
	private boolean filtered = false;
	public boolean isFiltered() { return this.filtered; }
	public void setFiltered(boolean filtered) { this.filtered = filtered; }
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public String getName()
	{
		return "text";
	}
	
	@Override
	public String read(String arg, CommandSender sender)
	{
		if (this.isFiltered())
		{
			arg = MixinChatFilter.get().modify(sender, arg);
		}
		return arg;
	}
	
	@Override
	public Collection<String> getTabList(CommandSender sender, String arg)
	{
		return Collections.emptySet();
	}

}
