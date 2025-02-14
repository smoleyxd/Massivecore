package com.massivecraft.massivecore.mson;

import com.massivecraft.massivecore.util.Txt;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum MsonEventAction
{
	// -------------------------------------------- //
	// ENUM
	// -------------------------------------------- //

	SUGGEST_COMMAND,
	RUN_COMMAND,
	OPEN_URL,
	SHOW_TEXT,
	SHOW_ITEM
	
	// End of list
	;
	
	// -------------------------------------------- //
	// PROPERTIES
	// -------------------------------------------- //

	@Contract(pure = true)
	public @NotNull MsonEventType getType()
	{
		if (this == SHOW_TEXT) return MsonEventType.HOVER;
		if (this == SHOW_ITEM) return MsonEventType.HOVER;
		return MsonEventType.CLICK;
	}
	
	public @Nullable String getTooltipPrefix()
	{
		if (this == SUGGEST_COMMAND) return Txt.parse("<h>Suggest: <c>");
		if (this == RUN_COMMAND) return Txt.parse("<h>Command: <c>");
		if (this == OPEN_URL) return Txt.parse("<h>Link: <c>");
		return null;
	}

}
