package com.massivecraft.massivecore.mixin;

import com.massivecraft.massivecore.util.MUtil;
import org.jetbrains.annotations.Contract;

public class MixinAlternateAccount extends Mixin {
    // -------------------------------------------- //
    // INSTANCE
    // -------------------------------------------- //

    private static MixinAlternateAccount d = new MixinAlternateAccount();
    private static MixinAlternateAccount i = d;
    @Contract(pure = true)
    public static MixinAlternateAccount get() { return i; }

    // -------------------------------------------- //
    // CONSTRUCT
    // -------------------------------------------- //

    public MixinAlternateAccount()
    {
    }

    // -------------------------------------------- //
    // METHODS
    // -------------------------------------------- //

    public boolean isAlternateAccount(Object senderObject)
    {
        return MUtil.isntPlayer(senderObject);
    }

    public boolean isAltAccount(Object senderObject)
    {
        return this.isAlternateAccount(senderObject);
    }
}
