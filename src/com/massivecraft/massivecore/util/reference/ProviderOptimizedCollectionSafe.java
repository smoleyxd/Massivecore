package com.massivecraft.massivecore.util.reference;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class ProviderOptimizedCollectionSafe {

    // For building an enum set based on a Class and names of the constants, but just skip elements that don't match
    public static <E extends Enum<E>> EnumSet<E> enumSetOf(Class<E> clazz, String... names) {
        Map<String, E> namesToElements = getNameToElementForEnum(clazz);
        EnumSet<E> ret = EnumSet.noneOf(clazz);
        for (String name : names) {
            E element = namesToElements.get(name);
            if (element != null) {
                ret.add(element);
            }
        }
        return ret;
    }

    // Creating this map can end up being much more efficient when we have to process large sets of names or operate on large enums
    // It prevents repeated iteration
    private static <E extends Enum<E>> Map<String, E> getNameToElementForEnum(Class<E> clazz) {
        E[] enumConstants = clazz.getEnumConstants();
        if (enumConstants == null) throw new IllegalArgumentException("clazz is not an Enum");
        Map<String, E> ret = new HashMap<>();
        for (E element : enumConstants) {
            ret.put(element.name(), element);
        }
        return ret;
    }
}
