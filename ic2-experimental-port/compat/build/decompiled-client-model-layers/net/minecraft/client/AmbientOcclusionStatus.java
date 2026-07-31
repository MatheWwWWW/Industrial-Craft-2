/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.Mth;
import net.minecraft.util.OptionEnum;

public final class AmbientOcclusionStatus
extends Enum<AmbientOcclusionStatus>
implements OptionEnum {
    public static final /* enum */ AmbientOcclusionStatus OFF = new AmbientOcclusionStatus(0, "options.ao.off");
    public static final /* enum */ AmbientOcclusionStatus MIN = new AmbientOcclusionStatus(1, "options.ao.min");
    public static final /* enum */ AmbientOcclusionStatus MAX = new AmbientOcclusionStatus(2, "options.ao.max");
    private static final AmbientOcclusionStatus[] f_90476_;
    private final int f_90477_;
    private final String f_90478_;
    private static final /* synthetic */ AmbientOcclusionStatus[] $VALUES;

    public static AmbientOcclusionStatus[] values() {
        return (AmbientOcclusionStatus[])$VALUES.clone();
    }

    public static AmbientOcclusionStatus valueOf(String p_90493_) {
        return Enum.valueOf(AmbientOcclusionStatus.class, p_90493_);
    }

    private AmbientOcclusionStatus(int p_90484_, String p_90485_) {
        this.f_90477_ = p_90484_;
        this.f_90478_ = p_90485_;
    }

    @Override
    public int m_35965_() {
        return this.f_90477_;
    }

    @Override
    public String m_35968_() {
        return this.f_90478_;
    }

    public static AmbientOcclusionStatus m_90487_(int p_90488_) {
        return f_90476_[Mth.m_14100_(p_90488_, f_90476_.length)];
    }

    private static /* synthetic */ AmbientOcclusionStatus[] m_167681_() {
        return new AmbientOcclusionStatus[]{OFF, MIN, MAX};
    }

    static {
        $VALUES = AmbientOcclusionStatus.m_167681_();
        f_90476_ = (AmbientOcclusionStatus[])Arrays.stream(AmbientOcclusionStatus.values()).sorted(Comparator.comparingInt(AmbientOcclusionStatus::m_35965_)).toArray(AmbientOcclusionStatus[]::new);
    }
}

