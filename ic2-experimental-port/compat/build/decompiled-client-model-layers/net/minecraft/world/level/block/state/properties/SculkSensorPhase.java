/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class SculkSensorPhase
extends Enum<SculkSensorPhase>
implements StringRepresentable {
    public static final /* enum */ SculkSensorPhase INACTIVE = new SculkSensorPhase("inactive");
    public static final /* enum */ SculkSensorPhase ACTIVE = new SculkSensorPhase("active");
    public static final /* enum */ SculkSensorPhase COOLDOWN = new SculkSensorPhase("cooldown");
    private final String f_156044_;
    private static final /* synthetic */ SculkSensorPhase[] $VALUES;

    public static SculkSensorPhase[] values() {
        return (SculkSensorPhase[])$VALUES.clone();
    }

    public static SculkSensorPhase valueOf(String p_156055_) {
        return Enum.valueOf(SculkSensorPhase.class, p_156055_);
    }

    private SculkSensorPhase(String p_156050_) {
        this.f_156044_ = p_156050_;
    }

    public String toString() {
        return this.f_156044_;
    }

    @Override
    public String m_7912_() {
        return this.f_156044_;
    }

    private static /* synthetic */ SculkSensorPhase[] m_156051_() {
        return new SculkSensorPhase[]{INACTIVE, ACTIVE, COOLDOWN};
    }

    static {
        $VALUES = SculkSensorPhase.m_156051_();
    }
}

