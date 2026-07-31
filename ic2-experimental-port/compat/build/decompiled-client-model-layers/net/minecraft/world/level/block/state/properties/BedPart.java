/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class BedPart
extends Enum<BedPart>
implements StringRepresentable {
    public static final /* enum */ BedPart HEAD = new BedPart("head");
    public static final /* enum */ BedPart FOOT = new BedPart("foot");
    private final String f_61333_;
    private static final /* synthetic */ BedPart[] $VALUES;

    public static BedPart[] values() {
        return (BedPart[])$VALUES.clone();
    }

    public static BedPart valueOf(String p_61343_) {
        return Enum.valueOf(BedPart.class, p_61343_);
    }

    private BedPart(String p_61339_) {
        this.f_61333_ = p_61339_;
    }

    public String toString() {
        return this.f_61333_;
    }

    @Override
    public String m_7912_() {
        return this.f_61333_;
    }

    private static /* synthetic */ BedPart[] m_155975_() {
        return new BedPart[]{HEAD, FOOT};
    }

    static {
        $VALUES = BedPart.m_155975_();
    }
}

