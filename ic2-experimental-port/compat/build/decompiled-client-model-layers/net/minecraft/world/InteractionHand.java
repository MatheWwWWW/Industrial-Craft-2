/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world;

public final class InteractionHand
extends Enum<InteractionHand> {
    public static final /* enum */ InteractionHand MAIN_HAND = new InteractionHand();
    public static final /* enum */ InteractionHand OFF_HAND = new InteractionHand();
    private static final /* synthetic */ InteractionHand[] $VALUES;

    public static InteractionHand[] values() {
        return (InteractionHand[])$VALUES.clone();
    }

    public static InteractionHand valueOf(String p_19066_) {
        return Enum.valueOf(InteractionHand.class, p_19066_);
    }

    private static /* synthetic */ InteractionHand[] m_146650_() {
        return new InteractionHand[]{MAIN_HAND, OFF_HAND};
    }

    static {
        $VALUES = InteractionHand.m_146650_();
    }
}

