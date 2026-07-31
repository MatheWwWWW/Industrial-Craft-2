/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

final class BoatGoals
extends Enum<BoatGoals> {
    public static final /* enum */ BoatGoals GO_TO_BOAT = new BoatGoals();
    public static final /* enum */ BoatGoals GO_IN_BOAT_DIRECTION = new BoatGoals();
    private static final /* synthetic */ BoatGoals[] $VALUES;

    public static BoatGoals[] values() {
        return (BoatGoals[])$VALUES.clone();
    }

    public static BoatGoals valueOf(String p_25080_) {
        return Enum.valueOf(BoatGoals.class, p_25080_);
    }

    private static /* synthetic */ BoatGoals[] m_148079_() {
        return new BoatGoals[]{GO_TO_BOAT, GO_IN_BOAT_DIRECTION};
    }

    static {
        $VALUES = BoatGoals.m_148079_();
    }
}

