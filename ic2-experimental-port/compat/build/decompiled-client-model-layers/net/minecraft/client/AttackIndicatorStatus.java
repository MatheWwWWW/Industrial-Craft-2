/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.util.Mth;
import net.minecraft.util.OptionEnum;

public final class AttackIndicatorStatus
extends Enum<AttackIndicatorStatus>
implements OptionEnum {
    public static final /* enum */ AttackIndicatorStatus OFF = new AttackIndicatorStatus(0, "options.off");
    public static final /* enum */ AttackIndicatorStatus CROSSHAIR = new AttackIndicatorStatus(1, "options.attack.crosshair");
    public static final /* enum */ AttackIndicatorStatus HOTBAR = new AttackIndicatorStatus(2, "options.attack.hotbar");
    private static final AttackIndicatorStatus[] f_90498_;
    private final int f_90499_;
    private final String f_90500_;
    private static final /* synthetic */ AttackIndicatorStatus[] $VALUES;

    public static AttackIndicatorStatus[] values() {
        return (AttackIndicatorStatus[])$VALUES.clone();
    }

    public static AttackIndicatorStatus valueOf(String p_90515_) {
        return Enum.valueOf(AttackIndicatorStatus.class, p_90515_);
    }

    private AttackIndicatorStatus(int p_90506_, String p_90507_) {
        this.f_90499_ = p_90506_;
        this.f_90500_ = p_90507_;
    }

    @Override
    public int m_35965_() {
        return this.f_90499_;
    }

    @Override
    public String m_35968_() {
        return this.f_90500_;
    }

    public static AttackIndicatorStatus m_90509_(int p_90510_) {
        return f_90498_[Mth.m_14100_(p_90510_, f_90498_.length)];
    }

    private static /* synthetic */ AttackIndicatorStatus[] m_167682_() {
        return new AttackIndicatorStatus[]{OFF, CROSSHAIR, HOTBAR};
    }

    static {
        $VALUES = AttackIndicatorStatus.m_167682_();
        f_90498_ = (AttackIndicatorStatus[])Arrays.stream(AttackIndicatorStatus.values()).sorted(Comparator.comparingInt(AttackIndicatorStatus::m_35965_)).toArray(AttackIndicatorStatus[]::new);
    }
}

