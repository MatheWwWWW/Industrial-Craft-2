/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level;

public final class LightLayer
extends Enum<LightLayer> {
    public static final /* enum */ LightLayer SKY = new LightLayer(15);
    public static final /* enum */ LightLayer BLOCK = new LightLayer(0);
    public final int f_46967_;
    private static final /* synthetic */ LightLayer[] $VALUES;

    public static LightLayer[] values() {
        return (LightLayer[])$VALUES.clone();
    }

    public static LightLayer valueOf(String p_46975_) {
        return Enum.valueOf(LightLayer.class, p_46975_);
    }

    private LightLayer(int p_46973_) {
        this.f_46967_ = p_46973_;
    }

    private static /* synthetic */ LightLayer[] m_151586_() {
        return new LightLayer[]{SKY, BLOCK};
    }

    static {
        $VALUES = LightLayer.m_151586_();
    }
}

