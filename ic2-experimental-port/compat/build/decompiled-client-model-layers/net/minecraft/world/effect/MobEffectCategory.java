/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.effect;

import net.minecraft.ChatFormatting;

public final class MobEffectCategory
extends Enum<MobEffectCategory> {
    public static final /* enum */ MobEffectCategory BENEFICIAL = new MobEffectCategory(ChatFormatting.BLUE);
    public static final /* enum */ MobEffectCategory HARMFUL = new MobEffectCategory(ChatFormatting.RED);
    public static final /* enum */ MobEffectCategory NEUTRAL = new MobEffectCategory(ChatFormatting.BLUE);
    private final ChatFormatting f_19490_;
    private static final /* synthetic */ MobEffectCategory[] $VALUES;

    public static MobEffectCategory[] values() {
        return (MobEffectCategory[])$VALUES.clone();
    }

    public static MobEffectCategory valueOf(String p_19499_) {
        return Enum.valueOf(MobEffectCategory.class, p_19499_);
    }

    private MobEffectCategory(ChatFormatting p_19496_) {
        this.f_19490_ = p_19496_;
    }

    public ChatFormatting m_19497_() {
        return this.f_19490_;
    }

    private static /* synthetic */ MobEffectCategory[] m_146709_() {
        return new MobEffectCategory[]{BENEFICIAL, HARMFUL, NEUTRAL};
    }

    static {
        $VALUES = MobEffectCategory.m_146709_();
    }
}

