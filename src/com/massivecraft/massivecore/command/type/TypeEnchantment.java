package com.massivecraft.massivecore.command.type;

import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TypeEnchantment extends TypeAbstractChoice<Enchantment>
{
	// -------------------------------------------- //
	// DATA
	// -------------------------------------------- //
	// http://minecraft.gamepedia.com/Enchanting#Enchantments
	
	// The first name is taken from the wiki. Those names are those people think of.
	// The second name is the Spigot key name.
	// Thereafter comes assorted extras
	public static Map<String, List<String>> ID_TO_RAWNAMES = new MassiveMap<>(
		"protection", new MassiveList<>("Protection", "PROTECTION_ENVIRONMENTAL"),
		"fire_protection", new MassiveList<>("Fire Protection", "PROTECTION_FIRE"),
		"feather_falling", new MassiveList<>("Feather Falling", "PROTECTION_FALL", "FallProtection"),
		"blast_protection", new MassiveList<>("Blast Protection", "PROTECTION_EXPLOSIONS", "ExplosionProtection"),
		"projectile_protection", new MassiveList<>("Projectile Protection", "PROTECTION_PROJECTILE", "ProjectileProtection"),
		"respiration", new MassiveList<>("Respiration", "OXYGEN", "Breathing"),
		"aqua_affinity", new MassiveList<>("Aqua Affinity", "WATER_WORKER"),
		"thorns", new MassiveList<>("Thorns", "THORNS"),
		"depth_strider", new MassiveList<>("Depth Strider", "DEPTH_STRIDER"),
		"frost_walker", new MassiveList<>("Frost Walker", "FROST_WALKER"),
		"binding_curse", new MassiveList<>("Curse of Binding", "BINDING_CURSE", "BindingCurse"),
		"sharpness", new MassiveList<>("Sharpness", "DAMAGE_ALL"),
		"smite", new MassiveList<>("Smite", "DAMAGE_UNDEAD"),
		"bane_of_arthropods", new MassiveList<>("Bane of Arthropods", "DAMAGE_ARTHROPODS", "BaneArthropods", "Arthropods"),
		"knockback", new MassiveList<>("Knockback", "KNOCKBACK"),
		"fire_aspect", new MassiveList<>("Fire Aspect", "FIRE_ASPECT"),
		"looting", new MassiveList<>("Looting", "LOOT_BONUS_MOBS"),
		"sweeping", new MassiveList<>("Sweeping Edge", "SWEEPING_EDGE"),
		"efficiency", new MassiveList<>("Efficiency", "DIG_SPEED"),
		"silk_touch", new MassiveList<>("Silk Touch", "SILK_TOUCH"),
		"unbreaking", new MassiveList<>("Unbreaking", "DURABILITY"),
		"fortune", new MassiveList<>("Fortune", "LOOT_BONUS_BLOCKS"),
		"power", new MassiveList<>("Power", "ARROW_DAMAGE"),
		"punch", new MassiveList<>("Punch", "ARROW_KNOCKBACK"),
		"flame", new MassiveList<>("Flame", "ARROW_FIRE"),
		"infinity", new MassiveList<>("Infinity", "ARROW_INFINITE", "ArrowInfinity"),
		"luck_of_the_sea", new MassiveList<>("Luck of the Sea", "LUCK", "LuckOfSea", "LuckTheSea", "LuckSea"),
		"lure", new MassiveList<>("Lure", "LURE"),
		"mending", new MassiveList<>("Mending", "MENDING"),
		"vanishing_curse", new MassiveList<>("Curse of Vanishing", "VANISHING_CURSE"),
		"loyalty", new MassiveList<>("Loyalty", "LOYALTY"),
		"impaling", new MassiveList<>("Impaling", "IMPALING"),
		"riptide", new MassiveList<>("Riptide", "RIPTIDE"),
		"channeling", new MassiveList<>("Channeling", "CHANNELING"),
		"piercing", new MassiveList<>("Piercing", "PIERCING"),
		"multishot", new MassiveList<>("Multishot", "MULTISHOT"),
		"quick_charge", new MassiveList<>("Quick Charge", "QUICKCHARGE", "QuickCharge")
	);
	
	public static String enchantmentToKey(Enchantment enchantment) {
		return enchantment.getKey().getKey();
	}
	
	public static Enchantment enchantmentFromKey(String key) {
		return Enchantment.getByKey(NamespacedKey.minecraft(key));
	}
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static TypeEnchantment i = new TypeEnchantment();
	public static TypeEnchantment get() { return i; }
	private TypeEnchantment()
	{
		super(Enchantment.class);
		this.setVisualColor(ChatColor.AQUA);
		this.setAll(Enchantment.values());
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //

	@Override
	public String getNameInner(Enchantment enchantment)
	{
		String rawname = enchantment.getKey().getKey();
		List<String> rawnames = ID_TO_RAWNAMES.get(enchantmentToKey(enchantment));
		if (rawnames != null) rawname = rawnames.get(0);
		return Txt.getNicedEnumString(rawname);
	}

	@Override
	public Set<String> getNamesInner(Enchantment enchantment)
	{
		// Create
		Set<String> ret = new MassiveSet<>();
		
		// Fill
		List<String> raws = new MassiveList<>();
		List<String> rawnames = ID_TO_RAWNAMES.get(enchantmentToKey(enchantment));
		if (rawnames != null) raws.addAll(rawnames);
		raws.add(enchantment.getKey().getKey());
		for (String raw : raws)
		{
			ret.add(Txt.getNicedEnumString(raw));
		}
		
		// Return
		return ret;
	}
	
	@Override
	public String getIdInner(Enchantment enchantment)
	{
		return enchantmentToKey(enchantment);
	}

}
