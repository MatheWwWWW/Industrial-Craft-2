/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity;

import net.minecraft.util.OptionEnum;

public final class HumanoidArm
extends Enum<HumanoidArm>
implements OptionEnum {
    public static final /* enum */ HumanoidArm LEFT = new HumanoidArm(0, "options.mainHand.left");
    public static final /* enum */ HumanoidArm RIGHT = new HumanoidArm(1, "options.mainHand.right");
    private final int f_217024_;
    private final String f_20821_;
    private static final /* synthetic */ HumanoidArm[] $VALUES;

    public static HumanoidArm[] values() {
        return (HumanoidArm[])$VALUES.clone();
    }

    public static HumanoidArm valueOf(String p_20832_) {
        return Enum.valueOf(HumanoidArm.class, p_20832_);
    }

    private HumanoidArm(int p_217028_, String p_217029_) {
        this.f_217024_ = p_217028_;
        this.f_20821_ = p_217029_;
    }

    public HumanoidArm m_20828_() {
        if (this == LEFT) {
            return RIGHT;
        }
        return LEFT;
    }

    @Override
    public int m_35965_() {
        return this.f_217024_;
    }

    @Override
    public String m_35968_() {
        return this.f_20821_;
    }

    private static /* synthetic */ HumanoidArm[] m_147131_() {
        return new HumanoidArm[]{LEFT, RIGHT};
    }

    static {
        $VALUES = HumanoidArm.m_147131_();
    }
}

