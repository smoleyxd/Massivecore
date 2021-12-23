package com.massivecraft.massivecore.engine;

import com.massivecraft.massivecore.ConfServer;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.store.Coll;
import org.jetbrains.annotations.Contract;

public class EngineMassiveCoreCollTick extends Engine
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final EngineMassiveCoreCollTick i = new EngineMassiveCoreCollTick();
	@Contract(pure = true)
	public static EngineMassiveCoreCollTick get() { return i; }
	public EngineMassiveCoreCollTick()
	{
		this.setPeriod(1L);
		this.setSync(!ConfServer.collTickAsync);
	}
	
	// -------------------------------------------- //
	// OVERRIDE: RUNNABLE
	// -------------------------------------------- //
	
	@Override
	public void run()
	{
		for (Coll<?> coll : Coll.getInstances())
		{
			coll.onTick();
		}
	}
	
}
