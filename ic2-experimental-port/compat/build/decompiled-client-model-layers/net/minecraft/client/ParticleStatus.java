/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.Mth;
import net.minecraft.util.OptionEnum;

public final class ParticleStatus
extends Enum<ParticleStatus>
implements OptionEnum {
    public static final /* enum */ ParticleStatus ALL = new ParticleStatus(0, "options.particles.all");
    public static final /* enum */ ParticleStatus DECREASED = new ParticleStatus(1, "options.particles.decreased");
    public static final /* enum */ ParticleStatus MINIMAL = new ParticleStatus(2, "options.particles.minimal");
    private static final ParticleStatus[] f_92185_;
    private final int f_92186_;
    private final String f_92187_;
    private static final /* synthetic */ ParticleStatus[] $VALUES;

    public static ParticleStatus[] values() {
        return (ParticleStatus[])$VALUES.clone();
    }

    public static ParticleStatus valueOf(String p_92202_) {
        return Enum.valueOf(ParticleStatus.class, p_92202_);
    }

    private ParticleStatus(int p_92193_, String p_92194_) {
        this.f_92186_ = p_92193_;
        this.f_92187_ = p_92194_;
    }

    @Override
    public String m_35968_() {
        return this.f_92187_;
    }

    @Override
    public int m_35965_() {
        return this.f_92186_;
    }

    public static ParticleStatus m_92196_(int p_92197_) {
        return f_92185_[Mth.m_14100_(p_92197_, f_92185_.length)];
    }

    private static /* synthetic */ ParticleStatus[] m_168537_() {
        return new ParticleStatus[]{ALL, DECREASED, MINIMAL};
    }

    static {
        $VALUES = ParticleStatus.m_168537_();
        f_92185_ = (ParticleStatus[])Arrays.stream(ParticleStatus.values()).sorted(Comparator.comparingInt(ParticleStatus::m_35965_)).toArray(ParticleStatus[]::new);
    }
}

