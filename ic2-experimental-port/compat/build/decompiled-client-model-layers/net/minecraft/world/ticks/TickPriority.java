/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.ticks;

public final class TickPriority
extends Enum<TickPriority> {
    public static final /* enum */ TickPriority EXTREMELY_HIGH = new TickPriority(-3);
    public static final /* enum */ TickPriority VERY_HIGH = new TickPriority(-2);
    public static final /* enum */ TickPriority HIGH = new TickPriority(-1);
    public static final /* enum */ TickPriority NORMAL = new TickPriority(0);
    public static final /* enum */ TickPriority LOW = new TickPriority(1);
    public static final /* enum */ TickPriority VERY_LOW = new TickPriority(2);
    public static final /* enum */ TickPriority EXTREMELY_LOW = new TickPriority(3);
    private final int f_193438_;
    private static final /* synthetic */ TickPriority[] $VALUES;

    public static TickPriority[] values() {
        return (TickPriority[])$VALUES.clone();
    }

    public static TickPriority valueOf(String p_193450_) {
        return Enum.valueOf(TickPriority.class, p_193450_);
    }

    private TickPriority(int p_193444_) {
        this.f_193438_ = p_193444_;
    }

    public static TickPriority m_193446_(int p_193447_) {
        for (TickPriority $$1 : TickPriority.values()) {
            if ($$1.f_193438_ != p_193447_) continue;
            return $$1;
        }
        if (p_193447_ < TickPriority.EXTREMELY_HIGH.f_193438_) {
            return EXTREMELY_HIGH;
        }
        return EXTREMELY_LOW;
    }

    public int m_193445_() {
        return this.f_193438_;
    }

    private static /* synthetic */ TickPriority[] m_193448_() {
        return new TickPriority[]{EXTREMELY_HIGH, VERY_HIGH, HIGH, NORMAL, LOW, VERY_LOW, EXTREMELY_LOW};
    }

    static {
        $VALUES = TickPriority.m_193448_();
    }
}

