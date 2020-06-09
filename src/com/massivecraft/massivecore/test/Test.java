package com.massivecraft.massivecore.test;

import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.MassiveCoreMConf;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.mixin.MixinMessage;
import com.massivecraft.massivecore.util.IdUtil;
import com.massivecraft.massivecore.util.Txt;

import java.util.List;


public abstract class Test extends Engine
{
	// -------------------------------------------- //
	// FIELDS
	// -------------------------------------------- //
	
	private final List<Object> issues = new MassiveList<>();
	protected void addIssue(Object issue)
	{
		this.issues.add(issue);
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public void setActiveInner(boolean active)
	{
		if (!MassiveCoreMConf.get().debugEnabled) return;
		
		this.test();
		
		
		
		Object message;
		
		if (this.issues.isEmpty()) {
			message = Txt.parse("<g>NO Issues Detected by %s in %s", this.getClass().getSimpleName(), this.getActivePlugin().getName());
		}
		else {
			message = Txt.parse("<b>%d Issues Detected by %s in %s", issues.size(), this.getClass().getSimpleName(), this.getActivePlugin().getName());
		}
		
		MixinMessage.get().messageOne(IdUtil.CONSOLE_ID, Txt.titleize(message));
		
		for (Object issue : issues)
		{
			MixinMessage.get().messageOne(IdUtil.CONSOLE_ID, issue);
		}
		
		try
		{
			if (!this.issues.isEmpty()) Thread.sleep(20000L);
		}
		catch (InterruptedException ignored)
		{
			
		}
	}
	
	
	// -------------------------------------------- //
	// ABSTRACT
	// -------------------------------------------- //
	
	protected abstract void test();
}
