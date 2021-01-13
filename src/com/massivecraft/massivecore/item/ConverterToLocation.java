package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Location;

public class ConverterToLocation extends Converter<PS, Location>
{
    // -------------------------------------------- //
    // INSTANCE & CONSTRUCT
    // -------------------------------------------- //

    private static final ConverterToLocation i = new ConverterToLocation();
    public static ConverterToLocation get() { return i; }

    // -------------------------------------------- //
    // OVERRIDE
    // -------------------------------------------- //

    @Override
    public Location convert(PS ps) {
        if (ps == null) return null;
        return ps.asBukkitLocation();
    }
}
