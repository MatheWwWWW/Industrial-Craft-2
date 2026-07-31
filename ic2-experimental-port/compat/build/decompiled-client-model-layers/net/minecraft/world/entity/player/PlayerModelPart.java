/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.player;

import net.minecraft.network.chat.Component;

public final class PlayerModelPart
extends Enum<PlayerModelPart> {
    public static final /* enum */ PlayerModelPart CAPE = new PlayerModelPart(0, "cape");
    public static final /* enum */ PlayerModelPart JACKET = new PlayerModelPart(1, "jacket");
    public static final /* enum */ PlayerModelPart LEFT_SLEEVE = new PlayerModelPart(2, "left_sleeve");
    public static final /* enum */ PlayerModelPart RIGHT_SLEEVE = new PlayerModelPart(3, "right_sleeve");
    public static final /* enum */ PlayerModelPart LEFT_PANTS_LEG = new PlayerModelPart(4, "left_pants_leg");
    public static final /* enum */ PlayerModelPart RIGHT_PANTS_LEG = new PlayerModelPart(5, "right_pants_leg");
    public static final /* enum */ PlayerModelPart HAT = new PlayerModelPart(6, "hat");
    private final int f_36434_;
    private final int f_36435_;
    private final String f_36436_;
    private final Component f_36437_;
    private static final /* synthetic */ PlayerModelPart[] $VALUES;

    public static PlayerModelPart[] values() {
        return (PlayerModelPart[])$VALUES.clone();
    }

    public static PlayerModelPart valueOf(String p_36449_) {
        return Enum.valueOf(PlayerModelPart.class, p_36449_);
    }

    private PlayerModelPart(int p_36443_, String p_36444_) {
        this.f_36434_ = p_36443_;
        this.f_36435_ = 1 << p_36443_;
        this.f_36436_ = p_36444_;
        this.f_36437_ = Component.m_237115_("options.modelPart." + p_36444_);
    }

    public int m_36445_() {
        return this.f_36435_;
    }

    public int m_150114_() {
        return this.f_36434_;
    }

    public String m_36446_() {
        return this.f_36436_;
    }

    public Component m_36447_() {
        return this.f_36437_;
    }

    private static /* synthetic */ PlayerModelPart[] m_150115_() {
        return new PlayerModelPart[]{CAPE, JACKET, LEFT_SLEEVE, RIGHT_SLEEVE, LEFT_PANTS_LEG, RIGHT_PANTS_LEG, HAT};
    }

    static {
        $VALUES = PlayerModelPart.m_150115_();
    }
}

