/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.entity;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public final class MobCategory
extends Enum<MobCategory>
implements StringRepresentable {
    public static final /* enum */ MobCategory MONSTER = new MobCategory("monster", 70, false, false, 128);
    public static final /* enum */ MobCategory CREATURE = new MobCategory("creature", 10, true, true, 128);
    public static final /* enum */ MobCategory AMBIENT = new MobCategory("ambient", 15, true, false, 128);
    public static final /* enum */ MobCategory AXOLOTLS = new MobCategory("axolotls", 5, true, false, 128);
    public static final /* enum */ MobCategory UNDERGROUND_WATER_CREATURE = new MobCategory("underground_water_creature", 5, true, false, 128);
    public static final /* enum */ MobCategory WATER_CREATURE = new MobCategory("water_creature", 5, true, false, 128);
    public static final /* enum */ MobCategory WATER_AMBIENT = new MobCategory("water_ambient", 20, true, false, 64);
    public static final /* enum */ MobCategory MISC = new MobCategory("misc", -1, true, true, 128);
    public static final Codec<MobCategory> f_21584_;
    private final int f_21586_;
    private final boolean f_21587_;
    private final boolean f_21588_;
    private final String f_21589_;
    private final int f_21590_ = 32;
    private final int f_21591_;
    private static final /* synthetic */ MobCategory[] $VALUES;

    public static MobCategory[] values() {
        return (MobCategory[])$VALUES.clone();
    }

    public static MobCategory valueOf(String p_21614_) {
        return Enum.valueOf(MobCategory.class, p_21614_);
    }

    private MobCategory(String p_21597_, int p_21598_, boolean p_21599_, boolean p_21600_, int p_21601_) {
        this.f_21589_ = p_21597_;
        this.f_21586_ = p_21598_;
        this.f_21587_ = p_21599_;
        this.f_21588_ = p_21600_;
        this.f_21591_ = p_21601_;
    }

    public String m_21607_() {
        return this.f_21589_;
    }

    @Override
    public String m_7912_() {
        return this.f_21589_;
    }

    public int m_21608_() {
        return this.f_21586_;
    }

    public boolean m_21609_() {
        return this.f_21587_;
    }

    public boolean m_21610_() {
        return this.f_21588_;
    }

    public int m_21611_() {
        return this.f_21591_;
    }

    public int m_21612_() {
        return 32;
    }

    private static /* synthetic */ MobCategory[] m_147275_() {
        return new MobCategory[]{MONSTER, CREATURE, AMBIENT, AXOLOTLS, UNDERGROUND_WATER_CREATURE, WATER_CREATURE, WATER_AMBIENT, MISC};
    }

    static {
        $VALUES = MobCategory.m_147275_();
        f_21584_ = StringRepresentable.m_216439_(MobCategory::values);
    }
}

