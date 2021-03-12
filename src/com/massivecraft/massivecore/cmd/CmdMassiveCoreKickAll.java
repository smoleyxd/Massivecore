package com.massivecraft.massivecore.cmd;

import com.massivecraft.massivecore.MassiveCore;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanTrue;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.entity.Player;

public class CmdMassiveCoreKickAll extends MassiveCoreCommand
{
	// -------------------------------------------- //
	// INSTANCE
	// -------------------------------------------- //

	private static CmdMassiveCoreKickAll i = new CmdMassiveCoreKickAll();

	public static CmdMassiveCoreKickAll get()
	{
		return i;
	}

	// -------------------------------------------- //
	// CONSTRUCT
	// -------------------------------------------- //

	public CmdMassiveCoreKickAll()
	{
		// Parameters
		this.addParameter(TypeBooleanTrue.get(), "include self", "true").setDesc("include sender");
		this.addParameter(TypeString.getFiltered(), "reason", "null", true).setDesc("reason for the kick");
	}

	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public void perform() throws MassiveException
	{
		// Args
		boolean includeSender = this.readArg(true);
		String kickReason = Txt.parse((String) this.readArg(null));

		// Apply
		for (Player player : MUtil.getOnlinePlayers())
		{
				if (!includeSender && me == player) continue;
				player.kickPlayer(kickReason);
		}

		//Inform
		String output = Txt.parse("%s kicked all players. Players online: %d", sender.getName(), MUtil.getOnlinePlayers().size());
		msg(output);
		MassiveCore.get().log(output);
	}

}
