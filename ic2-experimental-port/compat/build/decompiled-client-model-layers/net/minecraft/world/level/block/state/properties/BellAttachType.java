/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class BellAttachType
extends Enum<BellAttachType>
implements StringRepresentable {
    public static final /* enum */ BellAttachType FLOOR = new BellAttachType("floor");
    public static final /* enum */ BellAttachType CEILING = new BellAttachType("ceiling");
    public static final /* enum */ BellAttachType SINGLE_WALL = new BellAttachType("single_wall");
    public static final /* enum */ BellAttachType DOUBLE_WALL = new BellAttachType("double_wall");
    private final String f_61349_;
    private static final /* synthetic */ BellAttachType[] $VALUES;

    public static BellAttachType[] values() {
        return (BellAttachType[])$VALUES.clone();
    }

    public static BellAttachType valueOf(String p_61358_) {
        return Enum.valueOf(BellAttachType.class, p_61358_);
    }

    private BellAttachType(String p_61355_) {
        this.f_61349_ = p_61355_;
    }

    @Override
    public String m_7912_() {
        return this.f_61349_;
    }

    private static /* synthetic */ BellAttachType[] m_155976_() {
        return new BellAttachType[]{FLOOR, CEILING, SINGLE_WALL, DOUBLE_WALL};
    }

    static {
        $VALUES = BellAttachType.m_155976_();
    }
}

