/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class BambooLeaves
extends Enum<BambooLeaves>
implements StringRepresentable {
    public static final /* enum */ BambooLeaves NONE = new BambooLeaves("none");
    public static final /* enum */ BambooLeaves SMALL = new BambooLeaves("small");
    public static final /* enum */ BambooLeaves LARGE = new BambooLeaves("large");
    private final String f_61319_;
    private static final /* synthetic */ BambooLeaves[] $VALUES;

    public static BambooLeaves[] values() {
        return (BambooLeaves[])$VALUES.clone();
    }

    public static BambooLeaves valueOf(String p_61329_) {
        return Enum.valueOf(BambooLeaves.class, p_61329_);
    }

    private BambooLeaves(String p_61325_) {
        this.f_61319_ = p_61325_;
    }

    public String toString() {
        return this.f_61319_;
    }

    @Override
    public String m_7912_() {
        return this.f_61319_;
    }

    private static /* synthetic */ BambooLeaves[] m_155974_() {
        return new BambooLeaves[]{NONE, SMALL, LARGE};
    }

    static {
        $VALUES = BambooLeaves.m_155974_();
    }
}

