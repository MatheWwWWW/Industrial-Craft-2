/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.animal.horse;

import java.util.Arrays;
import java.util.Comparator;

public final class Variant
extends Enum<Variant> {
    public static final /* enum */ Variant WHITE = new Variant(0);
    public static final /* enum */ Variant CREAMY = new Variant(1);
    public static final /* enum */ Variant CHESTNUT = new Variant(2);
    public static final /* enum */ Variant BROWN = new Variant(3);
    public static final /* enum */ Variant BLACK = new Variant(4);
    public static final /* enum */ Variant GRAY = new Variant(5);
    public static final /* enum */ Variant DARKBROWN = new Variant(6);
    private static final Variant[] f_30977_;
    private final int f_30978_;
    private static final /* synthetic */ Variant[] $VALUES;

    public static Variant[] values() {
        return (Variant[])$VALUES.clone();
    }

    public static Variant valueOf(String p_30991_) {
        return Enum.valueOf(Variant.class, p_30991_);
    }

    private Variant(int p_30984_) {
        this.f_30978_ = p_30984_;
    }

    public int m_30985_() {
        return this.f_30978_;
    }

    public static Variant m_30986_(int p_30987_) {
        return f_30977_[p_30987_ % f_30977_.length];
    }

    private static /* synthetic */ Variant[] m_149559_() {
        return new Variant[]{WHITE, CREAMY, CHESTNUT, BROWN, BLACK, GRAY, DARKBROWN};
    }

    static {
        $VALUES = Variant.m_149559_();
        f_30977_ = (Variant[])Arrays.stream(Variant.values()).sorted(Comparator.comparingInt(Variant::m_30985_)).toArray(Variant[]::new);
    }
}

