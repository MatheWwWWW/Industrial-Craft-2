/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.item;

import net.minecraft.ChatFormatting;

public final class Rarity
extends Enum<Rarity> {
    public static final /* enum */ Rarity COMMON = new Rarity(ChatFormatting.WHITE);
    public static final /* enum */ Rarity UNCOMMON = new Rarity(ChatFormatting.YELLOW);
    public static final /* enum */ Rarity RARE = new Rarity(ChatFormatting.AQUA);
    public static final /* enum */ Rarity EPIC = new Rarity(ChatFormatting.LIGHT_PURPLE);
    public final ChatFormatting f_43022_;
    private static final /* synthetic */ Rarity[] $VALUES;

    public static Rarity[] values() {
        return (Rarity[])$VALUES.clone();
    }

    public static Rarity valueOf(String p_43030_) {
        return Enum.valueOf(Rarity.class, p_43030_);
    }

    private Rarity(ChatFormatting p_43028_) {
        this.f_43022_ = p_43028_;
    }

    private static /* synthetic */ Rarity[] m_151181_() {
        return new Rarity[]{COMMON, UNCOMMON, RARE, EPIC};
    }

    static {
        $VALUES = Rarity.m_151181_();
    }
}

