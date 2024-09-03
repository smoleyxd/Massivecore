package com.massivecraft.massivecore;

import com.massivecraft.massivecore.adapter.AdapterBackstringSet;
import com.massivecraft.massivecore.adapter.AdapterBannerPatterns;
import com.massivecraft.massivecore.adapter.AdapterCacheEntity;
import com.massivecraft.massivecore.adapter.AdapterEnchantment;
import com.massivecraft.massivecore.adapter.AdapterEntityInternalMap;
import com.massivecraft.massivecore.adapter.AdapterEntry;
import com.massivecraft.massivecore.adapter.AdapterInventory;
import com.massivecraft.massivecore.adapter.AdapterItemStack;
import com.massivecraft.massivecore.adapter.AdapterMassiveList;
import com.massivecraft.massivecore.adapter.AdapterMassiveMap;
import com.massivecraft.massivecore.adapter.AdapterMassiveSet;
import com.massivecraft.massivecore.adapter.AdapterMassiveTreeMap;
import com.massivecraft.massivecore.adapter.AdapterMassiveTreeSet;
import com.massivecraft.massivecore.adapter.AdapterModdedEnumType;
import com.massivecraft.massivecore.adapter.AdapterMson;
import com.massivecraft.massivecore.adapter.AdapterMsonEvent;
import com.massivecraft.massivecore.adapter.AdapterNamespacedKey;
import com.massivecraft.massivecore.adapter.AdapterPlayerInventory;
import com.massivecraft.massivecore.adapter.AdapterPotionEffectType;
import com.massivecraft.massivecore.adapter.AdapterSound;
import com.massivecraft.massivecore.adapter.AdapterUUID;
import com.massivecraft.massivecore.collections.BackstringSet;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.collections.MassiveListDef;
import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.collections.MassiveMapDef;
import com.massivecraft.massivecore.collections.MassiveSet;
import com.massivecraft.massivecore.collections.MassiveSetDef;
import com.massivecraft.massivecore.collections.MassiveTreeMap;
import com.massivecraft.massivecore.collections.MassiveTreeMapDef;
import com.massivecraft.massivecore.collections.MassiveTreeSet;
import com.massivecraft.massivecore.collections.MassiveTreeSetDef;
import com.massivecraft.massivecore.command.type.RegistryType;
import com.massivecraft.massivecore.item.DataBannerPattern;
import com.massivecraft.massivecore.item.DataItemStack;
import com.massivecraft.massivecore.item.WriterItemStack;
import com.massivecraft.massivecore.mixin.MixinEvent;
import com.massivecraft.massivecore.mson.Mson;
import com.massivecraft.massivecore.mson.MsonEvent;
import com.massivecraft.massivecore.nms.NmsBasics;
import com.massivecraft.massivecore.ps.PS;
import com.massivecraft.massivecore.ps.PSAdapter;
import com.massivecraft.massivecore.store.Coll;
import com.massivecraft.massivecore.store.EntityInternalMap;
import com.massivecraft.massivecore.store.ModificationPollerLocal;
import com.massivecraft.massivecore.store.ModificationPollerRemote;
import com.massivecraft.massivecore.store.migrator.MigratorUtil;
import com.massivecraft.massivecore.util.BoardUtil;
import com.massivecraft.massivecore.util.ContainerUtil;
import com.massivecraft.massivecore.util.EventUtil;
import com.massivecraft.massivecore.util.IdUtil;
import com.massivecraft.massivecore.util.IntervalUtil;
import com.massivecraft.massivecore.util.InventoryUtil;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.PeriodUtil;
import com.massivecraft.massivecore.util.PlayerUtil;
import com.massivecraft.massivecore.util.RecipeUtil;
import com.massivecraft.massivecore.util.ReflectionUtil;
import com.massivecraft.massivecore.util.SignUtil;
import com.massivecraft.massivecore.util.SmokeUtil;
import com.massivecraft.massivecore.util.TimeDiffUtil;
import com.massivecraft.massivecore.util.TimeUnit;
import com.massivecraft.massivecore.util.TimeZoneUtil;
import com.massivecraft.massivecore.util.Txt;
import com.massivecraft.massivecore.xlib.gson.Gson;
import com.massivecraft.massivecore.xlib.gson.GsonBuilder;
import com.massivecraft.massivecore.xlib.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class MassiveCore extends MassivePlugin
{
	// -------------------------------------------- //
	// COMMON CONSTANTS
	// -------------------------------------------- //
	
	public final static String INSTANCE = "instance";
	public final static String DEFAULT = "default";
	public static final String NONE = Txt.parse("<em><silver>none");
	
	public final static Set<String> NOTHING = MUtil.treeset("", "none", "null", "nothing");
	public final static Set<String> REMOVE = MUtil.treeset("clear", "c", "delete", "del", "d", "erase", "e", "remove", "rem", "r", "reset", "res");
	public final static Set<String> NOTHING_REMOVE = MUtil.treeset("", "none", "null", "nothing", "clear", "c", "delete", "del", "d", "erase", "e", "remove", "rem", "r", "reset", "res");
	
	public final static String YES = Txt.parse("<g>Yes");
	public final static String NO = Txt.parse("<b>No");
	public final static String ONLINE_RIGHT_NOW = Txt.parse("<g>Online right now");
	
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static MassiveCore i;
	public static MassiveCore get() { return i; }
	public MassiveCore() { i = this; }
	
	// -------------------------------------------- //
	// STATIC
	// -------------------------------------------- //
	
	public static final Random random = new Random();
	public static final Gson gson = getMassiveCoreGsonBuilder().create();
	
	public static GsonBuilder getMassiveCoreGsonBuilder()
	{
		// Create
		GsonBuilder ret = new GsonBuilder();
		
		// Basic Behavior
		ret.setPrettyPrinting();
		ret.disableHtmlEscaping();
		ret.excludeFieldsWithModifiers(Modifier.TRANSIENT);
		
		// Enumeration Annotation Dodge
		ret.registerTypeAdapterFactory(AdapterModdedEnumType.ENUM_FACTORY);
		
		// Massive Containers
		ret.registerTypeAdapter(MassiveList.class, AdapterMassiveList.get());
		ret.registerTypeAdapter(MassiveListDef.class, AdapterMassiveList.get());
		ret.registerTypeAdapter(MassiveMap.class, AdapterMassiveMap.get());
		ret.registerTypeAdapter(MassiveMapDef.class, AdapterMassiveMap.get());
		ret.registerTypeAdapter(MassiveSet.class, AdapterMassiveSet.get());
		ret.registerTypeAdapter(MassiveSetDef.class, AdapterMassiveSet.get());
		ret.registerTypeAdapter(MassiveTreeMap.class, AdapterMassiveTreeMap.get());
		ret.registerTypeAdapter(MassiveTreeMapDef.class, AdapterMassiveTreeMap.get());
		ret.registerTypeAdapter(MassiveTreeSet.class, AdapterMassiveTreeSet.get());
		ret.registerTypeAdapter(MassiveTreeSetDef.class, AdapterMassiveTreeSet.get());
		
		// Entries (Is this still needed?)
		ret.registerTypeAdapter(Entry.class, AdapterEntry.get());
		
		// Assorted Custom
		ret.registerTypeAdapter(BackstringSet.class, AdapterBackstringSet.get());
		ret.registerTypeAdapter(PS.class, PSAdapter.get());
		ret.registerTypeAdapter(Sound.class, AdapterSound.get());
		ret.registerTypeAdapter(UUID.class, AdapterUUID.get());
		ret.registerTypeAdapter(CacheEntity.class, AdapterCacheEntity.get());
		ret.registerTypeAdapter(NamespacedKey.class, AdapterNamespacedKey.get());
		
		// Mson
		ret.registerTypeAdapter(Mson.class, AdapterMson.get());
		ret.registerTypeAdapter(MsonEvent.class, AdapterMsonEvent.get());
		
		// Banner Patterns Upgrade Adapter
		// NOTE: Must come after the "MassiveContainers" section for priority.
		Type typeBannerPatterns = new TypeToken<MassiveListDef<DataBannerPattern>>() {}.getType();
		ret.registerTypeAdapter(typeBannerPatterns, AdapterBannerPatterns.get());
		
		// ItemStack
		ret.registerTypeAdapter(ItemStack.class, AdapterItemStack.get());
		ret.registerTypeAdapter(CraftItemStack.class, AdapterItemStack.get());
		
		// Inventory
		ret.registerTypeAdapter(Inventory.class, AdapterInventory.get());
		ret.registerTypeAdapter(PlayerInventory.class, AdapterPlayerInventory.get());
		
		// Storage
		ret.registerTypeAdapter(EntityInternalMap.class, AdapterEntityInternalMap.get());
		
		// PotionEffectType
		ret.registerTypeAdapter(PotionEffectType.class, AdapterPotionEffectType.get());
		
		// Enchantment
		ret.registerTypeAdapter(Enchantment.class, AdapterEnchantment.get());
		
		// Return
		return ret;
	}
	
	public static String getServerId() { return ConfServer.serverid; }
	public static String getTaskServerId() { return MassiveCoreMConf.get().taskServerId; }
	public static boolean isTaskServer()
	{
		String taskServerId = getTaskServerId();
		if (taskServerId == null) return true;
		return getServerId().equals(taskServerId);
	}
	
	// -------------------------------------------- //
	// LOAD
	// -------------------------------------------- //
	
	@Override
	public void onLoadInner()
	{
		// These util classes are not automatically loaded/resolved when MassiveCore is being loaded.
		// However they need to be loaded to ensure async safety.
		// This fixes a race condition within the asynchronous class loader (LinkageError).
		ReflectionUtil.forceLoadClasses(
			ContainerUtil.class,
			EventUtil.class,
			IntervalUtil.class,
			InventoryUtil.class,
			PeriodUtil.class,
			RecipeUtil.class,
			SignUtil.class,
			SmokeUtil.class,
			TimeUnit.class,
			TimeDiffUtil.class,
			TimeZoneUtil.class
		);
	}
	
	// -------------------------------------------- //
	// ENABLE
	// -------------------------------------------- //
	
	@Override
	public void onEnableInner()
	{
		// This is safe since all plugins using Persist should bukkit-depend this plugin.
		// Note this one must be before preEnable..
		// TODO: Create something like "de-init all" (perhaps a forloop) to re-add this.
		// TODO: Test and ensure reload compat.
		// Coll.instances.clear();
		
		// Load Server Config
		ConfServer.get().load();
		
		// Setup IdUtil
		IdUtil.setup();
		
		// Setup RegistryType
		RegistryType.registerAll();
		
		MigratorUtil.addJsonRepresentation(ItemStack.class, DataItemStack.class);
		MigratorUtil.addJsonRepresentation(Inventory.class, null);
		MigratorUtil.setTargetVersion(Inventory.class, MigratorUtil.getTargetVersion(DataItemStack.class));
		
		// Activate
		this.activateAuto();
		
		// These must be activated after nms
		this.activate(
			
			// Writer,
			WriterItemStack.class,
			
			// Util
			PlayerUtil.class,
			BoardUtil.class
		);
		
		// Start the examine threads
		// Start AFTER initializing the MConf, because they rely on the MConf.
		if (ConfServer.localPollingEnabled) ModificationPollerLocal.get().start();
		ModificationPollerRemote.get().start();
		
		// Delete Files (at once and additionally after all plugins loaded)
		MassiveCoreTaskDeleteFiles.get().run();
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, MassiveCoreTaskDeleteFiles.get());
	}
	
	@Override
	public List<Class<?>> getClassesActiveColls()
	{
		return this.getClassesActive(null, Coll.class);
	}
	
	@Override
	public List<Class<?>> getClassesActiveNms()
	{
		List<Class<?>> ret = super.getClassesActiveNms();
		
		ret.remove(NmsBasics.class);
		ret.add(0, NmsBasics.class);
		
		return ret;
	}
	
	@Override
	public List<Class<?>> getClassesActiveMixins()
	{
		List<Class<?>> ret = super.getClassesActiveMixins();
		
		ret.remove(MixinEvent.class);
		ret.add(0, MixinEvent.class);
		
		return ret;
	}
	
	// -------------------------------------------- //
	// DISABLE
	// -------------------------------------------- //
	
	@Override
	public void onDisable()
	{
		super.onDisable();
		ModificationPollerLocal.get().interrupt();
		ModificationPollerRemote.get().interrupt();
		
		MassiveCoreTaskDeleteFiles.get().run();
		IdUtil.saveCachefileDatas();
	}
	
}
