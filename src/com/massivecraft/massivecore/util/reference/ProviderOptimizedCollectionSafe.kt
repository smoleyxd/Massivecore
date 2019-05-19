package com.massivecraft.massivecore.util.reference

import java.util.EnumSet

// MassiveTraits
// TODO TraitEatCarnivore
// TODO TraitEatHerbivore
// TODO TraitEatStone
// TODO TraitEatWaterBottle
// TODO Trait
// TODO TraitHarvester
// TODO TraitTruceUndead
// TODO TraitTruceSpider
// TODO TraitTruceWitch
// TODO TraitTruceGhast
// TODO TraitTruceSilme
// TODO TraitTruceSilverfish
// TODO TraitTruceUndead
// TODO TraitTruceCreeper
// TODO TraitTruceBlaze

// MassiveCore
// TODO MUtil: HARMFUL_POTION_EFFECTS
// TODO SignUtil: VALID_INTERACT_ACTIONS, isSign(Material material)
// TODO
// TODO
// https://docs.google.com/document/d/1m4Nzhgo6CdiTxVzu1gqVGyQ24xsLkSpHqK8yxb33kuw/edit
object ProviderOptimizedCollectionSafe {

    fun <E: Enum<E>> enumSetOf(clazz: Class<E>, vararg names: String): EnumSet<E> {
        val mapper = getEnumMapper(clazz)
        val ret = EnumSet.noneOf(clazz)
        return names.mapNotNullTo(ret, mapper)
    }

    // TODO memoize
    private fun <E: Enum<E>> getEnumMapper(clazz: Class<E>): DestringifierSafe<E> {
        val mapping = clazz.enumConstants.associateBy { constant -> constant.name }
        return fun(name: String): E? {
            return mapping[name]
        }
    }



    /*private inline fun <E: Any, S: Set<E>> setBuilderOptimized(
            mapper: (String) -> E?,
            setConstructor: (Iterable<E>) -> S,
            names: Iterable<String>
    ): S {
        return setConstructor.invoke(names.mapNotNull(mapper))
    }*/

}

typealias DestringifierSafe<E> = (String) -> E?
