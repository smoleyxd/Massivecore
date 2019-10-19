package com.massivecraft.massivecore.test;

import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.command.type.TypeEnchantment;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.enchantments.Enchantment;

import java.util.Arrays;
import java.util.List;

public class TestTypeEnchantment extends Test
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static TestTypeEnchantment i = new TestTypeEnchantment();
	public static TestTypeEnchantment get() { return i; }
	
	// -------------------------------------------- //
	// TEST
	// -------------------------------------------- //
	
	@Override
	public void test()
	{
		final List<Enchantment> enchantments = new MassiveList<>(Arrays.asList(Enchantment.values()));
		
		enchantments.removeIf(enchantment -> TypeEnchantment.ID_TO_RAWNAMES.containsKey(TypeEnchantment.enchantmentToKey(enchantment)));
		
		for (Enchantment enchantment : enchantments)
		{
			String issue = Txt.parse("<i>The enchantment <h>%s<i> lacks nicename in TypeEnchantment.", TypeEnchantment.enchantmentToKey(enchantment));
			this.addIssue(issue);
		}
		
	}
}
