package com.massivecraft.massivecore.command.type;

import com.massivecraft.massivecore.collections.BackstringSet;
import com.massivecraft.massivecore.collections.ExceptionSet;
import com.massivecraft.massivecore.collections.MassiveList;
import com.massivecraft.massivecore.collections.MassiveMap;
import com.massivecraft.massivecore.collections.WorldExceptionSet;
import com.massivecraft.massivecore.command.editor.annotation.EditorType;
import com.massivecraft.massivecore.command.editor.annotation.EditorTypeInner;
import com.massivecraft.massivecore.command.type.combined.TypeDataArmorTrim;
import com.massivecraft.massivecore.command.type.combined.TypeDataAttributeModifier;
import com.massivecraft.massivecore.command.type.combined.TypeDataBannerPattern;
import com.massivecraft.massivecore.command.type.combined.TypeDataFireworkEffect;
import com.massivecraft.massivecore.command.type.combined.TypeDataPotionEffect;
import com.massivecraft.massivecore.command.type.combined.TypeEntry;
import com.massivecraft.massivecore.command.type.combined.TypePotionEffectWrap;
import com.massivecraft.massivecore.command.type.combined.TypeSoundEffect;
import com.massivecraft.massivecore.command.type.container.TypeBackstringSet;
import com.massivecraft.massivecore.command.type.container.TypeExceptionSet;
import com.massivecraft.massivecore.command.type.container.TypeList;
import com.massivecraft.massivecore.command.type.container.TypeMap;
import com.massivecraft.massivecore.command.type.container.TypeSet;
import com.massivecraft.massivecore.command.type.enumeration.TypeAttribute;
import com.massivecraft.massivecore.command.type.enumeration.TypeAttributeModifierOperation;
import com.massivecraft.massivecore.command.type.enumeration.TypeAxolotlVariant;
import com.massivecraft.massivecore.command.type.enumeration.TypeBiome;
import com.massivecraft.massivecore.command.type.enumeration.TypeBookGeneration;
import com.massivecraft.massivecore.command.type.enumeration.TypeChatColor;
import com.massivecraft.massivecore.command.type.enumeration.TypeDecoratedPotSide;
import com.massivecraft.massivecore.command.type.enumeration.TypeDifficulty;
import com.massivecraft.massivecore.command.type.enumeration.TypeDyeColor;
import com.massivecraft.massivecore.command.type.enumeration.TypeEntityType;
import com.massivecraft.massivecore.command.type.enumeration.TypeEnvironment;
import com.massivecraft.massivecore.command.type.enumeration.TypeEquipmentSlot;
import com.massivecraft.massivecore.command.type.enumeration.TypeEventPriority;
import com.massivecraft.massivecore.command.type.enumeration.TypeFireworkEffectType;
import com.massivecraft.massivecore.command.type.enumeration.TypeGameMode;
import com.massivecraft.massivecore.command.type.enumeration.TypeHorseColor;
import com.massivecraft.massivecore.command.type.enumeration.TypeHorseStyle;
import com.massivecraft.massivecore.command.type.enumeration.TypeLlamaColor;
import com.massivecraft.massivecore.command.type.enumeration.TypeMaterial;
import com.massivecraft.massivecore.command.type.enumeration.TypeParrotVariant;
import com.massivecraft.massivecore.command.type.enumeration.TypeParticle;
import com.massivecraft.massivecore.command.type.enumeration.TypeRabbitType;
import com.massivecraft.massivecore.command.type.enumeration.TypeSound;
import com.massivecraft.massivecore.command.type.enumeration.TypeSpawnReason;
import com.massivecraft.massivecore.command.type.enumeration.TypeTropicalFishPattern;
import com.massivecraft.massivecore.command.type.enumeration.TypeVillagerProfession;
import com.massivecraft.massivecore.command.type.enumeration.TypeWorldType;
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanTrue;
import com.massivecraft.massivecore.command.type.primitive.TypeByte;
import com.massivecraft.massivecore.command.type.primitive.TypeDouble;
import com.massivecraft.massivecore.command.type.primitive.TypeFloat;
import com.massivecraft.massivecore.command.type.primitive.TypeInteger;
import com.massivecraft.massivecore.command.type.primitive.TypeLong;
import com.massivecraft.massivecore.command.type.primitive.TypeString;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import com.massivecraft.massivecore.command.type.sender.TypeSender;
import com.massivecraft.massivecore.command.type.store.TypeAspect;
import com.massivecraft.massivecore.command.type.store.TypeMultiverse;
import com.massivecraft.massivecore.util.ReflectionUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RegistryType
{
	// -------------------------------------------- //
	// REGISTRY
	// -------------------------------------------- //
	
	private static final Map<Class<?>, Type<?>> registry = new MassiveMap<>();
	
	@Contract("null, _ -> fail; !null, null -> fail")
	public static <T> void register(Class<T> clazz, Type<? super T> type)
	{
		if (clazz == null) throw new NullPointerException("clazz");
		if (type == null) throw new NullPointerException("type");
		registry.put(clazz, type);
	}
	
	@Contract("null -> fail")
	public static <T> void register(Type<T> type)
	{
		if (type == null) throw new NullPointerException("type");
		register(type.getClazz(), type);
	}
	
	@Contract("null -> fail")
	@SuppressWarnings("unchecked")
	public static <T> Type<? super T> unregister(Class<T> clazz)
	{
		if (clazz == null) throw new NullPointerException("clazz");
		return (Type<T>) registry.remove(clazz);
	}
	
	@Contract("null -> fail")
	public static boolean isRegistered(Class<?> clazz)
	{
		if (clazz == null) throw new NullPointerException("clazz");
		return registry.containsKey(clazz);
	}
	
	// -------------------------------------------- //
	// GET TYPE
	// -------------------------------------------- //
	
	@Contract("null, null, _ -> fail")
	public static @Nullable Type<?> getType(Field field, java.lang.reflect.Type fieldType, boolean strictThrow)
	{
		if (field != null)
		{
			try
			{
				EditorType annotationType = ReflectionUtil.getAnnotation(field, EditorType.class);
				if (annotationType != null)
				{
					Class<?> typeClass = annotationType.value();
					return ReflectionUtil.getSingletonInstance(typeClass);
				}
			}
			catch (Throwable t)
			{
				// This has to do with backwards compatibility (Usually 1.7).
				// The annotations may trigger creation of type class instances.
				// Those type classes may refer to Bukkit classes not present.
				// This issue was first encountered for TypeDataItemStack. 
			}
			
			if (fieldType == null)
			{
				fieldType = field.getGenericType();
			}
		}
		
		if (fieldType != null)
		{
			if (fieldType instanceof ParameterizedType)
			{
				Class<?> fieldClass = field == null ? null : field.getType();
				List<Type<?>> innerTypes;
				
				if (ReflectionUtil.isRawTypeAssignableFromAny(BackstringSet.class, fieldType, fieldClass))
				{
					innerTypes = getInnerTypes(field, fieldType, 1);
					return TypeBackstringSet.get((Type<? extends Enum>)innerTypes.get(0));
				}
				
				if (ReflectionUtil.isRawTypeAssignableFromAny(List.class, fieldType, fieldClass))
				{
					innerTypes = getInnerTypes(field, fieldType, 1);
					return TypeList.get(innerTypes.get(0));
				}
				
				if (ReflectionUtil.isRawTypeAssignableFromAny(Set.class, fieldType, fieldClass))
				{
					innerTypes = getInnerTypes(field, fieldType, 1);
					return TypeSet.get(innerTypes.get(0));
				}
				
				if (ReflectionUtil.isRawTypeAssignableFromAny(Map.Entry.class, fieldType, fieldClass))
				{
					innerTypes = getInnerTypes(field, fieldType, 2);
					return TypeEntry.get(innerTypes.get(0), innerTypes.get(1));
				}
				
				if (ReflectionUtil.isRawTypeAssignableFromAny(Map.class, fieldType, fieldClass))
				{
					innerTypes = getInnerTypes(field, fieldType, 2);
					return TypeMap.get(innerTypes.get(0), innerTypes.get(1));
				}
				
				if (strictThrow) throw new IllegalArgumentException("Unhandled ParameterizedType: " + fieldType);
				return null;
			}
			
			if (fieldType instanceof Class)
			{
				Type<?> type = registry.get(fieldType);
				if (strictThrow && type == null) throw new IllegalStateException(fieldType + " is not registered.");
				return type;
			}
			
			throw new IllegalArgumentException("Neither ParameterizedType nor Class: " + fieldType);
		}
		
		throw new IllegalArgumentException("No Information Supplied");
	}

	public static Type<?> getType(@NotNull Field field, boolean strictThrow)
	{
		return getType(field, null, strictThrow);
	}
	
	public static Type<?> getType(@NotNull java.lang.reflect.Type fieldType, boolean strictThrow)
	{
		return getType(null, fieldType, strictThrow);
	}
	
	public static Type<?> getType(@NotNull Field field)
	{
		return getType(field, true);
	}
	
	public static Type<?> getType(@NotNull java.lang.reflect.Type fieldType)
	{
		return getType(fieldType, true);
	}
	
	// -------------------------------------------- //
	// GET INNER TYPES
	// -------------------------------------------- //

	public static @NotNull List<Type<?>> getInnerTypes(Field field, java.lang.reflect.Type fieldType, int amountRequired)
	{
		// Annotation
		if (field != null)
		{
			try
			{
				EditorTypeInner annotation = ReflectionUtil.getAnnotation(field, EditorTypeInner.class);
				if (annotation != null)
				{
					// Create
					List<Type<?>> ret = new MassiveList<>();
					
					// Fill
					Class<?>[] innerTypeClasses = annotation.value();
					for (Class<?> innerTypeClass : innerTypeClasses)
					{
						Type<?> innerType = ReflectionUtil.getSingletonInstance(innerTypeClass);
						ret.add(innerType);
					}
					
					// Return
					return ret;
				}
			}
			catch (Throwable t)
			{
				// This has to do with backwards compatibility (Usually 1.7).
				// The annotations may trigger creation of type class instances.
				// Those type classes may refer to Bukkit classes not present.
				// This issue was first encountered for TypeDataItemStack. 
			}
			
			if (fieldType == null)
			{
				fieldType = field.getGenericType();
			}
		}
		
		// Reflection
		if (fieldType != null)
		{
			if (fieldType instanceof ParameterizedType parameterizedType)
			{
				// Create
				List<Type<?>> ret = new MassiveList<>();
				
				// Fill
				int count = 0;
				for (java.lang.reflect.Type actualTypeArgument : parameterizedType.getActualTypeArguments())
				{
					boolean strictThrow = (amountRequired < 0 || count < amountRequired);
					Type<?> innerType = getType(actualTypeArgument, strictThrow);
					ret.add(innerType);
					count++;
				}
				
				// Return
				return ret;
			}
			
			throw new IllegalArgumentException("Not ParameterizedType: " + fieldType);
		}
		
		throw new IllegalArgumentException("Failure");
	}
	
	// -------------------------------------------- //
	// DEFAULTS
	// -------------------------------------------- //

	public static void registerAll()
	{
		// Primitive
		register(Boolean.TYPE, TypeBooleanTrue.get());
		register(Boolean.class, TypeBooleanTrue.get());
		
		register(Byte.TYPE, TypeByte.get());
		register(Byte.class, TypeByte.get());
		
		register(Double.TYPE, TypeDouble.get());
		register(Double.class, TypeDouble.get());
		
		register(Float.TYPE, TypeFloat.get());
		register(Float.class, TypeFloat.get());
		
		register(Integer.TYPE, TypeInteger.get());
		register(Integer.class, TypeInteger.get());
		
		register(Long.TYPE, TypeLong.get());
		register(Long.class, TypeLong.get());
		
		register(TypeString.get());
		
		// Enum
		register(TypeAttribute.get());
		register(TypeAxolotlVariant.get());
		register(TypeBiome.get());
		register(TypeBookGeneration.get());
		register(TypeChatColor.get());
		register(TypeDecoratedPotSide.get());
		register(TypeDifficulty.get());
		register(TypeDyeColor.get());
		register(TypeEntityType.get());
		register(TypeEnvironment.get());
		register(TypeEquipmentSlot.get());
		register(TypeEventPriority.get());
		register(TypeFireworkEffectType.get());
		register(TypeGameMode.get());
		register(TypeHorseColor.get());
		register(TypeHorseStyle.get());
		register(TypeLlamaColor.get());
		register(TypeMaterial.get());
		register(TypeAttributeModifierOperation.get());
		register(TypeParrotVariant.get());
		register(TypeParticle.get());
		register(TypeRabbitType.get());
		register(TypeSound.get());
		register(TypeSpawnReason.get());
		register(TypeTropicalFishPattern.get());
		register(TypeVillagerProfession.get());
		register(TypeWorldType.get());
		
		register(TypeContainerGameProfileProperty.get());
		register(TypeContainerGameProfile.get());
		
		// Bukkit
		register(TypeDestination.get());
		register(TypeItemStack.get());
		register(TypeUUID.get());
		
		register(TypeNamespacedKey.get());
		register(TypePotionEffectType.get());
		register(TypeEnchantment.get());
		register(TypeDataArmorTrim.get());
		register(TypeDataAttributeModifier.get());
		register(TypeDataBannerPattern.get());
		register(TypeDataPotionEffect.get());
		register(TypeDataFireworkEffect.get());
		register(TypeDataItemStack.get());
		
		register(TypePermission.get());
		register(TypePS.get());
		register(TypeWorld.get());
		register(TypePotionEffectWrap.get());
		register(TypeSoundEffect.get());
		
		// Sender
		register(TypePlayer.get());
		register(TypeSender.get());
		
		// Store
		register(TypeAspect.get());
		register(TypeMultiverse.get());
		
		// Collection
		register(ExceptionSet.class, TypeExceptionSet.get());
		register(WorldExceptionSet.class, TypeExceptionSet.get());
	}
	
}
