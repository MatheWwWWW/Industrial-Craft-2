/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public final class DoorHingeSide
extends Enum<DoorHingeSide>
implements StringRepresentable {
    public static final /* enum */ DoorHingeSide LEFT = new DoorHingeSide();
    public static final /* enum */ DoorHingeSide RIGHT = new DoorHingeSide();
    private static final /* synthetic */ DoorHingeSide[] $VALUES;

    public static DoorHingeSide[] values() {
        return (DoorHingeSide[])$VALUES.clone();
    }

    public static DoorHingeSide valueOf(String p_61562_) {
        return Enum.valueOf(DoorHingeSide.class, p_61562_);
    }

    public String toString() {
        return this.m_7912_();
    }

    @Override
    public String m_7912_() {
        return this == LEFT ? "left" : "right";
    }

    private static /* synthetic */ DoorHingeSide[] m_156005_() {
        return new DoorHingeSide[]{LEFT, RIGHT};
    }

    static {
        $VALUES = DoorHingeSide.m_156005_();
    }
}

