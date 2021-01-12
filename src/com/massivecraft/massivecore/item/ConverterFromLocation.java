package com.massivecraft.massivecore.item;

import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Location;

public class ConverterFromLocation extends Converter<Location, PS>
{
    // -------------------------------------------- //
    // INSTANCE & CONSTRUCT
    // -------------------------------------------- //

    private static final ConverterFromLocation i = new ConverterFromLocation();
    public static ConverterFromLocation get() { return i; }

    // -------------------------------------------- //
    // OVERRIDE
    // -------------------------------------------- //

    @Override
    public PS convert(Location location)
    {
        return PS.valueOf(location);
    }
}
