/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class DripstoneThickness
extends Enum<DripstoneThickness>
implements StringRepresentable {
    public static final /* enum */ DripstoneThickness TIP_MERGE = new DripstoneThickness("tip_merge");
    public static final /* enum */ DripstoneThickness TIP = new DripstoneThickness("tip");
    public static final /* enum */ DripstoneThickness FRUSTUM = new DripstoneThickness("frustum");
    public static final /* enum */ DripstoneThickness MIDDLE = new DripstoneThickness("middle");
    public static final /* enum */ DripstoneThickness BASE = new DripstoneThickness("base");
    private final String f_156012_;
    private static final /* synthetic */ DripstoneThickness[] $VALUES;

    public static DripstoneThickness[] values() {
        return (DripstoneThickness[])$VALUES.clone();
    }

    public static DripstoneThickness valueOf(String p_156023_) {
        return Enum.valueOf(DripstoneThickness.class, p_156023_);
    }

    private DripstoneThickness(String p_156018_) {
        this.f_156012_ = p_156018_;
    }

    public String toString() {
        return this.f_156012_;
    }

    @Override
    public String m_7912_() {
        return this.f_156012_;
    }

    private static /* synthetic */ DripstoneThickness[] m_156019_() {
        return new DripstoneThickness[]{TIP_MERGE, TIP, FRUSTUM, MIDDLE, BASE};
    }

    static {
        $VALUES = DripstoneThickness.m_156019_();
    }
}

