package com.massivecraft.massivecore.adapter;

import com.massivecraft.massivecore.item.ConverterFromNamespacedKey;
import com.massivecraft.massivecore.item.ConverterToNamespacedKey;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonDeserializer;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonNull;
import com.massivecraft.massivecore.xlib.gson.JsonParseException;
import com.massivecraft.massivecore.xlib.gson.JsonPrimitive;
import com.massivecraft.massivecore.xlib.gson.JsonSerializationContext;
import com.massivecraft.massivecore.xlib.gson.JsonSerializer;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.Contract;

import java.lang.reflect.Type;

public class AdapterEnchantment implements JsonDeserializer<Enchantment>, JsonSerializer<Enchantment> {

    // -------------------------------------------- //
    // INSTANCE & CONSTRUCT
    // -------------------------------------------- //

    private static final AdapterEnchantment i = new AdapterEnchantment();
    @Contract(pure = true)
    public static AdapterEnchantment get() { return i; }

    // -------------------------------------------- //
    // OVERRIDE
    // -------------------------------------------- //

    @Override
    public JsonElement serialize(Enchantment src, Type typeOfSrc, JsonSerializationContext context)
    {
        if (src == null) return JsonNull.INSTANCE;
        return new JsonPrimitive(ConverterFromNamespacedKey.get().convert(src.getKey()));
    }

    @Override
    public Enchantment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json == null) return null;
        if (json.equals(JsonNull.INSTANCE)) return null;
        return Enchantment.getByKey(ConverterToNamespacedKey.get().convert(json.getAsString()));
    }
}
