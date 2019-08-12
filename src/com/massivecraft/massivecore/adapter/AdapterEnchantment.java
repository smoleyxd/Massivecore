package com.massivecraft.massivecore.adapter;

import com.massivecraft.massivecore.xlib.gson.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Type;

public class AdapterEnchantment implements JsonDeserializer<Enchantment>, JsonSerializer<Enchantment> {

    // -------------------------------------------- //
    // INSTANCE & CONSTRUCT
    // -------------------------------------------- //

    private static AdapterEnchantment i = new AdapterEnchantment();
    public static AdapterEnchantment get() { return i; }

    // -------------------------------------------- //
    // OVERRIDE
    // -------------------------------------------- //

    @Override
    public JsonElement serialize(Enchantment src, Type typeOfSrc, JsonSerializationContext context)
    {
        if (src == null) return JsonNull.INSTANCE;
        return new JsonPrimitive(src.getName());
    }

    @Override
    public Enchantment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json == null) return null;
        if (json.equals(JsonNull.INSTANCE)) return null;
        return Enchantment.getByName(json.getAsString());
    }
}
