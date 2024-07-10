package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.util.Txt;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.Contract;

public class PotionUtil
{
	@Contract("null -> null")
	public static PotionType toPotionType(String potionString)
	{
		return Registry.POTION.get(NamespacedKey.minecraft(potionString));
	}

	@Contract("null -> null")
	public static String toPotionString(PotionType potionType)
	{
		NamespacedKey key = Registry.POTION.getKey(potionType);
		if (key == null) return null;
		return key.getKey();
	}
	
	public static String friendlyPotionEffectName(PotionEffectType unfriendlyPotionEffectType)
	{
		if (PotionEffectType.HASTE.equals(unfriendlyPotionEffectType)) return "Haste";
		else if (PotionEffectType.RESISTANCE.equals(unfriendlyPotionEffectType)) return "Resistance";
		else if (PotionEffectType.NAUSEA.equals(unfriendlyPotionEffectType)) return "Nausea";
		else if (PotionEffectType.INSTANT_DAMAGE.equals(unfriendlyPotionEffectType)) return "Instant Damage";
		else if (PotionEffectType.INSTANT_HEALTH.equals(unfriendlyPotionEffectType)) return "Instant Health";
		else if (PotionEffectType.STRENGTH.equals(unfriendlyPotionEffectType)) return "Strength";
		else if (PotionEffectType.JUMP_BOOST.equals(unfriendlyPotionEffectType)) return "Jump Boost";
		else if (PotionEffectType.SLOWNESS.equals(unfriendlyPotionEffectType)) return "Slowness";
		else if (PotionEffectType.MINING_FATIGUE.equals(unfriendlyPotionEffectType)) return "Mining Fatigue";
		else if (PotionEffectType.UNLUCK.equals(unfriendlyPotionEffectType)) return "Bad Luck";
		
		return Txt.getNicedEnumString(unfriendlyPotionEffectType.getName(), " ");
	}
}