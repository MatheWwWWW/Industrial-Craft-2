/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.border;

public final class BorderStatus
extends Enum<BorderStatus> {
    public static final /* enum */ BorderStatus GROWING = new BorderStatus(4259712);
    public static final /* enum */ BorderStatus SHRINKING = new BorderStatus(0xFF3030);
    public static final /* enum */ BorderStatus STATIONARY = new BorderStatus(2138367);
    private final int f_61894_;
    private static final /* synthetic */ BorderStatus[] $VALUES;

    public static BorderStatus[] values() {
        return (BorderStatus[])$VALUES.clone();
    }

    public static BorderStatus valueOf(String p_61903_) {
        return Enum.valueOf(BorderStatus.class, p_61903_);
    }

    private BorderStatus(int p_61900_) {
        this.f_61894_ = p_61900_;
    }

    public int m_61901_() {
        return this.f_61894_;
    }

    private static /* synthetic */ BorderStatus[] m_156091_() {
        return new BorderStatus[]{GROWING, SHRINKING, STATIONARY};
    }

    static {
        $VALUES = BorderStatus.m_156091_();
    }
}

