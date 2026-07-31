/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.animal.horse;

import java.util.Arrays;
import java.util.Comparator;

public final class Markings
extends Enum<Markings> {
    public static final /* enum */ Markings NONE = new Markings(0);
    public static final /* enum */ Markings WHITE = new Markings(1);
    public static final /* enum */ Markings WHITE_FIELD = new Markings(2);
    public static final /* enum */ Markings WHITE_DOTS = new Markings(3);
    public static final /* enum */ Markings BLACK_DOTS = new Markings(4);
    private static final Markings[] f_30861_;
    private final int f_30862_;
    private static final /* synthetic */ Markings[] $VALUES;

    public static Markings[] values() {
        return (Markings[])$VALUES.clone();
    }

    public static Markings valueOf(String p_30875_) {
        return Enum.valueOf(Markings.class, p_30875_);
    }

    private Markings(int p_30868_) {
        this.f_30862_ = p_30868_;
    }

    public int m_30869_() {
        return this.f_30862_;
    }

    public static Markings m_30870_(int p_30871_) {
        return f_30861_[p_30871_ % f_30861_.length];
    }

    private static /* synthetic */ Markings[] m_149547_() {
        return new Markings[]{NONE, WHITE, WHITE_FIELD, WHITE_DOTS, BLACK_DOTS};
    }

    static {
        $VALUES = Markings.m_149547_();
        f_30861_ = (Markings[])Arrays.stream(Markings.values()).sorted(Comparator.comparingInt(Markings::m_30869_)).toArray(Markings[]::new);
    }
}

