/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.math;

import com.mojang.math.Matrix3f;
import java.util.Arrays;
import net.minecraft.Util;

public final class SymmetricGroup3
extends Enum<SymmetricGroup3> {
    public static final /* enum */ SymmetricGroup3 P123 = new SymmetricGroup3(0, 1, 2);
    public static final /* enum */ SymmetricGroup3 P213 = new SymmetricGroup3(1, 0, 2);
    public static final /* enum */ SymmetricGroup3 P132 = new SymmetricGroup3(0, 2, 1);
    public static final /* enum */ SymmetricGroup3 P231 = new SymmetricGroup3(1, 2, 0);
    public static final /* enum */ SymmetricGroup3 P312 = new SymmetricGroup3(2, 0, 1);
    public static final /* enum */ SymmetricGroup3 P321 = new SymmetricGroup3(2, 1, 0);
    private final int[] f_109168_;
    private final Matrix3f f_109169_;
    private static final int f_175574_ = 3;
    private static final SymmetricGroup3[][] f_109170_;
    private static final /* synthetic */ SymmetricGroup3[] $VALUES;

    public static SymmetricGroup3[] values() {
        return (SymmetricGroup3[])$VALUES.clone();
    }

    public static SymmetricGroup3 valueOf(String p_109190_) {
        return Enum.valueOf(SymmetricGroup3.class, p_109190_);
    }

    private SymmetricGroup3(int p_109176_, int p_109177_, int p_109178_) {
        this.f_109168_ = new int[]{p_109176_, p_109177_, p_109178_};
        this.f_109169_ = new Matrix3f();
        this.f_109169_.m_8165_(0, this.m_109180_(0), 1.0f);
        this.f_109169_.m_8165_(1, this.m_109180_(1), 1.0f);
        this.f_109169_.m_8165_(2, this.m_109180_(2), 1.0f);
    }

    public SymmetricGroup3 m_109182_(SymmetricGroup3 p_109183_) {
        return f_109170_[this.ordinal()][p_109183_.ordinal()];
    }

    public int m_109180_(int p_109181_) {
        return this.f_109168_[p_109181_];
    }

    public Matrix3f m_109179_() {
        return this.f_109169_;
    }

    private static /* synthetic */ SymmetricGroup3[] m_175578_() {
        return new SymmetricGroup3[]{P123, P213, P132, P231, P312, P321};
    }

    static {
        $VALUES = SymmetricGroup3.m_175578_();
        f_109170_ = Util.m_137469_(new SymmetricGroup3[SymmetricGroup3.values().length][SymmetricGroup3.values().length], p_109188_ -> {
            for (SymmetricGroup3 $$1 : SymmetricGroup3.values()) {
                for (SymmetricGroup3 $$2 : SymmetricGroup3.values()) {
                    SymmetricGroup3 $$5;
                    int[] $$3 = new int[3];
                    for (int $$4 = 0; $$4 < 3; ++$$4) {
                        $$3[$$4] = $$1.f_109168_[$$2.f_109168_[$$4]];
                    }
                    p_109188_[$$1.ordinal()][$$2.ordinal()] = $$5 = Arrays.stream(SymmetricGroup3.values()).filter(p_175577_ -> Arrays.equals(p_175577_.f_109168_, $$3)).findFirst().get();
                }
            }
        });
    }
}

