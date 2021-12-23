package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.util.MUtil;
import org.jetbrains.annotations.Contract;

public class ContainerGameProfileProperty
{
	public String name;
	public String value;
	public String signature;
	
	@Contract(value = "null -> false", pure = true)
	@Override
	public boolean equals(Object object)
	{
		if ( ! (object instanceof ContainerGameProfileProperty that)) return false;
		
		return MUtil.equals(
			this.name, that.name,
			this.value, that.value,
			this.signature, that.signature
		);
	}
}
