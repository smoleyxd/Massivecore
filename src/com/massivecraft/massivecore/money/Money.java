package com.massivecraft.massivecore.money;

import com.massivecraft.massivecore.util.MUtil;
import org.jetbrains.annotations.Contract;

import java.util.Collection;

public class Money
{
	// -------------------------------------------- //
	// MIXIN
	// -------------------------------------------- //
	
	private static MoneyMixin mixin = null;
	public static MoneyMixin mixin() { return mixin; }
	
	public static void mixin(MoneyMixin mixin) { Money.mixin = mixin; }
	
	// -------------------------------------------- //
	// EXTRACT
	// -------------------------------------------- //
	
	@Contract("null -> null")
	public static String accountName(Object account)
	{
		// It's OK to send to or from null...
		if (account == null) return null;
		
		// ... but if something is supplied we must manage to extract a name.
		String ret = MUtil.extract(String.class, "accountName", account);
		if (ret == null) throw new IllegalArgumentException("extraction of accountName from object failed: " + account);
		return ret;
	}
	
	// -------------------------------------------- //
	// ENABLED
	// -------------------------------------------- //
	
	public static boolean enabled()
	{
		if (mixin == null) return false;
		return mixin.enabled();
	}
	
	public static boolean disabled()
	{
		return !enabled();
	}
	
	// -------------------------------------------- //
	// FORMAT AND NAME
	// -------------------------------------------- //
	
	public static String format(double amount)
	{
		if (disabled()) return String.valueOf(amount);
		return mixin.format(amount);
	}
	
	public static String format(double amount, boolean includeUnit)
	{
		if (disabled()) return amount + (includeUnit ? "$": "");
		return mixin.format(amount, includeUnit);
	}
	
	public static String singular()
	{
		if (disabled()) return "singular";
		return mixin.singular();
	}
	
	public static String plural()
	{
		if (disabled()) return "plural";
		return mixin.plural();
	}
	
	// -------------------------------------------- //
	// FRACTIONAL DIGITS
	// -------------------------------------------- //
	
	public static int fractionalDigits()
	{
		if (disabled()) return 0;
		return mixin.fractionalDigits();
	}
	
	public static double prepare(double amount)
	{
		if (disabled()) return amount;
		return mixin.prepare(amount);
	}
	
	// -------------------------------------------- //
	// EXISTANCE
	// -------------------------------------------- //
	
	public static boolean exists(Object account)
	{
		if (disabled()) return false;
		return mixin.exists(accountName(account));
	}
	
	public static boolean create(Object account)
	{
		if (disabled()) return false;
		return mixin.create(accountName(account));
	}
	
	// -------------------------------------------- //
	// CHECK
	// -------------------------------------------- //
	
	public static double get(Object account)
	{
		if (disabled()) return 0D;
		return mixin.get(accountName(account));
	}
	
	public static boolean has(Object account, double amount)
	{
		if (disabled()) return false;
		return mixin.has(accountName(account), amount);
	}
	
	// -------------------------------------------- //
	// MOVE
	// -------------------------------------------- //
	
	public static boolean move(Object from, Object to, Object by, double amount, Collection<String> categories, Object message)
	{
		if (disabled()) return false;
		return mixin.move(accountName(from), accountName(to), accountName(by), amount, categories, message);
	}
	public static boolean move(Object from, Object to, Object by, double amount, String category, Object message)
	{
		if (disabled()) return false;
		return mixin.move(accountName(from), accountName(to), accountName(by), amount, category, message);
	}
	public static boolean move(Object from, Object to, Object by, double amount, Collection<String> categories)
	{
		if (disabled()) return false;
		return mixin.move(accountName(from), accountName(to), accountName(by), amount, categories);
	}
	public static boolean move(Object from, Object to, Object by, double amount, String category)
	{
		if (disabled()) return false;
		return mixin.move(accountName(from), accountName(to), accountName(by), amount, category);
	}
	public static boolean move(Object from, Object to, Object by, double amount)
	{
		if (disabled()) return false;
		return mixin.move(accountName(from), accountName(to), accountName(by), amount);
	}
	
	// -------------------------------------------- //
	// SPAWN
	// -------------------------------------------- //
	
	public static boolean spawn(Object to, Object by, double amount, Collection<String> categories, Object message)
	{
		if (disabled()) return false;
		return mixin.spawn(accountName(to), accountName(by), amount, categories, message);
	}
	public static boolean spawn(Object to, Object by, double amount, String category, Object message)
	{
		if (disabled()) return false;
		return mixin.spawn(accountName(to), accountName(by), amount, category, message);
	}
	public static boolean spawn(Object to, Object by, double amount, Collection<String> categories)
	{
		if (disabled()) return false;
		return mixin.spawn(accountName(to), accountName(by), amount, categories);
	}
	public static boolean spawn(Object to, Object by, double amount, String category)
	{
		if (disabled()) return false;
		return mixin.spawn(accountName(to), accountName(by), amount, category);
	}
	public static boolean spawn(Object to, Object by, double amount)
	{
		if (disabled()) return false;
		return mixin.spawn(accountName(to), accountName(by), amount);
	}
	
	// -------------------------------------------- //
	// DESPAWN
	// -------------------------------------------- //
	
	public static boolean despawn(Object from, Object by, double amount, Collection<String> categories, Object message)
	{
		if (disabled()) return false;
		return mixin.despawn(accountName(from), accountName(by), amount, categories, message);
	}
	public static boolean despawn(Object from, Object by, double amount, String category, Object message)
	{
		if (disabled()) return false;
		return mixin.despawn(accountName(from), accountName(by), amount, category, message);
	}
	public static boolean despawn(Object from, Object by, double amount, Collection<String> categories)
	{
		if (disabled()) return false;
		return mixin.despawn(accountName(from), accountName(by), amount, categories);
	}
	public static boolean despawn(Object from, Object by, double amount, String category)
	{
		if (disabled()) return false;
		return mixin.despawn(accountName(from), accountName(by), amount, category);
	}
	public static boolean despawn(Object from, Object by, double amount)
	{
		if (disabled()) return false;
		return mixin.despawn(accountName(from), accountName(by), amount);
	}
	
	// -------------------------------------------- //
	// SET
	// -------------------------------------------- //
	
	public static boolean set(Object account, Object by, double amount, Collection<String> categories, Object message)
	{
		if (disabled()) return false;
		return mixin.set(accountName(account), accountName(by), amount, categories, message);
	}
	public static boolean set(Object account, Object by, double amount, String category, Object message)
	{
		if (disabled()) return false;
		return mixin.set(accountName(account), accountName(by), amount, category, message);
	}
	public static boolean set(Object account, Object by, double amount, Collection<String> categories)
	{
		if (disabled()) return false;
		return mixin.set(accountName(account), accountName(by), amount, categories);
	}
	public static boolean set(Object account, Object by, double amount, String category)
	{
		if (disabled()) return false;
		return mixin.set(accountName(account), accountName(by), amount, category);
	}
	public static boolean set(Object account, Object by, double amount)
	{
		if (disabled()) return false;
		return mixin.set(accountName(account), accountName(by), amount);
	}
	
}
