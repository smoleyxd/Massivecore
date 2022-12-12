package com.massivecraft.massivecore.command.type.enumeration;

import com.massivecraft.massivecore.command.type.TypeAbstractChoice;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.block.banner.PatternType;

import java.util.Map;

public class TypePatternType extends TypeAbstractChoice<PatternType>
{
	// -------------------------------------------- //
	// INSTANCE & CONSTRUCT
	// -------------------------------------------- //
	
	private static final TypePatternType i = new TypePatternType();
	public static TypePatternType get() {return i;}
	public TypePatternType()
	{
		super(PatternType.class);
		
		this.setAll(TypeEnum.getEnumValues(getClazz()));
	}
	
	// -------------------------------------------- //
	// CONSTANTS
	// -------------------------------------------- //
	
	private final Map<PatternType, String> friendlyNames = MUtil.map(
		PatternType.BASE, "Field",
		PatternType.BORDER, "Bordure",
		PatternType.BRICKS, "FieldMasoned",
		PatternType.CIRCLE_MIDDLE, "Roundel",
		PatternType.CREEPER, "CreeperCharge",
		PatternType.CROSS, "Saltire",
		PatternType.CURLY_BORDER, "BordureIndented",
		PatternType.DIAGONAL_LEFT, "PerBendSinister",
		PatternType.DIAGONAL_LEFT_MIRROR, "PerBendInverted",
		PatternType.DIAGONAL_RIGHT, "PerBendSinisterInverted",
		PatternType.DIAGONAL_RIGHT_MIRROR, "PerBend",
		PatternType.FLOWER, "FlowerCharge",
		PatternType.GLOBE, "Globe",
		PatternType.GRADIENT, "Gradient",
		PatternType.GRADIENT_UP, "BaseGradient",
		PatternType.HALF_HORIZONTAL, "PerFess",
		PatternType.HALF_HORIZONTAL_MIRROR, "PerFessInverted",
		PatternType.HALF_VERTICAL, "PerPale",
		PatternType.HALF_VERTICAL_MIRROR, "PerPaleInverted",
		PatternType.MOJANG, "Thing",
		PatternType.PIGLIN, "Snout",
		PatternType.RHOMBUS_MIDDLE, "Lozenge",
		PatternType.SKULL, "SkullCharge",
		PatternType.SQUARE_BOTTOM_LEFT, "BaseDexterCanton",
		PatternType.SQUARE_BOTTOM_RIGHT, "BaseSinisterCanton",
		PatternType.SQUARE_TOP_LEFT, "ChiefDexterCanton",
		PatternType.SQUARE_TOP_RIGHT, "ChiefSinisterCanton",
		PatternType.STRAIGHT_CROSS, "Cross",
		PatternType.STRIPE_BOTTOM, "Base",
		PatternType.STRIPE_CENTER, "Pale",
		PatternType.STRIPE_DOWNLEFT, "BendSinister",
		PatternType.STRIPE_DOWNRIGHT, "Bend",
		PatternType.STRIPE_LEFT, "PaleDexter",
		PatternType.STRIPE_MIDDLE, "Fess",
		PatternType.STRIPE_RIGHT, "PaleSinister",
		PatternType.STRIPE_SMALL, "Paly",
		PatternType.STRIPE_TOP, "Chief",
		PatternType.TRIANGLE_BOTTOM, "Chevron",
		PatternType.TRIANGLE_TOP, "InvertedChevron",
		PatternType.TRIANGLES_BOTTOM, "BaseIndented",
		PatternType.TRIANGLES_TOP, "ChiefIndented"
	);
	
	public Map<PatternType, String> getFriendlyNames() {
		return friendlyNames;
	}
	
	// -------------------------------------------- //
	// OVERRIDE
	// -------------------------------------------- //
	
	@Override
	public Class<PatternType> getClazz() { return PatternType.class; }
	
	@Override
	public String getName()
	{
		return Txt.getNicedEnumString(getClazz().getSimpleName());
	}
	
	@Override
	public String getNameInner(PatternType value)
	{
		if (getFriendlyNames().containsKey(value)) return getFriendlyNames().get(value);
		return Txt.getNicedEnum(value);
	}
	
	@Override
	public String getIdInner(PatternType value)
	{
		return "SPIGOT_" + value.name();
	}
	
}
